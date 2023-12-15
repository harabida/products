package com.alten.products.product.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductNotFoundException extends RuntimeException{

    private Long id;
}
