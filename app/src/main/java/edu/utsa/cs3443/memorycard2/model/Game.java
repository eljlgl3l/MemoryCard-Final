package edu.utsa.cs3443.memorycard2.model;

/*
 * @author: Maxwell
 */

import java.io.Serializable;
import java.util.*;

// the Game class is the model for a single individual game

public class Game implements Serializable {


    // game metadata
    private String gameTitle;
    private String gamePlatform;
    private String gameGenre;
    private String gameReleaseDate;
    private String gamePublisher;


    //user generated game data
    private Boolean completionStatus;
    private int completionYear;
    private String gameReview;
    private int gameRating;
    private double gamePlaytime;
    private double recentPlaytime;
    private Boolean isFavorite;
    private String drawableName;

    public Game(String gameTitle, String gamePlatform, String gameGenre, String gameReleaseDate,
                String gamePublisher, Boolean completionStatus, int completionYear, String gameReview,
                int gameRating, double gamePlaytime, double recentPlaytime, Boolean isFavorite, String drawableName) {
        this.gameTitle = gameTitle;
        this.gamePlatform = gamePlatform;
        this.gameGenre = gameGenre;
        this.gameReleaseDate = gameReleaseDate;
        this.gamePublisher = gamePublisher;
        this.completionStatus = completionStatus;
        this.completionYear = completionYear;
        this.gameReview = gameReview;
        this.gameRating = gameRating;
        this.gamePlaytime = gamePlaytime;
        this.recentPlaytime = recentPlaytime;
        this.isFavorite = isFavorite;
        this.drawableName = drawableName;
    }

    //toString method (to be updated later)
    @Override
    public String toString() {
        return "Game: " + gameTitle + ", Platform: " + gamePlatform + ", Genre: " + gameGenre + ", Release Date: " + gameReleaseDate + ", Publisher: " + gamePublisher;
    }

    //getters and setters
    public String getGameTitle() {return gameTitle;}
    public void setGameTitle(String gameTitle) {this.gameTitle = gameTitle;}

    public String getGamePlatform() {return gamePlatform;}
    public void setGamePlatform(String gamePlatform) {this.gamePlatform = gamePlatform;}

    public String getGameGenre() {return gameGenre;}
    public void setGameGenre(String gameGenre) {this.gameGenre = gameGenre;}

    public String getGameReleaseDate() {return gameReleaseDate;}
    public void setGameReleaseDate(String gameReleaseDate) {this.gameReleaseDate = gameReleaseDate;}

    public String getGamePublisher() {return gamePublisher;}
    public void setGamePublisher(String gamePublisher) {this.gamePublisher = gamePublisher;}

    public String getCompletionStatus() {
        if(completionStatus){
            return "yes";
        }else{
            return "no";
        }
    }
    public void setCompletionStatus(Boolean completionStatus) {this.completionStatus = completionStatus;}

    public int getCompletionYear() {return completionYear;}
    public void setCompletionYear(int completionYear) {this.completionYear = completionYear;}

    public String getGameReview() {return gameReview;}
    public void setGameReview(String gameReview) {this.gameReview = gameReview;}

    public int getGameRating() {return gameRating;}
    public void setGameRating(int gameRating) {this.gameRating = gameRating;}

    public double getGamePlaytime() {return gamePlaytime;}
    public void setGamePlaytime(double gamePlaytime) {this.gamePlaytime = gamePlaytime;}

    public double getRecentPlaytime(){return recentPlaytime;}
    public void setRecentPlaytime(double recentPlaytime){this.recentPlaytime = recentPlaytime;}

    public String getIsFavorite() {

        if(isFavorite){
            return "yes";
        }else{
            return "no";
        }
    }
    public void setIsFavorite(Boolean isFavorite) {this.isFavorite = isFavorite;}

    public String getDrawableName() {return drawableName;}
    public void setDrawableName(String drawableName) {this.drawableName = drawableName;}

}