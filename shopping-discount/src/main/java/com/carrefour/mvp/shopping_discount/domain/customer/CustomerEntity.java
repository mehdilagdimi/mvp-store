package com.carrefour.mvp.shopping_discount.domain.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
public class CustomerEntity {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String address;

    CustomerEntity(){}

    public CustomerEntity(String name, String email, String address) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        this.id = new CustomerId().id();
        this.name = name;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
