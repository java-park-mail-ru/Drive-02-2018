package project.mechanics.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import project.mechanics.GameSession;
import project.websockets.Message;

public class ResultMessage extends Message {
    private Payload payload;

    @JsonCreator
    public ResultMessage(@JsonProperty("payload") GameSession gameSession, Long userId) {
        this.payload = new Payload(gameSession, userId);
    }

    public Payload getPayload() {
        return payload;
    }


    public static class Payload {
        private final Long userId1;
        private final Long userId2;
        private final Long userId;
        private final Integer userId1Result;
        private final Integer userId2Result;

        @JsonCreator
        public Payload(GameSession gameSession, Long userId) {
            this.userId1 = gameSession.getUserId1();
            this.userId2 = gameSession.getUserId2();
            this.userId1Result = gameSession.getCorrectAnswersAmount(userId1);
            this.userId2Result = gameSession.getCorrectAnswersAmount(userId2);
            this.userId = userId;
        }

        public Long getUserId1() {
            return userId1;
        }

        public Long getUserId2() {
            return userId2;
        }

        public Long getUserId() {
            return userId;
        }

        public Integer getUserId1Result() {
            return userId1Result;
        }

        public Integer getUserId2Result() {
            return userId2Result;
        }
    }
}
