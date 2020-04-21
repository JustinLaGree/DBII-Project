package com.example.db2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.db2.helpers.UserSession;

public class BaseLogoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent loginIntent = new Intent(this, LoginActivity.class);

        final Button logout_button = findViewById(R.id.button_logout);

        final TextView welcome_text = findViewById(R.id.welcome_textView);
        String welcomeStr = String.format("Welcome, %s!", UserSession.getInstance().name);
        welcome_text.setText(welcomeStr);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                UserSession.setInstance(null);
                startActivity(loginIntent);
            }
        });
    }
}
