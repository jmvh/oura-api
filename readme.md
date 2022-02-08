# oura-api

Client implementation for Oura API 2.0 using the native HttpClient library and asynchronous calls. Requires Java 11 or newer.

For now, only supports personal token authentication. OAuth2 to be implemented.

### Usage
Fetch daily activity for the last 30 days
```
OuraClient client = new OuraClient.Builder().withAccessToken("___MY_TOKEN_HERE___").build();
LocalDateTime end = LocalDateTime.now();
LocalDateTime start = end.minus(30L, ChronoUnit.DAYS);
// Fetches the first page
List<DailyActivityModel> data = new ArrayList<>();
DailyActivityResponse response = client.getDailyActivity(start.toLocalDate(), end.toLocalDate(), null);
data.addAll(response.getData());
while(response.getNextToken() != null) {
    response = client.getDailyActivity(start.toLocalDate(), end.toLocalDate(), response.getNextToken());
    data.addAll(response.getData());
}
...
```
