package com.aaxena.bhojan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class HomeFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v3 =  inflater.inflate(R.layout.fragment_home,container,false);
        if (haveNetwork()) {
            if (isFirstTime()) {
                // Tap Target Start
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    new MaterialTapTargetPrompt.Builder(getActivity())
                            .setTarget(R.id.bhojan)
                            .setPrimaryText("Grab the Shared Food!")
                            .setSecondaryText("Food shared by everyone will appear here.")
                            .setBackButtonDismissEnabled(false)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setPrimaryTextTypeface(getResources().getFont(R.font.productsans))
                            .setSecondaryTextTypeface(getResources().getFont(R.font.productsans))
                            .setBackgroundColour(getResources().getColor(R.color.orange_700))
                            .show();
                    new MaterialTapTargetPrompt.Builder(getActivity())
                            .setTarget(R.id.profile)
                            .setPrimaryText("Your Profile!")
                            .setSecondaryText("Keep a Track of your Account from here.")
                            .setBackButtonDismissEnabled(false)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setPrimaryTextTypeface(getResources().getFont(R.font.productsans))
                            .setSecondaryTextTypeface(getResources().getFont(R.font.productsans))
                            .setBackgroundColour(getResources().getColor(R.color.orange_700))
                            .show();
                    new MaterialTapTargetPrompt.Builder(getActivity())
                            .setTarget(R.id.share)
                            .setPrimaryText("Share Food!")
                            .setSecondaryText("You can share your own food from here, your location will be required for food shares so others could locate it easily")
                            .setBackButtonDismissEnabled(false)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setMaxTextWidth(R.dimen.max_prompt_width)
                            .setPrimaryTextTypeface(getResources().getFont(R.font.productsans))
                            .setSecondaryTextTypeface(getResources().getFont(R.font.productsans))
                            .setBackgroundColour(getResources().getColor(R.color.orange_700))
                            .show();
                    // Tap Target End
                } else {
                    new MaterialTapTargetPrompt.Builder(getActivity())
                            .setTarget(R.id.bhojan)
                            .setPrimaryText("Grab the Shared Food!")
                            .setSecondaryText("Food shared by everyone will appear here.")
                            .setBackButtonDismissEnabled(false)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setBackgroundColour(getResources().getColor(R.color.orange_700))
                            .show();
                    new MaterialTapTargetPrompt.Builder(getActivity())
                            .setTarget(R.id.profile)
                            .setPrimaryText("Your Profile!")
                            .setSecondaryText("Keep a Track of your Account from here.")
                            .setBackButtonDismissEnabled(false)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setBackgroundColour(getResources().getColor(R.color.orange_700))
                            .show();
                    new MaterialTapTargetPrompt.Builder(getActivity())
                            .setTarget(R.id.share)
                            .setPrimaryText("Share Food!")
                            .setSecondaryText("You can share your own food from here, your location will be required for food shares so others could locate it easily")
                            .setBackButtonDismissEnabled(false)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setMaxTextWidth(R.dimen.max_prompt_width)
                            .setBackgroundColour(getResources().getColor(R.color.orange_700))
                            .show();
                }
            }
            Toast.makeText(getContext(), "Refreshing Dishes \uD83D\uDE0B", Toast.LENGTH_SHORT).show();
            ListView myListView;
            List<Food> foodList;
            DatabaseReference foodDbAdd = FirebaseDatabase.getInstance().getReference("Food/Food");
            myListView = v3.findViewById(R.id.myListView);
            foodList = new ArrayList<>();
            foodDbAdd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        foodList.clear();
                        for (DataSnapshot foodDatastamp : snapshot.getChildren()) {
                            Food food = foodDatastamp.getValue(Food.class);
                            try {
                                foodList.add(food);
                            } catch (NullPointerException e) {
                                //DO NOT REMOVE THIS EMPTY CATCH
                            }
                        }
                        ListAdapter adapter = new ListAdapter(getActivity(), foodList);
                        myListView.setAdapter(adapter);
                    } catch (Exception e) {
                        //DO NOT REMOVE THIS EMPTY CATCH
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            View v =  inflater.inflate(R.layout.activity_no_internet,container,false);
            return v;
        }
        return v3;
    }

    //First Time Run Checker
    private boolean isFirstTime() {
        SharedPreferences preferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }
    //Network Checking Boolean
    private boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
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

