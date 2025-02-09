package com.carrefour.mvp.shopping_discount.infra.spring;

import com.carrefour.mvp.shopping_discount.domain.discount.DiscountEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface DiscountRepositorySpringDataJpa extends CrudRepository<DiscountEntity, DiscountId> {
    Optional<DiscountEntity> findByCode(String Code);
}
