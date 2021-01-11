package com.aaxena.bhojan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fireSplashScreen();
        TextView appName = findViewById(R.id.title);
        appName.setText(R.string.app_name);
        appName.startAnimation(fadeIn);
        fadeIn.setDuration(1200);
    }
    private void fireSplashScreen() {
        int splash_screen_time_out = 2800;
        new Handler().postDelayed(() -> {
            Intent i=new Intent(SplashScreen.this,SignUp.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, splash_screen_time_out);
    }
}