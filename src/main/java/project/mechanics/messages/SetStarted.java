package project.mechanics.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import project.mechanics.GameSession;
import project.models.SetModel;
import project.websockets.Message;

import java.util.ArrayList;

public class SetStarted extends Message {
    private Payload payload;

    @JsonCreator
    public SetStarted(@JsonProperty("payload") GameSession gameSession) {
        this.payload = new Payload(gameSession);
    }

    public Payload getPayload() {
        return payload;
    }


    public static class Payload {
        private final Long userId1;
        private final Long userId2;
        private ArrayList<SetModel> questions;

        @JsonCreator
        public Payload(GameSession gameSession) {
            this.userId1 = gameSession.getUserId1();
            this.userId2 = gameSession.getUserId2();
            questions = gameSession.getQuestions();
        }

        public Long getUserId1() {
            return userId1;
        }

        public Long getUserId2() {
            return userId2;
        }

        public ArrayList<SetModel> getQuestions() {
            return questions;
        }
    }
}
