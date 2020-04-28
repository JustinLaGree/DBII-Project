package com.example.db2;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.db2.helpers.QueryExecution;
import com.example.db2.helpers.UserSession;
import com.example.db2.models.Student;
import com.example.db2.models.User;

import java.util.List;

public class EditStudentAccountActivity extends BaseLogoutBackActivity {
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
        final Intent landingPageStudentIntent = new Intent(this, LandingStudentActivity.class);
        final Intent landingPageParentIntent = new Intent(this, LandingParentActivity.class);

        String name = UserSession.getInstance().name;
        String phone = UserSession.getInstance().phone;
        String email = UserSession.getInstance().email;
        String password = UserSession.getInstance().password;
        Integer id;
        boolean isParent = getIntent().getBooleanExtra("isParent", false);
        boolean comesFromParent = getIntent().getBooleanExtra("comesFromParent", false);



        if(isParent)
        {
            editFullName.setText(UserSession.getInstance().name);
            editPhoneNumber.setText(UserSession.getInstance().phone);
            editEmail.setText(UserSession.getInstance().email);
            editGrade.setText(UserSession.getInstance().password);
            editPassword.setAlpha(0);
            editPassword.setEnabled(false);
            id = UserSession.getInstance().id;
        }
        else if(!isParent && !comesFromParent)
        {
            String query = String.format("SELECT * from students WHERE student_id = '%s'", UserSession.getInstance().id);
            QueryExecution.executeQuery(query);
            List<Student> student = QueryExecution.getResponse(Student.class);
            Integer grade = student.get(0).grade;
            id = UserSession.getInstance().id;
            editFullName.setText(name);
            editPhoneNumber.setText(phone);
            editEmail.setText(email);
            editGrade.setText(grade.toString());
            editPassword.setText(password);
        }
        else
        {
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");

            String query = String.format("SELECT * from users WHERE email = '%s'", email);
            QueryExecution.executeQuery(query);
            List<User> student = QueryExecution.getResponse(User.class);
            password = student.get(0).password;
            phone = student.get(0).phone;
            id = student.get(0).id;

            query = String.format("SELECT * from students WHERE student_id = '%s'", id);
            QueryExecution.executeQuery(query);
            List<Student> student_grade = QueryExecution.getResponse(Student.class);
            Integer grade = student_grade.get(0).grade;

            editFullName.setText(name);
            editPhoneNumber.setText(phone);
            editEmail.setText(email);
            editGrade.setText(grade.toString());
            editPassword.setText(password);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isParent)
                {
                    String submit_query_users = String.format("UPDATE users SET email = '%s', password = '%s', name = '%s', phone = '%s' WHERE id = '%s'",
                            editEmail.getText(), editGrade.getText(), editFullName.getText(), editPhoneNumber.getText(), id);
                    QueryExecution.executeQuery(submit_query_users);
                    String query_refresh = String.format("SELECT * from users WHERE id = '%s'", id);
                    QueryExecution.executeQuery(query_refresh);
                    List<User> users = QueryExecution.getResponse(User.class);

                    if (users != null && users.size() >= 1) {
                        UserSession.setInstance(users.get(0));
                    }


                    startActivity(landingPageParentIntent);
                }
                else {
                    String submit_query_users = String.format("UPDATE users SET email = '%s', password = '%s', name = '%s', phone = '%s' WHERE id = '%s'",
                            editEmail.getText(), editPassword.getText(), editFullName.getText(), editPhoneNumber.getText(), id);
                    String submit_query_students = String.format("UPDATE students SET grade = '%s' WHERE student_id = '%s'", editGrade.getText(), id);

                    QueryExecution.executeQuery(submit_query_users);
                    QueryExecution.executeQuery(submit_query_students);

                    String query_refresh = String.format("SELECT * from users WHERE id = '%s'", id);
                    QueryExecution.executeQuery(query_refresh);
                    List<User> users = QueryExecution.getResponse(User.class);



                    if(!comesFromParent)
                    {
                        if (users != null && users.size() >= 1) {
                            UserSession.setInstance(users.get(0));
                        }
                        startActivity(landingPageStudentIntent);

                    }
                    else
                    {
                        startActivity(landingPageParentIntent);
                    }

                }
            }
        });

    }
}
