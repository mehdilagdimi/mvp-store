package com.carrefour.mvp.shopping_discount.domain.Order;

import java.util.Objects;
import java.util.UUID;

public record OrderItemId(UUID id) {
    public OrderItemId {
        Objects.requireNonNull(id);
    }

    public OrderItemId(){
        this(UUID.randomUUID());
    }
}

