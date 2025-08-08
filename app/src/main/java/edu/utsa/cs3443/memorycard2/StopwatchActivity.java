package edu.utsa.cs3443.memorycard2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.utsa.cs3443.memorycard2.model.ConsoleLibrary;
import edu.utsa.cs3443.memorycard2.model.Game;
import edu.utsa.cs3443.memorycard2.model.Stopwatch;


/**
 * @author jason
 * fundamental ideas for the stopwatch:
 * totalPlaytime is something that is added onto, and never subtracted from or rewritten
 * recentPlaytime is the most recent number that occurs from when
 * the start button is pressed till the stop/reset button is pressed
 */

/**
 * This activity contains a a step-by-step process for how to load the console libraries,
 * which in turn load the games, into any activity so that you can work with their data.
 * Look for STEP 1: , STEP 2: etc.
 */

/**
 * NOTE: In order to update stuff, you need to upload both .csv files
 * to /data/user/0/edu.utsa.cs3443.memorycard2/files/
 * in the Device Explorer in Android Studio (should be on the right sidebar, a phone with a magnifying glass)
 */





public class StopwatchActivity extends Fragment {
    private Stopwatch stopwatch = new Stopwatch();
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updater;

    private List<ConsoleLibrary> allLibs;
    private List<Game> currentGameList = new ArrayList<>();

    // UI
    private Spinner consoleDropdown, gameDropdown;
    private TextView stopwatchTextView;
    private Button startBtn, stopBtn, resetBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateGameAppData app = (CreateGameAppData) requireActivity().getApplication();
        allLibs = Arrays.asList(
                app.getXboxLibrary(),
                app.getPlaystationLibrary(),
                app.getSteamLibrary(),
                app.getNintendoLibrary()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_stopwatch, container, false);

        consoleDropdown = v.findViewById(R.id.consoleDropdown);
        gameDropdown = v.findViewById(R.id.gameDropdown);
        stopwatchTextView = v.findViewById(R.id.time_view);
        startBtn = v.findViewById(R.id.stopwatchStartButton);
        stopBtn = v.findViewById(R.id.stopwatchStopButton);
        resetBtn = v.findViewById(R.id.stopwatchResetButton);

        setupConsoleDropdown();
        setupUpdater();
        setupButtons();

        return v;
    }

    private void setupConsoleDropdown() {
        List<String> names = new ArrayList<>();
        for (ConsoleLibrary lib : allLibs) {
            names.add(lib.getConsoleName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        consoleDropdown.setAdapter(adapter);

        consoleDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                currentGameList = allLibs.get(pos).getConsoleGames();
                List<String> titles = new ArrayList<>();
                for (Game g : currentGameList) titles.add(g.getGameTitle());
                ArrayAdapter<String> gameAdapter = new ArrayAdapter<>(
                        requireContext(), android.R.layout.simple_spinner_item, titles);
                gameAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                gameDropdown.setAdapter(gameAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupUpdater() {
        updater = () -> {
            stopwatchTextView.setText(stopwatch.getFormattedTime());
            if (stopwatch.isRunning())
                handler.postDelayed(updater, 100);
        };
    }

    private void setupButtons() {
        startBtn.setOnClickListener(v -> {
            stopwatch.start();
            handler.post(updater);
        });

        stopBtn.setOnClickListener(v -> {
            if (!stopwatch.isRunning()) return;

            int selectedGamePosition = gameDropdown.getSelectedItemPosition();
            Game selected = currentGameList.get(selectedGamePosition);

            stopwatch.stop(selected);
            handler.removeCallbacks(updater);

            stopwatch.saveAllToCsv(requireContext(), allLibs);

            Toast.makeText(getContext(), "Successfully updated playtime", Toast.LENGTH_SHORT).show();
        });

        resetBtn.setOnClickListener(v -> {
            stopwatch.reset();
            handler.removeCallbacks(updater);
            stopwatchTextView.setText("00:00:00");
        });
    }
}