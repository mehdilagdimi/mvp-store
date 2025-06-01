package com.carrefour.mvp.shopping_discount.infra.hibernate;


import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class HibernateConfig {

    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.passw}")
    private String passw;
    @Value("${db.schema-generation}")
    private String schema_generation;

    @Bean
    public Mutiny.SessionFactory sessionFactory() {
        return Persistence.createEntityManagerFactory("shopping-discount", hibernateProperties())
                .unwrap(Mutiny.SessionFactory.class);
    }

    @Bean
    public Properties hibernateProperties(){
        final Properties properties = new Properties();

        properties.put("jakarta.persistence.jdbc.url", url);
        properties.put("jakarta.persistence.jdbc.user", user);
        properties.put("jakarta.persistence.jdbc.password", passw);
        properties.put("jakarta.persistence.schema-generation.database.action", schema_generation);
        properties.put("hibernate.hbm2ddl.import_files", "data.sql");
        properties.put(
                "jakarta.persistence.sql-load-script-source",
                "classpath:data.sql");
        properties.put("hibernate.connection.pool_size", 10);
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.highlight_sql", "true");
        return properties;
    }

}
