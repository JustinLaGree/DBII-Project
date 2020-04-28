package com.example.db2.helpers;


import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.example.db2.config.ApplicationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


//Manages the execution of queries with our php api backend
//Allows the conversion of query results to a generic array of type <T>
public class QueryExecution {

    //the raw string response from the php server
    public static String rawResponse;

    //execute the query by creating a thread and waiting until the request to server has completed
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void executeQuery(String query)
    {
        Thread t = new Thread(() -> QueryExecution.executeQueryWebRequest(query));
        t.start();

        while (t.isAlive()){}
    }

    //execute the query by passing it to the php API in a post request
    //read back the response from the server and  insert it into the raw response
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void executeQueryWebRequest(String query) {
        StringBuilder response = new StringBuilder();

        String formattedBody = String.format("{ \"query\": \"%s\" }", query);
        try {
            URL url = new URL(ApplicationConfig.getInstance().getFullApiPath());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            osw.write(formattedBody);
            osw.flush();
            osw.close();
            os.close();

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            return;
        }

        QueryExecution.rawResponse = response.toString();
    }

    //take the raw response and convert it to a list of generic type <T>s
    public static <T> List<T> getResponse(Class<T> type){
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory factory = mapper.getTypeFactory();
        CollectionType listType = factory.constructCollectionType(List.class, type);
        try {
            return mapper.readValue(rawResponse, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
