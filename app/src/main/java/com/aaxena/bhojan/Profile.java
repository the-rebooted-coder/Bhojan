package com.aaxena.bhojan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        // Set Bhojan Selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        // Item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bhojan:
                    startActivity(new Intent(getApplicationContext()
                            ,Landing.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.share:
                    startActivity(new Intent(getApplicationContext()
                            ,Share.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.profile:
                    return true;
            }
            return false;
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext()
                ,Landing.class));
        overridePendingTransition(0,0);
    }
}