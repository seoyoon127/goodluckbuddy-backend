package com.goodluck_buddy.domain.reply.repository;

import com.goodluck_buddy.domain.letter.enums.Category;
import com.goodluck_buddy.domain.reply.entity.Reply;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("""
            SELECT r FROM Reply r
            LEFT JOIN FETCH r.letter
            LEFT JOIN FETCH r.user
            WHERE (:category IS NULL or r.letter.categories.name = :category)
            AND (:parentCategory IS NULL or r.letter.categories.parentCategory = :parentCategory)
            AND (:userId IS NULL or r.user.id = :userId)
            """)
    List<Reply> findAllByFilters(
            @Param("category") String category,
            @Param("parentCategory") Category parentCategory,
            @Param("userId") Long userId,
            Sort sort);
}
