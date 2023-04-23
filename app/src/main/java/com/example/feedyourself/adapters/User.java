package com.example.feedyourself.adapters;

import java.util.List;

public class User {
    private String id;
    private String email;
    private List<Recipe> savedRecipes;
    private String username;
    private String backgroundImageUrl;
    private String profileImageUrl;

    public User(String id, String email, List<Recipe> savedRecipes, String username, String backgroundImageUrl, String profileImageUrl) {
        this.id = id;
        this.email = email;
        this.savedRecipes = savedRecipes;
        this.username = username;
        this.backgroundImageUrl = backgroundImageUrl;
        this.profileImageUrl = profileImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Recipe> getSavedRecipes() {
        return savedRecipes;
    }

    public void setSavedRecipes(List<Recipe> savedRecipes) {
        this.savedRecipes = savedRecipes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
