package com.mmga.cocode.data.data.model;

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




    public TopicList getTopicList() {
        return topicList;
    }

    public void setTopicList(TopicList topicList) {
        this.topicList = topicList;
    }
}
