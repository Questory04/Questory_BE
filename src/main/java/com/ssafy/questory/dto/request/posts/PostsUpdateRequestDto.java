package com.ssafy.questory.dto.request.posts;

import com.ssafy.questory.domain.PostCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private Long postId;
    private String title;
    private String content;
    private PostCategory category;

    @Builder
    public PostsUpdateRequestDto(Long postId, String title, String content, PostCategory category) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
