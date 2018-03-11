package main.status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import main.models.User;

public class Message {

    @JsonProperty
    private String success;

    @JsonProperty
    private String status;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User user;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User[] users;


    Message() {
        success = "false";
        status = "undefined";
    }

    Message(String success, String status) {
        this.success = success;
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public User[] getUsers() {
        return users;
    }

    public void setSuccess(String success) {
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

}

