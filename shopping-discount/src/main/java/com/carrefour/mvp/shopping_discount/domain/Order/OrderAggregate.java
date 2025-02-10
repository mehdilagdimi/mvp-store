package com.carrefour.mvp.shopping_discount.domain.Order;

import com.carrefour.mvp.shopping_discount.domain.discount.DiscountAggregate;
import com.carrefour.mvp.shopping_discount.domain.product.ProductValObj;
import com.fasterxml.jackson.annotation.JsonIgnore;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.SequencedSet;
import java.util.stream.Collectors;

public class OrderAggregate {
    private final OrderAggregateId id;
    @JsonIgnore
    private final Mono<OrderEntity> orderEntity;
    @JsonIgnore
    private final DiscountAggregate discountAggregate;
    private SequencedSet<ProductValObj> productValObjs;
    private Boolean isDiscounted;

    public OrderAggregate(Mono<OrderEntity> orderEntity, DiscountAggregate discountAggregate){
        Objects.requireNonNull(orderEntity);
        this.id = new OrderAggregateId();
        this.orderEntity = orderEntity;
        this.discountAggregate = discountAggregate;
        this.productValObjs = constructProductValObjs();
        this.isDiscounted = false;
    }

    public Mono<OrderAggregate> applyDiscount() {
           return orderEntity.
                    flatMap(order ->
                            discountAggregate.applyDiscount(order.getOrderItems())
                                    .flatMap(isDiscounted -> {
                                        this.isDiscounted = isDiscounted;
                                        return
                                                Mono.fromCallable(() -> constructProductValObjs())
                                                        .map(products -> {
                                                            this.productValObjs = products;
                                                            return this;
                                                        });

                                    })
                    );
    }

    SequencedSet<ProductValObj> constructProductValObjs(){
        return orderEntity
                .map(order ->
                     order.getOrderItems()
                            .stream()
                            .map( item -> mapToProductValObj( item ) )
                            .collect(Collectors.toCollection(LinkedHashSet::new)))
                .block();

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

    public Boolean wasOrderDiscounted() {
        return isDiscounted;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity.block();
    }
}
