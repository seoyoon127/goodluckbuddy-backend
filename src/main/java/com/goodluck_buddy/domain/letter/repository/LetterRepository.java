package com.goodluck_buddy.domain.letter.repository;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.enums.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    @Query("""
            SELECT l from Letter l
            LEFT JOIN FETCH l.letterInfos li
            LEFT JOIN FETCH li.info
            WHERE (:category IS NULL or l.categories.name = :category)
            AND (:parentCategory IS NULL or l.categories.parentCategory = :parentCategory)
            """)
    List<Letter> findAllByFilters(
            @Param("category") String category,
            @Param("parentCategory") Category parentCategory,
            Sort sort);
}
