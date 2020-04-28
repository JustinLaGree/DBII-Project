package com.example.db2;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//landing page for the parent
public class LandingParentActivity extends BaseLogoutOnlyActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_landing_parent);
        super.onCreate(savedInstanceState);

        //setup buttons
        final Button button_editStudents = findViewById(R.id.button_editStudents);
        final Button button_editParent = findViewById(R.id.button_editParent);

        //possible activity redirects
        final Intent viewStudents = new Intent(this, ViewChildrenActivity.class);
        viewStudents.putExtra("BACK_ACTIVITY", this.getClass().getName());
        final Intent editParent = new Intent(this, EditStudentAccountActivity.class);
        editParent.putExtra("BACK_ACTIVITY", this.getClass().getName());

        button_editParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editParent.putExtra("isParent", true);
                startActivity(editParent);
            }
        });

        button_editStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(viewStudents);
            }
        });
    }
}
