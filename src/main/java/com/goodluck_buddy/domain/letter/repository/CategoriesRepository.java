package com.goodluck_buddy.domain.letter.repository;

import com.goodluck_buddy.domain.letter.entity.Categories;
import com.goodluck_buddy.domain.letter.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Optional<Categories> findByCategory(Category category);
}
