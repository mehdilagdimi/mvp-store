package com.carrefour.mvp.shopping_discount.domain.discount;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.rulesengine.DiscountRulesEngine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.SequencedSet;

public class DiscountAggregate{
    private final DiscountAggregateId id;
    private final Mono<DiscountEntity> discountEntity;
    private Boolean isDiscounted;

    public DiscountAggregate(Mono<DiscountEntity> discountEntity) {
        this.id = new DiscountAggregateId();
        this.discountEntity = discountEntity;
        this.isDiscounted = false;
    }

    public Mono<Boolean> applyDiscount(SequencedSet<OrderItemEntity> items ){
        final Mono<BigDecimal> discountPercentage = discountEntity.map(d -> d.getDiscountPercentage());

        return Mono.just(items)
                .flatMapMany(Flux::fromIterable)
                .flatMap(item ->  discountEntity.flatMap(d -> {
                        return Flux.fromIterable(d.getDiscountRestrictions())
                                .filter(restriction -> DiscountRulesEngine.applyRules(item, restriction))
                                .take(1)
                                .next()
                                .map(restriction -> true)
                                .defaultIfEmpty(false);
                    })
                    .map( isRestricted -> {
                            if (!isRestricted) {
                                apply(item, discountPercentage);
                            }
                            return this.isDiscounted;
                        }))
                .then(Mono.just(this.isDiscounted));
    }

    public void apply(OrderItemEntity item, Mono<BigDecimal> discountPercentage){
        this.isDiscounted = true;
        discountPercentage.map(percentage -> {
            BigDecimal discountedPrice =
                    item.getPrice()
                            .subtract( item.getPrice().multiply( percentage )).setScale(2, RoundingMode.HALF_UP);
            item.setDiscountedPrice( discountedPrice );
            item.flagAsDiscounted();
            return discountedPrice;
        });
    }

    void getDiscountPercentage(){};

    void getPriceDiff(){};

}
