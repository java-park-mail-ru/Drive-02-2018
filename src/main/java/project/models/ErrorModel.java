package project.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorModel {
    @JsonProperty
    private String message;

    public ErrorModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
