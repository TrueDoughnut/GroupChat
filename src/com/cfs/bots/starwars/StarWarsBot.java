package com.cfs.bots.starwars;

import com.cfs.bots.Bot;

import java.util.ArrayList;
import java.util.Arrays;

public class StarWarsBot extends Bot {

    public StarWarsBot() {
        super("swapi");
        name = "Star Wars Bot";
        super.setResources(new ArrayList<>(Arrays.asList("films", "people", "planets", "species", "starships", "vehicles")));
    }

    private int id;
    private String[] search;
    private boolean isSearch;

    @Override
    public String run(String[] arr) {
        try {
            String input = arr[1];
            if (isResource(input)) {
                try {
                    id = Integer.valueOf(arr[2]);
                    isSearch = false;
                } catch (NumberFormatException e) {
                    search = Arrays.copyOfRange(arr, 2, arr.length - 1);
                    isSearch = true;
                }

                switch (input) {
                    case "films":
                        return films(isSearch);

                    case "people":
                        return people(isSearch);

                    case "planets":
                        return planets(isSearch);

                    case "species":
                        return species(isSearch);

                    case "starships":
                        return starships(isSearch);

                    case "vehicles":
                        return vehicles(isSearch);
                }
            } else {
                return "That isn't a resource.";
            }
        } catch (IndexOutOfBoundsException e) {
            return "Not enough parameters";
        } finally {
            return null;
        }
    }

    @Override
    public String getInfo(){
        return "Delimiter: \"!swapi\", "
                + "\ntakes a resource, " + resources
                + "\nand either a search term or id."
                + "\nExample: !swapi people Luke Skywalker";
    }

    private String people(boolean isSearch) {
        Person person;
        if (isSearch) {
            person = new Person(this.search);
        } else {
            person = new Person(id, false);
        }
        return person.toString();
    }

    private String films(boolean isSearch) {
        Film film;
        if (isSearch) {
            film = new Film(this.search);
        } else {
            film = new Film(id, false);
        }
        return film.toString();
    }

    private String planets(boolean isSearch) {
        Planet planet;
        if (isSearch) {
            planet = new Planet(this.search);
        } else {
            planet = new Planet(id, false);
        }
        return planet.toString();
    }

    private String species(boolean isSearch) {
        Species species;
        if (isSearch) {
            species = new Species(this.search);
        } else {
            species = new Species(id, false);
        }
        return species.toString();
    }

    private String starships(boolean isSearch) {
        Starship starship;
        if (isSearch) {
            starship = new Starship(this.search);
        } else {
            starship = new Starship(id, false);
        }
        return starship.toString();
    }

    private String vehicles(boolean isSearch) {
        Vehicle vehicle;
        if (isSearch) {
            vehicle = new Vehicle(this.search);
        } else {
            vehicle = new Vehicle(id, false);
        }
        return vehicle.toString();
    }

}
