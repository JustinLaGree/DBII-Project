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

public class RegisterStudentActivity extends BaseBackOnlyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_student);
        super.onCreate(savedInstanceState);

        final EditText editText_RegStudentFullName = findViewById(R.id.editText_regStudentFullName);
        final EditText editText_RegStudentEmail = findViewById(R.id.editText_regStudentEmail);
        final EditText editText_RegStudentGradeLevel = findViewById(R.id.editText_regStudentGradeLevel);
        final EditText editText_RegStudentParentEmail = findViewById(R.id.editText_regStudentParentEmail);
        final EditText editText_RegStudentPassword = findViewById(R.id.editText_regStudentPassword);
        final EditText editText_RegStudentPhoneNumber = findViewById(R.id.editText_regStudentPhoneNumber);

        final Intent homeScreenIntent = new Intent(this, LoginActivity.class);

        final Button button_RegStudentSubmit = findViewById(R.id.button_regStudentSubmit);

        button_RegStudentSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                String name = editText_RegStudentFullName.getText().toString().toLowerCase();
                String email = editText_RegStudentEmail.getText().toString().toLowerCase();
                String grade = editText_RegStudentGradeLevel.getText().toString().toLowerCase();
                String parent_email = editText_RegStudentParentEmail.getText().toString().toLowerCase();
                String password = editText_RegStudentPassword.getText().toString().toLowerCase();
                String phone = editText_RegStudentPhoneNumber.getText().toString().toLowerCase();

                String query = String.format("INSERT INTO users (email, password, name, phone) VALUES ('%s', '%s', '%s', '%s')", email, password, name, phone);
                QueryExecution.executeQuery(query);

                query = String.format("INSERT INTO students SELECT (SELECT id FROM users WHERE email = '%s'), ('%s'), (SELECT parent_id FROM parents WHERE parent_id IN (SELECT id FROM users WHERE LOWER(email) = '%s'))", email, grade, parent_email);
                QueryExecution.executeQuery(query);

                startActivity(homeScreenIntent);

            }
        });
    }


}
