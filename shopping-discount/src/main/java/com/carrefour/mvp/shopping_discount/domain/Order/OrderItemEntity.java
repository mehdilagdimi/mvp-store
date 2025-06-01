package com.carrefour.mvp.shopping_discount.domain.Order;

import com.carrefour.mvp.shopping_discount.domain.product.ProductEntity;
import com.carrefour.mvp.shopping_discount.domain.product.ProductName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
public class OrderItemEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
//    @Embedded
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Boolean discounted;

    OrderItemEntity(){}

    public OrderItemEntity(ProductEntity product, BigDecimal price, Integer quantity, ProductName productName) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(price);
        Objects.requireNonNull(productName);
        Objects.requireNonNull(quantity);
        this.id = new OrderItemId().id();
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.name = productName.name();
        this.discounted = false;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductName getItemName() {
        return new ProductName(name);
    }

    public void setDiscountedPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsDiscounted() {
        return discounted;
    }

    public void flagAsDiscounted(){
        this.discounted = true;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
