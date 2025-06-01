package com.carrefour.mvp.shopping_discount.domain.product;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ProductEntity {
    @Id
    private UUID id;
//    @Embedded
//    @AttributeOverride(name = "value", column = @Column(name = "category"))
    private String category;
//    @Embedded
    private String name;
    private BigDecimal price;

    ProductEntity(){}

    public ProductEntity(Category category, BigDecimal price, ProductName name) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(price);
        Objects.requireNonNull(name);
        this.id = new ProductId().id();
        this.category = category.value();
        this.price = price;
        this.name = name.name();
    }

    public Category getCategory() {
        return new Category( category );
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductName getProductName() {
        return new ProductName(name);
    }
}
