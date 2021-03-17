package com.aaxena.bhojan;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ProfileFragment extends Fragment {

    CircularImageView photo;
    TextView username;
    TextView email;
    FirebaseAuth mAuth;
    Button signOut;

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
        FirebaseUser mUser = mAuth.getCurrentUser();

        if (account !=null){
            //Google
            String personName = account.getDisplayName();
            username.setText(personName);
            String personEmail = account.getEmail();
            email.setText(personEmail);
            Uri photoUrl = account.getPhotoUrl(); Glide.with(this).load(photoUrl).into(photo);
            signOut.setOnClickListener(view -> {
                Vibrator v2 = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                v2.vibrate(30);
                Toast.makeText(getActivity(),"Signing Out",Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"Goodbye"+personName,Toast.LENGTH_SHORT).show();
                int death_text = 2800;
                new Handler().postDelayed(() -> {
                    ((ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE))
                            .clearApplicationUserData();
                }, death_text);
            });
        }
        return v;
    }
}
