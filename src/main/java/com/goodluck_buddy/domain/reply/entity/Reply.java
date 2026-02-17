package com.goodluck_buddy.domain.reply.entity;

import com.goodluck_buddy.domain.letter.entity.Letter;
import com.goodluck_buddy.domain.user.entity.User;
import com.goodluck_buddy.global.entity.BaseEntity;
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
@Tag(name = "reply")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "like_count")
    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addLike() {
        likeCount += 1;
    }

    public void removeLike() {
        likeCount -= 1;
    }
}
