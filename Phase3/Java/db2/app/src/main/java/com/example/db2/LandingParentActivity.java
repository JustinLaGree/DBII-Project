package com.example.db2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.db2.helpers.UserSession;

public class LandingParentActivity extends BaseLogoutActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_landing_parent);
        super.onCreate(savedInstanceState);

        final TextView welcome_text = findViewById(R.id.textView_welcomeParent);
        String welcomeStr = String.format("Welcome, %s!", UserSession.getInstance().name);
        welcome_text.setText(welcomeStr);
    }
}
