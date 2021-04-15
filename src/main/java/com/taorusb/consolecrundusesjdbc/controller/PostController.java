package com.taorusb.consolecrundusesjdbc.controller;

import com.taorusb.consolecrundusesjdbc.model.Post;
import com.taorusb.consolecrundusesjdbc.model.Writer;
import com.taorusb.consolecrundusesjdbc.service.PostService;
import com.taorusb.consolecrundusesjdbc.service.WriterService;

import java.util.List;
import java.util.NoSuchElementException;

import static com.taorusb.consolecrundusesjdbc.controller.Validator.*;

public class PostController {

    private PostService postService;
    private WriterService writerService;

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public void setWriterService(WriterService writerService) {
        this.writerService = writerService;
    }

    public String showByWriterId(long id, List<Post> postList) {

        try {
            postList.addAll(postService.getByWriterId(id));
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return allRight;
    }

    public String addNewPost(long writerId, String content) {

        try {
            Writer writer = writerService.getById(writerId);
            writer.addPost(postService.savePost(new Post(content, new Writer(writerId))));
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String updatePost(long id, String content, Post post) {

        Post temp;
        try {
            temp = postService.updatePost(new Post(id, content));
            post.setId(temp.getId());
            post.setUpdated(temp.getUpdated());
            post.setCreated(temp.getCreated());
            post.setContent(temp.getContent());
            post.setWriter(temp.getWriter());
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String deletePost(long id) {
        try {
            postService.deletePost(id);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }
}