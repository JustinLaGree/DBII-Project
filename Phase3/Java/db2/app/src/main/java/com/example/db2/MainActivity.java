package com.example.db2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.db2.helpers.UserSession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView welcome_text = findViewById(R.id.welcome_text);
        String welcomeStr = String.format("Welcome, %s!", UserSession.getInstance().name);
        welcome_text.setText(welcomeStr);

        boolean isAdmin = UserSession.getIsAdmin();
        boolean isParent = UserSession.getIsParent();
        boolean isStudent = UserSession.getIsStudent();
    }
}
