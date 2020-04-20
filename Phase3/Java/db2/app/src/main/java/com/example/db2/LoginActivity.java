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
import com.example.db2.models.User;
import com.example.db2.helpers.UserSession;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public boolean loginError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Intent mainIntent = new Intent(this, MainActivity.class);
        final Intent registerParentIntent = new Intent(this, RegisterParentActivity.class);
        final Intent registerStudentIntent = new Intent(this, RegisterStudentActivity.class);

        final Button button_login = findViewById(R.id.button_login);
        final EditText textBox_email = findViewById(R.id.textBox_email);
        final EditText textBox_password = findViewById(R.id.textBox_password);

        if (UserSession.getInstance() != null){
            startActivity(mainIntent);
        }

        button_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                String email = textBox_email.getText().toString().toLowerCase();
                String password = textBox_password.getText().toString();

                String query = String.format("SELECT * from users WHERE LOWER(email)='%s' AND password='%s'", email, password);

                QueryExecution.executeQuery(query);

                ArrayList<User> users = QueryExecution.getResponse(User.class);

                if (users != null && users.size() >= 1) {
                    UserSession.setInstance(users.get(0));
                    startActivity(mainIntent);
                }

                loginError = (UserSession.getInstance() == null);
            }
        });

        final Button button_registerAsParent = findViewById(R.id.button_registerAsParent);
        button_registerAsParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to register parent activity
                startActivity(registerParentIntent);
            }
        });

        final Button button_registerAsStudent = findViewById(R.id.button_registerAsStudent);
        button_registerAsStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to register student activity
                startActivity(registerStudentIntent);
            }
        });


    }
}
