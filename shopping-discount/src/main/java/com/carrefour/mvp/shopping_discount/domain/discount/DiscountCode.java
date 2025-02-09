package com.carrefour.mvp.shopping_discount.domain.discount;

import java.util.Objects;
import java.util.UUID;

public record DiscountCode(String code) {

    public DiscountCode {
        Objects.requireNonNull(code);
    }

    public DiscountCode(){
        this(UUID.randomUUID().toString().substring(0, 10).toUpperCase());
    }
}
