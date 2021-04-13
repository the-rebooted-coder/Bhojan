package com.aaxena.bhojan;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Pair;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SplashScreen extends AppCompatActivity {
    protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        relativeLayout = findViewById(R.id.splashScreenLayout);
        TextView appName = findViewById(R.id.title);
        one();
        fireSplashScreen();
        appName.setText(R.string.app_name_extended);
        appName.startAnimation(fadeIn);
        fadeIn.setDuration(1200);
    }

    private void one() {
        int feelUI = 860;
        two();
        new Handler().postDelayed(this::vibrateDevice, feelUI);
    }
    private void two() {
        int feelUItwo = 920;
        three();
        new Handler().postDelayed(this::vibrateDeviceSecond, feelUItwo);
    }
    private void three() {
        int feelUIthree = 980;
        four();
        new Handler().postDelayed(this::vibrateDeviceThird, feelUIthree);
    }
    private void four() {
        int feelUIfour = 1040;
        five();
        new Handler().postDelayed(this::vibrateDeviceFourth, feelUIfour);
    }
    private void five() {
        int feelUIfive = 1100;
        six();
        new Handler().postDelayed(this::vibrateDeviceFifth, feelUIfive);
    }
    private void six() {
        int feelUIsix = 1160;
        seven();
        new Handler().postDelayed(this::vibrateDeviceSixth, feelUIsix);
    }
    private void seven() {
        int feelUIseven = 1220;
        new Handler().postDelayed(this::vibrateDeviceSeventh, feelUIseven);
    }

    private void fireSplashScreen() {
        int splash_screen_time_out = 2000;
        new Handler().postDelayed(() -> {
            check();
        }, splash_screen_time_out);
    }

    private void check() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            //User Signed In, Proceeding to Landing
            Intent i = new Intent(SplashScreen.this, Landing.class);
            Pair [] pairs = new Pair[1];
            TextView appName = findViewById(R.id.title);
            pairs[0] = new Pair<View, String> (appName,"imageTransition");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,pairs);
            startActivity(i,options.toBundle());
            int splash_screen_time_out = 1000;
            new Handler().postDelayed(() -> {
                finish();
            }, splash_screen_time_out);
        } else {
            //Newbie
            Intent i = new Intent(SplashScreen.this, SignUp.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    private void vibrateDeviceSecond() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(32, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(28);
        }
    }
    private void vibrateDeviceThird() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(36, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(32);
        }
    }
    private void vibrateDeviceFourth() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(36);
        }
    }
    private void vibrateDeviceFifth() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(45, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(39);
        }
    }
    private void vibrateDeviceSixth() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(42);
        }
    }
    private void vibrateDeviceSeventh() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(52, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(44);
        }
    }
    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(28, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(25);
        }
    }
}