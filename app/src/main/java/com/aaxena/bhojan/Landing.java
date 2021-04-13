
package com.aaxena.bhojan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Landing extends AppCompatActivity {
    AnimatedBottomBar animatedBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        animatedBottomBar = findViewById(R.id.bottomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()){
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
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {
                //DO NOTHING
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}