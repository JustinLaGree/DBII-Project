package com.example.db2.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.db2.models.Admin;
import com.example.db2.models.Parent;
import com.example.db2.models.Student;
import com.example.db2.models.User;

import java.util.List;

//track the currently logged in user information
public class UserSession {

    //instance of the logged in user singleton
    private static User instance;
    //corresponding userType enum value for the logged in user
    private static UserType userType = UserType.NONE;

    //return the value in the singleton instance
    public static User getInstance()
    {
        return instance;
    }

    //set the logged-in user that is stored in the singleton instance
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static User setInstance(User user)
    {
        instance = user;

        userType = (user != null)
                ? findUserType()
                : UserType.NONE;

        return instance;
    }

    //get the access-level / userType of the logged-in user
    public static UserType getUserType()
    {
        return userType;
    }

    //calculate the type of user once the logged-in user has been changed/set
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static UserType findUserType()
    {
        String query = String.format("SELECT * FROM admins WHERE admin_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        List<Admin> admins = QueryExecution.getResponse(Admin.class);

        if (admins != null && admins.size() >= 1)
        {
            return UserType.ADMIN;
        }

        query = String.format("SELECT * FROM parents WHERE parent_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        List<Parent> parents = QueryExecution.getResponse(Parent.class);

        if (parents != null && parents.size() >= 1)
        {
            return UserType.PARENT;
        }

        query = String.format("SELECT student_id FROM students WHERE student_id=%d", instance.id);
        QueryExecution.executeQuery(query);

        List<Student> students = QueryExecution.getResponse(Student.class);

        if (students != null && students.size() >= 1)
        {
            return UserType.STUDENT;
        }

        return UserType.NONE;
    }
}
