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
import com.example.db2.helpers.UserSession;
import com.example.db2.helpers.UserType;
import com.example.db2.models.Admin;
import com.example.db2.models.Student;
import com.example.db2.models.User;

import java.util.List;

public class EditStudentAccountActivity extends BaseLogoutActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_student_account);
        super.onCreate(savedInstanceState);

        final EditText editFullName = findViewById(R.id.editText_changeName);
        final EditText editPhoneNumber = findViewById(R.id.editText_changePhone);
        final EditText editEmail = findViewById(R.id.editText_changeEmail);
        final EditText editGrade = findViewById(R.id.editText_changeGrade);
        final EditText editPassword = findViewById(R.id.editText_changePassword);
        final Button submitButton = findViewById(R.id.button_submitChanges);
        final Intent landingPageIntent = new Intent(this, LandingStudentActivity.class);

        String name = UserSession.getInstance().name;
        String phone = UserSession.getInstance().phone;
        String email = UserSession.getInstance().email;
        String password = UserSession.getInstance().password;

        String query = String.format("SELECT * from students WHERE student_id = '%s'", UserSession.getInstance().id);
        QueryExecution.executeQuery(query);
        List<Student> student = QueryExecution.getResponse(Student.class);
        Integer grade = student.get(0).grade;

        editFullName.setText(name);
        editPhoneNumber.setText(phone);
        editEmail.setText(email);
        editGrade.setText(grade.toString());
        editPassword.setText(password);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String submit_query_users = String.format("UPDATE users SET email = '%s', password = '%s', name = '%s', phone = '%s' WHERE id = '%s'",
                                                    editEmail.getText(), editPassword.getText(), editFullName.getText(), editPhoneNumber.getText(), UserSession.getInstance().id);
                String submit_query_students = String.format("UPDATE students SET grade = '%s' WHERE student_id = '%s'", editGrade.getText(), UserSession.getInstance().id);

                QueryExecution.executeQuery(submit_query_users);
                QueryExecution.executeQuery(submit_query_students);

                String query_refresh = String.format("SELECT * from users WHERE id = '%s'", UserSession.getInstance().id);
                QueryExecution.executeQuery(query_refresh);
                List<User> users = QueryExecution.getResponse(User.class);

                if (users != null && users.size() >= 1)
                {
                    UserSession.setInstance(users.get(0));
                }


                startActivity(landingPageIntent);
            }
        });

    }
}
