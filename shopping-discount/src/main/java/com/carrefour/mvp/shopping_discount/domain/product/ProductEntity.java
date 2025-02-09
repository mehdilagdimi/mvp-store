package com.carrefour.mvp.shopping_discount.domain.product;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ProductEntity {
    @EmbeddedId
    private ProductId id;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category"))
    private Category category;
    @Embedded
    private ProductName name;
    private BigDecimal price;

    public ProductEntity(Category category, BigDecimal price, ProductName name) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(price);
        Objects.requireNonNull(name);
        this.id = new ProductId();
        this.category = category;
        this.price = price;
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductName getProductName() {
        return name;
    }
}
