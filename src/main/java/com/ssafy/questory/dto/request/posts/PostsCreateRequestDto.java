package com.ssafy.questory.dto.request.posts;

import com.ssafy.questory.domain.PostCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsCreateRequestDto {
    private String title;
    private String content;
    private PostCategory category;

    @Builder
    public PostsCreateRequestDto(String title, String content, PostCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
