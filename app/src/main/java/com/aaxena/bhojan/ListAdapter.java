package com.aaxena.bhojan;

import android.app.Activity;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

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
        ImageView foodImage = listItemView.findViewById(R.id.imageLoader);
        Food food = foodList.get(position);
        String url = food.getImageUrl();
        String lat = food.getLatitude();
        String lon = food.getLongitude();
        foodName.setText(food.getFood());
        foodDescription.setText(food.getDescription());
        foodSuggestions.setText(food.getSuggestions());
        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),""+lat+lon,Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(getContext())
                .load(url)
                .override(400,500)
                .fitCenter()
                .into(foodImage);
        return listItemView;
    }
}
