package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Post;
import com.ssafy.questory.dto.request.posts.PostsCreateRequestDto;
import com.ssafy.questory.dto.request.posts.PostsDeleteRequestDto;
import com.ssafy.questory.dto.request.posts.PostsUpdateRequestDto;
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

    public void update(Member member, PostsUpdateRequestDto postsUpdateRequestDto) {
        Post post = Post.builder()
                .postId(postsUpdateRequestDto.getPostId())
                .title(postsUpdateRequestDto.getTitle())
                .content(postsUpdateRequestDto.getContent())
                .category(postsUpdateRequestDto.getCategory())
                .email(member.getEmail())
                .build();
        validateMember(member, post.getPostId());
        postRepository.update(post);
    }

    public void delete(Member member, PostsDeleteRequestDto postsDeleteRequestDto) {
        Post post = postRepository.findById(postsDeleteRequestDto.getPostId());
        validateMember(member, post.getPostId());
        postRepository.delete(post);
    }

    private void validateMember(Member member, Long id) {
        Post find = postRepository.findById(id);
        if (!member.getEmail().equals(find.getUserEmail())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
