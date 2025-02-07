package com.carrefour.mvp.shopping_discount.domain.customer;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class CustomerEntity {
    @EmbeddedId
    private CustomerId id;
    private String name;
    private String email;
    private String address;

    CustomerEntity(){}

    public CustomerEntity(String name, String email, String address) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        this.id = new CustomerId();
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
