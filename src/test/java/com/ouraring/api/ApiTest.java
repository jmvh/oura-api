package com.ouraring.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ouraring.api.handler.ApiClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockserver.integration.ClientAndServer;

public abstract class ApiTest {

    protected static ClientAndServer mockServerClient;
    protected static ObjectMapper objectMapper;
    protected static final ApiClient apiClient = new ApiClient();

    protected static String host = "localhost";
    protected static final int port = 8888;

    @BeforeClass
    public static void setupCommon() {
        mockServerClient = new ClientAndServer(port);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        apiClient.setPort(port);
    }

    @AfterClass
    public static void tearDown() {
        mockServerClient.stop();
    }
}
