package com.goodluck_buddy.domain.letter.repository;

import com.goodluck_buddy.domain.letter.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
