package com.aaxena.bhojan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.bhojan:
                fragment = new HomeFragment();
                break;
            case R.id.share:
                fragment = new ShareFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        return true;
    };
}