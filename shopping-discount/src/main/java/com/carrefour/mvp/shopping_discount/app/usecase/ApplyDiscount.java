package com.carrefour.mvp.shopping_discount.app.usecase;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ApplyDiscount<T> {
    T apply(UUID id, String discount);
}
