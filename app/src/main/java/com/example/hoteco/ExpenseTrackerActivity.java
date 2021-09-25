package com.example.hoteco;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hoteco.R;

public class ExpenseTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_tracker);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Expense Tracker");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}