package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import project.websockets.GameWebSocketHandler;


@SpringBootApplication
public class Application {

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new PerConnectionWebSocketHandler(GameWebSocketHandler.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
