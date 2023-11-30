package org.clic.gamestar.achievementrace;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.logging.Level;

public class State {
    private static File dataFile;
    private static JsonObject state;

    public static void load() {
        State.dataFile = new File(AchievementRace.INSTANCE.getDataFolder(), "runs.json");
        dataFile.getParentFile().mkdirs();

        if (dataFile.exists()) {
            try (FileReader reader = new FileReader(dataFile)) {
                State.state = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            State.state = new JsonObject();
            state.add("scores", new JsonObject());
            state.add("results", new JsonObject());
        }
    }

    public static void saveResult(String playerName) {
        var resultsTable = state.getAsJsonObject("results");
        var currentResult = resultsTable.get(playerName);
        var score = getScore(playerName);

        if (currentResult == null || currentResult.getAsNumber().intValue() < score) {
            resultsTable.addProperty(playerName, score);
        }

        state.getAsJsonObject("scores").remove(playerName);

        save();
    }

    public static void setScore(String playerName, int score) {
        state.getAsJsonObject("scores").addProperty(playerName, score);
        save();
    }

    public static int getScore(String playerName) {
        var score = state.getAsJsonObject("scores").get(playerName);
        return score != null ? score.getAsInt() : 0;
    }

    private static void save() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            writer.write(state.toString());
        } catch (IOException e) {
            AchievementRace.LOGGER.log(Level.SEVERE, "Could not write into data folder: " + e.getMessage());
        }
    }
}
