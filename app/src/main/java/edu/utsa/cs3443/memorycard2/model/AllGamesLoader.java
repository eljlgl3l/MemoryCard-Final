package edu.utsa.cs3443.memorycard2.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Used to make All Console Games
 */
public class AllGamesLoader {

    private ArrayList<String> xboxGames = new ArrayList<>();
    private ArrayList<String> playstationGames = new ArrayList<>();
    private ArrayList<String> nintendoGames = new ArrayList<>();
    private ArrayList<String> steamGames = new ArrayList<>();


    public void loadAllGames(Context context, String csvFilename) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(csvFilename))
            );

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length < 13) {
                    //ensures that any games that do not have all CSV cells are not counted
                    continue;
                }


                String title = fields[0].trim();
                String platform = fields[1].trim().toLowerCase();

                if (platform.contains("xbox")) {
                    xboxGames.add(title);
                } else if (platform.contains("ps")) {
                    playstationGames.add(title);
                } else if (platform.equals("nintendoswitch")) {
                    nintendoGames.add(title);
                } else if (platform.equals("steam")) {
                    steamGames.add(title);
                }
            }

            reader.close();

        } catch (Exception e) {
            Log.e("AllGamesLoader", "Error loading all games", e);
        }
    }


    public ArrayList<String> getXboxGames() {
        return xboxGames;
    }

    public ArrayList<String> getPlaystationGames() {
        return playstationGames;
    }

    public ArrayList<String> getNintendoGames() {
        return nintendoGames;
    }

    public ArrayList<String> getSteamGames() {
        return steamGames;
    }

}