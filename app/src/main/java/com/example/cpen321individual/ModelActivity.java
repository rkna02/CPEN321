package com.example.cpen321individual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ModelActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager; // Continuously pulls location information
    final static String TAG = "ModelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        } else {
            TextView cityTextView = findViewById(R.id.city_textView);
            cityTextView.setText("Current City: \nUnknown");
        }

        TextView manufactorTextView = findViewById(R.id.manufactor_textView);
        manufactorTextView.setText("Manufacturer: \n" + Build.MANUFACTURER);

        TextView modelTextView = findViewById(R.id.model_textView);
        modelTextView.setText("Model: \n" + Build.MODEL);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        List<Address> addressList;
        Geocoder geocoder = new Geocoder(ModelActivity.this, Locale.getDefault());
        TextView cityTextView = findViewById(R.id.city_textView);
        try {
            addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String city = addressList.get(0).getLocality();
            cityTextView.setText("Current City: \n" + city);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}