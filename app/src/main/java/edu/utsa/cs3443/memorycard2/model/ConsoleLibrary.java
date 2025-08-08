package edu.utsa.cs3443.memorycard2.model;



import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;
import edu.utsa.cs3443.memorycard2.model.Game;
import edu.utsa.cs3443.memorycard2.model.User;

public class ConsoleLibrary implements Serializable {

    private String consoleName;
    private ArrayList<Game> consoleGames;

    public ConsoleLibrary(String consoleName, ArrayList<Game> consoleGames) {
        this.consoleName = consoleName;
        if(consoleGames!=null){
            // needed to implement this to prevent crashes
            this.consoleGames = consoleGames;
        }else{
            this.consoleGames = new ArrayList<>();
        }

    }
    public ConsoleLibrary(String consoleName) {
        this.consoleName = consoleName;
        this.consoleGames = new ArrayList<>();
    }

    public ArrayList<Game> getConsoleFavorites() {
        ArrayList<Game> favorites = new ArrayList<>();
        for (Game game : consoleGames) {
            if (game.getIsFavorite().equals("yes")) {
                favorites.add(game);
            }
        }
        return favorites;
    }

    public ArrayList<Game> getConsoleCompleted() {
        ArrayList<Game> completed = new ArrayList<>();
        for (Game game : consoleGames) {
            if (game.getCompletionStatus().equals("yes")) {
                completed.add(game);
            }
        }
        return completed;

    }


    public int appendGameToCSV(Game game, Context context) {
        String CSV_FILENAME = "user_game_library.csv";
        String CSV_HEADER =
                "gameTitle,gamePlatform,gameGenre,gameReleaseDate,gamePublisher,"
                        + "completionStatus,completionYear,gameReview,gameRating,"
                        + "gamePlaytime,recentPlaytime,isFavorite,drawableName";

        File file = new File(context.getFilesDir(), CSV_FILENAME);
        boolean isNewFile = !file.exists();

        String csvRow = String.format(
                "%s,%s,%s,%s,%s,%s,%d,%s,%d,%.2f,%.2f,%s,%s",
                game.getGameTitle(),
                game.getGamePlatform(),
                game.getGameGenre(),
                game.getGameReleaseDate(),
                game.getGamePublisher(),
                game.getCompletionStatus(),
                game.getCompletionYear(),
                game.getGameReview(),
                game.getGameRating(),
                game.getGamePlaytime(),
                game.getRecentPlaytime(),
                game.getIsFavorite(),
                game.getDrawableName()
        );

        try (
                // these were all needed to get it to work consistently for the CSV
                FileOutputStream ConsLibOutputStream = context.openFileOutput(CSV_FILENAME, Context.MODE_APPEND);
                OutputStreamWriter ConsLibOutputStreamWriter = new OutputStreamWriter(ConsLibOutputStream, StandardCharsets.UTF_8);
                BufferedWriter ConsLibBufferedWriter = new BufferedWriter(ConsLibOutputStreamWriter);
                PrintWriter ConsLibPrintWriter = new PrintWriter(ConsLibBufferedWriter)
        ) {
            if (isNewFile) {
                //prints the header if no header is present
                ConsLibPrintWriter.println(CSV_HEADER);
            }

            ConsLibPrintWriter.println(csvRow);
            ConsLibPrintWriter.flush();
            return 1;  // success
        } catch (IOException e) {
            Log.e("ADDGAME_ERROR", "Error writing to CSV", e);
            Toast.makeText(context, "Could not save game: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return -1; // failure
        }
    }


    public Integer getNumberOfFavorites(){
        int numFavorites = 0;
        for (Game game : consoleGames) {
            if (game.getIsFavorite().equals("yes")) {
                numFavorites++;
            }
        }
        return numFavorites;
    }


    // Getters and setters
    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public Game getGame(int index) {
        return consoleGames.get(index);
    }

    public ArrayList<Game> getConsoleGames() {
        return consoleGames;
    }


    public void setConsoleGames(ArrayList<Game> consoleGames) {
        this.consoleGames = consoleGames;
    }
    public int getNumberOfGames() {
        return consoleGames.size();
    }

    public void addGame(Game game) {
        consoleGames.add(game);
    }

    public void clearGames() {
        consoleGames.clear();
    }


    @Override
    @NonNull
    public String toString() {
        // build a string that is guaranteed not to be null - was having an issue with it earlier
        return consoleName + " Library: " + consoleGames;

    }
}
