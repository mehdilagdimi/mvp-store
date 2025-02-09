package com.carrefour.mvp.shopping_discount.domain.Order;

import com.carrefour.mvp.shopping_discount.domain.discount.DiscountAggregate;
import com.carrefour.mvp.shopping_discount.domain.product.ProductValObj;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.SequencedSet;
import java.util.stream.Collectors;

public class OrderAggregate {
    private final OrderAggregateId id;
    private final OrderEntity orderEntity;
    private final DiscountAggregate discountAggregate;
    private SequencedSet<ProductValObj> productValObjs;
    private Boolean isDiscounted;

    public OrderAggregate(OrderEntity orderEntity, DiscountAggregate discountAggregate){
        Objects.requireNonNull(orderEntity);
        this.id = new OrderAggregateId();
        this.orderEntity = orderEntity;
        this.discountAggregate = discountAggregate;
        this.productValObjs = constructProductValObjs();
        this.isDiscounted = false;
    }

    public OrderAggregate applyDiscount() {
        this.isDiscounted = discountAggregate.applyDiscount(orderEntity.getOrderItems());
        if(this.isDiscounted){
            this.productValObjs = constructProductValObjs();
        }
        return this;
    }

    SequencedSet<ProductValObj> constructProductValObjs(){
        return orderEntity
                .getOrderItems()
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

    public Boolean wasOrderDiscounted() {
        return isDiscounted;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }
}
