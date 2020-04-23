package com.example.db2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

public class BaseLogoutBackActivity extends BaseLogoutOnlyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent backIntent = null;

        final Button back_button = findViewById(R.id.back_button);

        back_button.setEnabled(true);
        back_button.setVisibility(View.VISIBLE);

        try {
            String className = this.getIntent().getStringExtra("BACK_ACTIVITY");
            Class<?> clazz = Class.forName(className);
            backIntent = new Intent(this, clazz);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent finalBackIntent = backIntent;

        back_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                startActivity(finalBackIntent);
            }
        });
    }
}
