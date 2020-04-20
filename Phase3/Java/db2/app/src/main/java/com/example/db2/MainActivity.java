package com.example.db2;

import android.os.Bundle;
import android.widget.TextView;

import com.example.db2.helpers.UserSession;

public class MainActivity extends BaseLogoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        final TextView welcome_text = findViewById(R.id.welcome_text);
        String welcomeStr = String.format("Welcome, %s!", UserSession.getInstance().name);
        welcome_text.setText(welcomeStr);
    }
}
