package com.carrefour.mvp.shopping_discount.domain.customer;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID id) {

    public CustomerId {
        Objects.requireNonNull(id);
    }

    public CustomerId(){
        this(UUID.randomUUID());
    }
}
