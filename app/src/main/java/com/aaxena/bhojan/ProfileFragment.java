package com.aaxena.bhojan;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
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


public class ProfileFragment extends Fragment {

    CircularImageView photo;
    TextView username;
    TextView email;
    FirebaseAuth mAuth;
    Button signOut,aboutDevs;

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
        });
        FirebaseUser mUser = mAuth.getCurrentUser();
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Bhojan, App developed by One Silicon Diode ;)",Toast.LENGTH_SHORT).show();
                Intent toEaster = new Intent(getActivity(), Menu.class);
                startActivity(toEaster);
            }
        });

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
    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(28, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(25);
        }
    }
}
