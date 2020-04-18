package com.example.db2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parent);

        final EditText editText_RegParentFullName = findViewById(R.id.editText_RegParentFullName);
        final EditText editText_RegParentEmail = findViewById(R.id.editText_RegParentEmail);
        final EditText editText_RegParentPassword = findViewById(R.id.editText_RegParentPassword);
        final EditText editText_RegParentPhoneNumber = findViewById(R.id.editText_RegParentPhoneNumber);

        final Intent homeScreenIntent = new Intent(this, MainActivity.class);

        final Button button_RegParentBack = findViewById(R.id.button_regParentBack);
        final Button button_RegParentSubmit = findViewById(R.id.button_RegParentSubmit);

        button_RegParentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call register parent function
            }
        });

        button_RegParentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeScreenIntent);
            }
        });
    }

    void registerParent(String full_name, String email, String password, String phone_number)
    {

    }
}
