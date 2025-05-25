package com.ssafy.questory.repository;

import com.ssafy.questory.domain.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostRepository {
    int create(Post post);
}
