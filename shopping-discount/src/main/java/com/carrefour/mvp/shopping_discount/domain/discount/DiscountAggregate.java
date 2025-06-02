package com.carrefour.mvp.shopping_discount.domain.discount;

import com.carrefour.mvp.shopping_discount.domain.order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.rulesengine.DiscountRulesEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.function.Predicate;

public class DiscountAggregate{
    private final DiscountAggregateId id;
    private final DiscountEntity discountEntity;
    private boolean isDiscountApplied;
    private static final Logger log = LoggerFactory.getLogger(DiscountAggregate.class);
    public DiscountAggregate(DiscountEntity discountEntity) {
        this.id = new DiscountAggregateId();
        this.discountEntity = discountEntity;
        this.isDiscountApplied = false;
    }

    public Mono<Boolean> applyDiscount(Set<OrderItemEntity> items ){
        final BigDecimal discountPercentage = discountEntity.getDiscountPercentage();

        return Flux.fromIterable(items)
                .filter(
                        item -> {
                            Predicate<DiscountRestrictionEntity> predic = (restriction) -> DiscountRulesEngine.applyRules(item, restriction);
                            boolean isRestricted = discountEntity.getDiscountRestrictions().stream().anyMatch(predic);
                            log.info("is Restricted ? {}", isRestricted);
                            if (!isRestricted) {
                                apply(item, discountPercentage);
                            }
                            return !isRestricted; })
                .doOnNext(item -> apply(item, discountPercentage))
                .hasElements();
    }

    public void apply(OrderItemEntity item, BigDecimal discountPercentage){
        BigDecimal discountedPrice =
                item.getPrice()
                        .subtract( item.getPrice().multiply( discountPercentage.movePointLeft(2) )).setScale(2, RoundingMode.HALF_UP);
        item.setDiscountedPrice( discountedPrice );
        item.flagAsDiscounted();
    }

    void getDiscountPercentage(){};

    void getPriceDiff(){};

}
