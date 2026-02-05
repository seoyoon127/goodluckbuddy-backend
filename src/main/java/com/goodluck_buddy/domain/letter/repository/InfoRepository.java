package com.goodluck_buddy.domain.letter.repository;

import com.goodluck_buddy.domain.letter.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfoRepository extends JpaRepository<Info, Long> {
    Optional<Info> findByName(String name);
}
