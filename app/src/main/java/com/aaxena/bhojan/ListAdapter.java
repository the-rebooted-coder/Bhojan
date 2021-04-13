package com.aaxena.bhojan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends ArrayAdapter {
    private final Activity mContext;
    List<Food> foodList;
    Button moreDetails;

    public ListAdapter(Activity mContext, List<Food> foodList){
        super(mContext,R.layout.list_item,foodList);
        this.mContext = mContext;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =  mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null,true);
        moreDetails = listItemView.findViewById(R.id.moreDetails);
        TextView foodName = listItemView.findViewById(R.id.foodName);
        TextView foodDescription = listItemView.findViewById(R.id.foodDescription);
        TextView foodSuggestions = listItemView.findViewById(R.id.foodSuggestions);
        SelectableRoundedImageView foodImage = listItemView.findViewById(R.id.imageLoader);
        TextView scrollingText = listItemView.findViewById(R.id.address);
        Food food = foodList.get(position);
        String url = food.getImageUrl();
        String lat = food.getLatitude();
        String lon = food.getLongitude();
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        foodName.setText(food.getFood());
        foodDescription.setText(food.getDescription());
        foodSuggestions.setSelected(true);
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            try{
                    scrollingText.setText(cityName+stateName);
                    scrollingText.setSelected(true);
            }
            catch (Exception e){
                //Couldn't fetch location
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        foodSuggestions.setText(food.getSuggestions());
        moreDetails.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
               vibrateDeviceSecond();
               Toast.makeText(getContext(),"Tap twice to view "+food.getFood()+" on map! \uD83D\uDDFA️",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleClick(View view) {
                vibrateDeviceSecond();
                int splash_screen_time_out = 360;
                new Handler().postDelayed(() -> {
                    vibrateDevice();
                }, splash_screen_time_out);
                Toast.makeText(getContext(),"Viewing "+food.getFood()+" on map! \uD83D\uDCCC",Toast.LENGTH_SHORT).show();
                String showPin = "https://www.google.com/maps/search/?api=1&query="+lat+","+lon;
                try {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(showPin));
                    getContext().startActivity(intent);
                }
                catch (Exception e){
                    //Something out of the blues happened!
                }
            }
        }));
        Glide.with(getContext())
                .load(url)
                .override(400,500)
                .fitCenter()
                .into(foodImage);
        return listItemView;
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
        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(27);
        }
    }
}