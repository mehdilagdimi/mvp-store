package com.carrefour.mvp.shopping_discount.domain.Order;

import com.carrefour.mvp.shopping_discount.app.usecase.ApplyDiscountOnOrderUseCase;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountAggregate;
import com.carrefour.mvp.shopping_discount.domain.product.ProductValObj;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.SequencedSet;
import java.util.stream.Collectors;

public class OrderAggregate {
    private final OrderAggregateId id;
    @JsonIgnore
    private final OrderEntity orderEntity;
    @JsonIgnore
    private final DiscountAggregate discountAggregate;
    private SequencedSet<ProductValObj> productValObjs;
    private boolean isDiscounted;
    static Logger log = LoggerFactory.getLogger(ApplyDiscountOnOrderUseCase.class);
    public OrderAggregate(OrderEntity orderEntity, DiscountAggregate discountAggregate){
        Objects.requireNonNull(orderEntity);
        this.id = new OrderAggregateId();
        this.orderEntity = orderEntity;
        this.discountAggregate = discountAggregate;
        this.isDiscounted = false;
       this.productValObjs = constructProductValObjs();
        log.info("productValObjs at construc : {}", productValObjs);
    }

    public Mono<OrderAggregate> applyDiscount() {
           return discountAggregate.applyDiscount(orderEntity.getOrderItems())
                                    .map(isDiscounted -> {
                                        this.isDiscounted = isDiscounted;
                                        if(this.isDiscounted){
                                            this.productValObjs = constructProductValObjs();
                                        }
                                        return this;
                                    });
    }

    SequencedSet<ProductValObj> constructProductValObjs(){
        return
                 orderEntity.getOrderItems()
                            .stream()
                            .map( item -> mapToProductValObj( item ) )
                            .collect(Collectors.toCollection(LinkedHashSet::new));

    }

    private ProductValObj mapToProductValObj(OrderItemEntity item){
        return
                new ProductValObj(
                        item.getItemName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getIsDiscounted());
    }

    public SequencedSet<ProductValObj> getItems(){
        return this.productValObjs;
    }

    public boolean wasOrderDiscounted() {
        return isDiscounted;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }
}
