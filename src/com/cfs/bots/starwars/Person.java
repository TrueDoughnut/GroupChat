package com.cfs.bots.starwars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Person extends Instance {

    private String height, mass, hair_color, skin_color,
            eye_color, birth_year, gender, homeworld;


    Person(String[] search){
        super(search);
        setResource("people");
        search();
    }
    Person(int id, boolean createdFromJSON) {
        super(id, createdFromJSON);
        setResource("people/");
        getData();
    }


    @Override
    void assignValues(JSONObject personJSON) throws JSONException{

        name = personJSON.getString("name");
        height = personJSON.getString("height") + "cm";
        mass = personJSON.getString("mass") + "kg";
        hair_color = personJSON.getString("hair_color");
        skin_color = personJSON.getString("skin_color");
        eye_color = personJSON.getString("eye_color");
        birth_year = personJSON.getString("birth_year");
        gender = personJSON.getString("gender");

        //parse lists

        if(!createdFromJSON) {
            //films
            JSONArray films = personJSON.getJSONArray("films");
            getFilmsArray(films);

            //species
            JSONArray species = personJSON.getJSONArray("species");
            getSpeciesArray(species);

            //vehicles
            JSONArray vehicles = personJSON.getJSONArray("vehicles");
            getVehiclesArray(vehicles);

            //starships
            JSONArray starships = personJSON.getJSONArray("starships");
            getStarshipsArray(starships);

            String[] homeWorldName = personJSON.getString("homeworld").split("/");
            homeworld = new Planet(Integer.valueOf(homeWorldName[homeWorldName.length-1]), true).name;
        }

    }

    @Override
    public String toString(){
        String msg = "";

        String basicInfo = "name: " + name
                + "\nheight: " + height
                + "\nmass: " + mass
                + "\nhair color: " + hair_color
                + "\nskin color: " + skin_color
                + "\neye color: " + eye_color
                + "\nbirth year: " + birth_year
                + "\ngender: " + gender
                + "\nhomeworld: " + homeworld
                + "\n";
        msg += basicInfo;

        for (Instance film : films) {
            msg += "This character has been in " + film.name + ".\n";
        }
        for(Instance species : species){
            msg += "This character is a " + species.name;
        }
        for(Instance vehicle : vehicles){
            msg += "This character has piloted " + vehicle.name;
        }
        for(Instance starship : starships){
            msg += "This character has piloted " + starship.name;
        }

        return msg;
    }
}
