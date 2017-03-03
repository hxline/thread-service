package com.hxline.threadservice.domain;

/**
 *
 * @author Handoyo
 */
public class Thumb {
    private String threadId;
    private Integer thumbsUp;
    private Integer thumbsDown;

    public Thumb() {
    }

    public Thumb(String threadId, Integer thumbsUp, Integer thumbsDown) {
        this.threadId = threadId;
        this.thumbsUp = thumbsUp;
        this.thumbsDown = thumbsDown;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public Integer getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(Integer thumbsDown) {
        this.thumbsDown = thumbsDown;
    }   
}