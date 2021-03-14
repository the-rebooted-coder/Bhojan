package com.aaxena.bhojan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class SignUp extends AppCompatActivity {
    TextView fader;
    private TextView afterfade;
    protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "Login";
    private int RC_SIGN_IN =1;
    String TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fader = findViewById(R.id.first_hello);
        fader.startAnimation(fadeIn);
        fader.startAnimation(fadeOut);
        fadeIn.setDuration(600);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(600);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(1200+fadeIn.getStartOffset());
        afterfade = findViewById(R.id.first_hello2);
        int welcome_fade = 2000;
        new Handler().postDelayed(() -> {
            afterfade.startAnimation(fadeIn);
            afterfade.setText(R.string.welcome_to_bhojan);
        }, welcome_fade);

    }
}