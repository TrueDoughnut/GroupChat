package com.cfs.bots.jikan;

import org.json.JSONObject;

public class Anime extends Instance {

    Anime(int id, String request){
        super(id, request);
        getData();
    }



    @Override
    public void assignValues(JSONObject jsonObject){
        switch(request){

        }
    }

    @Override
    public String toString(){
        return this.name;
    }
}
