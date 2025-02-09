package com.carrefour.mvp.shopping_discount.infra.spring;

import com.carrefour.mvp.shopping_discount.domain.discount.DiscountEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountRepository;
import org.springframework.stereotype.Service;


@Service
public class DiscountRepositoryJpa implements DiscountRepository {
    private final DiscountRepositorySpringDataJpa discountRepositorySpringDataJpa;

    public DiscountRepositoryJpa(DiscountRepositorySpringDataJpa discountRepositorySpringDataJpa){
        this.discountRepositorySpringDataJpa = discountRepositorySpringDataJpa;
    }

    @Override
    public DiscountEntity findByCode(String code) {
        return discountRepositorySpringDataJpa.findByCode(code).orElseThrow();
    }
}
