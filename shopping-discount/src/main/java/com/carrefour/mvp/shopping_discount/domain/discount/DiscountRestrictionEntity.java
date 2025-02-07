package com.carrefour.mvp.shopping_discount.domain.discount;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class DiscountRestrictionEntity {
    @EmbeddedId
    private DiscountRestrictionId id;
    @Enumerated(EnumType.STRING)
    private RestrictionType type;
    private String restriction;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    public DiscountRestrictionEntity(RestrictionType type, DiscountEntity discount, String restriction) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(discount);
        Objects.requireNonNull(restriction);
        this.id = new DiscountRestrictionId();
        this.type = type;
        this.discount = discount;
        this.restriction = restriction;
    }
}
