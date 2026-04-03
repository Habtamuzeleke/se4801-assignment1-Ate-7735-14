package com.shopwave.shopwave_starter.repository;

import com.shopwave.shopwave_starter.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired private ProductRepository productRepository;

    @Test
    void findByNameContainingIgnoreCase_ReturnsCorrectResults() {
        // Arrange
        Product p1 = Product.builder().name("Samsung Phone").price(BigDecimal.TEN).stock(5).build();
        Product p2 = Product.builder().name("iPhone").price(BigDecimal.TEN).stock(5).build();
        productRepository.saveAll(List.of(p1, p2));

        // Act
        List<Product> results = productRepository.findByNameContainingIgnoreCase("PHONE");

        // Assert
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Product::getName)
                .containsExactlyInAnyOrder("Samsung Phone", "iPhone");
    }
}