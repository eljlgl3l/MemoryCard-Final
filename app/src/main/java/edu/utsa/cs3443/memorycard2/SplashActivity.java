package edu.utsa.cs3443.memorycard2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3443.memorycard2.model.User;

/**
 * This is strictly used for the splash screen, and should be used nowhere else.
 * Doing things like a controller does not work here.
 */
public class SplashActivity extends AppCompatActivity {

    //TODO: I believe this is done. Don't add other stuff here like loading files, they don't work properly here 7/31

    private static int SPLASH_SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        Animation fade = new AlphaAnimation(1, 0);
        fade.setInterpolator(new AccelerateInterpolator());
        fade.setStartOffset(500);
        fade.setDuration(1800);

        ImageView image = findViewById(R.id.imageView);
        image.setAnimation(fade);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }, SPLASH_SCREEN_TIMEOUT);


    }
}