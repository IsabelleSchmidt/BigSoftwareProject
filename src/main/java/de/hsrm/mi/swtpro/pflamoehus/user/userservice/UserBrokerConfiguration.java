package de.hsrm.mi.swtpro.pflamoehus.user.userservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
 * UserBroker for the communication between back- and frontend.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Configuration
@EnableWebSocketMessageBroker
public class UserBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * The StompBroker reacts to everything with the prefix "/user".
     * 
     * @param registry for configuring message broker options
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user");
    }

    /**
     * Register requests from frontend.
     * 
     * @param registry for registering STOMP endpoints
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompbroker").setAllowedOrigins("*");
    }

}
