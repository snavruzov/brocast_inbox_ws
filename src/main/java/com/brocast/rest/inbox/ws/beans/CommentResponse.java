package com.brocast.rest.inbox.ws.beans;

import java.io.Serializable;

/**
 * Created by sardor on 3/7/16.
 */
public class CommentResponse implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id_comment;
    private String error;

    public CommentResponse() {
    }

    public Long getId_comment() {
        return id_comment;
    }

    public void setId_comment(Long id_comment) {
        this.id_comment = id_comment;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
