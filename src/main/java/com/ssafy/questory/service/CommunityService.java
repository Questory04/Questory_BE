package com.ssafy.questory.service;

import com.ssafy.questory.common.exception.CustomException;
import com.ssafy.questory.common.exception.ErrorCode;
import com.ssafy.questory.domain.Member;
import com.ssafy.questory.domain.Post;
import com.ssafy.questory.dto.request.posts.PostsCreateRequestDto;
import com.ssafy.questory.dto.request.posts.PostsDeleteRequestDto;
import com.ssafy.questory.dto.request.posts.PostsUpdateRequestDto;
import com.ssafy.questory.dto.response.post.PostDetailResponseDto;
import com.ssafy.questory.dto.response.post.PostsResponseDto;
import com.ssafy.questory.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final PostRepository postRepository;

    public List<PostsResponseDto> findAll(int page, int size, String keyword) {
        int offset = page * size;
        List<Post> posts = postRepository.findFiltered(offset, size, keyword);
        return posts.stream()
                .map(PostsResponseDto::from)
                .toList();
    }

    public PostDetailResponseDto findById(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        return PostDetailResponseDto.from(post);
    }

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
        postRepository.deleteById(post.getPostId());
    }

    public List<PostsResponseDto> findMyPosts(Member member, int page, int size, String keyword) {
        int offset = page * size;
        List<Post> posts = postRepository.findMyPosts(member.getEmail(), offset, size, keyword);
        return posts.stream()
                .map(PostsResponseDto::from)
                .toList();
    }

    private void validateMember(Member member, Long id) {
        Post find = postRepository.findById(id);
        if (!member.getEmail().equals(find.getUserEmail())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
