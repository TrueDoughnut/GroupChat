package com.cfs.bots.starwars.test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class Test {


    public static void main(String[] args) throws IOException, JSONException {
        URL url = new URL("https://swapi.co/api/people/1/?format=json");
        OkHttpClient client = new OkHttpClient();
        String personContent = null;
        try{
            personContent = client.newCall(new Request.Builder().url(url).build()).execute().body().string();
        }catch(IOException e){
            e.printStackTrace();
        } catch(NullPointerException e){
            e.printStackTrace();
        }
        JSONObject personJSON = new JSONObject(personContent);
        System.out.println(personJSON.toString(2));
    }
}
