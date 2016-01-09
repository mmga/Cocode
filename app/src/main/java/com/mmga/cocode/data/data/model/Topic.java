package com.mmga.cocode.data.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Topic {


    @Expose
    private Integer id;
    @Expose
    private String title;
    @Expose
    private String slug;

    @Expose
    @SerializedName("posts_count")
    private Integer postsCount;

    @Expose
    @SerializedName("created_at")
    private String createdAt;

    @Expose
    @SerializedName("last_posted_at")
    private String lastPostedAt;

    @Expose
    @SerializedName("last_poster_username")
    private String lastPosterUsername;

    @Expose
    @SerializedName("category_id")
    private Integer categoryId;

    @Expose
    @SerializedName("posters")
    private List<Posters> posters = new ArrayList<Posters>();


    private String avatarUrl;
    private String authorName;


    public class Posters {

        @Expose
        @SerializedName("user_id")
        private int userId;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }


    //getters & setters

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastPostedAt() {
        return lastPostedAt;
    }

    public void setLastPostedAt(String lastPostedAt) {
        this.lastPostedAt = lastPostedAt;
    }

    public String getLastPosterUsername() {
        return lastPosterUsername;
    }

    public void setLastPosterUsername(String lastPosterUsername) {
        this.lastPosterUsername = lastPosterUsername;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<Posters> getPosters() {
        return posters;
    }

    public void setPosters(List<Posters> posters) {
        this.posters = posters;
    }


}
