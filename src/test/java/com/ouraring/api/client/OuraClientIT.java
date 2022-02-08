package com.ouraring.api.client;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Ignore
public class OuraClientIT {


    private static OuraClient client;
    private static LocalDateTime start;
    private static LocalDateTime end;

    @BeforeClass
    public static void setupClient() {
        client = new OuraClient.Builder().withAccessToken("___MY_TOKEN_HERE___").build();
        end = LocalDateTime.now();
        start = end.minus(30L, ChronoUnit.DAYS);

    }

    @Test
    public void fetchDailyActivity() throws Exception {
        System.out.println(client.getDailyActivity(start.toLocalDate(), end.toLocalDate(), null));
    }

    @Test
    public void fetchHeartRate() throws Exception {
        System.out.println(client.getHeartRate(start, end, null));
    }

    @Test
    public void fetchPersonalInfo() throws Exception {
        System.out.println(client.getPersonalInfo());
    }

    @Test
    public void fetchSessions() throws Exception {
        System.out.println(client.getSession(start.toLocalDate(), end.toLocalDate(), null));
    }

    @Test
    public void fetchTags() throws Exception {
        System.out.println(client.getTags(start.toLocalDate(), end.toLocalDate(), null));
    }

    @Test
    public void fetchWorkouts() throws Exception {
        System.out.println(client.getWorkouts(start.toLocalDate(), end.toLocalDate(), null));
    }

}
