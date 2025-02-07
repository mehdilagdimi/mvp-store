package com.carrefour.mvp.shopping_discount.domain.discount;

public interface DiscountRepository {
    DiscountEntity findById(DiscountId id);
}
