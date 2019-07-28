package com.example.interviotrial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_home extends AppCompatActivity {
    private TextView mTextMessage;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    String name;
    Button logout, company;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("Testing", "onNavigationItemSelected: ");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("homePage", "case : navigation_home");
                    Intent intent = new Intent(activity_home.this, activity_home.class);
                    startActivity(intent);
                    return true;

                case R.id.navigation_topic:
                    Log.d("topic", "case : navigation_topic");
                    Intent intent1 = new Intent(activity_home.this, activity_company.class);
                    startActivity(intent1);
                    return true;

                case R.id.navigation_profile:
                    Log.d("profile", "case : navigation_profile");
                    Intent intent3 = new Intent(activity_home.this, activity_viewProfile.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("UserName","");
        logout = (Button)findViewById(R.id.logout);


        if(!name.equals("")) {
            logout.setVisibility(View.VISIBLE);
        }


    }

    public void onClickInterview(View view){
        Intent intent2 = new Intent(this, activity_instructions.class);
        startActivity(intent2);
    }

}
