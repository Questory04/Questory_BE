package com.ssafy.questory.dto.request.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsDeleteRequestDto {
    private Long postId;

    @Builder
    public PostsDeleteRequestDto(Long postId) {
        this.postId = postId;
    }
}
