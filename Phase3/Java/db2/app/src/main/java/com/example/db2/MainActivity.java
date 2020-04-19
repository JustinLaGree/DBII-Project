package com.example.db2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent registerParentIntent = new Intent(this, RegisterParentActivity.class);
        final Intent registerStudentIntent = new Intent(this, RegisterStudentActivity.class);

        final Button button_login = findViewById(R.id.button_login);
        final EditText textBox_email = findViewById(R.id.textBox_email);
        final EditText textBox_password = findViewById(R.id.textBox_password);

        button_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                StringBuilder response = new StringBuilder();

                try {
                    ApplicationConfig config = ApplicationConfig.getInstance();
                    URL url = new URL(ApplicationConfig.getFullApiPath());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);

                    String email = textBox_email.getText().toString().toLowerCase();
                    String password = textBox_password.getText().toString();

                    String query = String.format("SELECT * from users WHERE LOWER(email)='%s' AND password='%s'", email, password);
                    con.getOutputStream().write(query.getBytes("UTF8"));

                    try(BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream(), "utf-8"))) {
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        System.out.println(response.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e){
                    return;
                }

                String x = response.toString();

                //startActivity(landingActivity);
            }
        });

        final Button button_registerAsParent = findViewById(R.id.button_registerAsParent);
        button_registerAsParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to register parent activity
                startActivity(registerParentIntent);
            }
        });

        final Button button_registerAsStudent = findViewById(R.id.button_registerAsStudent);
        button_registerAsStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to register student activity
                startActivity(registerStudentIntent);
            }
        });


    }
}
