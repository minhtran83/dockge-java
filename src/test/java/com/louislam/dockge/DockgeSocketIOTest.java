package com.louislam.dockge;

import com.louislam.dockge.IntegrationTestBase;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Complete integration tests for Dockge via Socket.IO.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DockgeSocketIOTest extends IntegrationTestBase {

    protected Socket sharedSocket;
    protected String sharedAuthToken;
    protected boolean isConnected = false;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUpBase();
        
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
    public void tearDownOnce() {
        if (sharedSocket != null) {
            sharedSocket.disconnect();
            sharedSocket.close();
        }
    }

    @Test
    @Order(1)
    public void shouldConnectToServer() throws Exception {
        if (isConnected) return;

        CompletableFuture<Void> connectedFuture = new CompletableFuture<>();
        sharedSocket.on(Socket.EVENT_CONNECT, args -> {
            isConnected = true;
            connectedFuture.complete(null);
        });

        sharedSocket.connect();
        connectedFuture.get(5, TimeUnit.SECONDS);
        assertThat(sharedSocket.connected()).isTrue();
    }

    @Test
    @Order(2)
    public void shouldHandleSetupEvent() throws Exception {
        CompletableFuture<JSONObject> setupFuture = new CompletableFuture<>();
        
        String username = "admin";
        String password = "TestPassword123!";

        // Node.js backend expects separate username and password arguments
        sharedSocket.emit("setup", username, password, new Ack() {
            @Override
            public void call(Object... args) {
                setupFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = setupFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.has("ok")).isTrue();
    }

    @Test
    @Order(3)
    public void shouldLoginWithValidCredentials() throws Exception {
        CompletableFuture<JSONObject> loginFuture = new CompletableFuture<>();
        
        String username = "admin";
        String password = "TestPassword123!";

        // Node.js backend expects separate username and password arguments
        sharedSocket.emit("login", username, password, new Ack() {
            @Override
            public void call(Object... args) {
                loginFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = loginFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        assertThat(response.has("token")).isTrue();
        sharedAuthToken = response.getString("token");
    }

    @Test
    @Order(4)
    public void shouldRejectInvalidCredentials() throws Exception {
        CompletableFuture<JSONObject> loginFuture = new CompletableFuture<>();
        
        String username = "admin";
        String password = "wrongpassword";

        // Node.js backend expects separate username and password arguments
        sharedSocket.emit("login", username, password, new Ack() {
            @Override
            public void call(Object... args) {
                loginFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = loginFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isFalse();
    }

    @Test
    @Order(5)
    public void shouldLoginByToken() throws Exception {
        assertThat(sharedAuthToken).isNotNull();
        
        CompletableFuture<JSONObject> loginFuture = new CompletableFuture<>();
        sharedSocket.emit("loginByToken", sharedAuthToken, new Ack() {
            @Override
            public void call(Object... args) {
                loginFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = loginFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(6)
    public void shouldGetSettings() throws Exception {
        CompletableFuture<JSONObject> settingsFuture = new CompletableFuture<>();
        sharedSocket.emit("getSettings", new Ack() {
            @Override
            public void call(Object... args) {
                settingsFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = settingsFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(7)
    public void shouldGetStackList() throws Exception {
        CompletableFuture<JSONObject> listFuture = new CompletableFuture<>();
        sharedSocket.emit("agent", "", "getStackList", new Ack() {
            @Override
            public void call(Object... args) {
                listFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = listFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(8)
    public void shouldCreateStack() throws Exception {
        CompletableFuture<JSONObject> createFuture = new CompletableFuture<>();
        
        String stackName = "test-integration-stack";
        String composeContent = "services:\n  web:\n    image: nginx:latest";

        sharedSocket.emit("agent", "", "saveStack", stackName, composeContent, "", new Ack() {
            @Override
            public void call(Object... args) {
                createFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = createFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(9)
    public void shouldGetStack() throws Exception {
        CompletableFuture<JSONObject> getFuture = new CompletableFuture<>();
        String stackName = "test-integration-stack";

        sharedSocket.emit("agent", "", "getStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                getFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = getFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        assertThat(response.has("stack")).isTrue();
    }

    @Test
    @Order(10)
    public void shouldDeleteStack() throws Exception {
        CompletableFuture<JSONObject> deleteFuture = new CompletableFuture<>();
        String stackName = "test-integration-stack";

        sharedSocket.emit("agent", "", "deleteStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                deleteFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = deleteFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(11)
    public void shouldStartStack() throws Exception {
        shouldCreateStack();
        
        CompletableFuture<JSONObject> startFuture = new CompletableFuture<>();
        String stackName = "test-integration-stack";

        sharedSocket.emit("agent", "", "startStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                startFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = startFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(12)
    public void shouldStopStack() throws Exception {
        CompletableFuture<JSONObject> stopFuture = new CompletableFuture<>();
        String stackName = "test-integration-stack";

        sharedSocket.emit("agent", "", "stopStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                stopFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = stopFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(13)
    public void shouldRestartStack() throws Exception {
        CompletableFuture<JSONObject> restartFuture = new CompletableFuture<>();
        String stackName = "test-integration-stack";

        sharedSocket.emit("agent", "", "restartStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                restartFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = restartFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(14)
    public void shouldUpdateStack() throws Exception {
        CompletableFuture<JSONObject> updateFuture = new CompletableFuture<>();
        String stackName = "test-integration-stack";

        sharedSocket.emit("agent", "", "updateStack", stackName, new Ack() {
            @Override
            public void call(Object... args) {
                updateFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = updateFuture.get(10, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(15)
    public void shouldSetSettings() throws Exception {
        CompletableFuture<JSONObject> setFuture = new CompletableFuture<>();
        
        JSONObject settingsData = new JSONObject();
        settingsData.put("timezone", "UTC");

        sharedSocket.emit("setSettings", settingsData, "TestPassword123!", new Ack() {
            @Override
            public void call(Object... args) {
                setFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = setFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(16)
    public void shouldComposerize() throws Exception {
        CompletableFuture<JSONObject> composerizeFuture = new CompletableFuture<>();
        String dockerRun = "docker run -d -p 80:80 nginx";

        sharedSocket.emit("composerize", dockerRun, new Ack() {
            @Override
            public void call(Object... args) {
                composerizeFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = composerizeFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
    }

    @Test
    @Order(17)
    public void shouldChangePassword() throws Exception {
        CompletableFuture<JSONObject> changeFuture = new CompletableFuture<>();
        
        sharedSocket.emit("changePassword", "TestPassword123!", "NewPassword123!", new Ack() {
            @Override
            public void call(Object... args) {
                changeFuture.complete((JSONObject) args[0]);
            }
        });

        JSONObject response = changeFuture.get(5, TimeUnit.SECONDS);
        assertThat(response.getBoolean("ok")).isTrue();
        
        // Revert password for other tests
        sharedSocket.emit("changePassword", "NewPassword123!", "TestPassword123!", new Ack() {
            @Override
            public void call(Object... args) {
            }
        });
    }
}
