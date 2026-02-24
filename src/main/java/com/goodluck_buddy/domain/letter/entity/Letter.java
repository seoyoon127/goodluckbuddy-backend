package com.goodluck_buddy.domain.letter.entity;

import com.goodluck_buddy.domain.letter.entity.mapping.LetterInfo;
import com.goodluck_buddy.domain.letter.enums.LetterDesign;
import com.goodluck_buddy.domain.like.entity.Like;
import com.goodluck_buddy.domain.reply.entity.Reply;
import com.goodluck_buddy.global.entity.BaseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Letter")
public class Letter extends BaseEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LetterInfo> letterInfos = new HashSet<>();

    public List<Info> getInfos() {
        return letterInfos.stream()
                .map(LetterInfo::getInfo)
                .toList();
    }

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateLetterDesign(LetterDesign letterDesign) {
        this.letterDesign = letterDesign;
    }

    public void updateCategories(Categories categories) {
        this.categories = categories;
    }

    public void updateInfos(List<Info> infos) {
        this.letterInfos.forEach(LetterInfo::disconnect);
        this.letterInfos.clear();
        this.letterInfos.clear();
        for (Info info : infos) {
            this.letterInfos.add(
                    LetterInfo.of(this, info)
            );
        }
    }

    public void addLike() {
        likeCount += 1;
    }

    public void removeLike() {
        likeCount -= 1;
    }
}
