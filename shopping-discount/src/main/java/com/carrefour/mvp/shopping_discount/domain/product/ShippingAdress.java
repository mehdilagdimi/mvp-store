package com.carrefour.mvp.shopping_discount.domain.product;

import java.util.Objects;

public record ShippingAdress(String value) {
    public ShippingAdress {
        Objects.requireNonNull(value);
        //more validation if possible
    }
}
