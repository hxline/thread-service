package com.hxline.threadservice.domain;

import java.util.Set;

/**
 *
 * @author Handoyo
 */
public class Thread {

    private String id;
    private String threadTopic;
    private String threadDescription;
    private Set<Comment> comments;

    public Thread() {
    }

    public Thread(String id, String threadTopic, String threadDescription, Set<Comment> comments) {
        this.id = id;
        this.threadTopic = threadTopic;
        this.threadDescription = threadDescription;
        this.comments = comments;
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

}