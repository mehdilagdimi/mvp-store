package com.carrefour.mvp.shopping_discount.domain.discount;

import java.util.Objects;
import java.util.UUID;

public record DiscountId(UUID id) {

    public DiscountId {
        Objects.requireNonNull(id);
    }

    public DiscountId(){
        this(UUID.randomUUID());
    }
}
