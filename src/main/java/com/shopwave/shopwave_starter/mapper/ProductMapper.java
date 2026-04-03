//HABTAMU ZELEKE ATE/7735/14

package com.shopwave.shopwave_starter.mapper;

import com.shopwave.shopwave_starter.dto.*;
import com.shopwave.shopwave_starter.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDto(Product product) {
        if (product == null) return null;
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .createdAt(product.getCreatedAt())
                .build();
    }
}