package project.websockets;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class MessageSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    MessageSender() {}

    public static boolean send(@Nullable WebSocketSession session, @NotNull TextMessage message) {
        try {
            if (session == null) {
                LOGGER.info("Abort sending message to null session");
                return false;
            }
            session.sendMessage(message);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
