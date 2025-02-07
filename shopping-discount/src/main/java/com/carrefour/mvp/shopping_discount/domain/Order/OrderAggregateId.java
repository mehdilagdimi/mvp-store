package com.carrefour.mvp.shopping_discount.domain.Order;

import java.util.Objects;
import java.util.UUID;

public record OrderAggregateId(UUID id) {
    public OrderAggregateId {
        Objects.requireNonNull(id);
    }

    public OrderAggregateId(){
        this(UUID.randomUUID());
    }
}

