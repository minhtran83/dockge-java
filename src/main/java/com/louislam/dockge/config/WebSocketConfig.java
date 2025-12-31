package com.louislam.dockge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Spring WebSocket configuration for the Dockge application.
 * 
 * Configures WebSocket message broker for real-time communication between
 * server and clients. Supports STOMP protocol over WebSocket.
 */
@Configuration
// @EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure the message broker for WebSocket communication.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // TODO: Implement message broker configuration
        // This will be configured in Phase 2 when implementing WebSocket handlers
    }

    /**
     * Register WebSocket endpoints that clients will connect to.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // TODO: Register STOMP endpoints
        // This will be configured in Phase 2 when implementing WebSocket handlers
    }
}
