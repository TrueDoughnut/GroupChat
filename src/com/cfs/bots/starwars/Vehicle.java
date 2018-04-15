package com.cfs.bots.starwars;

import org.json.JSONException;
import org.json.JSONObject;

public class Vehicle extends Instance {

    private String model, vehicleClass, manufacturer, length,
            costInCredits, crew, passengers, maxAtmospheringSpeed,
            cargoCapacity, consumables;

    Vehicle(int id, boolean createdFromJSON) {
        super(id, createdFromJSON);
        setResource("vehicles/");
        getData();
    }
    Vehicle(String[] search){
        super(search);
        setResource("vehicles");
        search();
    }


    @Override
    void assignValues(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        model = jsonObject.getString("model");
        vehicleClass = jsonObject.getString("vehicle_class");
        manufacturer = jsonObject.getString("manufacturer");
        length = jsonObject.getString("length");
        costInCredits = jsonObject.getString("cost_in_credits");
        crew = jsonObject.getString("crew");
        passengers = jsonObject.getString("passengers");
        maxAtmospheringSpeed = jsonObject.getString("max_atmosphering_speed");
        cargoCapacity = jsonObject.getString("cargo_capacity");
        consumables = jsonObject.getString("consumables");

        if(!createdFromJSON){
            getPeopleArray(jsonObject.getJSONArray("pilots"));
            getFilmsArray(jsonObject.getJSONArray("films"));
        }
    }

    @Override
    public String toString(){
        String msg = "";

        msg += "name: " + name
                + "\nmodel: " + model
                + "\nvehicle class: " + vehicleClass
                + "\nmanufacturer: " + manufacturer
                + "\ncost in credits: " + costInCredits
                + "\nlength: " + length
                + "\nminimum crew: " + crew
                + "\nmaximum passengers: " + passengers
                + "\nmax atmosphering speed: " + maxAtmospheringSpeed
                + "\ncargo capacity: " + cargoCapacity
                + "\nconsumables: " + consumables
                + "\n";

        for(Instance instance : people){
            msg += instance.name + " has piloted this vehicle.\n";
        }
        for(Instance instance : films){
            msg += "This vehicle was in " + instance.name + ".\n";
        }

        return msg;
    }
}
