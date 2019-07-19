package com.example.invo.plantidentification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.invo.plantidentification.ui.plant.PlantFragment;

public class PlantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlantFragment.newInstance())
                    .commitNow();
        }
    }
}
