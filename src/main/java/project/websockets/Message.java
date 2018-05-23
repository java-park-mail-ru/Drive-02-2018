package project.websockets;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import project.mechanics.messages.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "event")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Start.class, name = "EVENTS_GAME_START"),
        @JsonSubTypes.Type(value = JoinGame.class, name = "MESSAGES_JOINGAME"),
        @JsonSubTypes.Type(value = ThemeSelected.class, name = "EVENTS_THEME_SELECTED"),
        @JsonSubTypes.Type(value = SetStarted.class, name = "EVENTS_SET_STARTED"),
        @JsonSubTypes.Type(value = NewAnswer.class, name = "EVENTS_NEW_ANSWER"),
        @JsonSubTypes.Type(value = CheckedAnswer.class, name = "EVENTS_ANSWER_CHECKED"),
        @JsonSubTypes.Type(value = NeedResult.class, name = "EVENTS_NEED_RESULT"),
        @JsonSubTypes.Type(value = ResultMessage.class, name = "EVENTS_GAME_FINISHED")
})


public abstract class Message {

}
