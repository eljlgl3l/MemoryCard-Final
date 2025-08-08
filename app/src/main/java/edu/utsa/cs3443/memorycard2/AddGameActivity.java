package edu.utsa.cs3443.memorycard2;


import android.os.Bundle;



import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Arrays;
import java.util.List;

import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;
import edu.utsa.cs3443.memorycard2.model.Game;
import edu.utsa.cs3443.memorycard2.model.User;


/**
 * @author Elias
 */


public class AddGameActivity extends Fragment {
    private User user;
    private List<ConsoleLibrary> allLibs;

    public AddGameActivity() { /* empty constructor */ }

    public static AddGameActivity newInstance() {
        return new AddGameActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this gets the libraries so that we can add games
        CreateGameAppData app = (CreateGameAppData) requireActivity().getApplication();
        user = app.getCurrentUser();
        allLibs = Arrays.asList(
                app.getXboxLibrary(),
                app.getPlaystationLibrary(),
                app.getSteamLibrary(),
                app.getNintendoLibrary()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add_game, container, false);

        // text  fields and checkboxes for user input
        EditText titleField = view.findViewById(R.id.addGameTitle);
        EditText platformField  = view.findViewById(R.id.addGameConsole);
        EditText genreField = view.findViewById(R.id.addGameGenre);
        EditText dateField = view.findViewById(R.id.addReleaseYear);
        EditText publisherField = view.findViewById(R.id.addPublisher);
        CheckBox completedBox = view.findViewById(R.id.boolCompletionStatus);
        EditText yearField = view.findViewById(R.id.addCompletionYear);
        EditText reviewField = view.findViewById(R.id.addReview);
        EditText ratingField = view.findViewById(R.id.addRating);
        CheckBox favoriteBox = view.findViewById(R.id.boolFavorite);
        EditText coverArtURL = view.findViewById(R.id.addGameCoverUrl);
        Button submitButton  = view.findViewById(R.id.btnSubmitGame);




        submitButton.setOnClickListener(v -> {
            //I was having trouble with booleans and transferring over to a CSV file, so these
            //two booleans add a little redundancy to mitigate that

            boolean completedStatus = completedBox.isChecked();
            boolean favoriteStatus = favoriteBox.isChecked();

            Game newGame = new Game(
                    titleField.getText().toString(),
                    platformField.getText().toString(),
                    genreField.getText().toString(),
                    dateField.getText().toString(),
                    publisherField.getText().toString(),
                    completedStatus,
                    Integer.parseInt(yearField.getText().toString()),
                    reviewField.getText().toString(),
                    Integer.parseInt(ratingField.getText().toString()),
                    0.0,
                    0.0,
                    favoriteStatus,
                    coverArtURL.getText().toString()
            );

            //Find the console library based off platform field

            ConsoleLibrary targetLib = findLibraryFor(newGame.getGamePlatform());
            if (targetLib == null) {
                Toast.makeText(getContext(), "Unknown platform: " + newGame.getGamePlatform(), Toast.LENGTH_LONG).show();
                return;
            }

            // appends to CSV, if return 1 then we're good
            int result = targetLib.appendGameToCSV(newGame, requireContext());

            if (result == 1) {
                targetLib.addGame(newGame);

                // this lets the user recalculate all the stats so it's updated elsewhere in the app
                user.loadUser(requireContext());

                Toast.makeText(getContext(),
                        "Added to " + targetLib.getConsoleName(),
                        Toast.LENGTH_SHORT).show();

                // escape from addgame
                getParentFragmentManager().popBackStack();

            } else {
                Toast.makeText(getContext(),
                        "Failed to save " + newGame.getGameTitle(),
                        Toast.LENGTH_LONG).show();
            }

        });

        return view;
    }

    /**
     * method to find the library the user specified
     * @param platformName
     * @return the library specified by the user
     */
    private ConsoleLibrary findLibraryFor(String platformName) {
        String key = platformName.toLowerCase().trim();
        for (ConsoleLibrary lib : allLibs) {
            if (key.contains(lib.getConsoleName().toLowerCase())) {
                return lib;
            }
        }
        return null;
    }
}