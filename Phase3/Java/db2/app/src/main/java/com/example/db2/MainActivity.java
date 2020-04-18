package com.example.db2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button_login = findViewById(R.id.button_login);
        final Intent registerParentIntent = new Intent(this, RegisterParentActivity.class);
        final Intent registerStudentIntent = new Intent(this, RegisterStudentActivity.class);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login function goes here
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
