package project.mechanics.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import project.websockets.Message;

public class NewAnswer extends Message {
    private Integer payload;

    @JsonCreator
    public NewAnswer(@JsonProperty("payload") Integer payload) {
        this.payload = payload;
    }

    public Integer getPayLoad() {
        return payload;
    }

    public void setPayload(Integer payload) {
        this.payload = payload;
    }
}
