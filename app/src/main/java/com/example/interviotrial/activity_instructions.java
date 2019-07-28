package com.example.interviotrial;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class activity_instructions extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(activity_instructions.this, activity_home.class);
                    startActivity(intent);
                    return true;

                case R.id.navigation_topic:
                    Intent intent1 = new Intent(activity_instructions.this, activity_company.class);
                    startActivity(intent1);
                    return true;

                case R.id.navigation_profile:
                    Intent intent3 = new Intent(activity_instructions.this, activity_viewProfile.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    public void takeMock(View view) {
        Intent myIntent = new Intent(this, activity_mockInterview.class);
        startActivity(myIntent);
    }

}
