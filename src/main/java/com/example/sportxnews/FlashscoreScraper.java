package com.example.sportxnews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FlashscoreScraper {

    public static String fetchLiveScores() {
        StringBuilder liveScores = new StringBuilder();
        try {
            // Connect to Flashscore and get the HTML document
            Document doc = Jsoup.connect("https://www.flashscore.com/").get();

            // Select the elements containing the live scores
            Elements matches = doc.select("div.event__match--live");

            // Iterate through the matches and extract the data
            for (Element match : matches) {
                String homeTeam = match.select("div.event__participant--home").text();
                String awayTeam = match.select("div.event__participant--away").text();
                String score = match.select("div.event__score--home").text() + " - " + match.select("div.event__score--away").text();

                // Append the match details to the liveScores StringBuilder
                liveScores.append(homeTeam).append(" vs ").append(awayTeam).append(": ").append(score).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to fetch live scores.";
        }

        // If no live matches are found, return a message
        if (liveScores.length() == 0) {
            return "No live matches currently.";
        }

        return liveScores.toString();
    }
}