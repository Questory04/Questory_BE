package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostRepository {
    List<Post> findFiltered(@Param("offset") int offset,
                            @Param("size") int size,
                            @Param("keyword") String keyword);
    int create(Post post);
    void update(Post post);
    void delete(Post post);
    Post findById(Long postId);
}
