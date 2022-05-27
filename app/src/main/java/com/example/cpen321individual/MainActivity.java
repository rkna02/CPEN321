package com.example.cpen321individual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mapsButton;
    private Button modelButton;
    private Button weatherButton;
    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Google Maps button
        mapsButton = findViewById(R.id.maps_button);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Trying to open Google Maps");
                Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapsIntent);
            }
        });

        modelButton = findViewById(R.id.model_button);
        modelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
                Log.d(TAG, "Trying to load device phone model information");
                
            }
        });
    }

    // Activity Method
    private synchronized void checkLocationPermission() {
        // if user granted both permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Intent modelIntent = new Intent(MainActivity.this, ModelActivity.class);
            startActivity(modelIntent);
            return;

        // if user denied either one permission
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(MainActivity.this, "Location permissions are needed to show full information", Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(this)
                        .setTitle("Need Location Permissions")
                        .setMessage("Location permissions are needed to detect current city")
                        .setNegativeButton("Proceed anyways", new DialogInterface.OnClickListener() {
                            @Override
                            // OnClickListener Method, Toast requires Activity method
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "We need these location permissions to run!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                Intent modelIntent2 = new Intent(MainActivity.this, ModelActivityAlt.class);
                                startActivity(modelIntent2);
                            }
                        })
                        .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()  // create dialog
                        .show();

            // if user has never seen the permission request
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);  // request code usually doesn't matter
            }
        }
    }
}