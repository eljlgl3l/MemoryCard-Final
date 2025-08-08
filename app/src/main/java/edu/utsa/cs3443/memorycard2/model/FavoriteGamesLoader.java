package edu.utsa.cs3443.memorycard2.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FavoriteGamesLoader {

    private ArrayList<String> xboxFavoriteGames = new ArrayList<>();
    private ArrayList<String> playstationFavoriteGames = new ArrayList<>();
    private ArrayList<String> nintendoFavoriteGames = new ArrayList<>();
    private ArrayList<String> steamFavoriteGames = new ArrayList<>();


    public void loadFavoriteGames(Context context, String csvFilename) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(csvFilename))
            );

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length < 13){
                    //ensures that any games that do not have all CSV cells are not counted
                    continue;
                }

                String title = fields[0].trim();
                String platform = fields[1].trim().toLowerCase();
                String favoriteStatus = fields[12].trim().toLowerCase();

                if (!favoriteStatus.equals("yes")){
                    //used only for favorite games loader
                    continue;
                }

                if (platform.contains("xbox")) {
                    xboxFavoriteGames.add(title);
                } else if (platform.contains("ps")) {
                    playstationFavoriteGames.add(title);
                } else if (platform.equals("nintendo")) {
                    nintendoFavoriteGames.add(title);
                } else if (platform.equals("steam")) {
                    steamFavoriteGames.add(title);
                }
            }

            reader.close();

        } catch (Exception e) {
            Log.e("FavoriteGamesLoader", "Error loading favorite games", e);
        }
    }


    public ArrayList<String> getXboxFavoriteGames() {
        return xboxFavoriteGames;
    }

    public ArrayList<String> getPlaystationFavoriteGames() {
        return playstationFavoriteGames;
    }

    public ArrayList<String> getNintendoFavoriteGames() {
        return nintendoFavoriteGames;
    }

    public ArrayList<String> getSteamFavoriteGames() {
        return steamFavoriteGames;
    }

}
