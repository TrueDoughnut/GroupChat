package com.cfs.bots.starwars;

import org.json.JSONException;
import org.json.JSONObject;

public class Starship extends Instance {

	private String model, starshipClass, manufacturer, costInCredits,
		length, crew, passengers, maxAtmospheringSpeed, hyperdriveRating,
		mglt, cargoCapacity, consumables;

    Starship(int id, boolean createdFromJSON) {
        super(id, createdFromJSON);
        setResource("starships/");
        getData();
    }
    Starship(String search[]){
        super(search);
        setResource("starships");
        getData();
    }


    @Override
    void assignValues(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        model = jsonObject.getString("model");
        starshipClass = jsonObject.getString("starship_class");
        manufacturer = jsonObject.getString("manufacturer");
        costInCredits = jsonObject.getString("cost_in_credits");
        length = jsonObject.getString("length") + "m";
        crew = jsonObject.getString("crew");
        passengers = jsonObject.getString("passengers");
        maxAtmospheringSpeed = jsonObject.getString("max_atmosphering_speed");
        hyperdriveRating = jsonObject.getString("hyperdrive_rating");
        mglt = jsonObject.getString("MGLT");
        cargoCapacity = jsonObject.getString("cargo_capacity");
        consumables = jsonObject.getString("consumables");

        if (!createdFromJSON) {
            getPeopleArray(jsonObject.getJSONArray("pilots"));
            getFilmsArray(jsonObject.getJSONArray("films"));
        }
    }

    @Override
    public String toString(){
        String msg = "";

		msg += "name: " + name
			    + "\nmodel: " + model
                + "\nstarship class: " + starshipClass
                + "\nmanufacturer: " + manufacturer
                + "\ncost in credits: " + costInCredits
                + "\nlength: " + length
                + "\nminimum crew: " + crew
                + "\nmaximum passengers: " + passengers
                + "\nmax atmosphering speed: " + maxAtmospheringSpeed
                + "\nhyperdrive rating: " + hyperdriveRating
                + "\nMGLT: " + mglt
                + "\ncargo capacity: " + cargoCapacity
                + "\nconsumables: " + consumables
                + "\n";

		for(Instance instance : people){
			msg += instance.name + " has piloted this starship.\n";
		}
		for(Instance instance : films){
			msg += "This starship has been in " + instance.name + ".\n";
		}

		return msg;
    }

}
