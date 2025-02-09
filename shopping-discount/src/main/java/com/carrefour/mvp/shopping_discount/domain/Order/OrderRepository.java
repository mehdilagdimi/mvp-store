package com.carrefour.mvp.shopping_discount.domain.Order;

public interface OrderRepository {
    OrderEntity findById(OrderId orderId);
    OrderEntity save(OrderEntity order);
}
