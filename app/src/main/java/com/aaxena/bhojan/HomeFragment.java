package com.aaxena.bhojan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View v3 =  inflater.inflate(R.layout.fragment_home,container,false);
        Button fetch;
        fetch = v3.findViewById(R.id.fetch);
        fetch.setOnClickListener(new View.OnClickListener() {
            ListView myListView;
            List<Food> foodList;
            @Override
            public void onClick(View v) {
                DatabaseReference foodDbAdd = FirebaseDatabase.getInstance().getReference("Food/Food");
                myListView =  v3.findViewById(R.id.myListView);
                foodList = new ArrayList<>();
                foodDbAdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try{
                            foodList.clear();
                            for (DataSnapshot foodDatastamp : snapshot.getChildren()){
                                Food food = foodDatastamp.getValue(Food.class);
                                try {
                                    foodList.add(food);
                                }
                                catch (NullPointerException e)
                                {
                                 Toast.makeText(getContext(),"Heh",Toast.LENGTH_SHORT).show();
                                }
                            }
                            ListAdapter adapter = new ListAdapter(getActivity(),foodList);
                            myListView.setAdapter(adapter);
                        }
                        catch (Exception e){
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return v3;
    }
}
