package com.carrefour.mvp.shopping_discount.infra.spring;

import com.carrefour.mvp.shopping_discount.domain.discount.DiscountEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static io.smallrye.mutiny.converters.uni.UniReactorConverters.toMono;


@Service
public class DiscountRepositoryVertx implements DiscountRepository {
    private final Mutiny.SessionFactory sessionFactory;

    public DiscountRepositoryVertx(Mutiny.SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Mono<DiscountEntity> findByCode(String code) {
        CriteriaBuilder criteriaBuilder = this.sessionFactory.getCriteriaBuilder();
        CriteriaQuery<DiscountEntity> query = criteriaBuilder.createQuery(DiscountEntity.class);
        Root<DiscountEntity> root = query.from(DiscountEntity.class);
        query.select(root).where(criteriaBuilder.equal(root.get("code"), code));
        return
                sessionFactory
                        .withSession(
                                session ->
                                        session.createQuery(query).getSingleResult())
                                        .convert()
                                        .with(toMono());
    }
}
