package com.carrefour.mvp.shopping_discount.domain.discount.rulesengine;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.Restriction;
import com.carrefour.mvp.shopping_discount.domain.discount.RestrictionType;

public class CategoryRestrictionRule implements DiscountRestrictionRule {

    @Override
    public boolean isSatisfied(OrderItemEntity item, RestrictionType restrictionType, Restriction discountRestriction) {
        return RestrictionType.CATEGORY.equals(restrictionType) && discountRestriction.value().equals( item.getProduct().getCategory().value() );
    }
}
