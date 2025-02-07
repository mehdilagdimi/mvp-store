package com.carrefour.mvp.shopping_discount.domain.discount;

public class DiscountAggregate{
    private DiscountAggregateId id;
    private DiscountEntity discountEntity;

    void applyDiscount(){}

    void getDiscountPercentage(){};

    void getPriceDiff(){};
}
