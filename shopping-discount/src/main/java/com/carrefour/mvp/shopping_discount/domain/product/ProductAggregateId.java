package com.carrefour.mvp.shopping_discount.domain.product;

import java.util.Objects;
import java.util.UUID;

public record ProductAggregateId(UUID id){
    public ProductAggregateId {
        Objects.requireNonNull(id);
    }

    public ProductAggregateId(){
        this(UUID.randomUUID());
    }
}
