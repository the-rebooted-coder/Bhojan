package com.aaxena.bhojan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShareFragment extends Fragment {

    TextInputEditText foodName;
    TextInputEditText desc;
    TextInputEditText suggestion;
    MaterialButton share;
    DatabaseReference foodDbAdd;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v2 = inflater.inflate(R.layout.fragment_share, container, false);
        foodName = v2.findViewById(R.id.nameOfFood);
        desc = v2.findViewById(R.id.descriptionOfFood);
        suggestion = v2.findViewById(R.id.suggestionsToGive);
        share = v2.findViewById(R.id.shareFood);
        foodDbAdd = FirebaseDatabase.getInstance().getReference().child("Food");
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = foodName.getText().toString();
                String description = desc.getText().toString();
                String sugs = suggestion.getText().toString();
                if (food.isEmpty()){
                    foodName.setError("Food name is required");
                }
                else if (description.isEmpty()){
                    desc.setError("Description is required");
                }
                else if (sugs.isEmpty()){
                    suggestion.setText("");
                }
                else {
                    Food food1 = new Food(food,description,sugs);
                    foodDbAdd.push().setValue(food1);
                    Toast.makeText(getActivity().getApplicationContext(),"Food Details Shared Successfully!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v2;
    }
}
