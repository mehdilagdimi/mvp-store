package com.carrefour.mvp.shopping_discount.domain.product;

public record Category(String value) {
    public Category {
        if(!isValidCategory(value)){
            throw new RuntimeException("Invalid Category");
        }
    }

    private boolean isValidCategory(String value){
        try{
            CategoryType.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
