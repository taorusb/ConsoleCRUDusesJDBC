package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Post;
import com.taorusb.consolecrundusesjdbc.repository.PostRepository;

import java.util.List;

public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.update(post);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getByWriterId(Long id) {
        return postRepository.getByWriterId(id);
    }
}