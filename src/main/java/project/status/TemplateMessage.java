package project.status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import project.models.UserModel;


public class TemplateMessage {

    @JsonProperty
    private boolean success;

    @JsonProperty
    private String status;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserModel user;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserModel[] users;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer usersLeft;


    public TemplateMessage() {
        success = false;
        status = "undefined";
    }

    public TemplateMessage(boolean success, String status) {
        this.success = success;
        this.status = status;
    }

    public TemplateMessage(boolean success, String status, UserModel user) {
        this.success = success;
        this.status = status;
        this.user = user;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public UserModel getUser() {
        return user;
    }

    public Integer getUsersLeft() {
        return this.usersLeft;
    }

    public UserModel[] getUsers() {
        return users;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public void setUsers(UserModel[] users) {
        this.users = users;
    }

    public void setUsersLeft(Integer amount) {
        this.usersLeft = amount;
    }

}

