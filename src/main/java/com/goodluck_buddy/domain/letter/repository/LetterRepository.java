package com.goodluck_buddy.domain.letter.repository;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.letter.enums.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    @Query("""
            SELECT l from Letter l
            WHERE (:category IS NULL or l.categories.name = :category)
            AND (:parentCategory IS NULL or l.categories.parentCategory = :parentCategory)
            AND (:userId IS NULL or l.writerId = :userId)
            """)
    List<Letter> findAllByFilters(
            @Param("category") String category,
            @Param("parentCategory") Category parentCategory,
            @Param("userId") Long userId,
            Sort sort);

    @Query("""
            SELECT l from Letter l
            WHERE (:category IS NULL or l.categories.name = :category)
            AND (:parentCategory IS NULL or l.categories.parentCategory = :parentCategory)
            AND EXISTS (
                 SELECT 1 FROM Like lk
                 WHERE lk.letter = l
                   AND lk.user.id = :userId
             )""")
    List<Letter> findAllByFiltersWithLike(
            @Param("category") String category,
            @Param("parentCategory") Category parentCategory,
            @Param("userId") Long userId,
            Sort sort);

    @Query("""
            SELECT l from Letter l
            LEFT JOIN FETCH l.likes li
            WHERE EXISTS (
                 SELECT 1 FROM User user
                 WHERE user.gender = li.user.gender
                   AND (
                       (YEAR(CURRENT_DATE) - YEAR(user.birth)) / 10
                       =
                       (YEAR(CURRENT_DATE) - YEAR(li.user.birth)) / 10
                   )
             )
             ORDER BY l.likeCount DESC
            """)
    List<Letter> findRecommendLetters(@Param("userId") Long userId);

    Optional<Letter> findById(Long letterId);

    Optional<List<Letter>> findByWriterId(Long writerId);
}
