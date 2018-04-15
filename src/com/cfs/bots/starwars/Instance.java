package com.cfs.bots.starwars;

import com.cfs.bots.starwars.helper.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public abstract class Instance implements Constants {

    String name;

    ArrayList<Instance> people = new ArrayList<>();
    ArrayList<Instance> planets = new ArrayList<>();
    ArrayList<Instance> starships = new ArrayList<>();
    ArrayList<Instance> vehicles = new ArrayList<>();
    ArrayList<Instance> species = new ArrayList<>();
    ArrayList<Instance> films = new ArrayList<>();

    ArrayList<ArrayList<Instance>> usedArrays = new ArrayList<>();

    String resource;
    void setResource(String resource) { this.resource = resource; }

    boolean createdFromJSON = false;

    String[] search;
    int id;

    Instance(String[] search){
        this.search = search;
    }
    Instance(int id, boolean createdFromJSON){
        this.id = id;
        this.createdFromJSON = createdFromJSON;
    }

    void search() {
        try {
            URL url;
            if(this.search.length <= 1) {
                url = new URL(baseURL + resource + format + "&"
                        + Constants.search + this.search[0]);
            } else {
                String createURL = baseURL + resource + format;
                for(String str : search){
                    createURL += "&" + Constants.search + str;
                }
                url = new URL(createURL);
            }

            JSONObject jsonObject = Helper.getJSONObject(url).
                    getJSONArray("results").getJSONObject(0);
            assignValues(jsonObject);

        } catch(JSONException e){
            System.out.println("That search has no results.");
            System.exit(1);
        } catch(IOException | IndexOutOfBoundsException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    void getData(){
        try {
            URL url = new URL(baseURL + resource + this.id
                    + "/" + format);
            JSONObject jsonObject = Helper.getJSONObject(url);
            assignValues(jsonObject);
        } catch(JSONException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    abstract void assignValues(JSONObject jsonObject) throws JSONException;


    void getPeopleArray(JSONArray people) throws JSONException {
        for(int i = 0; i < people.length(); i++){
            String[] arr = people.getString(i).split("/");
            this.people.add(new Person(Integer.valueOf(arr[arr.length-1]), true));
        }
        usedArrays.add(this.people);
    }
    void getPlanetsArray(JSONArray planets) throws JSONException {
        for(int i = 0; i < planets.length(); i++){
            String[] arr = planets.getString(i).split("/");
            this.planets.add(new Planet(Integer.valueOf(arr[arr.length-1]), true));
        }
        usedArrays.add(this.planets);
    }
    void getStarshipsArray(JSONArray starships) throws JSONException {
        for(int i = 0; i < starships.length(); i++){
            String[] arr = starships.getString(i).split("/");
            this.starships.add(new Starship(Integer.valueOf(arr[arr.length-1]), true));
        }
        usedArrays.add(this.starships);
    }
    void getVehiclesArray(JSONArray vehicles) throws JSONException {
        for(int i = 0; i < vehicles.length(); i++){
            String[] arr = vehicles.getString(i).split("/");
            this.vehicles.add(new Vehicle(Integer.valueOf(arr[arr.length-1]), true));
        }
        usedArrays.add(this.vehicles);
    }
    void getSpeciesArray(JSONArray species) throws JSONException {
        for(int i = 0; i < species.length(); i++){
            String[] arr = species.getString(i).split("/");
            this.species.add(new Species(Integer.valueOf(arr[arr.length-1]), true));
        }
        usedArrays.add(this.species);
    }
    void getFilmsArray(JSONArray films) throws JSONException {
        for(int i = 0; i < films.length(); i++){
            String[] arr = films.getString(i).split("/");
            this.films.add(new Film(Integer.valueOf(arr[arr.length-1]), true));
        }
        usedArrays.add(this.films);
    }

    @Override
    public String toString(){
        return "name: " + name;
    }
}
