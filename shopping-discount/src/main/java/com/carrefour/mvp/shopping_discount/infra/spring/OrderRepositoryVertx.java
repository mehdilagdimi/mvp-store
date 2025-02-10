package com.carrefour.mvp.shopping_discount.infra.spring;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderEntity;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderId;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderRepository;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static io.smallrye.mutiny.converters.uni.UniReactorConverters.toMono;


@Service
public class OrderRepositoryVertx implements OrderRepository {
    private final Mutiny.SessionFactory sessionFactory;

    public OrderRepositoryVertx(Mutiny.SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Mono<OrderEntity> findById(OrderId orderId) {
        return
                sessionFactory
                        .withSession(session -> session.find(OrderEntity.class, orderId))
                        .onItem()
                        .ifNull()
                        .failWith(RuntimeException::new)
                        .convert().with(toMono());
    }

    @Override
    public Mono<OrderEntity> update(OrderEntity order) {
        return
                sessionFactory
                    .withSession(session ->
                            session.merge(order)
                                    .onItem()
                                    .call(session::flush))
                    .convert().with(toMono());
    }
}
