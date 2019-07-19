package com.example.invo.plantidentification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.invo.plantidentification.ui.car.CarFragment;

public class CarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CarFragment.newInstance())
                    .commitNow();
        }
    }
}
