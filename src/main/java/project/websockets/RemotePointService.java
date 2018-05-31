package project.websockets;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import project.mechanics.GameSession;
import project.mechanics.messages.*;
import project.models.UserModel;
import project.services.SingleplayerService;
import project.services.UserService;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;



@Service
public class RemotePointService {
    private final Logger logger = LoggerFactory.getLogger(RemotePointService.class);
    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final HashMap<String, Long> userMailIdMap = new HashMap<>();
    private final HashMap<Long, String> userIdAndLogin = new HashMap<>();
    private final Queue<Long> waiters = new ConcurrentLinkedDeque<>();
    private final Map<Long, GameSession> gameMap = new ConcurrentHashMap<>();


    @NotNull
    private final UserService userService;
    private final ObjectMapper objectMapper;
    @NotNull
    private final SingleplayerService singleplayerService;

    public RemotePointService(@NotNull UserService userService,
                              @NotNull SingleplayerService singleplayerService,
                              @NotNull ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.singleplayerService = singleplayerService;
    }


    public void registerUser(String mail, @NotNull WebSocketSession webSocketSession) {
        final UserModel user = this.userService.getUserByMail(mail);
        this.userMailIdMap.put(mail, user.getId());
        logger.info("User with mail " + user.getMail() + " and id " + user.getId() + " connected");
        sessions.put(user.getId(), webSocketSession);
    }


    public void handleGameMessage(Message message, String userMail) {
        final Long userId = userMailIdMap.get(userMail);
        final GameSession gameSession = gameMap.get(userId);
        if (message.getClass() == ThemeSelected.class) {
            final boolean needAnswer = gameSession.selectTheme((ThemeSelected) message, userId);
            if (needAnswer) {
                this.sendMessageToUser(gameSession.getUserId1(), new SetStarted(gameSession));
                this.sendMessageToUser(gameSession.getUserId2(), new SetStarted(gameSession));
            }
        } else if (message.getClass() == NewAnswer.class) {
            final boolean needAnswer = gameSession.addAnswer((NewAnswer) message, userId);
            if (needAnswer) {
                this.sendMessageToUser(gameSession.getUserId1(), new CheckedAnswer(gameSession));
                this.sendMessageToUser(gameSession.getUserId2(), new CheckedAnswer(gameSession));
            }
        } else if (message.getClass() == NeedResult.class) {
            this.sendMessageToUser(userId, new ResultMessage(gameSession, userId));
            if (gameSession.needUpdateScore()) {
                gameSession.saveResults();
                gameMap.remove(gameSession.getUserId1());
                gameMap.remove(gameSession.getUserId2());
            }
        } else if (message.getClass() == CancelGame.class) {
            disconnectedHandler(userMail);
        }
    }


    public void sendMessageToUser(Long userId, @NotNull Message message) {
        final WebSocketSession webSocketSession = sessions.get(userId);
        if (webSocketSession == null || !webSocketSession.isOpen()) {
            throw new NullPointerException("Can't use WebSocketSession for user: " + userId);
        }
        try {
            final TextMessage messageToSend;
            try {
                messageToSend = new TextMessage(objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Parsing error ", e);
            }
            webSocketSession.sendMessage(messageToSend);
        } catch (IOException e) {
            throw new RuntimeException("Unable to send message ", e);
        }
    }


    public void addWaiter(String userMail, Message message) {
        final Long userId = this.userMailIdMap.get(userMail);
        this.waiters.add(userId);
        final JoinGame joinGame = (JoinGame) message;
        final String login = joinGame.getLogin();
        userIdAndLogin.put(userId, login);

        if (waiters.size() > 1) {
            final Long userId1 = waiters.poll();
            final Long userId2 = waiters.poll();
            final GameSession gameSession = new GameSession(userId1, userId2,
                    singleplayerService, userIdAndLogin.get(userId1),
                    userIdAndLogin.get(userId2), this.userService);
            gameMap.put(userId1, gameSession);
            gameMap.put(userId2, gameSession);
            gameSession.start();
            this.sendMessageToUser(userId1, new Start(gameSession, userId1));
            this.sendMessageToUser(userId2, new Start(gameSession, userId2));
        } else {
            this.sendMessageToUser(userId, new WaitingMessage());
        }
    }


    public void disconnectedHandler(String mail) {
        final Long userId = this.userMailIdMap.get(mail);
        final WebSocketSession webSocketSession = sessions.get(userId);
        if (this.waiters.contains(userId)) {
            waiters.remove(userId);
        } else if (gameMap.containsKey(userId)) {
            final GameSession userGame = gameMap.get(userId);
        }
        //games.remove(userGame);
        this.closeWebScoket(webSocketSession);
        sessions.remove(userId);
    }


    public void closeWebScoket(WebSocketSession webSocketSession) {
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.close();
            } catch (IOException ignore) {
                ignore.printStackTrace();
                logger.error("ERROR CLOSING WEBSOCKET");
            }
        }
    }
}
