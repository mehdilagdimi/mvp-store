package com.carrefour.mvp.shopping_discount.domain.order;


import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID id) {
    public OrderId {
        Objects.requireNonNull(id);
    }

    public OrderId(){
        this(UUID.randomUUID());
    }
}

