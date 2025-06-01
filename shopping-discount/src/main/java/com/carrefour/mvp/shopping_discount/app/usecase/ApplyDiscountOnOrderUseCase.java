package com.carrefour.mvp.shopping_discount.app.usecase;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderAggregate;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderEntity;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderId;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderRepository;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountAggregate;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ApplyDiscountOnOrderUseCase implements ApplyDiscount {

    private final OrderRepository orderRepository;
    private final DiscountRepository discountRepository;
    static Logger log = LoggerFactory.getLogger(ApplyDiscountOnOrderUseCase.class);

    ApplyDiscountOnOrderUseCase(final OrderRepository orderRepository, final  DiscountRepository discountRepository){
        this.orderRepository = orderRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    public Mono<OrderAggregate> apply(UUID orderId, String discountCode) {
        return getOrderAggregate(new OrderId(orderId), discountCode)
                .flatMap(aggre ->
                    aggre.applyDiscount()
                            .flatMap(updatedAggr -> {
                                        if(updatedAggr.wasOrderDiscounted()){
                                            orderRepository.update(updatedAggr.getOrderEntity());
                                        }
                                        return Mono.just(updatedAggr);
                            })
                );
    }

    private Mono<OrderAggregate> getOrderAggregate(OrderId orderId, String discountCode){
        Mono<OrderEntity> orderEntity =
                orderRepository
                        .findByIdWithItems(orderId);
        Mono<DiscountEntity> discountEntity =
                discountRepository.findByCode(discountCode);

        return Mono
                .zip(orderEntity, discountEntity)
                .map(t -> {
                    OrderEntity order = t.getT1();
                    DiscountEntity discount = t.getT2();

                    log.info("Order in : {}", order);
                    log.info("discount in : {}", discount );
                    return new OrderAggregate(order, new DiscountAggregate(discount));
                });

    }
}
