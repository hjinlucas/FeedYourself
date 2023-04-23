package com.example.feedyourself.adapters;

public class Review {

    private String id;
    private String recipeName;
    private String userId;
    private String comments;
    private float rating;

    public Review(String id, String recipeName, String userId, String comments, float rating) {
        this.id = id;
        this.recipeName = recipeName;
        this.userId = userId;
        this.comments = comments;
        this.rating= rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
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
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
