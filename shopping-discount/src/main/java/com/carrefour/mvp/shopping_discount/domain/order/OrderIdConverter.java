package com.carrefour.mvp.shopping_discount.domain.order;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
public class OrderIdConverter implements AttributeConverter<OrderId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(OrderId attribute) {
        return attribute == null ? null : attribute.id();
    }

    @Override
    public OrderId convertToEntityAttribute(UUID dbData) {
        return dbData == null ? null : new OrderId(dbData);
    }
}
