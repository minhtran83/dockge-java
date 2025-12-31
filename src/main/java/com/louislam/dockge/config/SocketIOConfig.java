package com.louislam.dockge.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Configuration for the Socket.IO server.
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
        
        // If port is 0, find a random available port
        if (port == 0) {
            port = findRandomPort();
        }
        config.setPort(port);
        
        config.setOrigin("*");
        this.server = new SocketIOServer(config);
        
        try {
            this.server.start();
        } catch (Exception e) {
            this.server.stop();
            this.server.start();
        }
        
        return this.server;
    }

    private int findRandomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            return 5051; // Fallback
        }
    }

    @PreDestroy
    public void stopSocketIOServer() {
        if (this.server != null) {
            this.server.stop();
        }
    }
    
    public int getPort() {
        return port;
    }
}
