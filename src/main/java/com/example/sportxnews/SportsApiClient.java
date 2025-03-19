package com.example.sportxnews;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class SportsApiClient {
    private static final String API_KEY = "00397541f1aeaec49f372008d88a5abb"; // Your API key
    private static final String BASE_URL = "https://v1.baseball.api-sports.io"; // Base URL for api-sports.io

    // Fetch upcoming events for a team
    public static String fetchNextEvents(String teamId) {
        String endpoint = "/fixtures"; // Correct endpoint for fixtures
        String urlString = BASE_URL + endpoint + "?team=" + teamId; // Add team ID as a query parameter
        return fetchData(urlString);
    }

    // Generic method to fetch data from the API
    private static String fetchData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-rapidapi-host", "v1.baseball.api-sports.io"); // Required header
            connection.setRequestProperty("x-rapidapi-key", API_KEY); // Pass API key in headers
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.setReadTimeout(5000); // 5 seconds timeout

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Print the raw JSON response for debugging
                System.out.println("Raw JSON Response: " + response.toString());

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.has("response")) {
                    JSONArray events = jsonResponse.getJSONArray("response");
                    StringBuilder eventDetails = new StringBuilder();
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject event = events.getJSONObject(i);
                        String homeTeam = event.getJSONObject("teams").getJSONObject("home").getString("name");
                        String awayTeam = event.getJSONObject("teams").getJSONObject("away").getString("name");
                        String date = event.getString("date");
                        String time = date.split("T")[1].substring(0, 5); // Extract time from date

                        eventDetails.append(homeTeam)
                                .append(" vs ")
                                .append(awayTeam)
                                .append(" on ")
                                .append(date.split("T")[0]) // Extract date
                                .append(" at ")
                                .append(time)
                                .append("\n");
                    }
                    return eventDetails.toString();
                } else {
                    return "No events found for the selected category.";
                }
            } else {
                return "Failed to fetch events. Response code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch events: " + e.getMessage();
        }
    }
    public static String fetchTeams() {
        String endpoint = "/teams";
        String urlString = BASE_URL + endpoint + "?league=1&season=2023"; // Example for MLB (league ID 1)
        return fetchData(urlString);
    }
}