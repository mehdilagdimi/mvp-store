package com.carrefour.mvp.shopping_discount.infra.hibernate;


import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
    @Bean
    public Mutiny.SessionFactory sessionFactory() {
        return Persistence.createEntityManagerFactory("shopping-discount")
                .unwrap(Mutiny.SessionFactory.class);
    }

}
