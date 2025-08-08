package edu.utsa.cs3443.memorycard2.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CompletedGamesLoader {

    private ArrayList<String> xboxCompletedGames = new ArrayList<>();
    private ArrayList<String> playstationCompletedGames = new ArrayList<>();
    private ArrayList<String> nintendoCompletedGames = new ArrayList<>();
    private ArrayList<String> steamCompletedGames = new ArrayList<>();


    public void loadCompletedGames(Context context, String csvFilename) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(csvFilename))
            );

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length < 13){
                    continue;
                    //ensures that any games that do not have all CSV cells are not counted
                }


                String title = fields[0].trim();
                String platform = fields[1].trim().toLowerCase();
                String completionStatus = fields[5].trim().toLowerCase();

                if (!completionStatus.equals("yes")){
                    //used only for this loader (completed games)
                    continue;
                }

                if (platform.contains("xbox")) {
                    xboxCompletedGames.add(title);
                } else if (platform.contains("ps")) {
                    playstationCompletedGames.add(title);
                } else if (platform.equals("nintendo")) {
                    nintendoCompletedGames.add(title);
                } else if (platform.equals("steam")) {
                    steamCompletedGames.add(title);
                }
            }

            reader.close();

        } catch (Exception e) {
            Log.e("CompletedGamesLoader", "Error loading completed games", e);
        }
    }


    public ArrayList<String> getXboxCompletedGames() {
        return xboxCompletedGames;
    }

    public ArrayList<String> getPlaystationCompletedGames() {
        return playstationCompletedGames;
    }

    public ArrayList<String> getNintendoCompletedGames() {
        return nintendoCompletedGames;
    }

    public ArrayList<String> getSteamCompletedGames() {
        return steamCompletedGames;
    }

}
