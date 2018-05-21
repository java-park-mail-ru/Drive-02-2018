package project.mechanics.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import project.websockets.Message;

public class ThemeSelected extends Message {
    private String payload;

    @JsonCreator
    public ThemeSelected(@JsonProperty("payload") String payload) {
        this.payload = payload;
    }

    public String getPayLoad() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
