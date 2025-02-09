package com.carrefour.mvp.shopping_discount.infra.spring;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderEntity;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderId;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderRepository;
import org.springframework.stereotype.Service;


@Service
public class OrderRepositoryJpa implements OrderRepository {
    private final OrderRepositorySpringDataJpa orderRepositorySpringDataJpa;

    public OrderRepositoryJpa(OrderRepositorySpringDataJpa orderRepositorySpringDataJpa){
        this.orderRepositorySpringDataJpa = orderRepositorySpringDataJpa;
    }

    @Override
    public OrderEntity findById(OrderId orderId) {
        return orderRepositorySpringDataJpa.findById(orderId).orElseThrow();
    }

    @Override
    public OrderEntity save(OrderEntity order) {
        return orderRepositorySpringDataJpa.save(order);
    }
}
