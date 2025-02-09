package com.carrefour.mvp.shopping_discount.domain.discount.rulesengine;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountRestrictionEntity;

import java.util.LinkedHashSet;
import java.util.SequencedSet;

public class DiscountRulesEngine {
    public static final SequencedSet<DiscountRestrictionRule> rules = new LinkedHashSet<>();

    static {
        rules.add(new CategoryRestrictionRule());
        rules.add(new MinimumPriceRestrictionRule());
    }

    public static boolean applyRules(OrderItemEntity item, DiscountRestrictionEntity discountRestriction){
        for(DiscountRestrictionRule rule : rules){
            if(rule.isSatisfied(item, discountRestriction.getRestrictionType(), discountRestriction.getRestriction())){
                return true;
            }
        }
        return false;
    }


}
