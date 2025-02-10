package com.carrefour.mvp.shopping_discount.app.controller;


import com.carrefour.mvp.shopping_discount.app.usecase.ApplyDiscountOnOrderUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class OrderHandler {
    private final ApplyDiscountOnOrderUseCase applyDiscountOnOrderUseCase;

    public OrderHandler(ApplyDiscountOnOrderUseCase applyDiscountOnOrderUseCase) {
        this.applyDiscountOnOrderUseCase = applyDiscountOnOrderUseCase;
    }

    public Mono<ServerResponse> discountOrder(UUID id, String discountCode){
        return applyDiscountOnOrderUseCase
                .apply(id, discountCode)
                .flatMap(order ->
                        ServerResponse.ok().body(BodyInserters.fromValue(order)))
                        .switchIfEmpty(ServerResponse.notFound().build());
    }
}
