package com.aaxena.bhojan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    private final Activity mContext;
    List<Food> foodList;

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

        TextView foodName = listItemView.findViewById(R.id.foodName);
        TextView foodDescription = listItemView.findViewById(R.id.foodDescription);
        TextView foodSuggestions = listItemView.findViewById(R.id.foodSuggestions);
        Food food = foodList.get(position);
        foodName.setText(food.getFood());
        foodDescription.setText(food.getDescription());
        foodSuggestions.setText(food.getSuggestions());

        return listItemView;
    }
}
