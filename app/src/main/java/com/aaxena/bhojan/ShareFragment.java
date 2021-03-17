package com.aaxena.bhojan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ShareFragment extends Fragment {

    TextInputEditText foodName;
    TextInputEditText desc;
    TextInputEditText suggestion;
    MaterialButton share;
    DatabaseReference foodDbAdd;
    ImageView foodImage;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v2 = inflater.inflate(R.layout.fragment_share, container, false);
        foodName = v2.findViewById(R.id.nameOfFood);
        desc = v2.findViewById(R.id.descriptionOfFood);
        suggestion = v2.findViewById(R.id.suggestionsToGive);
        share = v2.findViewById(R.id.shareFood);
        foodImage = v2.findViewById(R.id.foodImage);
        foodDbAdd = FirebaseDatabase.getInstance().getReference().child("Food");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        foodImage.setOnClickListener(v -> selectImage());
        share.setOnClickListener(v -> {
            String food = foodName.getText().toString();
            String description = desc.getText().toString();
            String suggestions = suggestion.getText().toString();
            if (food.isEmpty()){
                final Handler handler = new Handler();
                handler.postDelayed(() -> vibrateDevice(), 100);
                vibrateDeviceThird();
                foodName.setError("Food name is required");
            }
            else if (description.isEmpty()){
                final Handler handler = new Handler();
                handler.postDelayed(() -> vibrateDevice(), 100);
                vibrateDeviceThird();
                desc.setError("Description is required");
            }
            else if (suggestions.isEmpty()){
                final Handler handler = new Handler();
                handler.postDelayed(() -> vibrateDevice(), 100);
                vibrateDeviceThird();
                suggestion.setError("Please provide atleast one line suggestion :)");
             }
            else if (filePath != null){
                vibrateDevice();
                Food food1 = new Food(food,description,suggestions);
                DatabaseReference specimenReference = foodDbAdd.child("Food").push();
                food1.setImageUrl("");
                specimenReference.setValue(food1);
                String key = specimenReference.getKey();
                food1.setKey(key);
                StorageReference ref
                        = storageReference
                        .child(
                                "foodImages/"
                                        +filePath.getLastPathSegment());
                ref.putFile(filePath)
                        .addOnSuccessListener(
                                taskSnapshot -> {
                                    Task<Uri> downloadUrl = ref.getDownloadUrl();
                                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageReference = uri.toString();
                                            foodDbAdd.child("Food").child(food1.getKey()).child("imageUrl").setValue(imageReference);
                                            food1.setImageUrl(imageReference);
                                        }
                                    });
                                })
                        .addOnFailureListener(e -> Toast.makeText(getActivity().getApplicationContext(),
                                        "Image Upload Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show());
                Toast.makeText(getActivity().getApplicationContext(),"Food Details Shared Successfully!",Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(() -> vibrateDeviceThird(), 100);
                final Handler handler2 = new Handler();
                handler2.postDelayed(() -> vibrateDevice(), 300);
            }
            else {
                final Handler handler = new Handler();
                handler.postDelayed(() -> vibrateDevice(), 100);
                vibrateDeviceThird();
                Toast.makeText(getActivity().getApplicationContext(),"Image is required",Toast.LENGTH_SHORT).show();
            }
        });
        return v2;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
        vibrateDeviceThird();
        Toast.makeText(getContext(),"Pick a yummy image",Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(() -> vibrateDevice(), 100);
    }
    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getActivity().getContentResolver(),
                                filePath);
                foodImage.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void vibrateDevice() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(32, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(28);
        }
    }
    private void vibrateDeviceThird() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(36, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(30);
        }
    }
}
