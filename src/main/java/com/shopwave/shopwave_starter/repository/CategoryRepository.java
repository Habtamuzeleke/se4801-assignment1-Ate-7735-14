
package com.shopwave.shopwave_starter.repository;

import com.shopwave.shopwave_starter.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}