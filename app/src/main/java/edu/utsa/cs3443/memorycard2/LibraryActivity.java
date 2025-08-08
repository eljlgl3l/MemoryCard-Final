package edu.utsa.cs3443.memorycard2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;
import edu.utsa.cs3443.memorycard2.model.User;


public class LibraryActivity extends Fragment {
    //TODO: Fix XML to make buttons dynamic for multiple phones
    //TODO: Can't think of anything else at the moment 7/31

    private ConsoleLibrary xboxLibrary;
    private ConsoleLibrary playstationLibrary;
    private ConsoleLibrary steamLibrary;
    private ConsoleLibrary nintendoLibrary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get libraries from CreateGameAppData
        CreateGameAppData app = (CreateGameAppData) requireActivity().getApplication();
        xboxLibrary = app.getXboxLibrary();
        playstationLibrary = app.getPlaystationLibrary();
        steamLibrary = app.getSteamLibrary();
        nintendoLibrary = app.getNintendoLibrary();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //buttons for library

        View view = inflater.inflate(R.layout.activity_library, container, false);

        view.findViewById(R.id.xboxButton).setOnClickListener(v ->
                launchConsoleGamesActivity(xboxLibrary, "Xbox"));

        view.findViewById(R.id.steamButton).setOnClickListener(v ->
                launchConsoleGamesActivity(steamLibrary, "Steam"));

        view.findViewById(R.id.playstationButton).setOnClickListener(v ->
                launchConsoleGamesActivity(playstationLibrary, "Playstation"));

        view.findViewById(R.id.nintendoSwitchButton).setOnClickListener(v ->
                launchConsoleGamesActivity(nintendoLibrary, "Nintendo Switch"));

        return view;
    }

    private void launchConsoleGamesActivity(ConsoleLibrary consoleLibrary, String name) {
        ConsoleGamesActivity consoleGamesScreen = ConsoleGamesActivity.newInstance(consoleLibrary, name);

        // needed to update scren
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, consoleGamesScreen)
                .addToBackStack(null)
                .commit();
    }
}