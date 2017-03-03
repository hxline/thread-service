package com.hxline.threadservice.domain;

/**
 *
 * @author Handoyo
 */
public class Comment {
    private String id;
    private String comment;

    public Comment() {
    }

    public Comment(String id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}