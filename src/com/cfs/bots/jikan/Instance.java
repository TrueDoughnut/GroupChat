package com.cfs.bots.jikan;

import com.cfs.bots.helper.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


public abstract class Instance implements Constants {

    String name;

    int id;
    String request;
    Instance(int id, String request){
        this.id = id;
        this.request = request;
    }

    Instance(String type, String query, int page){
        try {
            URL url = new URL(baseURL + type + "/" + query + "/" + page);
            assignValues(Helper.getJSONObject(url).
                    getJSONArray("result").
                    getJSONObject(0));
        } catch(IOException | JSONException e){
            e.printStackTrace();
        }
    }
    Instance(String type, String query){
        this(type, query, 1);
    }

    abstract void assignValues(JSONObject jsonObject) throws JSONException;

    void getData(){
        try {
            URL url = new URL(baseURL + id + "/" + request);
            JSONObject jsonObject = Helper.getJSONObject(url);
            assignValues(jsonObject);
        } catch(IOException | JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    public String toString(){
        return this.name;
    }
}
