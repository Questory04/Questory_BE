package com.ssafy.questory.dto.response.post;

import com.ssafy.questory.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDetailResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    @Builder
    private PostDetailResponseDto(Long postId, String title, String content, String nickname,
                                  LocalDateTime createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    public static PostDetailResponseDto from(Post post) {
        return PostDetailResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getNickname())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
