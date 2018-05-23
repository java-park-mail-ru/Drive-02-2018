package project.websockets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import project.mechanics.messages.JoinGame;

import java.io.IOException;


public class GameWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameWebSocketHandler.class);
    @NotNull
    private final RemotePointService service;
    @Autowired
    private final ObjectMapper objectMapper;


    public GameWebSocketHandler(@NotNull RemotePointService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }


    @SuppressWarnings("OverlyBroadThrowsClause")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        final String userMail = (String) session.getAttributes().get("mail");
        service.registerUser(userMail, session);
        LOGGER.info("Connected user with mail " + userMail);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        final String mail = (String) session.getAttributes().get("mail");
        this.service.disconnectedHandler(mail);
        LOGGER.info("Disconnected user with mail " + mail);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage jsonTextMessage) throws Exception {
        final String userMail = (String) session.getAttributes().get("mail");
        try {
            final Message message = objectMapper.readValue(jsonTextMessage.getPayload(), Message.class);
            if (message.getClass() == JoinGame.class) {
                service.addWaiter(userMail, message);
            } else {
                service.handleGameMessage(message, userMail);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            LOGGER.error("wrong json format response ", ex);
        }
    }
}
