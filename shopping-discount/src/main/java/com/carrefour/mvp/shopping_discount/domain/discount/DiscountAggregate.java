package com.carrefour.mvp.shopping_discount.domain.discount;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.rulesengine.DiscountRulesEngine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.SequencedSet;

public class DiscountAggregate{
    private final DiscountAggregateId id;
    private final DiscountEntity discountEntity;
    private Boolean isDiscounted;

    public DiscountAggregate(DiscountEntity discountEntity) {
        this.id = new DiscountAggregateId();
        this.discountEntity = discountEntity;
        this.isDiscounted = false;
    }

    public boolean applyDiscount(SequencedSet<OrderItemEntity> items ){
        final BigDecimal discountPercentage = discountEntity.getDiscountPercentage();

        for(OrderItemEntity item : items){
            boolean isRestricted = false;
            for(DiscountRestrictionEntity restriction : discountEntity.getDiscountRestrictions()){
                if(DiscountRulesEngine.applyRules( item, restriction)){
                    isRestricted = true;
                    break;
                }
            }

            if(isRestricted) continue;
            apply(item, discountPercentage);
        }

        return this.isDiscounted;
    }

    public void apply(OrderItemEntity item, BigDecimal discountPercentage){
        this.isDiscounted = true;
        BigDecimal discountedPrice =
                item.getPrice()
                        .subtract( item.getPrice().multiply( discountPercentage )).setScale(2, RoundingMode.HALF_UP);

        item.setDiscountedPrice( discountedPrice );
        item.flagAsDiscounted();
    }

    void getDiscountPercentage(){};

    void getPriceDiff(){};

}
