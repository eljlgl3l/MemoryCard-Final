package edu.utsa.cs3443.memorycard2;


import static android.content.Intent.getIntent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;
import edu.utsa.cs3443.memorycard2.model.Game;
import edu.utsa.cs3443.memorycard2.model.User;


public class ProfileActivity extends Fragment {
    private User user;

    private TextView recentPlaytimeValue;
    private TextView lifetimePlaytimeValue;
    private TextView favoriteConsoleValue;
    private TextView completionRateValue;

    public ProfileActivity() {
        // empty  constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CreateGameAppData app = (CreateGameAppData) requireActivity().getApplication();
        user = app.getCurrentUser();
        user.loadUser(requireContext());     // loads games, libraries, computes stats
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        recentPlaytimeValue   = view.findViewById(R.id.recentPlaytimeValue);
        lifetimePlaytimeValue = view.findViewById(R.id.lifetimePlaytimeValue);
        favoriteConsoleValue  = view.findViewById(R.id.favoriteConsoleValue);
        completionRateValue   = view.findViewById(R.id.completionRateValue);

        // populates states for the profile
        populateUserStats();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // used when a new game is added / playtime updated
        Log.d("ProfileStatsUPDATED", "onResume() was fired");
        user.loadUser(requireContext());
        populateUserStats();
    }


    /**
     * This function is critical in updating the profile activity absouletely
     * every time that any aspect of game data is updated (mostly adding a game and updating playtime)
     */
    private void populateUserStats() {
        double recentHours = user.getRecentPlaytime();
        double totalHours = user.getTotalPlaytime();
        int totalGamesPlayed = user.getTotalGamesPlayed();
        String favoriteConsole = user.getFavoriteConsole();
        int favoriteCount = user.getNumberOfFavoriteGamesInFavoriteConsole();
        double completionRate = user.getCompletionRate();

        recentPlaytimeValue.setText(
                String.format("%.1f hours", recentHours)
        );

        lifetimePlaytimeValue.setText(
                String.format("%.1f hours (%d games)", totalHours, totalGamesPlayed)
        );

        favoriteConsoleValue.setText(
                String.format("%s (%d favorites)", favoriteConsole, favoriteCount)
        );

        completionRateValue.setText(
                String.format("%.1f%% of games completed", completionRate)
        );
    }
}