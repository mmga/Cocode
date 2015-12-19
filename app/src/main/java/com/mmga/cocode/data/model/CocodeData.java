package com.mmga.cocode.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CocodeData {

    @SerializedName("topic_list")
    private TopicList topicList;

    public class TopicList {
        private List<Topic> topics = new ArrayList<Topic>();

        public List<Topic> getTopics() {
            return topics;
        }

        public void setTopics(List<Topic> topics) {
            this.topics = topics;
        }
    }

    public class Topic {
        private Integer id;
        private String title;
        private String slug;

        @SerializedName("posts_count")
        private Integer postsCount;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("last_posted_at")
        private String lastPostedAt;

        @SerializedName("last_poster_username")
        private String lastPosterUsername;

        @SerializedName("category_id")
        private Integer categoryId;

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
    }


    public TopicList getTopicList() {
        return topicList;
    }

    public void setTopicList(TopicList topicList) {
        this.topicList = topicList;
    }
}
