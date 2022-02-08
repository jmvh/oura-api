/*
 * Oura API
 * # Overview   The Oura API allows Oura users and partner applications to improve their user experience with Oura data.  This document describes the Oura API Version 2 (V2), which supports access to the latest Oura Ring data types.  For access to other data types—which have not yet migrated to V2—refer to the [Oura API Version 1 (V1)](https://cloud.ouraring.com/docs/) documentation.  # Data Access  Individual Oura users can access their own data through the API by using a [Personal Access Token](https://cloud.ouraring.com/personal-access-tokens).  If you want to retrieve data for multiple users, a registered [API Application](https://cloud.ouraring.com/oauth/applications) is required.  API Applications are limited to **10** users before requiring approval from Oura. There is no limit once an application is approved.  Additionally, Oura users **must provide consent** to share each data type an API Application has access to.  All data access requests through the Oura API require [Authentication](https://cloud.ouraring.com/docs/authentication).  Additionally, we recommend that Oura users keep their mobile app updated to support API access for the latest data types.  The Oura API V2 returns a 426 response code for users who do not have an updated version of the app installed.  # Authentication  The Oura API provides two methods for Authentication: (1) the OAuth2 protocol and (2) Personal Access Tokens. For more information on the OAuth2 flow, see our [Authentication instructions](https://cloud.ouraring.com/docs/authentication).  Access tokens must be included in the request header as follows: ```http GET /v2/usercollection/personal_info HTTP/1.1 Host: api.ouraring.com Authorization: Bearer <token> ```  # Oura HTTP Response Codes  | Response Code                        | Description | | ------------------------------------ | - | | 200 OK                               | Successful Response         | | 400 Query Parameter Validation Error | The request contains query parameters that are invalid or incorrectly formatted. | | 426 Minimum App Version Error        | The Oura user's mobile app does not meet the minimum app version requirement to support sharing the requested data type. The Oura user must update their mobile app to enable API access for the requested data type. | | 429 Request Rate Limit Exceeded        | The API is rate limited to 5000 requests in a 5 minute period. You will receive a 429 error code if you exceed this limit. [Contact us](mailto:api-support@ouraring.com) if you expect your usage to exceed this limit.|
 *
 * The version of the OpenAPI document: 2.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.ouraring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ouraring.api.handler.ApiException;
import com.ouraring.api.model.*;

import java.time.LocalDate;

import com.ouraring.api.model.Error;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

/**
 * API tests for DailyActivityApi
 */
public class DailyActivityApiTest extends ApiTest {

    private final DailyActivityApi api = new DailyActivityApi(apiClient);

    @Before
    public void setup() throws JsonProcessingException {
        mockServerClient.reset();
        mockServerClient.when(
                HttpRequest.request()
                        .withPath("/v2/usercollection/daily_activity")
                        .withHeader("Accept", "application/json")

        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withBody(
                                objectMapper.writeValueAsString(new DailyActivityResponse().addDataItem(
                                        new DailyActivityModel()
                                                .activeCalories(500)
                                                .day(LocalDate.now())
                                                .highActivityTime(90)
                                                .lowActivityTime(120)
                                                .contributors(new ActivityContributors().moveEveryHour(5))
                                                .equivalentWalkingDistance(12000)
                                                .nonWearTime(1)
                                                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                                )))
        );
    }

    /**
     * Get Daily Activity
     * <p>
     * Returns Oura Daily Activity data for the specified Oura user within a given timeframe
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void dailyActivityRouteDailyActivityGetTest() throws Exception {
        LocalDate startDate = null;
        LocalDate endDate = null;
        String nextToken = null;
        CompletableFuture<DailyActivityResponse> response =
                api.dailyActivityRouteDailyActivityGet(startDate, endDate, nextToken);

        List<DailyActivityModel> data = response.get(1L, TimeUnit.SECONDS).getData();
        assertEquals(1, data.size());
        assertEquals(Integer.valueOf(500), data.get(0).getActiveCalories());
        assertEquals(LocalDate.now(), data.get(0).getDay());
    }

}
