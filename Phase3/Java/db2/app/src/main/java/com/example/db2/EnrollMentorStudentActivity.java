package com.example.db2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

public class EnrollMentorStudentActivity extends BaseLogoutActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enroll_mentor_student);
        super.onCreate(savedInstanceState);

    }
}
