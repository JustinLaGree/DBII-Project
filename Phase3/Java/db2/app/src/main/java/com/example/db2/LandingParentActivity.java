package com.example.db2;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;

public class LandingParentActivity extends BaseLogoutOnlyActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_landing_parent);
        super.onCreate(savedInstanceState);
    }
}
