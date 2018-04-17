package com.cfs.bots.jikan;

import org.json.JSONObject;

public class Manga extends Instance {

    Manga(int id, String request){
        super(id, request);
    }


    @Override
    public void assignValues(JSONObject jsonObject){

    }

    @Override
    public String toString(){
        return this.name;
    }
}
