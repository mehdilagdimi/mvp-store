package com.carrefour.mvp.shopping_discount.domain.discount.rulesengine;

import com.carrefour.mvp.shopping_discount.domain.order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.Restriction;
import com.carrefour.mvp.shopping_discount.domain.discount.RestrictionType;

import java.math.BigDecimal;

public class MinimumPriceRestrictionRule implements DiscountRestrictionRule {

    @Override
    public boolean isSatisfied(OrderItemEntity item, RestrictionType restrictionType, Restriction discountRestriction) {
        return
                RestrictionType.PRICE.equals(restrictionType)
                        &&  item.getPrice().compareTo( new BigDecimal( discountRestriction.value() )) < 0;
    }
}
