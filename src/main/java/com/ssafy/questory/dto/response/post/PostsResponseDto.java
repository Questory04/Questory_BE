package com.ssafy.questory.dto.response.post;

import com.ssafy.questory.domain.Post;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private PostsResponseDto(Long postId, String title, String content, String email,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PostsResponseDto from(Post post) {
        return PostsResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .email(post.getUserEmail())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
