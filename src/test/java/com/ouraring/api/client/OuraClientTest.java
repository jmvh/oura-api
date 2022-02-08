package com.ouraring.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ouraring.api.ApiTest;
import com.ouraring.api.model.PersonalInfoResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;

import java.math.BigDecimal;
import java.util.UUID;

public class OuraClientTest extends ApiTest {

    final String token = UUID.randomUUID().toString();

    @Before
    public void setup() throws JsonProcessingException, InterruptedException {
        mockServerClient.when(
                HttpRequest.request()
                        .withPath("/v2/usercollection/personal_info")
                        .withHeader("Accept", "application/json")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withBody(
                                objectMapper.writeValueAsString(new PersonalInfoResponse()
                                        .age(25)
                                        .biologicalSex("M")
                                        .height(BigDecimal.valueOf(180))
                                        .weight(BigDecimal.valueOf(80))
                                        .email("foo@bar.org")
                                )
                        )
        );
    }

    @Test
    public void withoutAccessToken() {
        try {
            new OuraClient.Builder("http", "localhost", port)
                    .build()
                    .getPersonalInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mockServerClient.verify(
                HttpRequest.request()
                        .withPath("/v2/usercollection/personal_info")
                        .withHeader("Accept", "application/json"),
                VerificationTimes.once()
        );
    }

    @Test
    public void withAccessToken() throws Exception {
        new OuraClient.Builder("http", "localhost", port)
                .withAccessToken(token)
                .withTimeout(10)
                .build()
                .getPersonalInfo();
        mockServerClient.verify(
                HttpRequest.request()
                        .withPath("/v2/usercollection/personal_info")
                        .withHeaders(
                                new Header("Accept", "application/json"),
                                new Header("Authorization", "Bearer " + token)
                        ),
                VerificationTimes.once()
        );
    }
}