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
import java.util.ArrayList;
import java.util.List;


public class QueryExecution {

    public static String rawResponse;
    public static Object[] response;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void executeQuery(String query) {
        StringBuilder response = new StringBuilder();

        Pair queryPair = new Pair("query", query);
        String formattedBody = String.format("{ \"%s\": \"%s\" }", queryPair.first, queryPair.second);

        try {
            URL url = new URL(ApplicationConfig.getInstance().getFullApiPath());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            //con.addRequestProperty("Content-Type", "application/" + "json");

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
                System.out.println(response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            return;
        }

        QueryExecution.rawResponse = response.toString();
    }

    public static <T> ArrayList<T> getResponse(T type){
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory factory = mapper.getTypeFactory();
        CollectionType listType = factory.constructCollectionType(List.class, type.getClass());
        try {
            return mapper.readValue(rawResponse, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
