package com.carrefour.mvp.shopping_discount.domain.discount;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Objects;
import java.util.SequencedSet;

@Entity
public class DiscountEntity {
    @EmbeddedId
    private DiscountId id;
    private Float percentage;
    @OneToMany(mappedBy = "discount")
    private SequencedSet<DiscountRestrictionEntity> discountRestriction;

    public DiscountEntity(Float percentage, SequencedSet<DiscountRestrictionEntity> discountRestriction) {
        Objects.requireNonNull(percentage);
        this.id = new DiscountId();
        this.percentage = percentage;
        this.discountRestriction = discountRestriction;
    }
}
