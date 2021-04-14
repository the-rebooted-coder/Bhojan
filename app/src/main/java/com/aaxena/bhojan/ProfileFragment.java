package com.aaxena.bhojan;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.avaris.flyfood.Menu;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    CircularImageView photo;
    TextView username;
    TextView email;
    FirebaseAuth mAuth;
    Button signOut,aboutDevs,themeSwitcher;
    AlertDialog alertDialog1;
    String name;
    public static final String UI_MODE = "uiMode";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v =  inflater.inflate(R.layout.fragment_profile,container,false);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());
        photo = v.findViewById(R.id.acc_photo);
        username = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        signOut = v.findViewById(R.id.sign_out);
        mAuth = FirebaseAuth.getInstance();
        aboutDevs = v.findViewById(R.id.abtDevs);
        aboutDevs.setOnClickListener(v1 -> {
            Intent toAbtDevs = new Intent(getContext(),AboutDevelopers.class);
            startActivity(toAbtDevs);
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            getActivity().finish();
        });
        themeSwitcher = v.findViewById(R.id.themeFromProfile);
        themeSwitcher.setOnClickListener(v12 -> {
            vibrateDevice();
            CreateAlertDialogWithRadioButtonGroup();
        });
        FirebaseUser mUser = mAuth.getCurrentUser();
        photo.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                vibrateDevice();
                Toast.makeText(getActivity(),"Maybe Tapping Twice Might Help! \uD83E\uDD37",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleClick(View view) {
                vibrateDevice();
                int splash_screen_time_out = 360;
                new Handler().postDelayed(() -> {
                   vibrateDeviceSecond();
                }, splash_screen_time_out);
                Toast.makeText(getActivity(),"Bhojan, App developed by One Silicon Diode ;)",Toast.LENGTH_SHORT).show();
                Intent toEaster = new Intent(getActivity(), Menu.class);
                startActivity(toEaster);
            }
        }));

        if (account !=null){
            String personName = account.getDisplayName();
            username.setText(personName);
            String personEmail = account.getEmail();
            email.setText(personEmail);
            Uri photoUrl = account.getPhotoUrl(); Glide.with(this).load(photoUrl).into(photo);
            signOut.setOnClickListener(new DoubleClick(new DoubleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    vibrateDevice();
                    Toast.makeText(getActivity(),"Tap twice to sign out!\uD83D\uDEF6",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onDoubleClick(View view) {
                    vibrateDevice();
                    Toast.makeText(getActivity(),"Signing Out",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),"Have a nice day "+personName,Toast.LENGTH_SHORT).show();
                    int death_text = 2800;
                    new Handler().postDelayed(() -> {
                        ((ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE))
                                .clearApplicationUserData();
                    }, death_text);
                }
            }));
        }
        return v;
    }
    private void vibrateDeviceSecond() {
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(32, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(28);
        }
    }
    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(28, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(25);
        }
    }
    public void CreateAlertDialogWithRadioButtonGroup() {
        int nightModeFlags =
                this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Theme for Bhojan");
        builder.setPositiveButton("Light", (dialog, which) -> {
            switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                    vibrateDevice();
                    alertDialog1.dismiss();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(getContext(),"Switched to Light Mode️ ☀",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(UI_MODE, MODE_PRIVATE).edit();
                    editor.putString("uiMode","Light");
                    editor.apply();
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    Toast.makeText(getContext(),"Already in Light Mode ☀️",Toast.LENGTH_SHORT).show();
                    alertDialog1.dismiss();
                    break;
                default:
                    Toast.makeText(getContext(),"Choose a theme",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Dark", (dialog, which) -> {
            switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                    Toast.makeText(getContext(),"Already in Dark Mode \uD83C\uDF19",Toast.LENGTH_SHORT).show();
                    alertDialog1.dismiss();
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    vibrateDevice();
                    alertDialog1.dismiss();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(getContext(),"Switched to Dark Mode \uD83C\uDF19️",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(UI_MODE, MODE_PRIVATE).edit();
                    editor.putString("uiMode","Dark");
                    editor.apply();
                    break;
                default:
                    Toast.makeText(getContext(),"Choose a theme",Toast.LENGTH_SHORT).show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            builder.setNeutralButton("System Default", (dialog, which) -> {
                vibrateDevice();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                Toast.makeText(getContext(),"Switched to System Default️ \uD83C\uDF17",Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(UI_MODE, MODE_PRIVATE).edit();
                editor.putString("uiMode","System");
                editor.apply();
                alertDialog1.dismiss();
            });
        }
        alertDialog1 = builder.create();
        alertDialog1.show();

    }
}
