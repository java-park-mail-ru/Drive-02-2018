package project.mechanics.messages;

import project.websockets.Message;

public class WaitingMessage extends Message {

    private String payload;

    public WaitingMessage() {
        payload = "waiting for new users";
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
