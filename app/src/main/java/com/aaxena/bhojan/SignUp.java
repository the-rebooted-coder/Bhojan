package com.aaxena.bhojan;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUp extends AppCompatActivity {
    TextView fader;
    private TextView afterfade;
    protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
    protected AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
    private Button signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "Login";
    private FirebaseAuth mAuth;
    LottieAnimationView food_load;
    private final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signInButton = findViewById(R.id.sign_in_button);
        food_load = findViewById(R.id.food_load);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(v -> {
            Vibrator v2 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v2.vibrate(34);
            vibrateDevice();
            signIn();
        });

        //Fades the Text View Dynamically
        fader = findViewById(R.id.first_hello);
        fader.startAnimation(fadeIn);
        fader.startAnimation(fadeOut);
        fadeIn.setDuration(600);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(600);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(1200 + fadeIn.getStartOffset());
        afterfade = findViewById(R.id.first_hello2);
        int welcome_fade = 2000;
        new Handler().postDelayed(() -> {
            afterfade.startAnimation(fadeIn);
            afterfade.setText(R.string.welcome_to_bhojan);
        }, welcome_fade);
    }

    private void signIn() {
        food_load.setVisibility(View.VISIBLE);
        food_load.playAnimation();
        signInButton.setVisibility(View.INVISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
        else {
            food_load.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            Toast.makeText(this,"User Cancelled the Login",Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (account != null) {
                food_load.setVisibility(View.INVISIBLE);
                Intent i=new Intent(SignUp.this,Landing.class);
                startActivity(i);
                finish();
            }
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            signInButton.setVisibility(View.VISIBLE);
            Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(null);
            food_load.setVisibility(View.INVISIBLE);
        }
    }
    private void vibrateDevice() {
        Vibrator v3 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v3.vibrate(VibrationEffect.createOneShot(28, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v3.vibrate(25);
        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
            } else {
                signInButton.setVisibility(View.VISIBLE);
                Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_LONG).show();
                food_load.setVisibility(View.INVISIBLE);
            }
        });
    }
}