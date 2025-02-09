package com.carrefour.mvp.shopping_discount.domain.product;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductValObj(
        ProductName productName,
        BigDecimal price,
        Integer quantity,
        Boolean isDiscounted) {

    public ProductValObj {
        Objects.requireNonNull(productName);
        Objects.requireNonNull(price);
        Objects.requireNonNull(quantity);
        Objects.requireNonNull(isDiscounted);
    }
}
