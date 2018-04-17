package com.cfs.bots;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Bot {

    public String delimiter;
    public String name;

    protected void setResources(ArrayList<String> resources) {
        this.resources = resources;
    }

    protected ArrayList<String> resources;

    public Bot(String delimiter){
        this.delimiter = delimiter;
    }

    public abstract String run(String[] arr) throws IOException;

    public abstract String getInfo();

    public boolean isResource(String input){
		for (String str : resources) {
            if (input.equals(str)) {
                return true;
            }
        }
        return false;	
	}
    
    @Override
    public String toString(){
        return this.name;
    }

}
