package com.cfs.bots.starwars;

import org.json.JSONException;
import org.json.JSONObject;

public class Species extends Instance {

    private String averageHeight, averageLifeSpan, classification,
            designation, eyeColors, hairColors, homeworld, language,
            skinColors;


    Species(int id, boolean createdFromJSON) {
        super(id, createdFromJSON);
        setResource("species/");
        getData();
    }
    Species(String[] search){
        super(search);
        setResource("species");
        search();
    }


    @Override
    void assignValues(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        averageHeight = jsonObject.getString("average_height");
        averageLifeSpan = jsonObject.getString("average_lifespan");
        classification = jsonObject.getString("classification");
        designation = jsonObject.getString("designation");
        eyeColors = jsonObject.getString("eye_colors");
        hairColors = jsonObject.getString("hair_colors");
        language = jsonObject.getString("language");
        skinColors = jsonObject.getString("skin_colors");


        if(!createdFromJSON){
            String[] homeWorldName = jsonObject.getString("homeworld").split("/");
            homeworld = new Planet(Integer.valueOf(homeWorldName[homeWorldName.length-1]), true).name;

            getPeopleArray(jsonObject.getJSONArray("people"));
            getFilmsArray(jsonObject.getJSONArray("films"));
        }

    }

    @Override
    public String toString(){
        String msg = "";

        msg += "name: " + name
                + "\nclassification: " + classification
                + "\ndesignation: " + designation
                + "\nlanguage: " + language
                + "\naverage height: " + averageHeight
                + "\naverage lifespan: " + averageLifeSpan
                + "\nhomeworld: " + homeworld
                + "\nskin colors: " + skinColors
                + "\nhair colors: " + hairColors
                + "\neye colors: " + eyeColors
                + "\n";

        for(Instance instance : people){
            msg += instance.name + " is this species.\n";
        }
        for(Instance instance : films){
            msg += "This species is in " + instance.name + ".\n";
        }

        return msg;
    }
}
