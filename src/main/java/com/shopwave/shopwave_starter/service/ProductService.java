package com.shopwave.shopwave_starter.service;

import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.exception.ProductNotFoundException;
import com.shopwave.shopwave_starter.mapper.ProductMapper;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDTO createProduct(CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                // Category lookup logic would go here
                .build();
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {
        // Simple search logic: combining keyword and price filters
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO updateStock(Long id, int delta) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        int newStock = product.getStock() + delta;
        if (newStock < 0) {
            throw new IllegalArgumentException("Final stock cannot be negative. Current: " 
                + product.getStock() + ", Change: " + delta);
        }

        product.setStock(newStock);
        return productMapper.toDto(productRepository.save(product));
    }
}