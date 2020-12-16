package de.hsrm.mi.swtpro.pflamoehus.user.userService;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class UserBrokerConfigurations implements WebSocketMessageBrokerConfigurer {

    /**
     * @param registry the StompBroker reacts to everything with the prefix "/user"
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user");
    }

    /**
     * @param registry Registration requests are received
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompbroker").setAllowedOrigins("*");
    }

}
