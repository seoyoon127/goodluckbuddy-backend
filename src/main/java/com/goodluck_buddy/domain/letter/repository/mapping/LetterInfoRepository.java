package com.goodluck_buddy.domain.letter.repository.mapping;

import com.goodluck_buddy.domain.letter.entity.mapping.LetterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterInfoRepository extends JpaRepository<LetterInfo, Long> {
}
