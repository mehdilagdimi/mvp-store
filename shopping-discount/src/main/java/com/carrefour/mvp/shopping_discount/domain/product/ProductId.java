package com.carrefour.mvp.shopping_discount.domain.product;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID id) {
    public ProductId {
        Objects.requireNonNull(id);
    }

    public ProductId(){
        this(UUID.randomUUID());
    }
}
