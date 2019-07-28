package com.example.interviotrial;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activity_viewProfile extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(activity_viewProfile.this, activity_home.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_topic:
                    Intent intent1 = new Intent(activity_viewProfile.this, activity_company.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_profile:
                    Intent intent3 = new Intent(activity_viewProfile.this, activity_viewProfile.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void goToSignUp(View v) {
        Intent intent = new Intent(activity_viewProfile.this, activity_signUp.class);
        startActivity(intent);
    }

    public void viewScoreCard(View v) {
        Intent intent1 = new Intent(activity_viewProfile.this, activity_view_scorecard.class);
        startActivity(intent1);
    }

}
