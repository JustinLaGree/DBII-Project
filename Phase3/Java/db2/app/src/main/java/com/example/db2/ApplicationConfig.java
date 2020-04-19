package com.example.db2;

public class ApplicationConfig {

    private static ApplicationConfig instance;

    private String basePath;
    private String apiPath;

    private ApplicationConfig()
    {
        basePath = "http://localhost/";
        apiPath = "Phase3/api/ExecQuery.php";
    }

    public static ApplicationConfig getInstance()
    {
        if (instance == null){
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public static String getFullApiPath()
    {
        return instance.basePath.concat(instance.apiPath);
    }
}
