package com.ouraring.api.client;

import com.ouraring.api.*;
import com.ouraring.api.handler.ApiClient;
import com.ouraring.api.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Client class for fetching data from the Oura API
 * @author Jussi Hynninen
 */
public class OuraClient {

    private final ApiClient client;
    private DailyActivityApi daApi;
    private HeartRateApi hrApi;
    private PersonalInfoApi piApi;
    private SessionsApi sApi;
    private TagsApi tApi;
    private WorkoutsApi wApi;

    private String bearerToken = null;

    private static final String defaultScheme = "https";
    private static final String defaultHost = "api.ouraring.com";
    private static final int defaultPort = -1;
    private long timeoutSecs = 10L;

    private OuraClient() {
        this(defaultScheme, defaultHost, defaultPort);
    }

    private OuraClient(String scheme, String host, int port) {
        client = new ApiClient();
        assert Arrays.asList("http", "https").contains(scheme) : "Scheme has to be either http or https";
        client.setScheme(scheme);
        client.setHost(host);
        client.setPort(Integer.valueOf(port) > 0 ? port : defaultPort);
    }

    private OuraClient(ApiClient apiClient) {
        client = apiClient;
    }

    private void init() {
        if (bearerToken != null && !bearerToken.trim().isEmpty()) {
            client.setRequestInterceptor((builder) -> builder.header("Authorization", "Bearer " + bearerToken));
        }
        daApi = new DailyActivityApi(client);
        hrApi = new HeartRateApi(client);
        piApi = new PersonalInfoApi(client);
        sApi = new SessionsApi(client);
        tApi = new TagsApi(client);
        wApi = new WorkoutsApi(client);
    }

    public DailyActivityResponse getDailyActivity(LocalDate startDate, LocalDate endDate, String nextToken) throws Exception {
        return daApi.dailyActivityRouteDailyActivityGet(
                startDate, endDate, nextToken
        ).get(timeoutSecs, TimeUnit.SECONDS);
    }

    public HeartRateResponse getHeartRate(LocalDateTime startDate, LocalDateTime endDate, String nextToken) throws Exception {
        return hrApi.heartrateRouteHeartrateGet(
                startDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                endDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                nextToken
        ).get(timeoutSecs, TimeUnit.SECONDS);
    }

    public PersonalInfoResponse getPersonalInfo() throws Exception {
        return piApi.personalInfoRouteGet().get(timeoutSecs, TimeUnit.SECONDS);
    }

    public SessionResponse getSession(LocalDate startDate, LocalDate endDate, String nextToken) throws Exception {
        return sApi.sessionsRouteSessionGet(
                startDate, endDate, nextToken
        ).get(timeoutSecs, TimeUnit.SECONDS);
    }

    public TagResponse getTags(LocalDate startDate, LocalDate endDate, String nextToken) throws Exception {
        return tApi.tagsRouteTagGet(
                startDate, endDate, nextToken
        ).get(timeoutSecs, TimeUnit.SECONDS);
    }

    public WorkoutResponse getWorkouts(LocalDate startDate, LocalDate endDate, String nextToken) throws Exception {
        return wApi.workoutsRouteWorkoutGet(
                startDate, endDate, nextToken
        ).get(timeoutSecs, TimeUnit.SECONDS);
    }

    /**
     * Builder class for OuraClient instances
     */
    public static class Builder {

        private final OuraClient client;

        /**
         * Constructs an OuraClient with default settings
         */
        public Builder() {
            client = new OuraClient();
        }

        /**
         * Constructs an OuraClient with given server settings
         * @param scheme <i>http</i> or <i>https</i>
         * @param host DNS name or IP address of the server
         * @param port Port to use for connections
         */
        public Builder(String scheme, String host, int port) {
            client = new OuraClient(scheme, host, port);
        }

        /**
         * Constructs an OuraClient based on given preconfigured ApiClient instance
         * @param apiClient
         */
        public Builder(ApiClient apiClient) {
            client = new OuraClient(apiClient);
        }

        /**
         * Sets the bearer token to be used for connections
         * @param accessToken
         * @return
         */
        public Builder withAccessToken(String accessToken) {
            client.bearerToken = accessToken;
            return this;
        }

        /**
         * Sets the timeout for asynchronous API calls to complete
         * @param timeoutInSecs Timeout in seconds - has to be greater than 0
         * @return
         */
        public Builder withTimeout(long timeoutInSecs) {
            assert timeoutInSecs > 0 : "The timeout has to be > 0";
            client.timeoutSecs = timeoutInSecs;
            return this;
        }

        /**
         * Builds the OuraClient instance
         * @return A configured OuraClient instance
         */
        public OuraClient build() {
            client.init();
            return client;
        }

    }
}
