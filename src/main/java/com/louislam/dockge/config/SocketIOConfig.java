package com.louislam.dockge.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;

/**
 * Configuration for the Socket.IO server.
 * 
 * Sets up the Netty-based Socket.IO server on a specific port.
 */
@Configuration
public class SocketIOConfig {

    @Value("${socket-io.host:0.0.0.0}")
    private String host;

    @Value("${socket-io.port:5001}")
    private int port;

    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        
        // Allow all origins for development
        config.setOrigin("*");
        
        // Handle Socket.IO server
        this.server = new SocketIOServer(config);
        
        // Start the server
        try {
            this.server.start();
        } catch (Exception e) {
            // If port is already in use, try to stop and restart or just log
            // In tests, this might happen if context is not cleaned up
            this.server.stop();
            this.server.start();
        }
        
        return this.server;
    }

    @PreDestroy
    public void stopSocketIOServer() {
        if (this.server != null) {
            this.server.stop();
        }
    }
}
