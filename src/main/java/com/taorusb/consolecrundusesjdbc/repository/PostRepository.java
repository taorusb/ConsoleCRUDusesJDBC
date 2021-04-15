package com.taorusb.consolecrundusesjdbc.repository;

import com.taorusb.consolecrundusesjdbc.model.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Long> {
    List<Post> getByWriterId(Long id);
}
