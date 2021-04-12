package com.aaxena.bhojan;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    LottieAnimationView lottieAnimationView, lottieAnimationView2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v3 =  inflater.inflate(R.layout.fragment_home,container,false);
        lottieAnimationView = v3.findViewById(R.id.animation_view_here);
        lottieAnimationView2 = v3.findViewById(R.id.animation_view_here2);
        if (isFirstTime()) {
            //Perform something only once
            Toast.makeText(getActivity(),"Tip: Tap cards to view on Map \uD83D\uDCA1",Toast.LENGTH_LONG).show();
        }
        if (haveNetwork()) {
            ListView myListView;
            List<Food> foodList;
            DatabaseReference foodDbAdd = FirebaseDatabase.getInstance().getReference("Food/Food");
            myListView = v3.findViewById(R.id.myListView);
            foodList = new ArrayList<>();
            foodDbAdd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    lottieAnimationView.setVisibility(View.GONE);
                    lottieAnimationView2.setVisibility(View.GONE);
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
    private boolean isFirstTime() {
        SharedPreferences preferences = getActivity().getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }
}

