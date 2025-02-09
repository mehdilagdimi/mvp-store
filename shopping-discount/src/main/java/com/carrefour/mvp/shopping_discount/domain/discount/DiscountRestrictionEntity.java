package com.carrefour.mvp.shopping_discount.domain.discount;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class DiscountRestrictionEntity {
    @EmbeddedId
    private DiscountRestrictionId id;
    @Enumerated(EnumType.STRING)
    private RestrictionType type;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "restriction"))
    private Restriction restriction;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    public DiscountRestrictionEntity(RestrictionType type, DiscountEntity discount, Restriction restriction) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(discount);
        Objects.requireNonNull(restriction);
        this.id = new DiscountRestrictionId();
        this.type = type;
        this.discount = discount;
        this.restriction = restriction;
    }

    public RestrictionType getRestrictionType() {
        return type;
    }

    public Restriction getRestriction() {
        return restriction;
    }
}
