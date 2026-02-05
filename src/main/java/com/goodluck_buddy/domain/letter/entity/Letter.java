package com.goodluck_buddy.domain.letter.entity;

import com.goodluck_buddy.domain.letter.enums.LetterDesign;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Letter")
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "writer_id")
    private Long writerId;

    @Column(name = "title", length = 30)
    private String title;

    @Column(name = "content", length = 200)
    private String content;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "letter_design")
    @Enumerated(EnumType.STRING)
    private LetterDesign letterDesign;
}
