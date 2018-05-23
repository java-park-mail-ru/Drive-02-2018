package project.mechanics.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import project.websockets.Message;

public class JoinGame extends Message {
    private String login;

    @JsonCreator
    public JoinGame(@JsonProperty("payload") String payload) {
        this.login = payload;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String payload) {
        this.login = payload;
    }

}
