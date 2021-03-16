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
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Test Succcess",Toast.LENGTH_SHORT).show();
            }
        });

        return v2;
    }
}
