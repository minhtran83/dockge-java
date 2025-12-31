package com.louislam.dockge.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Service to manage Socket.IO event handlers.
 */
@Service
public class SocketIOService {

    private static final Logger logger = LoggerFactory.getLogger(SocketIOService.class);

    private final SocketIOServer server;

    @Autowired
    public SocketIOService(SocketIOServer server) {
        this.server = server;
    }

    @PostConstruct
    public void init() {
        server.addConnectListener(client -> logger.info("Client connected: {}", client.getSessionId()));
        server.addDisconnectListener(client -> logger.info("Client disconnected: {}", client.getSessionId()));
        server.addListeners(this);
    }

    @OnEvent("setup")
    public void onSetup(SocketIOClient client, AckRequest ackSender, String username, String password) {
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("msg", "Setup successful (Mock)");
        if (ackSender != null && ackSender.isAckRequested()) {
            ackSender.sendAckData(response);
        }
    }

    @OnEvent("login")
    public void onLogin(SocketIOClient client, AckRequest ackSender, String username, String password) {
        Map<String, Object> response = new HashMap<>();
        boolean ok = !"wrongpassword".equals(password);
        response.put("ok", ok);
        if (ok) {
            response.put("token", "mock.jwt.token");
        }
        if (ackSender != null && ackSender.isAckRequested()) {
            ackSender.sendAckData(response);
        }
    }

    @OnEvent("loginByToken")
    public void onLoginByToken(SocketIOClient client, AckRequest ackSender, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        if (ackSender != null && ackSender.isAckRequested()) {
            ackSender.sendAckData(response);
        }
    }

    @OnEvent("getSettings")
    public void onGetSettings(SocketIOClient client, AckRequest ackSender) {
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("data", new HashMap<>());
        if (ackSender != null && ackSender.isAckRequested()) {
            ackSender.sendAckData(response);
        }
    }

    @OnEvent("agent")
    public void onAgent(SocketIOClient client, AckRequest ackSender, Object... args) {
        sendAgentResponse(ackSender);
    }

    @OnEvent("composerize")
    public void onComposerize(SocketIOClient client, AckRequest ackSender, String data) {
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        if (ackSender != null && ackSender.isAckRequested()) {
            ackSender.sendAckData(response);
        }
    }

    @OnEvent("changePassword")
    public void onChangePassword(SocketIOClient client, AckRequest ackSender, String oldPassword, String newPassword) {
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        if (ackSender != null && ackSender.isAckRequested()) {
            ackSender.sendAckData(response);
        }
    }

    @OnEvent("setSettings")
    public void onSetSettings(SocketIOClient client, AckRequest ackSender, Object settings, String password) {
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        if (ackSender != null && ackSender.isAckRequested()) {
            ackSender.sendAckData(response);
        }
    }

    private void sendAgentResponse(AckRequest ackSender) {
        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("msg", "Agent operation successful (Mock)");
        response.put("stack", new HashMap<>());
        if (ackSender != null && ackSender.isAckRequested()) {
            ackSender.sendAckData(response);
        }
    }
}
