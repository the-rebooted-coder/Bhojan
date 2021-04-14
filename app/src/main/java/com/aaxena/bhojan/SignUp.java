package com.aaxena.bhojan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
import com.tomer.fadingtextview.FadingTextView;

public class SignUp extends AppCompatActivity {
    private Button signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "Login";
    private FirebaseAuth mAuth;
    LottieAnimationView food_load;
    AlertDialog alertDialog1;
    private final int RC_SIGN_IN = 1;
    public static final String UI_MODE = "uiMode";
    Button themeChooser;

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
            vibrateDevice();
            signIn();
        });
        themeChooser = findViewById(R.id.themeChoose);
        themeChooser.setOnClickListener(v -> {
            vibrateDevice();
            CreateAlertDialogWithRadioButtonGroup();
        });
    }

    public void CreateAlertDialogWithRadioButtonGroup() {
        LottieAnimationView lottieAnimationView;
        lottieAnimationView = findViewById(R.id.animation_view_theme);
        FadingTextView fadingTextView;
        fadingTextView = findViewById(R.id.first_hello);
        int nightModeFlags =
                this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setTitle("Choose Theme for Bhojan");
        builder.setMessage("You can always change it later inside the app!");
        builder.setPositiveButton("Light", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (nightModeFlags) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        vibrateDevice();
                        signInButton.setVisibility(View.INVISIBLE);
                        fadingTextView.setVisibility(View.INVISIBLE);
                        fadingTextView.pause();
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        lottieAnimationView.setAnimation("light_mode.json");
                        lottieAnimationView.playAnimation();
                        alertDialog1.dismiss();
                        int theme_timeout = 2000;
                        new Handler().postDelayed(() -> {
                            lottieAnimationView.cancelAnimation();
                            lottieAnimationView.setVisibility(View.GONE);
                            signInButton.setVisibility(View.VISIBLE);
                            fadingTextView.resume();
                            fadingTextView.setVisibility(View.VISIBLE);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            SharedPreferences.Editor editor = getSharedPreferences(UI_MODE, MODE_PRIVATE).edit();
                            editor.putString("uiMode","Light");
                            editor.apply();
                        }, theme_timeout);
                        break;
                    case Configuration.UI_MODE_NIGHT_NO:
                        Toast.makeText(getApplicationContext(),"Already in Light Mode ☀️",Toast.LENGTH_SHORT).show();
                        alertDialog1.dismiss();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Choose a theme",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Dark", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (nightModeFlags) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        Toast.makeText(getApplicationContext(),"Already in Dark Mode \uD83C\uDF19",Toast.LENGTH_SHORT).show();
                        alertDialog1.dismiss();
                        break;
                    case Configuration.UI_MODE_NIGHT_NO:
                        vibrateDevice();
                        signInButton.setVisibility(View.INVISIBLE);
                        fadingTextView.pause();
                        fadingTextView.setVisibility(View.INVISIBLE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        lottieAnimationView.setAnimation("dark_mode.json");
                        lottieAnimationView.playAnimation();
                        alertDialog1.dismiss();
                        int theme_timeout = 2000;
                        new Handler().postDelayed(() -> {
                            lottieAnimationView.cancelAnimation();
                            lottieAnimationView.setVisibility(View.GONE);
                            fadingTextView.resume();
                            signInButton.setVisibility(View.VISIBLE);
                            fadingTextView.setVisibility(View.VISIBLE);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            SharedPreferences.Editor editor = getSharedPreferences(UI_MODE, MODE_PRIVATE).edit();
                            editor.putString("uiMode","Dark");
                            editor.apply();
                        }, theme_timeout);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Choose a theme",Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            builder.setNeutralButton("System Default", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    vibrateDevice();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    SharedPreferences.Editor editor = getSharedPreferences(UI_MODE, MODE_PRIVATE).edit();
                    editor.putString("uiMode","System");
                    editor.apply();
                    alertDialog1.dismiss();
                }
            });
        }
        alertDialog1 = builder.create();
        alertDialog1.show();

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
        if (haveNetwork()){
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
        else {
            Toast.makeText(this,"No internet connection",Toast.LENGTH_SHORT).show();
            food_load.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.VISIBLE);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (account != null) {
                food_load.setVisibility(View.INVISIBLE);
                Intent i=new Intent(this,Landing.class);
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
    //Network Checking Boolean
    private boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    have_WIFI = true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MobileData = true;
        }
        return have_MobileData||have_WIFI;
    }
}