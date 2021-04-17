package com.aaxena.bhojan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

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
    FusedLocationProviderClient mFusedLocationClient;
    Double lon,lat;
    String longitude;
    String latitude;
    int PERMISSION_ID = 44;
    AlertDialog.Builder builder;
    AlertDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v2 = inflater.inflate(R.layout.fragment_share, container, false);
        progressDialog = getDialogProgressBar().create();
        //Location and geo
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        // method to get the location
        getLastLocation();
        //Other Database Obs
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
            try {
                latitude = lat.toString();
                longitude = lon.toString();
            }
            catch (NullPointerException nullPointerException)
            {
                //
            }
            if (food.isEmpty()){
                ScrollView nestedScrollView = v2.findViewById(R.id.scrollViewHere);
                nestedScrollView.fullScroll(View.FOCUS_UP);
                final Handler handler = new Handler();
                handler.postDelayed(() -> vibrateDevice(), 100);
                vibrateDeviceThird();
                foodName.setError("Food name is required");
            }
            else if (description.isEmpty()){
                ScrollView nestedScrollView = v2.findViewById(R.id.scrollViewHere);
                nestedScrollView.fullScroll(View.FOCUS_UP);
                final Handler handler = new Handler();
                handler.postDelayed(() -> vibrateDevice(), 100);
                vibrateDeviceThird();
                desc.setError("Description is required");
            }
            else if (suggestions.isEmpty()){
                ScrollView nestedScrollView = v2.findViewById(R.id.scrollViewHere);
                nestedScrollView.fullScroll(View.FOCUS_UP);
                final Handler handler = new Handler();
                handler.postDelayed(() -> vibrateDevice(), 100);
                vibrateDeviceThird();
                suggestion.setError("Please provide atleast one line suggestion :)");
            }
            else if (filePath != null){
                if (checkPermissions()) {
                    vibrateDevice();
                    Food food1 = new Food(food, description, suggestions, latitude, longitude);
                    DatabaseReference specimenReference = foodDbAdd.child("Food").push();
                    food1.setImageUrl("");
                    specimenReference.setValue(food1);
                    String key = specimenReference.getKey();
                    food1.setKey(key);
                    StorageReference ref
                            = storageReference
                            .child(
                                    "foodImages/"
                                            + filePath.getLastPathSegment());
                    ref.putFile(filePath)
                            .addOnProgressListener(snapshot -> {
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                            })
                            .addOnSuccessListener(
                                    taskSnapshot -> {
                                        Task<Uri> downloadUrl = ref.getDownloadUrl();
                                        downloadUrl.addOnSuccessListener(uri -> {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity().getApplicationContext(), "Food Details Shared Successfully!", Toast.LENGTH_SHORT).show();
                                            final Handler handler = new Handler();
                                            handler.postDelayed(() -> vibrateDeviceThird(), 100);
                                            final Handler handler2 = new Handler();
                                            handler2.postDelayed(() -> vibrateDevice(), 300);
                                            String imageReference = uri.toString();
                                            foodDbAdd.child("Food").child(food1.getKey()).child("imageUrl").setValue(imageReference);
                                            food1.setImageUrl(imageReference);
                                        });
                                    })
                            .addOnFailureListener(e -> Toast.makeText(getActivity().getApplicationContext(),
                                    "Image Upload Failed " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show());
                }
                else {
                    vibrateDeviceThird();
                    Toast.makeText(getContext(),"Unable to fetch location, Open App Settings and enable 'Location Permissions' \uD83D\uDE15",Toast.LENGTH_LONG).show();
                }
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
    public AlertDialog.Builder getDialogProgressBar() {

        if (builder == null) {
            builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Sharing Food ✨\n");
            builder.setMessage("Please Wait!");
            final ProgressBar progressBar = new ProgressBar(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            progressBar.setLayoutParams(lp);
            builder.setView(progressBar);
        }
        return builder;
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {
                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        lon = location.getLongitude();
                        lat = location.getLatitude();
                        //  latitudeTextView.setText(location.getLatitude() + "");
                        //  longitTextView.setText(location.getLongitude() + "");
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please turn on your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }
    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lon = mLastLocation.getLongitude();
            lat = mLastLocation.getLatitude();
        }
    };
    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
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
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            try {
                filePath = data.getData();
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
}