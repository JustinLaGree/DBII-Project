package com.example.db2;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.db2.helpers.QueryExecution;
import com.example.db2.helpers.UserSession;
import com.example.db2.models.Meeting;

import java.util.ArrayList;

public class MeetingsAdminActivity extends BaseLogoutActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        final TextView welcome_text = findViewById(R.id.welcome_text);
        String welcomeStr = String.format("Welcome, %s!", UserSession.getInstance().name);
        welcome_text.setText(welcomeStr);

        String query = "SELECT * FROM meetings ORDER BY date ASC";
        QueryExecution.executeQuery(query);

        ArrayList<Meeting> meetings = QueryExecution.getResponse(Meeting.class);
    }
}
