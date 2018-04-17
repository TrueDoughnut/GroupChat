package com.cfs.bots.jikan;

import org.json.JSONObject;

public class Character extends Instance {

    Character(int id, String request){
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
