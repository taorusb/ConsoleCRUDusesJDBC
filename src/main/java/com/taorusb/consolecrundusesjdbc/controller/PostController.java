package com.taorusb.consolecrundusesjdbc.controller;

import com.taorusb.consolecrundusesjdbc.model.Post;
import com.taorusb.consolecrundusesjdbc.model.Writer;
import com.taorusb.consolecrundusesjdbc.service.PostService;
import com.taorusb.consolecrundusesjdbc.service.WriterService;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class PostController {

    private PostService postService;
    private WriterService writerService;

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public void setWriterService(WriterService writerService) {
        this.writerService = writerService;
    }

    public List<Post> showByWriterId(ResponseStatus responseStatus, long id) {

        List<Post> posts = new LinkedList<>();
        try {
            posts.addAll(writerService.getById(id).getPosts());
            responseStatus.setSuccessful();
        } catch (NoSuchElementException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return posts;
    }

    public Post addNewPost(ResponseStatus responseStatus, long writerId, String content) {

        Post post = null;
        try {
            Writer writer = writerService.getById(writerId);
            post = postService.savePost(new Post(content, new Writer(writerId)));
            writer.addPost(post);
            responseStatus.setSuccessful();
        } catch (NoSuchElementException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return post;
    }

    public Post updatePost(ResponseStatus responseStatus, long id, String content) {

        Post post = null;
        try {
            post = postService.updatePost(new Post(id, content));
            responseStatus.setSuccessful();
        } catch (NoSuchElementException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return post;
    }

    public void deletePost(ResponseStatus responseStatus, long id) {
        try {
            postService.deletePost(id);
            responseStatus.setSuccessful();
        } catch (NoSuchElementException e) {
            responseStatus.setElementNotFoundStatus();
        }
    }
}