package main.status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import main.models.User;

public class Message {

    @JsonProperty
    private boolean success;

    @JsonProperty
    private String status;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User user;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User[] users;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int usersLeft;

    Message() {
        success = false;
        status = "undefined";
    }

    Message(boolean success, String status) {
        this.success = success;
        this.status = status;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public int gerUsersLeft() {
        return this.usersLeft;
    }

    public User[] getUsers() {
        return users;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public void setUsersLeft(int amount) {
        this.usersLeft = amount;
    }

}

