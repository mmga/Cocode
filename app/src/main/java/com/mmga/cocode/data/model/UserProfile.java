
package com.mmga.cocode.data.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {

    @Expose
    private List<Users> users = new ArrayList<Users>();
    @Expose
    private List<Topic> topics = new ArrayList<Topic>();


    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }


}
