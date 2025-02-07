package com.carrefour.mvp.shopping_discount.domain.discount;

import java.util.Objects;
import java.util.UUID;

public record DiscountRestrictionId(UUID id){
    public DiscountRestrictionId {
        Objects.requireNonNull(id);
    }

    public DiscountRestrictionId(){
        this(UUID.randomUUID());
    }
}
