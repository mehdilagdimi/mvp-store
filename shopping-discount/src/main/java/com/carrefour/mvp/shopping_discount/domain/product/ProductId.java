package com.carrefour.mvp.shopping_discount.domain.product;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public record ProductId(UUID id) {
    public ProductId {
        Objects.requireNonNull(id);
    }

    public ProductId(){
        this(UUID.randomUUID());
    }
}
