package com.hxline.threadservice.dto;

import com.hxline.threadservice.domain.Comment;
import com.hxline.threadservice.domain.Thumb;
import java.util.Set;

/**
 *
 * @author Handoyo
 */
public class ThreadDTO {
    private String id;
    private String threadTopic;
    private String threadDescription;
    private Set<Comment> comments;
    private Thumb thumb;

    public ThreadDTO() {
    }

    public ThreadDTO(String id, String threadTopic, String threadDescription, Set<Comment> comments, Thumb thumb) {
        this.id = id;
        this.threadTopic = threadTopic;
        this.threadDescription = threadDescription;
        this.comments = comments;
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThreadTopic() {
        return threadTopic;
    }

    public void setThreadTopic(String threadTopic) {
        this.threadTopic = threadTopic;
    }

    public String getThreadDescription() {
        return threadDescription;
    }

    public void setThreadDescription(String threadDescription) {
        this.threadDescription = threadDescription;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }   
}