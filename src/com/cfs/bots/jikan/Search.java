package com.cfs.bots.jikan;

import org.json.JSONObject;

public class Search extends Instance {

    Search(String type, String query, int page){
        super(type, query, page);
    }
    Search(String type, String query){
        super(type, query);
    }

    @Override
    public void assignValues(JSONObject jsonObject){

    }

    @Override
    public String toString(){
        return this.name;
    }
}
