package com.carrefour.mvp.shopping_discount.domain.discount.exception;

public class DiscountNotFoundException extends RuntimeException{
    public DiscountNotFoundException(String code){
        super("Discount not found with code :" + code);
    }
}
