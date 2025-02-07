package com.carrefour.mvp.shopping_discount.domain.product;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ProductEntity {
    @EmbeddedId
    private ProductId id;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category"))
    private Category category;
    private Float price;

    public ProductEntity(Category category, Float price) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(price);
        this.id = new ProductId();
        this.category = category;
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public Float getPrice() {
        return price;
    }
}
