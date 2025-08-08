package edu.utsa.cs3443.memorycard2;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;


import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;
import edu.utsa.cs3443.memorycard2.model.Game;

public class GameActivity extends Fragment {

    private Game game;
    private String previousFragment;
    private ConsoleLibrary consoleLibrary;
    private String consoleName;


    /**
     * These are the variables for the UI elements
     */
    private ImageButton backButton;
    private TextView gameTitle;
    private ImageView gameCover;
    private TextView gamePlatform;
    private TextView gameGenre;
    private TextView gameReleaseDate;
    private TextView gamePublisher;
    private TextView userRating;
    private TextView userReview;
    private TextView userPlaytime;
    private TextView recentPlaytime;

    public GameActivity() { /*empty*/ }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            game = (Game) getArguments().getSerializable("game", Game.class);
            previousFragment = getArguments().getString("previousFragment");
            consoleLibrary = (ConsoleLibrary) getArguments().getSerializable("consoleLibrary", ConsoleLibrary.class);
            consoleName = getArguments().getString("consoleName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_game, container, false);


        /**
         * We don't call populateGameData here because it needs to initialize these items onCreate
         */

        backButton = view.findViewById(R.id.backButton);
        gameTitle = view.findViewById(R.id.gameTitleTextView);
        gameCover = view.findViewById(R.id.gameCover);
        gamePlatform = view.findViewById(R.id.gamePlatformTextView);
        gameGenre = view.findViewById(R.id.gameGenreTextView);
        gameReleaseDate = view.findViewById(R.id.gameReleaseDateTextView);
        gamePublisher = view.findViewById(R.id.gamePublisherTextView);
        userRating = view.findViewById(R.id.userRatingTextView);
        userReview = view.findViewById(R.id.userReviewTextView);
        userPlaytime = view.findViewById(R.id.userPlaytimeTextView);
        recentPlaytime = view.findViewById(R.id.recentPlaytimeTextView);

        gameTitle.setText(game.getGameTitle());
        gamePlatform.setText(game.getGamePlatform());
        gameGenre.setText(game.getGameGenre());
        gameReleaseDate.setText(game.getGameReleaseDate());
        gamePublisher.setText(game.getGamePublisher());
        userReview.setText(game.getGameReview());

        String truncatedTotalPlaytime = String.format("%.2f",game.getGamePlaytime());
        userPlaytime.setText(new StringBuilder().append(truncatedTotalPlaytime).append(" hours").toString());
        String truncatedRecentPlaytime = String.format("%.2f",game.getRecentPlaytime());
        recentPlaytime.setText(new StringBuilder().append(truncatedRecentPlaytime).append(" hours").toString());


        String coverUrl = game.getDrawableName();  // this must be a url that ends in jpg or png
        // MAKE PLACEHOLDER
        Glide.with(this)
                .load(coverUrl)
                .fitCenter()
                .into(gameCover);

        StringBuilder rating = new StringBuilder();
        rating.append(game.getGameRating());
        if (game.getGameRating() == 1) {
            rating.append(" star");
        } else {
            rating.append(" stars");
        }
        userRating.setText(rating.toString());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previousFragment != null) {
                    switch (previousFragment) {
                        case "ConsoleGamesActivity":
                            launchConsoleGamesActivity(consoleLibrary, consoleName);
                            break;
                        case "AllConsoleGamesActivity":
                            launchAllConsoleGamesActivity(consoleLibrary, consoleName);
                            break;
                        case "FavoriteConsoleGamesActivity":
                            launchFavoriteConsoleGamesActivity(consoleLibrary, consoleName);
                            break;
                        case "CompletedConsoleGamesActivity":
                            launchCompletedConsoleGamesActivity(consoleLibrary, consoleName);
                            break;
                    }
                }
                else {
                    Log.d("GameActivity", "No previous fragment found.");
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateGameData();
    }

    /**
     * This function just repopulates the game data when you come back to the screen,
     * this is most useful for updating playtime
     */
    private void populateGameData(){

        gameTitle.setText(game.getGameTitle());
        gamePlatform.setText(game.getGamePlatform());
        gameGenre.setText(game.getGameGenre());
        gameReleaseDate.setText(game.getGameReleaseDate());
        gamePublisher.setText(game.getGamePublisher());
        userReview.setText(game.getGameReview());

        String truncatedTotalPlaytime = String.format("%.2f",game.getGamePlaytime());
        userPlaytime.setText(new StringBuilder().append(truncatedTotalPlaytime).append(" hours").toString());
        String truncatedRecentPlaytime = String.format("%.2f",game.getRecentPlaytime());
        recentPlaytime.setText(new StringBuilder().append(truncatedRecentPlaytime).append(" hours").toString());


        //new way to make images
        //you need     implementation("com.github.bumptech.glide:glide:4.16.0") in your dependencies in gradle modules:app
        String coverUrl = game.getDrawableName();  // image url
        // MAKE PLACEHOLDER
        Glide.with(this)
                .load(coverUrl)
                .fitCenter()
                .into(gameCover);

        StringBuilder rating = new StringBuilder();
        rating.append(game.getGameRating());
        if (game.getGameRating() == 1) {
            rating.append(" star");
        } else {
            rating.append(" stars");
        }
        userRating.setText(rating.toString());


    }


    /**
     * These four functions are used to implement the back button
     */
    private void launchConsoleGamesActivity(ConsoleLibrary consoleLibrary, String consoleName) {
        ConsoleGamesActivity fragment = new ConsoleGamesActivity();
        Bundle args = new Bundle();
        args.putSerializable("consoleLibrary", consoleLibrary);
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
}