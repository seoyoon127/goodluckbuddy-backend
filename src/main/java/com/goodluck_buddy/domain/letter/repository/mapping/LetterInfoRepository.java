package com.goodluck_buddy.domain.letter.repository.mapping;

import com.goodluck_buddy.domain.letter.entity.mapping.LetterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LetterInfoRepository extends JpaRepository<LetterInfo, Long> {
    Optional<LetterInfo> findByLetterId(Long letterId);
}
