package com.carrefour.mvp.shopping_discount.domain.product;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record ProductName(String name) {
    private static final String PRODUCT_NAME_REGEX = "^[A-Za-z0-9][A-Za-z0-9\\s\\-_\\.]{2,100}[A-Za-z0-9]$";

    public ProductName {
        Objects.requireNonNull(name);

        Matcher matcher = Pattern.compile(PRODUCT_NAME_REGEX).matcher(name);

        if(name.isBlank() || !matcher.matches()){
                throw new IllegalArgumentException("Invalid Product Name");
        }
    }

}
