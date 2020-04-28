package com.example.db2.config;

//static configuration for the Application's address configuration
public class ApplicationConfig {

    //instance of the application config in our singleton pattern
    private static ApplicationConfig instance;

    private String basePath;
    private String apiPath;

    //default constructor only called in lazy loaded fashion
    private ApplicationConfig()
    {
        basePath = "http://10.0.2.2:8070/";
        apiPath = "Phase3/api/ExecQuery.php";
    }

    //returns the singleton instance. If none exists, lazy-load the singleton instance
    public static ApplicationConfig getInstance()
    {
        if (instance == null){
            instance = new ApplicationConfig();
        }
        return instance;
    }

    //get the full path of our php API for queries
    public static String getFullApiPath()
    {
        return instance.basePath.concat(instance.apiPath);
    }
}
