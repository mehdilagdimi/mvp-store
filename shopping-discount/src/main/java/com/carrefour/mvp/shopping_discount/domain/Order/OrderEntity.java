package com.carrefour.mvp.shopping_discount.domain.Order;

import com.carrefour.mvp.shopping_discount.domain.customer.CustomerEntity;
import com.carrefour.mvp.shopping_discount.domain.product.ShippingAdress;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.SequencedSet;

@Entity
public class OrderEntity {
    @EmbeddedId
    private OrderId id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "shipping-address"))
    private ShippingAdress shippingAdress;
    @OneToMany(mappedBy = "order")
    private SequencedSet<OrderItemEntity> orderItems;
    private BigDecimal discountPercentage;

    OrderEntity(){}

    public OrderEntity(CustomerEntity customer, ShippingAdress shippingAdress, SequencedSet<OrderItemEntity> orderItems) {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(shippingAdress);
        Objects.requireNonNull(orderItems);
        if(orderItems.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.id = new OrderId();
        this.status = OrderStatus.PENDING;
        this.discountPercentage = BigDecimal.ZERO;
        this.customer = customer;
        this.shippingAdress = shippingAdress;
        this.orderItems = orderItems;

    }

    public SequencedSet<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void assignDiscountPercentage(BigDecimal discountPercentage){

        this.discountPercentage = discountPercentage;
    }

}
