package project.mechanics.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import project.websockets.Message;


public class CancelGame extends Message {
    private String payload;

    @JsonCreator
    public CancelGame(@JsonProperty("payload") String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
