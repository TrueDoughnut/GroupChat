package com.cfs.bots.jikan;

import org.json.JSONException;
import org.json.JSONObject;

public class Person extends Instance {

    Person(int id, String request){
        super(id, request);
    }

    @Override
    public void assignValues(JSONObject jsonObject){

    }

    @Override
    void defaultInfo(JSONObject jsonObject) throws JSONException {

    }

    @Override
    public String toString(){
        return this.name;
    }
}
