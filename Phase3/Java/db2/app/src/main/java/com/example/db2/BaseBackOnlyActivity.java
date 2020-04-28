package com.example.db2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//only display the back button in the navbar
public class BaseBackOnlyActivity extends BaseLogoutBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Button logout_button = findViewById(R.id.button_logout);

        logout_button.setEnabled(false);
        logout_button.setVisibility(View.INVISIBLE);
    }
}
