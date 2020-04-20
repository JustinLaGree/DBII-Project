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
    private static boolean isAdmin;
    private static boolean isParent;
    private static boolean isStudent;

    public static User getInstance()
    {
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static User setInstance(User user)
    {
        instance = user;
        setIsAdmin();
        setIsParent();
        setIsStudent();
        return instance;
    }

    public static boolean getIsAdmin()
    {
        return isAdmin;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void setIsAdmin()
    {
        String query = String.format("SELECT * FROM admins WHERE admin_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        ArrayList<Admin> admins = QueryExecution.getResponse(new Admin());

        isAdmin = (admins != null && admins.size() >= 1);
    }

    public static boolean getIsParent()
    {
        return isParent;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void setIsParent()
    {
        String query = String.format("SELECT * FROM parents WHERE parent_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        ArrayList<Parent> parents = QueryExecution.getResponse(new Parent());

        isParent = (parents != null && parents.size() >= 1);
    }

    public static boolean getIsStudent()
    {
        return isStudent;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void setIsStudent()
    {
        String query = String.format("SELECT student_id FROM students WHERE student_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        ArrayList<Student> students = QueryExecution.getResponse(new Student());

        isStudent = (students != null && students.size() >= 1);
    }
}
