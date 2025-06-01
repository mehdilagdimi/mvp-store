package com.carrefour.mvp.shopping_discount.domain.Order;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderRepository {
    Mono<OrderEntity> findById(UUID orderId);
    Mono<OrderEntity> findById(OrderId orderId);
    Mono<OrderEntity> findByIdWithItems(OrderId orderId);
    Mono<OrderEntity> update(OrderEntity order);
}
