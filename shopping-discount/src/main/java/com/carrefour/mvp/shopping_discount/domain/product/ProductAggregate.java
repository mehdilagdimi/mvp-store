package com.carrefour.mvp.shopping_discount.domain.product;

public class ProductAggregate {
    private ProductAggregateId id;
    private ProductEntity product;

    public ProductAggregate(ProductEntity product) {
        this.id = new ProductAggregateId();
        this.product = product;
    }
}
