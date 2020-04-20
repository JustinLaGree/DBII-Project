package com.example.db2.helpers;

public class UserSession {
    private static User instance;

    public static User getInstance()
    {
        return instance;
    }

    public static User setInstance(User user)
    {
        instance = user;
        return instance;
    }
}
