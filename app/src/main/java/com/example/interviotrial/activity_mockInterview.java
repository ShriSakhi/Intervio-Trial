package com.example.interviotrial;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class activity_mockInterview extends AppCompatActivity {

    private TextView mTextMessage;
    TextToSpeech t1;
    public int i = 0;
    String sendAcc = "";
    public int mCurrentLine = -1;
    public ArrayList<String> mLines;
    public TextView mTextView;
    public Button next;

    Button buttonStart, buttonStop ;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(activity_mockInterview.this, MainActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.navigation_topic:
                    Intent intent1 = new Intent(activity_mockInterview.this, activity_companny.class);
                    startActivity(intent1);
                    return true;


                case R.id.navigation_profile:
                    Intent intent3 = new Intent(activity_mockInterview.this, activity_viewProfile.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_interview);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        t1=new TextToSpeech(getApplicationContext(), new
                TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            t1.setLanguage(Locale.UK);
                        }
                    }
                });

    }

    /*public void sendToServer(View v) {
        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "http://192.168.43.40:3000/abc/accuracy"; ///get?param1=hello";
        final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        String acc = response.optString("text");

                        Log.d("HERE", acc);
                        i += 1;
                        sendAcc = sendAcc + "Answer " + i + "accuracy is " + acc + "\n";

                        //Log.d("ACCURACY", sendAcc);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        queue.add(getRequest);
        getRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

// add it to the RequestQueue
        queue.add(getRequest);
    }

    */
    public void sendToServer(View v) {

        buttonStart = (Button) findViewById(R.id.record);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setEnabled(false);

        random = new Random();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Tag is", "This is a sample one");
                if(checkPermission()) {
                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                    CreateRandomAudioFileName(5) + "AudioRecording.mp3";

                    Log.d("Path", "Path is as follows " + AudioSavePathInDevice);

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    Toast.makeText(activity_mockInterview.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }

            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);

                Toast.makeText(activity_mockInterview.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();

                /*Intent returnIntent = new Intent();
                returnIntent.putExtra("result",AudioSavePathInDevice);
                setResult(Activity.RESULT_OK,returnIntent);

                finish();
*/
            }
        });

    }
    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity_mockInterview.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(activity_mockInterview.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(activity_mockInterview.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void seeNext (View v) {
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
                    new InputStreamReader(getAssets().open("misc")));
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
            Toast.makeText(getApplicationContext(),
                    "End Of The Interview", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(MockInterviewActivity.this, ResultsActivity.class);
            //intent.putExtra("message", sendAcc);
            //startActivity(intent);
            setContentView(R.layout.activity_results);
            return;
        }

        mCurrentLine++;
        mTextView.setText(mLines.get(mCurrentLine));
        t1.speak(  mLines.get(mCurrentLine) , TextToSpeech.QUEUE_FLUSH, null);


    }

}
