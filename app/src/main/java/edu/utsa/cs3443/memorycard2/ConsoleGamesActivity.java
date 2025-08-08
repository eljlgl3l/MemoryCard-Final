package edu.utsa.cs3443.memorycard2;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.utsa.cs3443.memorycard2.model.AllGamesLoader;
import edu.utsa.cs3443.memorycard2.model.CompletedGamesLoader;
import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;
import edu.utsa.cs3443.memorycard2.model.FavoriteGamesLoader;
import edu.utsa.cs3443.memorycard2.model.Game;


public class ConsoleGamesActivity extends Fragment {
    //TODO: Need to add images and make sure the XML is dynamic 7/31
    // Going to be kind of a tough one- maybe we can just have buttons here that lead to a page like
    // how a button pulls up the people list in Lab 3?

    private ConsoleLibrary selectedConsoleLibrary;
    private ArrayList<Game> selectedConsoleFavorites;
    private ArrayList<Game> selectedConsoleCompleted;

    private ArrayList<Game> consoleGames;

    private String consoleName;
    private final String activityName = "ConsoleGamesActivity";


    public ConsoleGamesActivity() {
        //empty constructor
    }

    public static ConsoleGamesActivity newInstance(ConsoleLibrary consoleLibrary, String consoleName) {

        ConsoleGamesActivity fragment = new ConsoleGamesActivity();
        Bundle args = new Bundle();
        args.putSerializable("consoleLibrary", consoleLibrary);
        args.putString("consoleName", consoleName);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedConsoleLibrary = (ConsoleLibrary) requireArguments().getSerializable("consoleLibrary", ConsoleLibrary.class);
            consoleName = getArguments().getString("consoleName"); // ✅ assign to the field
            selectedConsoleFavorites = selectedConsoleLibrary.getConsoleFavorites();
            selectedConsoleCompleted = selectedConsoleLibrary.getConsoleCompleted();
            consoleGames = selectedConsoleLibrary.getConsoleGames();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_console_games, container, false);
        TextView title = view.findViewById(R.id.consoleNameTextView);

        if (consoleName != null) {
            title.setText(consoleName);
        }
        //playlist for completed games.
        CompletedGamesLoader loader = new CompletedGamesLoader();
        loader.loadCompletedGames(getContext(), "user_game_library.csv");

        //playlist for favorite games.
        FavoriteGamesLoader loader2 = new FavoriteGamesLoader();
        loader2.loadFavoriteGames(getContext(), "user_game_library.csv");

        //playlist for all games.
        AllGamesLoader loader3 = new AllGamesLoader();
        loader3.loadAllGames(getContext(), "user_game_library.csv");





        ImageButton backButton = view.findViewById(R.id.backButton);

        ImageButton firstGameButton = view.findViewById(R.id.firstGameButton);
        ImageButton secondGameButton = view.findViewById(R.id.secondGameButton);
        ImageButton thirdGameButton = view.findViewById(R.id.thirdGameButton);
        ImageButton fourthGameButton = view.findViewById(R.id.fourthGameButton);
        Button seeAllGamesButton = view.findViewById(R.id.seeAllGamesButton);

        ImageButton firstFavoriteButton = view.findViewById(R.id.firstFavoriteButton);
        ImageButton secondFavoriteButton = view.findViewById(R.id.secondFavoriteButton);
        ImageButton thirdFavoriteButton = view.findViewById(R.id.thirdFavoriteButton);
        ImageButton fourthFavoriteButton = view.findViewById(R.id.fourthFavoriteButton);
        Button seeAllFavoritesButton = view.findViewById(R.id.seeAllFavoritesButton);

        ImageButton firstCompletedButton = view.findViewById(R.id.firstCompletedButton);
        ImageButton secondCompletedButton = view.findViewById(R.id.secondCompletedButton);
        ImageButton thirdCompletedButton = view.findViewById(R.id.thirdCompletedButton);
        ImageButton fourthCompletedButton = view.findViewById(R.id.fourthCompletedButton);
        Button seeAllCompletedButton = view.findViewById(R.id.seeAllCompletedButton);


        ImageButton[] buttonsAll = {firstGameButton, secondGameButton, thirdGameButton, fourthGameButton};
        ImageButton[] buttonsFavorite = {firstFavoriteButton, secondFavoriteButton, thirdFavoriteButton, fourthFavoriteButton};
        ImageButton[] buttonsCompleted = {firstCompletedButton, secondCompletedButton, thirdCompletedButton, fourthCompletedButton};


        //gets the image for the 4 buttons under all games.
        for (int i = 0; i < buttonsAll.length && i < consoleGames.size(); i++) {
            String drawableName = consoleGames.get(i).getDrawableName();
            Glide.with(this)
                    .load(drawableName)
                    .fitCenter()
                    .into(buttonsAll[i]);
        }

        //gets the image for the 4 buttons under favorite games.
        for (int i = 0; i < buttonsFavorite.length && i < selectedConsoleFavorites.size(); i++) {
            String drawableName = selectedConsoleFavorites.get(i).getDrawableName();
            Glide.with(this)
                    .load(drawableName)
                    .fitCenter()
                    .into(buttonsFavorite[i]);
        }

        //Gets the image for the 4 buttons under completed games.
        for (int i = 0; i < buttonsCompleted.length && i < selectedConsoleCompleted.size(); i++) {
            String drawableName = selectedConsoleCompleted.get(i).getDrawableName();
            Glide.with(this)
                    .load(drawableName)
                    .fitCenter()
                    .into(buttonsCompleted[i]);
        }

        backButton.setOnClickListener(v -> {
            launchLibraryActivity();
        });
        firstGameButton.setOnClickListener(v -> {
            if (selectedConsoleLibrary.getNumberOfGames() >= 1) {
                Game game = selectedConsoleLibrary.getGame(0);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        secondGameButton.setOnClickListener(v -> {
            if (selectedConsoleLibrary.getNumberOfGames() >= 2) {
                Game game = selectedConsoleLibrary.getGame(1);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        thirdGameButton.setOnClickListener(v -> {
            if (selectedConsoleLibrary.getNumberOfGames() >= 3) {
                Game game = selectedConsoleLibrary.getGame(2);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        fourthGameButton.setOnClickListener(v -> {
            if (selectedConsoleLibrary.getNumberOfGames() >= 4) {
                Game game = selectedConsoleLibrary.getGame(3);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        seeAllGamesButton.setOnClickListener(v -> launchAllConsoleGamesActivity(selectedConsoleLibrary, consoleName));

        firstFavoriteButton.setOnClickListener(v -> {
            if (!selectedConsoleFavorites.isEmpty()) {
                Game game = selectedConsoleFavorites.get(0);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        secondFavoriteButton.setOnClickListener(v -> {
            if (selectedConsoleFavorites.size() >= 2) {
                Game game = selectedConsoleFavorites.get(1);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        thirdFavoriteButton.setOnClickListener(v -> {
            if (selectedConsoleFavorites.size() >= 3) {
                Game game = selectedConsoleFavorites.get(2);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        fourthFavoriteButton.setOnClickListener(v -> {
            if (selectedConsoleFavorites.size() >= 4) {
                Game game = selectedConsoleFavorites.get(3);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        seeAllFavoritesButton.setOnClickListener(v -> launchFavoriteConsoleGamesActivity(selectedConsoleLibrary, consoleName));

        firstCompletedButton.setOnClickListener(v -> {
            if (!selectedConsoleCompleted.isEmpty()) {
                Game game = selectedConsoleCompleted.get(0);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        secondCompletedButton.setOnClickListener(v -> {
            if (selectedConsoleCompleted.size() >= 2) {
                Game game = selectedConsoleCompleted.get(1);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        thirdCompletedButton.setOnClickListener(v -> {
            if (selectedConsoleCompleted.size() >= 3) {
                Game game = selectedConsoleCompleted.get(2);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        fourthCompletedButton.setOnClickListener(v -> {
            if (selectedConsoleCompleted.size() >= 4) {
                Game game = selectedConsoleCompleted.get(3);
                launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName);
            }
        });
        seeAllCompletedButton.setOnClickListener(v -> launchCompletedConsoleGamesActivity(selectedConsoleLibrary, consoleName));

        return view;
    }

    private void launchLibraryActivity() {
        LibraryActivity fragment = new LibraryActivity();
        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
    private void launchGameActivity(Game game, String activityName, ConsoleLibrary selectedConsoleLibrary, String consoleName) {
        GameActivity fragment = new GameActivity();
        Bundle args = new Bundle();
        args.putSerializable("game", game);
        args.putString("previousFragment", activityName);
        args.putSerializable("consoleLibrary", selectedConsoleLibrary);
        args.putString("consoleName", consoleName);
        fragment.setArguments(args);
        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
    private void launchAllConsoleGamesActivity(ConsoleLibrary consoleLibrary, String consoleName) {
        AllConsoleGamesActivity fragment = new AllConsoleGamesActivity();
        Bundle args = new Bundle();
        args.putSerializable("consoleLibrary", consoleLibrary);
        args.putString("consoleName", consoleName);
        fragment.setArguments(args);
        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
    private void launchFavoriteConsoleGamesActivity(ConsoleLibrary consoleLibrary, String consoleName) {
        FavoriteConsoleGamesActivity fragment = new FavoriteConsoleGamesActivity();
        Bundle args = new Bundle();
        args.putSerializable("consoleLibrary", consoleLibrary);
        args.putString("consoleName", consoleName);
        fragment.setArguments(args);
        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
    private void launchCompletedConsoleGamesActivity(ConsoleLibrary consoleLibrary, String consoleName) {
        CompletedConsoleGamesActivity fragment = new CompletedConsoleGamesActivity();
        Bundle args = new Bundle();
        args.putSerializable("consoleLibrary", consoleLibrary);
        args.putString("consoleName", consoleName);
        fragment.setArguments(args);
        getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

    private String formatDrawableName(String title) {
        return title.toLowerCase().replaceAll("[^a-z0-9]", "");
    }


}