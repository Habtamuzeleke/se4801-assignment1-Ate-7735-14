//HABTAMU ZELEKE ATE/7735/14

package com.shopwave.shopwave_starter.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}