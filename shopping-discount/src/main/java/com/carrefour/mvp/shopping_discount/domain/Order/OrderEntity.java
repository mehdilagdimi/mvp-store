package com.carrefour.mvp.shopping_discount.domain.Order;

import com.carrefour.mvp.shopping_discount.domain.customer.CustomerEntity;
import com.carrefour.mvp.shopping_discount.domain.product.ShippingAdress;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.SequencedSet;

@Entity
public class OrderEntity {
    @EmbeddedId
    private OrderId id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "shipping-address"))
    private ShippingAdress shippingAdress;
    @OneToMany(mappedBy = "order")
    private SequencedSet<OrderItemEntity> orderItems;

    OrderEntity(){}

    public OrderEntity(CustomerEntity customer, ShippingAdress shippingAdress, SequencedSet<OrderItemEntity> orderItems) {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(shippingAdress);
        Objects.requireNonNull(orderItems);
        if(orderItems.isEmpty()){
            throw new IllegalArgumentException();
        }

        this.id = new OrderId();
        this.customer = customer;
        this.shippingAdress = shippingAdress;
        this.orderItems = orderItems;
    }

    public SequencedSet<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

}
