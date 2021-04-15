package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Post;

import java.util.List;

public interface PostService {

    Post getById(Long id);

    Post updatePost(Post post);

    Post savePost(Post post);

    void deletePost(Long id);

    List<Post> getByWriterId(Long id);

}
