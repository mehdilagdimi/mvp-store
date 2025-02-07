package com.carrefour.mvp.shopping_discount.domain.product;

public interface ProductRepository {
    ProductEntity findById(ProductId id);
}
