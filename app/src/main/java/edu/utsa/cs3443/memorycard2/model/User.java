package edu.utsa.cs3443.memorycard2.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

import edu.utsa.cs3443.memorycard2.CreateGameAppData;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/**
 * @author Maxwell
 */

// The User class is the model for user data/stats and game libraries
public class User implements Serializable {

    private String username;

    private ArrayList<String> favoriteConsoles = new ArrayList<>();
    private int totalGamesPlayed = 0;
    private double recentPlaytime = 0;
    private double totalPlaytime = 0;
    private String favoriteConsole = "";
    private int numberOfFavoriteGamesInFavoriteConsole = 0;
    private double completionRate = 0.0;

    private ArrayList<ConsoleLibrary> fullLibrary = new ArrayList<>();

    // Constructor left empty because all data is loaded from asset files
    public User() { }

    // loads default user and game data from csv files upon startup and initializes all variables
    public void loadUser(Context context) {
        // 1) Fetch the GameRepository from your Application subclass
        CreateGameAppData appData =
                (CreateGameAppData) context.getApplicationContext();
        GamesList repo = appData.getGameRepository();

        // 2) Load every Game from the CSV
        List<Game> allGames = Collections.emptyList();
        try {
            allGames = repo.getAllGames();
        } catch (IOException e) {
            Log.e("User.loadUser", "Failed to load games", e);
        }

        // 3) Create four fresh console libraries
        ConsoleLibrary xbox = new ConsoleLibrary("Xbox");
        ConsoleLibrary playstation = new ConsoleLibrary("PlayStation");
        ConsoleLibrary nintendo = new ConsoleLibrary("Nintendo Switch");
        ConsoleLibrary steam = new ConsoleLibrary("Steam");

        // 4) Group each Game into its library
        for (Game g : allGames) {
            String p = g.getGamePlatform();
            if (p.startsWith("Xbox")) {
                xbox.addGame(g);
            } else if (p.startsWith("PS")) {
                playstation.addGame(g);
            } else if (p.contains("Nintendo")) {
                nintendo.addGame(g);
            } else if (p.equals("Steam")) {
                steam.addGame(g);
            }
        }

        // 5) Build your fullLibrary list
        fullLibrary.clear();
        fullLibrary.add(xbox);
        fullLibrary.add(playstation);
        fullLibrary.add(nintendo);
        fullLibrary.add(steam);

        // 6) Load the username from assets (unchanged)
        try (Scanner scan = new Scanner(context.getAssets().open("username.csv"))) {
            if (scan.hasNextLine()) {
                username = scan.nextLine().trim();
            }
        } catch (IOException e) {
            Log.w("User.loadUser", "username.csv missing, defaulting", e);
            username = "Player";
        }

        // 7) Compute all your aggregates
        calculateTotalGamesPlayed();
        calculateRecentPlaytime();
        calculateTotalPlaytime();
        calculateFavoriteConsoles();
        calculateFavoriteConsole();
        calculateCompletionRate();
    }

    public void calculateTotalGamesPlayed() {
        int totalGamesPlayed = 0;
        for (ConsoleLibrary console : fullLibrary) {
            for (Game game : console.getConsoleGames()) {
                totalGamesPlayed++;
                Log.d("calctotplayed",game.toString() + "\n");
            }
        }
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public void calculateRecentPlaytime() {
        double recentPlaytime = 0;
        for (ConsoleLibrary console : fullLibrary) {
            for (Game game : console.getConsoleGames()) {
                recentPlaytime += game.getRecentPlaytime();
                Log.d("calcrecplaytime",game.toString() + "\n");
            }
        }
        this.recentPlaytime = recentPlaytime;
    }

    public void calculateTotalPlaytime() {
        double totalPlaytime = 0;
        for (ConsoleLibrary console : fullLibrary) {
            for (Game game : console.getConsoleGames()) {
                totalPlaytime += game.getGamePlaytime();
                Log.d("calctotplaytime",game.toString() + "\n");
            }
        }
        this.totalPlaytime = totalPlaytime;
    }

//    public void calculateFavoriteGenres() {
//        Map<String, Integer> genreMap = new HashMap<>();
//        for (ConsoleLibrary console : fullLibrary) {
//            for (Game game : console.getConsoleGames()) {
//                if (genreMap.containsKey(game.getGameGenre())) {
//                    genreMap.put(game.getGameGenre(), genreMap.get(game.getGameGenre()) + 1);
//                } else {
//                    genreMap.put(game.getGameGenre(), 1);
//                }
//            }
//        }
//        favoriteGenres.clear();
//        int largest = 0;
//        for (Map.Entry<String, Integer> entry : genreMap.entrySet()) {
//            if (entry.getValue() > largest) {
//                largest = entry.getValue();
//                favoriteGenres.clear();
//                favoriteGenres.add(entry.getKey() + " (" + largest + " games)");
//            } else if (entry.getValue() == largest) {
//                favoriteGenres.add("\n" + entry.getKey() + " (" + largest + " games)");
//            }
//        }
//    }

    public void calculateFavoriteConsoles() {
        int xboxFavorites = 0;
        int psFavorites = 0;
        int nintendoFavorites = 0;
        int steamFavorites = 0;
        for (ConsoleLibrary console : fullLibrary) {
            if (console.getConsoleName().equals("Xbox")) {
                xboxFavorites += console.getConsoleGames().size();
            } else if (console.getConsoleName().equals("PlayStation")) {
                psFavorites += console.getConsoleGames().size();
            } else if (console.getConsoleName().equals("Nintendo Switch")) {
                nintendoFavorites += console.getConsoleGames().size();
            } else if (console.getConsoleName().equals("Steam")) {
                steamFavorites += console.getConsoleGames().size();
            }
        }
        favoriteConsoles.clear();
        int largest = xboxFavorites;
        favoriteConsoles.add("Xbox (" + xboxFavorites + " favorites)");
        if (psFavorites > largest) {
            largest = psFavorites;
            favoriteConsoles.clear();
            favoriteConsoles.add("Playstation (" + psFavorites + " favorites)");
        } else if (psFavorites == largest) {
            favoriteConsoles.add("\nPlaystation (" + psFavorites + " favorites)");
        }
        if (nintendoFavorites > largest) {
            largest = nintendoFavorites;
            favoriteConsoles.clear();
            favoriteConsoles.add("Nintendo (" + nintendoFavorites + " favorites)");
        } else if (nintendoFavorites == largest) {
            favoriteConsoles.add("\nNintendo (" + nintendoFavorites + " favorites)");
        }
        if (steamFavorites > largest) {
            largest = steamFavorites;
            favoriteConsoles.clear();
            favoriteConsoles.add("Steam (" + steamFavorites + " favorites)");
        } else if (steamFavorites == largest) {
            favoriteConsoles.add("\nSteam (" + steamFavorites + " favorites)");
        }
    }

    public void calculateFavoriteConsole() {
        String bestName = "None";
        int bestCount = -1;

        for (ConsoleLibrary lib : fullLibrary) {
            int favCount = lib.getNumberOfFavorites();
            if (favCount > bestCount) {
                bestCount = favCount;
                bestName  = lib.getConsoleName();
            }
        }

        this.favoriteConsole = bestName;
        this.numberOfFavoriteGamesInFavoriteConsole = bestCount;
    }


    public void calculateCompletionRate(){
        int completedGames = 0;
        int totalGames = 0;

        for(ConsoleLibrary lib : fullLibrary){
            for(Game game : lib.getConsoleGames()){
                totalGames++;
                if(game.getCompletionStatus().equals("yes")){
                    completedGames++;
                }
            }
        }

        if(totalGames == 0){
            this.completionRate = 0.0;
        }else{
            this.completionRate = (completedGames * 100.0) / totalGames;
        }

    }

    // Getters and setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getFavoriteConsoles() {
        calculateFavoriteConsoles();
        return favoriteConsoles;
    }
    public void setFavoriteConsoles(ArrayList<String> favoriteConsoles) {
        this.favoriteConsoles = favoriteConsoles;
    }
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }
    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public double getTotalPlaytime() {
        return totalPlaytime;
    }
    public void setTotalPlaytime(double totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }

    public double getRecentPlaytime() {
        return recentPlaytime;
    }
    public void setRecentPlaytime(double recentPlaytime) {
        this.recentPlaytime = recentPlaytime;
    }


    public int getNumberOfFavoriteGamesInFavoriteConsole(){
        return numberOfFavoriteGamesInFavoriteConsole;
    }
    public void setNumberOfFavoriteGamesInFavoriteConsole(int number){
        this.numberOfFavoriteGamesInFavoriteConsole = number;
    }

    public String getFavoriteConsole(){
        return favoriteConsole;
    }
    public void setFavoriteConsole(String favoriteConsole) {
        this.favoriteConsole = favoriteConsole;
    }

    public void setCompletionRate(double completionRate){
        this.completionRate = completionRate;
    }
    public double getCompletionRate(){
        return this.completionRate;
    }

    public ArrayList<ConsoleLibrary> getFullLibrary() {
        return fullLibrary;
    }
    public void setFullLibrary(ArrayList<ConsoleLibrary> fullLibrary) { this.fullLibrary = fullLibrary; }
}