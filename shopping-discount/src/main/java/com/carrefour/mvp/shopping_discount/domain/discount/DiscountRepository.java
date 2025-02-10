package com.carrefour.mvp.shopping_discount.domain.discount;

import reactor.core.publisher.Mono;

public interface DiscountRepository {
    Mono<DiscountEntity> findByCode(String code);
}
