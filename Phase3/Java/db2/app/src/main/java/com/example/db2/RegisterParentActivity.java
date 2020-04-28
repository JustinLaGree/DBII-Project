package com.example.db2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.db2.helpers.QueryExecution;

//register a parent user
public class RegisterParentActivity extends BaseBackOnlyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_parent);
        super.onCreate(savedInstanceState);

        //all input fields
        final EditText editText_RegParentFullName = findViewById(R.id.editText_RegParentFullName);
        final EditText editText_RegParentEmail = findViewById(R.id.editText_RegParentEmail);
        final EditText editText_RegParentPassword = findViewById(R.id.editText_RegParentPassword);
        final EditText editText_RegParentPhoneNumber = findViewById(R.id.editText_RegParentPhoneNumber);

        //possible redirect activities
        final Intent homeScreenIntent = new Intent(this, LoginActivity.class);

        //buttons
        final Button button_RegParentSubmit = findViewById(R.id.button_RegParentSubmit);

        //register the parent account
        button_RegParentSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //call register parent function
                String fullName = editText_RegParentFullName.getText().toString().toLowerCase();
                String email = editText_RegParentEmail.getText().toString().toLowerCase();
                String password = editText_RegParentPassword.getText().toString().toLowerCase();
                String phoneNumber = editText_RegParentPhoneNumber.getText().toString().toLowerCase();

                String query = String.format("INSERT INTO users (email, password, name, phone) VALUES ('%s', '%s', '%s', '%s')", email, password, fullName, phoneNumber);
                QueryExecution.executeQuery(query);

                query = String.format("INSERT INTO parents (parent_id) SELECT id FROM users WHERE email = '%s'", email);
                QueryExecution.executeQuery(query);

                startActivity(homeScreenIntent);
            }
        });
    }

    void registerParent(String full_name, String email, String password, String phone_number)
    {

    }
}
