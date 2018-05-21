package project.mechanics.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import project.mechanics.GameSession;
import project.websockets.Message;


public class CheckedAnswer extends Message{
    private Payload payload;

    @JsonCreator
    public CheckedAnswer(@JsonProperty("payload") GameSession gameSession) {
        this.payload = new Payload(gameSession);
    }

    public Payload getPayload() {
        return payload;
    }


    public static class Payload {
        private final Long userId1;
        private final Long userId2;
        private final Integer userId1Answer;
        private final Integer userId2Answer;
        private final Integer correctAnswer;

        @JsonCreator
        public Payload(GameSession gameSession) {
            this.userId1 = gameSession.getUserId1();
            this.userId2 = gameSession.getUserId2();
            this.userId1Answer = gameSession.getLastUserAnswer(userId1);
            this.userId2Answer = gameSession.getLastUserAnswer(userId2);
            this.correctAnswer = gameSession.getLastCorrectAnswer();
        }

        public Long getUserId1() {
            return userId1;
        }

        public Long getUserId2() {
            return userId2;
        }

        public Integer getUserId1Answer() {
            return userId1Answer;
        }

        public Integer getUserId2Answer() {
            return userId2Answer;
        }

        public Integer getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
