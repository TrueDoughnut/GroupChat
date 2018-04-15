package com.cfs.bots.starwars;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Film extends Instance {

    private int episodeID;
    private String openingCrawl, director, producer, releaseDate;


    Film(int id, boolean createdFromJSON) {
        super(id, createdFromJSON);
        setResource("films/");
        getData();
    }
    Film(String search[]){
        super(search);
        setResource("films");
        search();
    }

    @Override
    void assignValues(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("title");
        episodeID = jsonObject.getInt("episode_id");
        openingCrawl = jsonObject.getString("opening_crawl");
        director = jsonObject.getString("director");
        producer = jsonObject.getString("producer");
        releaseDate = jsonObject.getString("release_date");

        if(!createdFromJSON){
            JSONArray characters = jsonObject.getJSONArray("characters");
            getPeopleArray(characters);

            JSONArray planets = jsonObject.getJSONArray("planets");
            getPlanetsArray(planets);

            JSONArray starships = jsonObject.getJSONArray("starships");
            getStarshipsArray(starships);

            JSONArray vehicles = jsonObject.getJSONArray("vehicles");
            getVehiclesArray(vehicles);

            JSONArray species = jsonObject.getJSONArray("species");
            getSpeciesArray(species);
        }
    }

    @Override
    public String toString(){
        String msg = "";

        msg += "title: " + name
                + "\nepisode id: " + episodeID
                + "\n\nopening crawl: \n" + openingCrawl
                + "\n\ndirector: " + director
                + "\nproducer: " + producer
                + "\nrelease date: " + releaseDate
                + "\n";

        msg += "\n" + addLists();

        return msg;
    }

    private String addLists(){
        String msg = "";

        for(ArrayList<Instance> arrayList : usedArrays){
            for(Instance instance : arrayList){
                msg += instance.name + " was in this movie.\n";
            }
            msg += "\n";
        }
        return msg;
    }
}
