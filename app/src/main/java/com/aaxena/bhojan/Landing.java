package com.aaxena.bhojan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // Initialize
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        // Set Bhojan Selected
        bottomNavigationView.setSelectedItemId(R.id.bhojan);

        // Item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bhojan:
                    return true;

                case R.id.share:
                    startActivity(new Intent(getApplicationContext()
                            ,Share.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.profile:
                    startActivity(new Intent(getApplicationContext()
                            ,Profile.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }
}