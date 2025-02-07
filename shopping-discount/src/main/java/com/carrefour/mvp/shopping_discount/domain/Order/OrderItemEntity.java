package com.carrefour.mvp.shopping_discount.domain.Order;

import com.carrefour.mvp.shopping_discount.domain.product.ProductEntity;
import jakarta.persistence.*;

@Entity
public class OrderItemEntity {
    @EmbeddedId
    private OrderItemId id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    private Float price;
    private Float discountPercentage;
    private Integer quantity;

    OrderItemEntity(){}

    public OrderItemEntity(ProductEntity product, Float price, Float discountPercentage, Integer quantity) {
        this.id = new OrderItemId();
        this.product = product;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.quantity = quantity;
    }

    public ProductEntity getProduct() {
        return product;
    }

}
