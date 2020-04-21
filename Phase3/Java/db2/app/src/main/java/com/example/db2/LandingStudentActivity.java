package com.example.db2;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.db2.helpers.UserSession;

public class LandingStudentActivity extends BaseLogoutActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_landing_student);
        super.onCreate(savedInstanceState);

        final TextView welcome_text = findViewById(R.id.textView_welcomeStudent);
        String welcomeStr = String.format("Welcome, %s!", UserSession.getInstance().name);
        welcome_text.setText(welcomeStr);

        final Button enrollasmentor = findViewById(R.id.button_enrollasmentor);
        final Button enrollasmentee = findViewById(R.id.button_enrollasmentee);
        final Button viewenrolledmeetings = findViewById(R.id.button_viewenrolledmeetings);
        final Button editaccount = findViewById(R.id.button_editaccount);

        final Intent editStudentIntent = new Intent(this, EditStudentAccountActivity.class);
        final Intent enrollAsMentorIntent = new Intent(this, EnrollMentorStudentActivity.class);
        final Intent enrollAsMenteeIntent = new Intent(this, EnrollMenteeStudentActivity.class);
        final Intent viewEnrolledMeetingsIntent = new Intent(this, ViewEnrolledMeetingsActivity.class);

        enrollasmentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(enrollAsMentorIntent);
            }
        });

        enrollasmentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(enrollAsMenteeIntent);
            }
        });

        viewenrolledmeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(viewEnrolledMeetingsIntent);
            }
        });

        editaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(editStudentIntent);
            }
        });


    }
}
