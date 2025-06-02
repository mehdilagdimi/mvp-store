package com.carrefour.mvp.shopping_discount.domain.discount;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
public class DiscountEntity {
    @Id
    private UUID id;
    private String code;
    private BigDecimal percentage;
    @OneToMany(mappedBy = "discount")
    private Set<DiscountRestrictionEntity> discountRestrictions;

    DiscountEntity () {};

    public DiscountEntity(BigDecimal percentage, Set<DiscountRestrictionEntity> discountRestrictions) {
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.ONE) > 1) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 1");
        }
        this.id = new DiscountId().id();
        this.percentage = percentage;
        this.discountRestrictions = discountRestrictions;
    }

    public Set<DiscountRestrictionEntity> getDiscountRestrictions() {
        return discountRestrictions;
    }

    public BigDecimal getDiscountPercentage() {
        return percentage;
    }
}
