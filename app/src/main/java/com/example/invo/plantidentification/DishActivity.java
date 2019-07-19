package com.example.invo.plantidentification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.invo.plantidentification.ui.dish.DishFragment;

public class DishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DishFragment.newInstance())
                    .commitNow();
        }
    }
}
