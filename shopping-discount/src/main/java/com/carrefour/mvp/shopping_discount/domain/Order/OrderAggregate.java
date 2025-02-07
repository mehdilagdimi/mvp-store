package com.carrefour.mvp.shopping_discount.domain.Order;

import com.carrefour.mvp.shopping_discount.domain.product.CategoryType;
import com.carrefour.mvp.shopping_discount.domain.product.ProductEntity;
import com.carrefour.mvp.shopping_discount.domain.product.ProductValObj;

import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.stream.Collectors;

public class OrderAggregate {
    private OrderAggregateId id;
    private OrderEntity orderEntity;

    private SequencedSet<ProductValObj> productValObjs;

    public OrderAggregate(OrderEntity orderEntity){
        this.id = new OrderAggregateId();
        this.orderEntity = orderEntity;
        this.productValObjs = this.constructProductValObjs(orderEntity.getOrderItems());
    }

    OrderAggregate applyDiscount(OrderAggregate orderAggregate) {
        return orderAggregate;
    }

    SequencedSet<ProductValObj> constructProductValObjs(SequencedSet<OrderItemEntity> orderItems){
        return orderItems
                .stream()
                .map( order -> mapToProductValObj( order.getProduct()) )
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private ProductValObj mapToProductValObj(ProductEntity productEntity){
        return
                new ProductValObj(
                        CategoryType.valueOf( productEntity.getCategory().value() ),
                        productEntity.getPrice());
    }
}
