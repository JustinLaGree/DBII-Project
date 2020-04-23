package com.example.db2;


import androidx.annotation.RequiresApi;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingStudentActivity extends BaseLogoutOnlyActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_landing_student);
        super.onCreate(savedInstanceState);

        final Button enrollasmentor = findViewById(R.id.button_enrollasmentor);
        final Button enrollasmentee = findViewById(R.id.button_enrollasmentee);
        final Button viewenrolledmeetings = findViewById(R.id.button_viewenrolledmeetings);
        final Button editaccount = findViewById(R.id.button_editaccount);

        final Intent editStudentIntent = new Intent(this, EditStudentAccountActivity.class);
        editStudentIntent.putExtra("BACK_ACTIVITY", this.getClass().getName());
        final Intent enrollAsMentorIntent = new Intent(this, EnrollMentorStudentActivity.class);
        enrollAsMentorIntent.putExtra("BACK_ACTIVITY", this.getClass().getName());
        final Intent enrollAsMenteeIntent = new Intent(this, EnrollMenteeStudentActivity.class);
        enrollAsMenteeIntent.putExtra("BACK_ACTIVITY", this.getClass().getName());
        final Intent viewEnrolledMeetingsIntent = new Intent(this, ViewEnrolledMeetingsActivity.class);
        viewEnrolledMeetingsIntent.putExtra("BACK_ACTIVITY", this.getClass().getName());

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
