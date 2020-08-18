package com.test.mosun.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test.mosun.R;

public class AreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
    }

    public void onClick_handler(View view)
    {
        Intent intent = new Intent(AreaActivity.this, CourseActivity.class);
        startActivity(intent);
    }
}