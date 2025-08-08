package edu.utsa.cs3443.memorycard2.model;

import android.content.Context;
import android.os.Handler;
import android.widget.ArrayAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Stopwatch {
    private long startTime = 0L;
    private long elapsedBeforeStart = 0L;
    private boolean isRunning = false;

    public void start() {
        if (isRunning) return;
        startTime = System.currentTimeMillis();
        isRunning = true;
    }

    public void stop(Game game) {
        if (!isRunning) return;
        long now = System.currentTimeMillis();
        elapsedBeforeStart += now - startTime;
        isRunning = false;

        // compute decimal hours
        double hoursToAdd = elapsedBeforeStart / 3_600_000.0;
        game.setRecentPlaytime(hoursToAdd);
        game.setGamePlaytime(game.getGamePlaytime() + hoursToAdd);
    }

    public void reset() {
        isRunning = false;
        elapsedBeforeStart = 0L;
        startTime = 0L;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public long getElapsedMillis() {
        if (!isRunning) return elapsedBeforeStart;
        return elapsedBeforeStart + (System.currentTimeMillis() - startTime);
    }

    public String getFormattedTime() {
        long ms = getElapsedMillis();
        int seconds = (int) (ms / 1000) % 60;
        int minutes = (int) (ms / 1000) / 60 % 60;
        int hours   = (int) (ms / 1000) / 3600;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Persist the entire library back to CSV.
     * This belongs to the Model because it’s raw data I/O.
     */
    public void saveAllToCsv(Context ctx, List<ConsoleLibrary> allLibs) {
        File csvFile = new File(ctx.getFilesDir(), "user_game_library.csv");
        try (BufferedWriter w = new BufferedWriter(new FileWriter(csvFile, false))) {
            w.write("gameTitle,gamePlatform,gameGenre,gameReleaseDate,"
                    + "gamePublisher,completionStatus,completionYear,gameReview,"
                    + "gameRating,gamePlaytime,recentPlaytime,favoriteStatus,drawableName");
            w.newLine();

            for (ConsoleLibrary lib : allLibs) {
                for (Game game : lib.getConsoleGames()) {
                    String row = String.format(
                            "%s,%s,%s,%s,%s,%s,%d,%s,%d,%.2f,%.2f,%s,%s",
                            game.getGameTitle(),
                            game.getGamePlatform(),
                            game.getGameGenre(),
                            game.getGameReleaseDate(),
                            game.getGamePublisher(),
                            game.getCompletionStatus(),
                            game.getCompletionYear(),
                            game.getGameReview().replace(",", ";"),
                            game.getGameRating(),
                            game.getGamePlaytime(),
                            game.getRecentPlaytime(),
                            game.getIsFavorite(),
                            game.getDrawableName()
                    );
                    w.write(row);
                    w.newLine();
                }
            }
        } catch (IOException e) {
            // you might throw a custom exception here or notify the caller
            e.printStackTrace();
        }
    }
}