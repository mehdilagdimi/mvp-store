package com.carrefour.mvp.shopping_discount.domain.Order;

import reactor.core.publisher.Mono;

public interface OrderRepository {
    Mono<OrderEntity> findById(OrderId orderId);
    Mono<OrderEntity> update(OrderEntity order);
}
