package edu.utsa.cs3443.memorycard2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.utsa.cs3443.memorycard2.databinding.ActivityMainBinding;
import edu.utsa.cs3443.memorycard2.model.User;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user = new User();
        user.loadUser(this);

        // this line makes the app start at the stopwatch
        replaceFragment(new StopwatchActivity());


        ProfileActivity profileFragment = new ProfileActivity();
        Bundle profileFragmentArgs = new Bundle();

        //we've steered away from using these in favor of gameslist, but I think it would be too
        // hard to take them out now
        profileFragmentArgs.putDouble("LIFETIME_PLAYTIME", user.getTotalPlaytime());
        profileFragmentArgs.putDouble("RECENT_PLAYTIME", user.getRecentPlaytime());
        profileFragmentArgs.putInt("TOTAL_GAMES_PLAYED", user.getTotalGamesPlayed());
        profileFragmentArgs.putStringArrayList("FAVORITE_CONSOLES", user.getFavoriteConsoles());
        profileFragment.setArguments(profileFragmentArgs);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.profile) {
                replaceFragment(profileFragment);
            } else if (id == R.id.library) {

                replaceFragment(new LibraryActivity());

            } else if (id == R.id.addGame) {

                replaceFragment(AddGameActivity.newInstance());

            } else if (id == R.id.stopwatch) {

                replaceFragment(new StopwatchActivity());
            }

            return true;
        });
    }

    /**
     * This function is used to update the screen based off different events and actions
     * like in the libraries
     * @param fragment
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }

}