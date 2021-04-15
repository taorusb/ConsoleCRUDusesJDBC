package com.taorusb.consolecrundusesjdbc.model;

import java.util.LinkedList;
import java.util.List;

public class Writer {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Region region;
    private Role role;

    public Writer() {
    }

    public Writer(Long id) {
        this.id = id;
    }

    public Writer(String firstName, String lastName, Region region) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.region = region;
    }

    public Writer(Long id, String firstName, String lastName, Region region) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public int getPostsCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id.equals(writer.id);
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts count= " + posts.size() +
                ", region id= " + region.getId() +
                ", role= " + role.name() +
                '}';
    }
}