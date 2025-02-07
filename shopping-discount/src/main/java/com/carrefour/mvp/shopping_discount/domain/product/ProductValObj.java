package com.carrefour.mvp.shopping_discount.domain.product;

import java.util.Objects;

public record ProductValObj(
        CategoryType category,
        Float price) {

    public ProductValObj {
        Objects.requireNonNull(category);
        Objects.requireNonNull(price);
    }
}
