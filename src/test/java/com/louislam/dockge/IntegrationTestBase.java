package com.louislam.dockge;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Base class for Dockge integration tests
 * Provides Socket.IO connection setup and teardown
 */
public class IntegrationTestBase {
    
    protected static final String BACKEND_HOST = "http://localhost";
    protected static final int BACKEND_PORT = 5001;
    protected static Socket socket;
    
    @BeforeAll
    public static void setUpBase() throws URISyntaxException, InterruptedException {
        String url = BACKEND_HOST + ":" + BACKEND_PORT;
        IO.Options options = IO.Options.builder()
                .setTransports(new String[]{"websocket"})
                .setReconnection(true)
                .build();
        
        socket = IO.socket(url, options);
        socket.connect();
        
        // Wait for connection
        CountDownLatch connectLatch = new CountDownLatch(1);
        socket.on(Socket.EVENT_CONNECT, args -> connectLatch.countDown());
        
        if (!connectLatch.await(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("Failed to connect to backend");
        }
    }
    
    @AfterAll
    public static void tearDownBase() {
        if (socket != null && socket.connected()) {
            socket.disconnect();
        }
    }
    
    /**
     * Emit a Socket.IO event and wait for response
     */
    protected static Object emitAndWait(String event, Object... args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        final Object[] response = new Object[1];
        
        socket.emit(event, args, new io.socket.client.Ack() {
            @Override
            public void call(Object... objects) {
                if (objects.length > 0) {
                    response[0] = objects[0];
                }
                latch.countDown();
            }
        });
        
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout waiting for response to: " + event);
        }
        
        return response[0];
    }
}
