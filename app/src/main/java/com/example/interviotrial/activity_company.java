package com.example.interviotrial;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class activity_company extends AppCompatActivity {

    private TextView mTextMessage;
    TextToSpeech t1;
    public int mCurrentLine = -1;
    public ArrayList<String> mLines;
    public TextView mTextView;
    public Button next;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("Testing", "onNavigationItemSelected: ");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("home", "case : navigation_home");
                    Intent intent1 = new Intent(activity_company.this, MainActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_topic:
                    Log.d("topic", "case : navigation_topic");
                    Intent intent2 = new Intent(activity_company.this, activity_company.class);
                    startActivity(intent2);
                    return true;

                case R.id.navigation_profile:
                    Log.d("profile", "case : navigation_profile");
                    Intent intent4 = new Intent(activity_company.this, activity_viewProfile.class);
                    startActivity(intent4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void viewNext(View v) {
        mLines = new ArrayList<String>();
        mTextView = (TextView) findViewById(R.id.questionView);
        next = (Button)findViewById(R.id.next);

        try {
            readLinesAndSaveToArrayList();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        setTextAndIncrement();

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setTextAndIncrement();
            }
        });
    }


    private void readLinesAndSaveToArrayList() throws IOException {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("companyQuestions")));
            String line;
            while ((line = reader.readLine()) != null) {
                mLines.add(line);
            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }

    }

    private void setTextAndIncrement() {
        if (mCurrentLine == mLines.size()-2) {
            Intent myIntent = new Intent(this, MainActivity
                    .class);
            startActivity(myIntent);
            return;
        }


        mCurrentLine++;
        mTextView.setText(mLines.get(mCurrentLine));
        //t1.speak(  mLines.get(mCurrentLine) , TextToSpeech.QUEUE_FLUSH, null);


    }
}


