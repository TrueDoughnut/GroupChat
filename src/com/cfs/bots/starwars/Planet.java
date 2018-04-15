package com.cfs.bots.starwars;

import org.json.JSONException;
import org.json.JSONObject;

public class Planet extends Instance {

    private String rotationPeriod, orbitalPeriod, diameter, climate,
            gravity, terrain, surfaceWater, population;

    Planet(int id, boolean createdFromJSON) {
        super(id, createdFromJSON);
        setResource("planets/");
        getData();
    }
    Planet(String search[]){
        super(search);
        setResource("planets");
        search();
    }


    @Override
    void assignValues(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        rotationPeriod = jsonObject.getString("rotation_period");
        orbitalPeriod = jsonObject.getString("orbital_period");
        diameter = jsonObject.getString("diameter") + "km";
        climate = jsonObject.getString("climate");
        gravity = jsonObject.getString("gravity");
        terrain = jsonObject.getString("terrain");
        surfaceWater = jsonObject.getString("surface_water") + "%";
        population = jsonObject.getString("population");

        if(!createdFromJSON){
            getFilmsArray(jsonObject.getJSONArray("films"));
            getPeopleArray(jsonObject.getJSONArray("residents"));
        }
    }

    @Override
    public String toString(){
        String msg = "";

        msg += "name: " + name
                + "\npopulation: " + population
                + "\nclimate: " + climate
                + "\ndiameter: " + diameter
                + "\nrotation period: " + rotationPeriod
                + "\norbital period: " + orbitalPeriod
                + "\ngravity: " + gravity
                + "\nterrain: " + terrain
                + "\nsurface water: " + surfaceWater;

        for(Instance person : people){
            msg += person.name + " lived on this planet.\n";
        }
        for (Instance film : films) {
            msg += "This planet has been in " + film.name + ".\n";
        }


        return msg;
    }
}
