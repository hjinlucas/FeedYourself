package com.example.feedyourself.adapters;

public class Review {

    private String Id;
    private String userId;
    private String comments;

    public Review(String id, String userId, String comments) {
        Id = id;
        this.userId = userId;
        this.comments = comments;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
