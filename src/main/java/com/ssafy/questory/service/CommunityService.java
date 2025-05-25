package com.ssafy.questory.service;

import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Post;
import com.ssafy.questory.dto.request.posts.PostsCreateRequestDto;
import com.ssafy.questory.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final PostRepository postRepository;

    public void create(Member member, PostsCreateRequestDto postsCreateRequestDto) {
        Post post = Post.builder()
                .email(member.getEmail())
                .title(postsCreateRequestDto.getTitle())
                .content(postsCreateRequestDto.getContent())
                .category(postsCreateRequestDto.getCategory())
                .build();
        postRepository.create(post);
    }
}
