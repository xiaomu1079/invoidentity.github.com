package com.example.invo.plantidentification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.invo.plantidentification.ui.animal.AnimalFragment;

public class AnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AnimalFragment.newInstance())
                    .commitNow();
        }
    }
}
