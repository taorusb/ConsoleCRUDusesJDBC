package com.taorusb.consolecrundusesjdbc.view;

import com.taorusb.consolecrundusesjdbc.controller.PostController;
import com.taorusb.consolecrundusesjdbc.controller.ResponseStatus;
import com.taorusb.consolecrundusesjdbc.model.Post;

import java.util.LinkedList;
import java.util.List;

import static com.taorusb.consolecrundusesjdbc.controller.Validator.*;
import static java.lang.Long.parseLong;

public class ShowPost {

    private ResponseStatus responseStatus;
    private PostController postController;
    private static final String[] template = {"%-8s%-26.20s%-12s%-12s%-12s%n", "id", "content", "created", "updated", "writerId"};
    private List<Post> container = new LinkedList<>();

    public ShowPost(PostController postController, ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
        this.postController = postController;
    }

    public void showByWriterId(String id) {

        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }

        container.addAll(postController.showByWriterId(responseStatus, parseLong(id)));

        if (responseStatus.getStatus().equals("elementNotFound")) {
            System.out.println(elementNotFoundError);
            return;
        }
        printPosts();
        container.clear();
    }

    public void addPost(String writerId, String content) {

        if (!checkId(writerId)) {
            System.out.println(idError);
            return;
        }

        postController.addNewPost(responseStatus, parseLong(writerId), content);

        if (responseStatus.getStatus().equals("elementNotFound")) {
            System.out.println(elementNotFoundError);
            return;
        }
        showByWriterId(writerId);
    }

    public void updatePost(String id, String content) {

        Post post;
        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }

        post = postController.updatePost(responseStatus, parseLong(id), content);

        if (responseStatus.getStatus().equals("elementNotFound")) {
            System.out.println(elementNotFoundError);
            return;
        }

        System.out.printf(template[0], template[1], template[2], template[3], template[4], template[5]);
        System.out.printf(template[0], post.getId(), post.getContent(), post.getCreated(), post.getUpdated(), post.getWriter().getId());
        System.out.print("\n");
    }

    public void deletePost(String id) {

        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }

        postController.deletePost(responseStatus, parseLong(id));

        if (responseStatus.getStatus().equals("elementNotFound")) {
            System.out.println(elementNotFoundError);
            return;
        }
        System.out.println(successful);
    }

    private void printPosts() {
        System.out.printf(template[0], template[1], template[2], template[3], template[4], template[5]);
        container.forEach(post -> System.out.printf
                (template[0], post.getId(), post.getContent(), post.getCreated(), post.getUpdated(), post.getWriter().getId()));
        System.out.print("\n");
        container.clear();
    }
}