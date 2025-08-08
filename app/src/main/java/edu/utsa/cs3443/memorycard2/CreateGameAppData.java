package edu.utsa.cs3443.memorycard2;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import edu.utsa.cs3443.memorycard2.model.Game;
import edu.utsa.cs3443.memorycard2.model.GamesList;
import edu.utsa.cs3443.memorycard2.model.User;
import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;

/**
 * @author Elias
 */

/**
 * This CreateAppData was necessary in order to allow the ConsoleLibrary classes to traverse the different fragments
 * for easy adding to and modifying
 * (Ex. adding a game to a console)
 */



import java.io.File;
import java.util.Collections;
import java.util.List;

public class CreateGameAppData extends Application {
    private static final String TAG             = "AppData";
    private static final String CSV_FILENAME    = "user_game_library.csv";

    private GamesList        gameRepo;
    private User             currentUser;

    private ConsoleLibrary   xboxLibrary;
    private ConsoleLibrary   playstationLibrary;
    private ConsoleLibrary   steamLibrary;
    private ConsoleLibrary   nintendoLibrary;

    @Override
    public void onCreate() {
        super.onCreate();

        // 1) Copy the bundled asset CSV into app-private storage (once only)
        copyCsvFromAssetsIfNeeded();

        // 2) Initialize your games repository to read the now-internal CSV
        File csvFile = new File(getFilesDir(), CSV_FILENAME);
        gameRepo = new GamesList(csvFile);

        // 3) Build empty per-console libraries
        xboxLibrary        = new ConsoleLibrary("Xbox");
        playstationLibrary = new ConsoleLibrary("PlayStation");
        steamLibrary       = new ConsoleLibrary("Steam");
        nintendoLibrary    = new ConsoleLibrary("Nintendo Switch");

        // 4) Load all games and distribute into libraries
        List<Game> allGames;
        try {
            allGames = gameRepo.getAllGames();
        } catch (IOException e) {
            Log.e(TAG, "Failed to load all games", e);
            allGames = Collections.emptyList();
        }

        for (Game g : allGames) {
            String platform = g.getGamePlatform();
            if (platform.startsWith("Xbox")) {
                xboxLibrary.addGame(g);
            } else if (platform.startsWith("PS")) {
                playstationLibrary.addGame(g);
            } else if (platform.contains("Nintendo")) {
                nintendoLibrary.addGame(g);
            } else if (platform.equals("Steam")) {
                steamLibrary.addGame(g);
            }
        }
    }

    /**
     * Copies user_game_library.csv from /assets into getFilesDir() on first launch.
     */
    private void copyCsvFromAssetsIfNeeded() {
        File outFile = new File(getFilesDir(), CSV_FILENAME);
        if (outFile.exists()) {
            Log.d(TAG, "Internal CSV already exists, skipping copy.");
            return;
        }

        try (InputStream in = new BufferedInputStream(getAssets().open(CSV_FILENAME));
             FileOutputStream out = openFileOutput(CSV_FILENAME, Context.MODE_PRIVATE)) {

            byte[] buffer = new byte[4096];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            Log.d(TAG, "Default CSV copied to internal storage.");
        } catch (IOException e) {
            Log.e(TAG, "Error copying CSV from assets", e);
        }
    }

    /** Expose the raw, mutable CSV-backed repository. */
    public GamesList getGameRepository() {
        return gameRepo;
    }

    /** Lazy-init your singleton User. */
    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = new User();
        }
        return currentUser;
    }

    /** Typed access to each console’s library. */
    public ConsoleLibrary getXboxLibrary() {
        return xboxLibrary;
    }

    public ConsoleLibrary getPlaystationLibrary() {
        return playstationLibrary;
    }

    public ConsoleLibrary getSteamLibrary() {
        return steamLibrary;
    }

    public ConsoleLibrary getNintendoLibrary() {
        return nintendoLibrary;
    }
}


