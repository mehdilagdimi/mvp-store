package com.carrefour.mvp.shopping_discount.domain.discount;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.SequencedSet;

@Entity
public class DiscountEntity {
    @EmbeddedId
    private DiscountId id;
    private DiscountCode code;
    private BigDecimal percentage;
    @OneToMany(mappedBy = "discount")
    private SequencedSet<DiscountRestrictionEntity> discountRestrictions;

    public DiscountEntity(BigDecimal percentage, SequencedSet<DiscountRestrictionEntity> discountRestrictions) {
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.ONE) > 1) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 1");
        }
        this.id = new DiscountId();
        this.percentage = percentage;
        this.discountRestrictions = discountRestrictions;
    }

    public SequencedSet<DiscountRestrictionEntity> getDiscountRestrictions() {
        return discountRestrictions;
    }

    public BigDecimal getDiscountPercentage() {
        return percentage;
    }
}
