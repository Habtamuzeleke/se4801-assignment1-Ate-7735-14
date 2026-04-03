//HABTAMU ZELEKE ATE/7735/14

package com.shopwave.shopwave_starter.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalAmount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    // Convenience method
    public void addItem(Product product, int quantity) {
        OrderItem item = OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .order(this)
                .build();
        this.items.add(item);
        
        // Update total amount (simple logic)
        if (this.totalAmount == null) this.totalAmount = BigDecimal.ZERO;
        BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        this.totalAmount = this.totalAmount.add(itemTotal);
    }
}