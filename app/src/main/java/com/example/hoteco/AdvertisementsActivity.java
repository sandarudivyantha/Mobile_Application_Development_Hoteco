package com.example.hoteco;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hoteco.R;

public class AdvertisementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisements);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Advertisements");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}