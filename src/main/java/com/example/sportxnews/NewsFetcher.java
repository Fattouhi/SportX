package com.example.sportxnews;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewsFetcher {
    private static final String API_KEY = "a88ba1292c6b45449bdaa45e2783512e";
    private static final String BASE_URL = "https://newsapi.org/v2/everything";

    public static String fetchSportsNews(String sport) {
        try {
            String query = "q=" + sport + "&apiKey=" + API_KEY;
            URL url = new URL(BASE_URL + "?" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.setReadTimeout(5000); // 5 seconds timeout

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Log the JSON response for debugging
            System.out.println("JSON Response: " + response.toString());

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray articles = jsonResponse.getJSONArray("articles");

            // Extract news titles, links, and images
            StringBuilder news = new StringBuilder();
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.optString("title", "No title available");
                String link = article.optString("url", "#");
                String imageUrl = article.optString("urlToImage", "https://via.placeholder.com/200x100");

                System.out.println("Title: " + title);
                System.out.println("Link: " + link);
                System.out.println("Image URL: " + imageUrl);

                // Format: "Titre: Lien: ImageURL"
                news.append(title).append(":").append(link).append(":").append(imageUrl).append("\n");
            }

            return news.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch news.";
        }
    }
}