package de.hsrm.mi.swtpro.pflamoehus.productService;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ProductBrokerConfigurations implements WebSocketMessageBrokerConfigurer{

    
    /** 
     * @param registry
     * the StompBroker reacts to everything with the prefix "/ topic"
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

    
    /** 
     * @param registry
     * Registration requests are received
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompbroker").setAllowedOrigins("*");
    }


    
}