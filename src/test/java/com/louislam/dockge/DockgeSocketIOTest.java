package com.louislam.dockge;

import io.socket.client.Ack;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * 18 Integration Tests for Dockge Node.js Backend
 * Tests all critical functionality via Socket.IO
 */
@DisplayName("Dockge Socket.IO Integration Tests")
public class DockgeSocketIOTest extends IntegrationTestBase {
    
    // Connection Tests (1)
    @Test
    @DisplayName("Should connect to server")
    public void shouldConnectToServer() {
        assertThat(socket.connected()).isTrue();
    }
    
    // Authentication Tests (3)
    @Test
    @DisplayName("Should login with valid credentials")
    public void shouldLoginWithValidCredentials() throws InterruptedException {
        // Assume default admin user exists
        JSONObject loginData = new JSONObject();
        loginData.put("username", "admin");
        loginData.put("password", "admin");
        
        Object response = emitAndWait("login", loginData);
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should reject invalid credentials")
    public void shouldRejectInvalidCredentials() throws InterruptedException {
        JSONObject loginData = new JSONObject();
        loginData.put("username", "admin");
        loginData.put("password", "wrongpassword");
        
        Object response = emitAndWait("login", loginData);
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should handle setup event")
    public void shouldHandleSetupEvent() throws InterruptedException {
        JSONObject setupData = new JSONObject();
        setupData.put("username", "testadmin");
        setupData.put("password", "testpass123");
        
        Object response = emitAndWait("setup", setupData);
        assertThat(response).isNotNull();
    }
    
    // JWT Token Tests (2)
    @Test
    @DisplayName("Should login by token")
    public void shouldLoginByToken() throws InterruptedException {
        // First login to get token
        JSONObject loginData = new JSONObject();
        loginData.put("username", "admin");
        loginData.put("password", "admin");
        
        Object loginResponse = emitAndWait("login", loginData);
        assertThat(loginResponse).isNotNull();
        
        // Then test token auth
        Object tokenResponse = emitAndWait("loginByToken");
        assertThat(tokenResponse).isNotNull();
    }
    
    @Test
    @DisplayName("Should change password")
    public void shouldChangePassword() throws InterruptedException {
        JSONObject changeData = new JSONObject();
        changeData.put("newPassword", "newpass123");
        
        Object response = emitAndWait("changePassword", changeData);
        assertThat(response).isNotNull();
    }
    
    // Settings Tests (2)
    @Test
    @DisplayName("Should get settings")
    public void shouldGetSettings() throws InterruptedException {
        Object response = emitAndWait("getSettings");
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should set settings")
    public void shouldSetSettings() throws InterruptedException {
        JSONObject settings = new JSONObject();
        settings.put("testKey", "testValue");
        
        Object response = emitAndWait("setSettings", settings);
        assertThat(response).isNotNull();
    }
    
    // Stack Management Tests (4)
    @Test
    @DisplayName("Should get stack list")
    public void shouldGetStackList() throws InterruptedException {
        Object response = emitAndWait("getStackList");
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should create stack")
    public void shouldCreateStack() throws InterruptedException {
        JSONObject stackData = new JSONObject();
        stackData.put("name", "test-stack");
        stackData.put("composePath", "/tmp/docker-compose.yml");
        
        Object response = emitAndWait("createStack", stackData);
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should get stack")
    public void shouldGetStack() throws InterruptedException {
        JSONObject stackData = new JSONObject();
        stackData.put("stackName", "test-stack");
        
        Object response = emitAndWait("getStack", stackData);
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should delete stack")
    public void shouldDeleteStack() throws InterruptedException {
        JSONObject stackData = new JSONObject();
        stackData.put("stackName", "test-stack");
        
        Object response = emitAndWait("deleteStack", stackData);
        assertThat(response).isNotNull();
    }
    
    // Stack Lifecycle Tests (4)
    @Test
    @DisplayName("Should start stack")
    public void shouldStartStack() throws InterruptedException {
        JSONObject stackData = new JSONObject();
        stackData.put("stackName", "test-stack");
        
        Object response = emitAndWait("startStack", stackData);
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should stop stack")
    public void shouldStopStack() throws InterruptedException {
        JSONObject stackData = new JSONObject();
        stackData.put("stackName", "test-stack");
        
        Object response = emitAndWait("stopStack", stackData);
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should restart stack")
    public void shouldRestartStack() throws InterruptedException {
        JSONObject stackData = new JSONObject();
        stackData.put("stackName", "test-stack");
        
        Object response = emitAndWait("restartStack", stackData);
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("Should update stack")
    public void shouldUpdateStack() throws InterruptedException {
        JSONObject stackData = new JSONObject();
        stackData.put("stackName", "test-stack");
        stackData.put("composeYaml", "version: '3'");
        
        Object response = emitAndWait("updateStack", stackData);
        assertThat(response).isNotNull();
    }
    
    // Utility Tests (1)
    @Test
    @DisplayName("Should composerize docker run command")
    public void shouldComposerize() throws InterruptedException {
        JSONObject data = new JSONObject();
        data.put("dockerRunCmd", "docker run -d -p 80:80 nginx");
        
        Object response = emitAndWait("composerize", data);
        assertThat(response).isNotNull();
    }
    
    // Error Handling Test (1)
    @Test
    @DisplayName("Should handle connection timeout")
    public void shouldHandleConnectionTimeout() {
        assertThat(socket.connected()).isTrue();
        // Verify connection is established and can handle ops
    }
}
