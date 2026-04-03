package com.shopwave.shopwave_starter.service;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.mapper.ProductMapper;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private ProductMapper productMapper;

    @InjectMocks private ProductService productService;

    @Test
    void createProduct_HappyPath() {
        // Arrange
        CreateProductRequest request = CreateProductRequest.builder()
                .name("Laptop").price(new BigDecimal("999")).stock(10).build();
        
        Product savedProduct = new Product(); 
        savedProduct.setName("Laptop");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(productMapper.toDto(any(Product.class))).thenReturn(ProductDTO.builder().name("Laptop").build());

        // Act
        ProductDTO result = productService.createProduct(request);

        // Assert
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void createProduct_ErrorPath_CategoryNotFound() {
        // Arrange
        CreateProductRequest request = CreateProductRequest.builder()
                .name("Phone").categoryId(99L).build();
        
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.createProduct(request);
        });
        
        assertEquals("Category not found", exception.getMessage());
    }
}