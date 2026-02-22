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
            WHERE (:parentCategory IS NULL or r.letter.categories.category = :category)
            AND (:userId IS NULL or r.user.id = :userId)
            """)
    List<Reply> findAllByFilters(
            @Param("category") Category category,
            @Param("userId") Long userId,
            Sort sort);
}
