package com.example.cpen321individual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ModelActivityAlt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_alt);

        TextView cityTextView = findViewById(R.id.city_textView2);
        cityTextView.setText("Current City: \nUnknown");

        TextView manufacturerTextView = findViewById(R.id.manufactor_textView2);
        manufacturerTextView.setText("Manufacturer: \n" + Build.MANUFACTURER);

    }
}