package com.example.db2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        final EditText editText_RegStudentFullName = findViewById(R.id.editText_regStudentFullName);
        final EditText editText_RegStudentEmail = findViewById(R.id.editText_regStudentEmail);
        final EditText editText_RegStudentGradeLevel = findViewById(R.id.editText_regStudentGradeLevel);
        final EditText editText_RegStudentParentEmail = findViewById(R.id.editText_regStudentParentEmail);
        final EditText editText_RegStudentPassword = findViewById(R.id.editText_regStudentPassword);
        final EditText editText_RegStudentPhoneNumber = findViewById(R.id.editText_regStudentPhoneNumber);

        final Intent homeScreenIntent = new Intent(this, LoginActivity.class);

        final Button button_RegStudentBack = findViewById(R.id.button_regStudentBack);
        final Button button_RegStudentSubmit = findViewById(R.id.button_regStudentSubmit);

        button_RegStudentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the register student function here
            }
        });

        button_RegStudentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreenIntent);
            }
        });
    }


}
