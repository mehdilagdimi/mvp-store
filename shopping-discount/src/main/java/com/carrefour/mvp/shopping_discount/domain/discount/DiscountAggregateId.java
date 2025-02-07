package com.carrefour.mvp.shopping_discount.domain.discount;

import java.util.Objects;
import java.util.UUID;

public record DiscountAggregateId (UUID id){
    public DiscountAggregateId {
        Objects.requireNonNull(id);
    }

    public DiscountAggregateId(){
        this(UUID.randomUUID());
    }
}
