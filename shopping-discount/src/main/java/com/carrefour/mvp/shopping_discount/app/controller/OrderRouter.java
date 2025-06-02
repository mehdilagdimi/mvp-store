package com.carrefour.mvp.shopping_discount.app.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration(proxyBeanMethods = false)
public class OrderRouter {

  @Bean
  public RouterFunction<ServerResponse> route(OrderHandler orderHandler) {
    return RouterFunctions
      .route(POST("/api/v1/order/{id}/discount"),
              request -> {
                UUID id = UUID.fromString(request.pathVariable("id"));
                String discountCode = request.headers().header("X-DISCOUNT-CODE").get(0);
                return orderHandler.discountOrder(id, discountCode);
              });
  }
}