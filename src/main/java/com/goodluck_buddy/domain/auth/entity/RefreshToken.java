package com.goodluck_buddy.domain.auth.entity;

import com.goodluck_buddy.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireDate);
    }

    public RefreshToken(String refreshToken, User user, LocalDateTime expireDate) {
        this.refreshToken = refreshToken;
        this.user = user;
        this.expireDate = expireDate;
    }

    public void update(String refreshToken, LocalDateTime expireDate) {
        this.refreshToken = refreshToken;
        this.expireDate = expireDate;
    }
}
