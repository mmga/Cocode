package com.mmga.cocode.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CocodeData {

    @Expose
    private List<Users> users;

    @Expose
    @SerializedName("topic_list")
    private TopicList topicList;


    public class TopicList {
        @Expose
        private List<Topic> topics = new ArrayList<Topic>();


        public List<Topic> getTopics() {
            return topics;
        }


        public void setTopics(List<Topic> topics) {
            this.topics = topics;
        }
    }


    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public TopicList getTopicList() {
        return topicList;
    }

    public void setTopicList(TopicList topicList) {
        this.topicList = topicList;
    }
}
