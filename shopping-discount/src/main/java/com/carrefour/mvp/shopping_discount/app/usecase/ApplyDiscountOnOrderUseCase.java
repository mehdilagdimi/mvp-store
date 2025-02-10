package com.carrefour.mvp.shopping_discount.app.usecase;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderAggregate;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderEntity;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderId;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderRepository;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountAggregate;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ApplyDiscountOnOrderUseCase implements ApplyDiscount {

    private final OrderRepository orderRepository;
    private final DiscountRepository discountRepository;

    ApplyDiscountOnOrderUseCase(final OrderRepository orderRepository, final  DiscountRepository discountRepository){
        this.orderRepository = orderRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    public Mono<OrderAggregate> apply(UUID orderId, String discountCode) {
        OrderAggregate orderAggregate = getOrderAggregate(new OrderId(orderId), discountCode);
        return orderAggregate
                .applyDiscount()
                .doOnSuccess(value -> {
                        if(value.wasOrderDiscounted()){
                            orderRepository.update(orderAggregate.getOrderEntity());
                        }
                })
                .then(Mono.just(orderAggregate));
    }

    private OrderAggregate getOrderAggregate(OrderId orderId, String discountCode){
        Mono<OrderEntity> orderEntity =
                orderRepository
                        .findById(orderId);
        Mono<DiscountEntity> discountEntity =
                discountRepository.findByCode(discountCode);

        DiscountAggregate discountAggregate = new DiscountAggregate(discountEntity);

        return new OrderAggregate(orderEntity, discountAggregate);
    }
}
