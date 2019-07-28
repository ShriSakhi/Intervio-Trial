package com.example.interviotrial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.interviotrial.Database.DBConnection;

public class activity_signUp extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(activity_signUp.this, MainActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.navigation_topic:
                    Intent intent1 = new Intent(activity_signUp.this, activity_company.class);
                    startActivity(intent1);
                    return true;


                case R.id.navigation_profile:
                    Intent intent3 = new Intent(activity_signUp.this, activity_viewProfile.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };

    Button select;
    SharedPreferences sharedPreferences;
    private EditText et_userName, et_emailId, et_password;
    private String uname, email, val;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private String password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        select = (Button) findViewById(R.id.signup);
        et_userName = (EditText) findViewById(R.id.Name);
        et_emailId = (EditText) findViewById(R.id.email);
        et_password = (EditText) findViewById(R.id.password);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    public boolean isVisited() {
        return true;
    }

    public final void onClickUpload(View v) {
        Intent mediaIntent = new Intent("android.intent.action.GET_CONTENT");
        mediaIntent.setType("*/*");
        this.startActivityForResult(mediaIntent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0
                && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Log.d("", "Video URI= " + uri);

            val = uri.getPath();
            val = val.toString();


            Button tvName = (Button)findViewById(R.id.resume);
            tvName.setText("File selected!");
        }
    }



    public void done(View v) {
        register();
        activity_signUp.DoSignup doSignup = new activity_signUp.DoSignup();
        doSignup.execute();
    }

    public void register() {
        intialize();
        if (!validate()) {
            Toast.makeText(this, "Signup has Failed", Toast.LENGTH_SHORT).show();

        }
    }

    public void signupSuccess() {
        Intent myIntent = new Intent(activity_signUp.this, MainActivity.class);
        startActivity(myIntent);
    }

    public boolean validate() {
        boolean valid = true;

        if (uname.isEmpty() || uname.length() > 32) {
            et_userName.setError("Please enter valid name");
            valid = false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_emailId.setError("Please enter valid Email Address");
            valid = false;
        }

        if (!isValidPassword(et_password.getText().toString().trim())) {
            et_password.setError("Please enter valid password");
        }

        return valid;
    }

    public void intialize() {

        uname = et_userName.getText().toString().trim();
        email = et_emailId.getText().toString().trim();
        password = et_password.getText().toString().trim();

    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public class DoSignup extends AsyncTask<String, Void, String> {
        String z = "";
        Boolean isSuccess = false;
        String uname = et_userName.getText().toString().trim();
        String email = et_emailId.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(activity_signUp.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(activity_signUp.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                Intent i = new Intent(activity_signUp.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        protected String doInBackground(String... params) {
            try {
                java.sql.Connection con = DBConnection.getConnection();
                if (con == null) {
                    z = "Error in connection with SQL server";
                    System.out.println("Connection Error!!");
                } else {
                    String query = "select * from IntervieweeDetails where emailId = '" + email + "' and userName = '" + uname + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        z = "User already exists";
                        isSuccess = false;

                    } else {

                        int flag = stmt.executeUpdate("insert into IntervieweeDetails(userName, emailId, password) values('" + uname + "','" + email + "','" + password + "');");
                        isSuccess = true;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserName", uname);
                        editor.apply();
                        System.out.println(flag);
                        z = "SignUp successful";
                        isSuccess = true;
                        System.out.println("Added user");
                    }
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = "Exceptions";
                Log.e("ERROR", ex.getMessage());

            }

            return z;
        }
    }
}

