package edu.utsa.cs3443.memorycard2.model;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This model is the newest part of the code that allows for ProfileActivity to update
 * consistently, so that it doesn't have to constantly reupdate in the controller
 */
public class GamesList {

    private final File csvFile;


    public GamesList(File csvFile) {
        this.csvFile = csvFile;
    }


    public List<Game> getAllGames() throws IOException {

        List<Game> games = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvFile)))) {
            String csvLine;
            // skip header
            if ((csvLine = reader.readLine()) == null) {
                return games;
            }

            while ((csvLine = reader.readLine()) != null) {

                String[] currentLineColumns = csvLine.split(",", -1);
                if (currentLineColumns.length < 13) {
                    //used to not count rows without correct number of cells
                    continue;
                }
                //the below variables are used to prevent errors with adding "true/false" to CSV
                boolean completedStatus = "yes".equalsIgnoreCase(currentLineColumns[5]);
                boolean favoriteStatus  = "yes".equalsIgnoreCase(currentLineColumns[11]);

                Game g = new Game(
                        currentLineColumns[0],
                        currentLineColumns[1],
                        currentLineColumns[2],
                        currentLineColumns[3],
                        currentLineColumns[4],
                        completedStatus,
                        Integer.parseInt(currentLineColumns[6]),
                        currentLineColumns[7],
                        Integer.parseInt(currentLineColumns[8]),
                        Double.parseDouble(currentLineColumns[9]),
                        Double.parseDouble(currentLineColumns[10]),
                        favoriteStatus,
                        currentLineColumns[12]
                );
                games.add(g);
            }
        }
        return games;
    }


}
