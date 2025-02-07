package com.carrefour.mvp.shopping_discount.app.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/order")
public class OrderController {

    @PostMapping(value = "/{id}/discount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity discountOrder(@PathVariable UUID id){
        return ResponseEntity.ok().build();
    }
}
