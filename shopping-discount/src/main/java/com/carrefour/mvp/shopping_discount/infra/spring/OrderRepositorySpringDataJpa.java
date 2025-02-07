package com.carrefour.mvp.shopping_discount.infra.spring;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderEntity;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OrderRepositorySpringDataJpa extends CrudRepository<OrderEntity, OrderId> {
}
