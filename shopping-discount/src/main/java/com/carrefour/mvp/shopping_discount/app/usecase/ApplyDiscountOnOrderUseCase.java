package com.carrefour.mvp.shopping_discount.app.usecase;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderAggregate;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderEntity;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderId;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyDiscountOnOrderUseCase implements ApplyDiscount {

    private final OrderRepository orderRepository;

    ApplyDiscountOnOrderUseCase(final OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderAggregate apply(UUID orderId) {
        OrderEntity orderEntity =
                orderRepository
                        .findById(new OrderId(orderId));
        return new OrderAggregate(orderEntity);
    }
}
