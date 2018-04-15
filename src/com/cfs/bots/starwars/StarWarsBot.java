package com.cfs.bots.starwars;

import com.cfs.bots.Bot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class StarWarsBot extends Bot {

    private static ArrayList<String> resources = new ArrayList<>();

    public StarWarsBot(DataInputStream dis, DataOutputStream dos) {
        super("swapi", dis, dos);
        name = "Star Wars Bot";
        resources.add("films");
        resources.add("people");
        resources.add("planets");
        resources.add("species");
        resources.add("starships");
        resources.add("vehicles");
    }

    private int id;
    private String[] search;
    private boolean isSearch;

    @Override
    public void run(String[] arr) throws IOException {
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
                        films(isSearch);
                        break;

                    case "people":
                        people(isSearch);
                        break;

                    case "planets":
                        planets(isSearch);
                        break;

                    case "species":
                        species(isSearch);
                        break;

                    case "starships":
                        starships(isSearch);
                        break;

                    case "vehicles":
                        vehicles(isSearch);
                        break;
                }
            } else {
                dos.writeUTF("That isn't a resource.");
            }
        } catch (IndexOutOfBoundsException e) {
            dos.writeUTF("Not enough parameters");
        }
    }

    @Override
    public String getInfo(){
        return "Delimiter: \"!swapi\", "
                + "\ntakes a resource, " + resources
                + "\nand either a search term or id."
                + "\nExample: !swapi people Luke Skywalker";
    }

    private boolean isResource(String input) {
        for (String str : resources) {
            if (input.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void people(boolean isSearch) throws IOException {
        Person person;
        if (isSearch) {
            person = new Person(this.search);
        } else {
            person = new Person(id, false);
        }
        dos.writeUTF(person.toString());
    }

    private void films(boolean isSearch) throws IOException {
        Film film;
        if (isSearch) {
            film = new Film(this.search);
        } else {
            film = new Film(id, false);
        }
        dos.writeUTF(film.toString());
    }

    private void planets(boolean isSearch) throws IOException {
        Planet planet;
        if (isSearch) {
            planet = new Planet(this.search);
        } else {
            planet = new Planet(id, false);
        }
        dos.writeUTF(planet.toString());
    }

    private void species(boolean isSearch) throws IOException {
        Species species;
        if (isSearch) {
            species = new Species(this.search);
        } else {
            species = new Species(id, false);
        }
        dos.writeUTF(species.toString());
    }

    private void starships(boolean isSearch) throws IOException {
        Starship starship;
        if (isSearch) {
            starship = new Starship(this.search);
        } else {
            starship = new Starship(id, false);
        }
        dos.writeUTF(starship.toString());
    }

    private void vehicles(boolean isSearch) throws IOException {
        Vehicle vehicle;
        if (isSearch) {
            vehicle = new Vehicle(this.search);
        } else {
            vehicle = new Vehicle(id, false);
        }
        dos.writeUTF(vehicle.toString());
    }

}
