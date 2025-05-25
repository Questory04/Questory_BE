package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class Post {
    private Long postId;
    private String userEmail;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PostCategory category;

    protected Post() {}

    @Builder
    private Post(Long postId, String email, String title, String content,
                 LocalDateTime createdAt, LocalDateTime updatedAt,
                 PostCategory category) {
        this.postId = postId;
        this.userEmail = email;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
    }
}
