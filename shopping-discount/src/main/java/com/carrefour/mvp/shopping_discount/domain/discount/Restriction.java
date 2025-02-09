package com.carrefour.mvp.shopping_discount.domain.discount;

import java.util.Objects;

public record Restriction(String value) {

    public Restriction {
        Objects.requireNonNull(value);
    }

}
