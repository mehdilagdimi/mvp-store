package com.carrefour.mvp.shopping_discount.domain.discount.rulesengine;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.Restriction;
import com.carrefour.mvp.shopping_discount.domain.discount.RestrictionType;

public interface DiscountRestrictionRule {
    boolean isSatisfied(OrderItemEntity item, RestrictionType restrictionType, Restriction discountRestriction);
}
