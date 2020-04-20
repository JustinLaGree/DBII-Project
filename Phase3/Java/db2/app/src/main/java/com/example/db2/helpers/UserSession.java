package com.example.db2.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.db2.models.Admin;
import com.example.db2.models.Parent;
import com.example.db2.models.Student;
import com.example.db2.models.User;

import java.util.ArrayList;

public class UserSession {
    private static User instance;
    private static UserType userType;

    public static User getInstance()
    {
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static User setInstance(User user)
    {
        instance = user;

        userType = (user != null)
                ? findUserType()
                : UserType.NONE;

        return instance;
    }

    public static UserType getUserType()
    {
        return userType;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static UserType findUserType()
    {
        String query = String.format("SELECT * FROM admins WHERE admin_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        ArrayList<Admin> admins = QueryExecution.getResponse(new Admin());

        if (admins != null && admins.size() >= 1)
        {
            return UserType.ADMIN;
        }

        query = String.format("SELECT * FROM parents WHERE parent_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        ArrayList<Parent> parents = QueryExecution.getResponse(new Parent());

        if (parents != null && parents.size() >= 1)
        {
            return UserType.PARENT;
        }

        query = String.format("SELECT student_id FROM students WHERE student_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        ArrayList<Student> students = QueryExecution.getResponse(new Student());

        if (students != null && students.size() >= 1)
        {
            return UserType.STUDENT;
        }

        return UserType.NONE;
    }
}
