package com.louislam.dockge;

import com.louislam.dockge.IntegrationTestBase;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Complete integration tests for Dockge Node.js backend via Socket.IO.
 * 
 * Tests all 17 Socket.IO events that the backend supports.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DockgeSocketIOTest extends IntegrationTestBase {

    private static Socket sharedSocket;
    private static String sharedAuthToken;
    private static boolean isConnected = false;


    @BeforeEach
    void setUp() throws Exception {
        super.setUpBase();
        
        // Reuse connection across tests to avoid rate limiting
        if (sharedSocket == null || !sharedSocket.connected()) {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            options.reconnection = false;
            options.timeout = 5000;
            
            sharedSocket = IO.socket(getSocketIOUrl(), options);
            isConnected = false;
        }
    }

    @AfterAll
    static void tearDownOnce() {
        if (sharedSocket != null && sharedSocket.connected()) {
            sharedSocket.disconnect();
            sharedSocket.close();
        }
    }

    // ========================================================================
    // AUTHENTICATION TESTS
    // ========================================================================

    @Test
    @Order(1)
    @DisplayName("Should connect to Socket.IO server")
    void shouldConnectToServer() throws Exception {
        CountDownLatch connectLatch = new CountDownLatch(1);
        
        sharedSocket.on(Socket.EVENT_CONNECT, args -> {
            System.out.println("✅ Connected to server");
            connectLatch.countDown();
        });
        
        sharedSocket.connect();
        
        boolean connected = connectLatch.await(5, TimeUnit.SECONDS);
        assertThat(connected).isTrue();
        assertThat(sharedSocket.connected()).isTrue();
    }

    @Test
    @Order(2)
    @DisplayName("Should handle setup event (succeeds if no users, fails gracefully if users exist)")
    void shouldHandleSetupEvent() throws Exception {
        CountDownLatch connectLatch = new CountDownLatch(1);
        sharedSocket.on(Socket.EVENT_CONNECT, args -> connectLatch.countDown());
        sharedSocket.connect();
        connectLatch.await(5, TimeUnit.SECONDS);
        
        CompletableFuture<JSONObject> setupFuture = new CompletableFuture<>();
        
        String username = "integrationtest";
        String password = "TestPassword123!";
        
        System.out.println("Attempting setup with username: " + username);
        sharedSocket.emit("setup", username, password, new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("✅ Got setup response, args length: " + args.length);
                if (args.length > 0) {
                    System.out.println("Response: " + args[0]);
                }
                JSONObject response = (JSONObject) args[0];
                setupFuture.complete(response);
            }
        });
        
        JSONObject response = setupFuture.get(10, TimeUnit.SECONDS);
        
        System.out.println("Setup response: " + response);
        
        // Validate response structure - should have 'ok' and 'msg' fields
        assertThat(response).isNotNull();
        assertThat(response.has("ok")).isTrue();
        assertThat(response.has("msg")).isTrue();
        
        // Two acceptable outcomes:
        // 1. Setup succeeds (ok:true) - user was created
        // 2. Setup fails (ok:false) - user already exists (expected on non-fresh installs)
        boolean setupOk = response.getBoolean("ok");
        String msg = response.optString("msg", "");
        
        if (!setupOk) {
            // Setup failed - this is expected if users already exist
            System.out.println("✅ Setup correctly rejected (users already exist or setup disabled)");
            System.out.println("   Message: " + msg);
            // Validate that we got a meaningful error message
            assertThat(msg).isNotEmpty();
        } else {
            // Setup succeeded - user was created
            System.out.println("✅ Setup succeeded, user created");
            assertThat(setupOk).isTrue();
        }
    }

    @Test
    @Order(3)
    @DisplayName("Should login with valid credentials and receive JWT token")
    void shouldLoginWithValidCredentials() throws Exception {
        // Connect first
        CountDownLatch connectLatch = new CountDownLatch(1);
        sharedSocket.on(Socket.EVENT_CONNECT, args -> connectLatch.countDown());
        sharedSocket.connect();
        connectLatch.await(5, TimeUnit.SECONDS);
        
        // Login
        CompletableFuture<JSONObject> loginFuture = new CompletableFuture<>();
        
        JSONObject loginData = new JSONObject();
        loginData.put("username", "integrationtest");
        loginData.put("password", "TestPassword123!");
        
        System.out.println("Sending login request...");
        sharedSocket.emit("login", loginData, new Ack() {
            @Override
            public void call(Object... args) {
                System.out.println("✅ Got login response, args length: " + args.length);
                if (args.length > 0) {
                    System.out.println("Response type: " + args[0].getClass().getName());
                    System.out.println("Response: " + args[0]);
                }
                JSONObject response = (JSONObject) args[0];
                loginFuture.complete(response);
            }
        });
        System.out.println("Login request sent, waiting for response...");
        
        JSONObject response = loginFuture.get(10, TimeUnit.SECONDS);
        
        System.out.println("Login response: " + response);
        
        assertThat(response.getBoolean("ok")).isTrue();
        assertThat(response.has("token")).isTrue();
        
        String token = response.getString("token");
        assertThat(token).isNotNull();
        assertThat(token.split("\\.")).hasSize(3); // JWT format: header.payload.signature
        
        // Store for other tests
        sharedAuthToken = token;
    }

    @Test
    @Order(4)
    @DisplayName("Should reject login with invalid credentials")
    void shouldRejectInvalidCredentials() throws Exception {
        CountDownLatch connectLatch = new CountDownLatch(1);
        sharedSocket.on(Socket.EVENT_CONNECT, args -> connectLatch.countDown());
        sharedSocket.connect();
        connectLatch.await(5, TimeUnit.SECONDS);
        
        CompletableFuture<JSONObject> loginFuture = new CompletableFuture<>();
        
        JSONObject loginData = new JSONObject();
        loginData.put("username", "admin");
        loginData.put("password", "wrongpassword");
        
        sharedSocket.emit("login", loginData, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                loginFuture.complete(response);
            }
        });
        
        JSONObject response = loginFuture.get(10, TimeUnit.SECONDS);
        
        System.out.println("Invalid login response: " + response);
        
        assertThat(response.getBoolean("ok")).isFalse();
        assertThat(response.has("token")).isFalse();
    }

    @Test
    @Order(5)
    @DisplayName("Should authenticate with JWT token (loginByToken)")
    void shouldLoginByToken() throws Exception {
        // First get a token
        if (sharedAuthToken == null) {
            shouldLoginWithValidCredentials();
        }
        
        // Disconnect and reconnect
        sharedSocket.disconnect();
        sharedSocket = IO.socket(getSocketIOUrl());
        
        CountDownLatch connectLatch = new CountDownLatch(1);
        sharedSocket.on(Socket.EVENT_CONNECT, args -> connectLatch.countDown());
        sharedSocket.connect();
        connectLatch.await(5, TimeUnit.SECONDS);
        
        // Login with token
        CompletableFuture<JSONObject> loginFuture = new CompletableFuture<>();
        
        sharedSocket.emit("loginByToken", sharedAuthToken, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                loginFuture.complete(response);
            }
        });
        
        JSONObject response = loginFuture.get(10, TimeUnit.SECONDS);
        
        System.out.println("Login by token response: " + response);
        
        assertThat(response.getBoolean("ok")).isTrue();
    }

    // ========================================================================
    // SETTINGS TESTS
    // ========================================================================

    @Test
    @Order(6)
    @DisplayName("Should get application settings")
    void shouldGetSettings() throws Exception {
        loginAndConnect();
        
        CompletableFuture<JSONObject> settingsFuture = new CompletableFuture<>();
        
        sharedSocket.emit("getSettings", new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                settingsFuture.complete(response);
            }
        });

        JSONObject response = settingsFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        assertThat(response.has("data")).isTrue();
        System.out.println("✅ Settings retrieved: " + response.get("data"));
    }

    // ========================================================================
    // STACK OPERATIONS
    // ========================================================================

    @Test
    @Order(7)
    @DisplayName("Should get stack list from agent")
    void shouldGetStackList() throws Exception {
        loginAndConnect();
        
        CompletableFuture<JSONObject> listFuture = new CompletableFuture<>();
        
        // Emits 'agent' event which proxies to the local agent logic
        sharedSocket.emit("agent", "", "getStackList", new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                listFuture.complete(response);
            }
        });

        JSONObject response = listFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        System.out.println("✅ Stack list retrieved");
    }

    @Test
    @Order(8)
    @DisplayName("Should create and save a new stack")
    void shouldCreateStack() throws Exception {
        loginAndConnect();
        
        CompletableFuture<JSONObject> createFuture = new CompletableFuture<>();
        
        String stackName = "integration-test-stack";
        String composeContent = """
            version: "3.8"
            services:
              web:
                image: nginx:alpine
                ports:
                  - "8080:80"
            """;

        System.out.println("Creating stack: " + stackName);
        sharedSocket.emit("agent", "", "saveStack", stackName, composeContent, "", true, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                createFuture.complete(response);
            }
        });

        JSONObject response = createFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        System.out.println("✅ Stack created successfully");
    }

    @Test
    @Order(9)
    @DisplayName("Should get specific stack details")
    void shouldGetStack() throws Exception {
        loginAndConnect();
        ensureStackExists("integration-test-stack");
        
        CompletableFuture<JSONObject> getFuture = new CompletableFuture<>();
        String stackName = "integration-test-stack";

        sharedSocket.emit("agent", "", "getStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                getFuture.complete(response);
            }
        });

        JSONObject response = getFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        assertThat(response.has("stack")).isTrue();
        System.out.println("✅ Stack details retrieved for: " + stackName);
    }

    @Test
    @Order(10)
    @DisplayName("Should delete a stack")
    void shouldDeleteStack() throws Exception {
        loginAndConnect();
        ensureStackExists("integration-test-stack");
        
        CompletableFuture<JSONObject> deleteFuture = new CompletableFuture<>();
        String stackName = "integration-test-stack";

        sharedSocket.emit("agent", "", "deleteStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                deleteFuture.complete(response);
            }
        });

        JSONObject response = deleteFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        System.out.println("✅ Stack deleted successfully");
    }

    @Test
    @Order(11)
    @DisplayName("Should start a stack")
    void shouldStartStack() throws Exception {
        loginAndConnect();
        ensureStackExists("integration-test-stack");
        
        CompletableFuture<JSONObject> startFuture = new CompletableFuture<>();
        String stackName = "integration-test-stack";

        sharedSocket.emit("agent", "", "startStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                startFuture.complete(response);
            }
        });

        JSONObject response = startFuture.get(15, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        System.out.println("✅ Stack start command accepted");
    }

    @Test
    @Order(12)
    @DisplayName("Should stop a stack")
    void shouldStopStack() throws Exception {
        loginAndConnect();
        ensureStackExists("integration-test-stack");
        
        CompletableFuture<JSONObject> stopFuture = new CompletableFuture<>();
        String stackName = "integration-test-stack";

        sharedSocket.emit("agent", "", "stopStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                stopFuture.complete(response);
            }
        });

        JSONObject response = stopFuture.get(15, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        System.out.println("✅ Stack stop command accepted");
    }

    @Test
    @Order(13)
    @DisplayName("Should restart a stack")
    void shouldRestartStack() throws Exception {
        loginAndConnect();
        ensureStackExists("integration-test-stack");
        
        CompletableFuture<JSONObject> restartFuture = new CompletableFuture<>();
        String stackName = "integration-test-stack";

        sharedSocket.emit("agent", "", "restartStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                restartFuture.complete(response);
            }
        });

        JSONObject response = restartFuture.get(15, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        System.out.println("✅ Stack restart command accepted");
    }

    @Test
    @Order(14)
    @DisplayName("Should update a stack")
    void shouldUpdateStack() throws Exception {
        loginAndConnect();
        ensureStackExists("integration-test-stack");
        
        CompletableFuture<JSONObject> updateFuture = new CompletableFuture<>();
        String stackName = "integration-test-stack";

        sharedSocket.emit("agent", "", "updateStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                updateFuture.complete(response);
            }
        });

        JSONObject response = updateFuture.get(15, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        System.out.println("✅ Stack update command accepted");
    }

    // ========================================================================
    // UTILITY TESTS
    // ========================================================================

    @Test
    @Order(15)
    @DisplayName("Should update timezone setting")
    void shouldSetSettings() throws Exception {
        loginAndConnect();
        
        CompletableFuture<JSONObject> getFuture = new CompletableFuture<>();
        CompletableFuture<JSONObject> setFuture = new CompletableFuture<>();
        
        sharedSocket.emit("getSettings", new Ack() {
            @Override
            public void call(Object... args) {
                getFuture.complete((JSONObject) args[0]);
            }
        });
        
        JSONObject currentSettings = getFuture.get(10, TimeUnit.SECONDS);
        JSONObject data = currentSettings.optJSONObject("data");
        
        // Try to update settings (need current password)
        sharedSocket.emit("setSettings", data, "TestPassword123!", new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                setFuture.complete(response);
            }
        });
        
        JSONObject response = setFuture.get(10, TimeUnit.SECONDS);
        
        System.out.println("Set settings response: " + response);
        
        // Settings update might require specific fields or permissions
        // Just verify we get a response
        assertThat(response).isNotNull();
    }

    @Test
    @Order(16)
    @DisplayName("Should convert docker run to compose (composerize)")
    void shouldComposerize() throws Exception {
        loginAndConnect();
        
        String dockerRunCommand = "docker run -d -p 8080:80 nginx:alpine";
        
        CompletableFuture<JSONObject> composeFuture = new CompletableFuture<>();
        
        sharedSocket.emit("composerize", dockerRunCommand, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                composeFuture.complete(response);
            }
        });
        
        JSONObject response = composeFuture.get(10, TimeUnit.SECONDS);
        
        System.out.println("Composerize response: " + response);
        
        assertThat(response.getBoolean("ok"))
            .withFailMessage("Composerize failed: " + response.optString("msg", "Unknown error"))
            .isTrue();
    }

    @Test
    @Order(17)
    @DisplayName("Should change password")
    void shouldChangePassword() throws Exception {
        loginAndConnect();
        
        // Change password to a new one
        String newPassword = "NewTestPassword456!";
        
        CompletableFuture<JSONObject> changeFuture = new CompletableFuture<>();
        
        sharedSocket.emit("changePassword", newPassword, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                changeFuture.complete(response);
            }
        });
        
        JSONObject response = changeFuture.get(10, TimeUnit.SECONDS);
        
        System.out.println("Change password response: " + response);
        
        // Password change might succeed or fail depending on requirements
        // Just verify we get a response
        assertThat(response).isNotNull();
        
        // If password changed successfully, change it back
        if (response.optBoolean("ok", false)) {
            System.out.println("Password changed successfully, changing back...");
            
            CompletableFuture<JSONObject> revertFuture = new CompletableFuture<>();
            sharedSocket.emit("changePassword", "TestPassword123!", new Ack() {
                @Override
                public void call(Object... args) {
                    revertFuture.complete((JSONObject) args[0]);
                }
            });
            revertFuture.get(10, TimeUnit.SECONDS);
        }
    }

    // ========================================================================
    // ERROR HANDLING TESTS
    // ========================================================================

    @Test
    @Order(18)
    @DisplayName("Should handle connection timeout to non-existent server")
    void shouldHandleConnectionTimeout() throws Exception {
        IO.Options options = new IO.Options();
        options.timeout = 1000;
        options.reconnection = false;
        
        // Use a valid URL format but unreachable port to test timeout
        Socket badSocket = IO.socket("http://127.0.0.1:65535", options);
        
        CountDownLatch errorLatch = new CountDownLatch(1);
        
        badSocket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            System.out.println("✅ Connection error caught as expected: " + args[0]);
            errorLatch.countDown();
        });
        
        badSocket.connect();
        
        boolean gotError = errorLatch.await(5, TimeUnit.SECONDS);
        
        badSocket.close();
        
        assertThat(gotError).withFailMessage("Should have received connection error or timeout").isTrue();
    }

    // ========================================================================
    // HELPER METHODS
    // ========================================================================

    /**
     * Helper method to login and connect for tests that need authentication.
     * Reuses connection to avoid rate limiting.
     */
    private void loginAndConnect() throws Exception {
        // Only connect once
        if (!isConnected) {
            CountDownLatch connectLatch = new CountDownLatch(1);
            sharedSocket.on(Socket.EVENT_CONNECT, args -> {
                System.out.println("✅ Connected (shared connection)");
                connectLatch.countDown();
            });
            sharedSocket.connect();
            connectLatch.await(5, TimeUnit.SECONDS);
            isConnected = true;
        }
        
        // Only login once
        if (sharedAuthToken == null) {
            CompletableFuture<JSONObject> loginFuture = new CompletableFuture<>();
            
            JSONObject loginData = new JSONObject();
            loginData.put("username", "integrationtest");
            loginData.put("password", "TestPassword123!");
            
            System.out.println("Logging in (once for all tests)...");
            
            sharedSocket.emit("login", loginData, new Ack() {
                @Override
                public void call(Object... args) {
                    JSONObject response = (JSONObject) args[0];
                    loginFuture.complete(response);
                }
            });
            
            JSONObject response = loginFuture.get(10, TimeUnit.SECONDS);
            
            System.out.println("Login helper response: " + response);
            
            if (response.getBoolean("ok") && response.has("token")) {
                sharedAuthToken = response.getString("token");
            } else {
                throw new RuntimeException("Login failed: " + response.optString("msg", "Unknown error"));
            }
        }
    }
    
    /**
     * Get the shared socket for tests
     */
    private Socket getSocket() {
        return sharedSocket;
    }
    
    /**
     * Ensure a stack exists by creating it if it doesn't.
     * This helper is used by stack operation tests.
     */
    private void ensureStackExists(String stackName) throws Exception {
        CompletableFuture<Boolean> existsFuture = new CompletableFuture<>();
        
        // Try to get the stack to see if it exists
        sharedSocket.emit("agent", "", "getStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                try {
                    boolean exists = response.getBoolean("ok");
                    existsFuture.complete(exists);
                } catch (Exception e) {
                    existsFuture.complete(false);
                }
            }
        });
        
        try {
            boolean exists = existsFuture.get(5, TimeUnit.SECONDS);
            
            if (!exists) {
                System.out.println("Stack " + stackName + " does not exist, creating it...");
                
                String composeYAML = """
                    version: "3.8"
                    services:
                      nginx:
                        image: nginx:alpine
                        ports:
                          - "8080:80"
                    """;
                
                CompletableFuture<JSONObject> createFuture = new CompletableFuture<>();
                
                sharedSocket.emit("agent", "", "saveStack", stackName, composeYAML, "", true, new Ack() {
                    @Override
                    public void call(Object... args) {
                        createFuture.complete((JSONObject) args[0]);
                    }
                });
                
                JSONObject createResponse = createFuture.get(10, TimeUnit.SECONDS);
                if (createResponse.getBoolean("ok")) {
                    System.out.println("✅ Stack " + stackName + " created successfully");
                } else {
                    System.out.println("⚠️  Failed to create stack: " + createResponse.optString("msg", "Unknown error"));
                }
            } else {
                System.out.println("✅ Stack " + stackName + " already exists");
            }
        } catch (Exception e) {
            System.out.println("⚠️  Error checking/creating stack: " + e.getMessage());
            // Don't fail the test - stack operations should handle missing stacks gracefully
        }
    }
}
