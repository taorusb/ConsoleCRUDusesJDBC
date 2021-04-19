package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Post;

public interface PostService {

    Post getById(Long id);

    Post updatePost(Post post);

    Post savePost(Post post);

    void deletePost(Long id);

}
