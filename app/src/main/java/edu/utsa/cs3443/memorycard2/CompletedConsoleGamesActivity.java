package edu.utsa.cs3443.memorycard2;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.utsa.cs3443.memorycard2.model.CompletedGamesLoader;
import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;
import edu.utsa.cs3443.memorycard2.model.Game;
import edu.utsa.cs3443.memorycard2.model.User;


public class CompletedConsoleGamesActivity extends Fragment {
    //TODO: Need to make this fragment and make sure the XML is dynamic 7/31
    //Remember: This fragment is for the "Completed games" portion of the individual console lib

    private ConsoleLibrary selectedConsoleLibrary;
    private String consoleName;
    private final String activityName = "CompletedConsoleGamesActivity";
    private boolean completionStatus;


    public CompletedConsoleGamesActivity() {
     /*empty constructor */
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedConsoleLibrary = (ConsoleLibrary) requireArguments().getSerializable("consoleLibrary", ConsoleLibrary.class);
            consoleName = getArguments().getString("consoleName");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_completed_console_games, container, false);
        GridLayout completedGamesGrid = view.findViewById(R.id.completedGameGridLayout);

        if (consoleName != null) {
            TextView CompletedGamesTag = view.findViewById(R.id.CompletedGamesTag);
        }

        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            launchConsoleGamesActivity(selectedConsoleLibrary, consoleName);
        });
        //populating the grid with games
        for (Game game: selectedConsoleLibrary.getConsoleCompleted()){
            ImageButton gameButton = new ImageButton(requireContext());

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 230;
            params.height = 230;
            params.setMargins(14, 14, 14, 14);
            gameButton.setLayoutParams(params);

            String coverUrl = game.getDrawableName();  // image url
            // MAKE PLACEHOLDER
            Glide.with(this)
                    .load(coverUrl)
                    .fitCenter()
                    .into(gameButton);

            gameButton.setScaleType(ImageButton.ScaleType.CENTER_CROP);
            gameButton.setBackground(null);
            gameButton.setOnClickListener(v -> launchGameActivity(game, activityName, selectedConsoleLibrary, consoleName));
            completedGamesGrid.addView(gameButton);
        }

        return view;
    }

    private void launchConsoleGamesActivity(ConsoleLibrary selectedConsoleLibrary, String consoleName) {
        ConsoleGamesActivity fragment = new ConsoleGamesActivity();
        Bundle args = new Bundle();
        args.putSerializable("consoleLibrary", selectedConsoleLibrary);
        args.putString("consoleName", consoleName);
        fragment.setArguments(args);
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


}