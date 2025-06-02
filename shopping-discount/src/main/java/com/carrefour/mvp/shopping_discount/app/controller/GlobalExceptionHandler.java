package com.carrefour.mvp.shopping_discount.app.controller;


import com.carrefour.mvp.shopping_discount.domain.discount.exception.DiscountNotFoundException;
import com.carrefour.mvp.shopping_discount.domain.order.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public Mono<ResponseEntity> entityNotFoundExceptionHandling(OrderNotFoundException e){
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
        return Mono.just(ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse));
    }

    @ExceptionHandler(DiscountNotFoundException.class)
    public Mono<ResponseEntity> entityNotFoundExceptionHandling(DiscountNotFoundException e){
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
        return Mono.just(ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse));
    }


    @ExceptionHandler(NullPointerException.class)
    public Mono<ResponseEntity> entityNotFoundExceptionHandling(NullPointerException e){
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
        return Mono.just(ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse));
    }
}
