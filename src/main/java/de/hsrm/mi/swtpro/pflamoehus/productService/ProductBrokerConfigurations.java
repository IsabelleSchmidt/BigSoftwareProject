package de.hsrm.mi.swtpro.pflamoehus.productService;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
 * ProductBroker for communication between front- and backend.
 * 
 * @author Ann-Cathrin Fabian
 * @version 2
 */
@Configuration
@EnableWebSocketMessageBroker
public class ProductBrokerConfigurations implements WebSocketMessageBrokerConfigurer {

    /**
     * The StompBroker reacts to everything with the prefix "/product".
     * 
     * @param registry for configuring message broker options
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/product");
    }

    /**
     * Recievinig requests from frontend.
     * 
     * @param registry for registering STOMP endpoint
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompbroker").setAllowedOrigins("*");
    }

}