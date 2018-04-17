package com.cfs.bots.jikan;

import com.cfs.bots.Bot;

import java.util.ArrayList; 
import java.util.Arrays;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JikanBot extends Bot {

    private HashMap<String, ArrayList<String>> extendedRequests = new HashMap<>();

  	public JikanBot() {
  		super("jikan");
  		name = "Jikan Bot";
  		setResources(new ArrayList<>(
  		        Arrays.asList("anime", "manga", "person", "character")));

  		extendedRequests.put("anime", new ArrayList<>(
  	            Arrays.asList("characters_staff", "episodes", "news", "pictures",
                                "videos", "stats")));
  	    extendedRequests.put("manga", new ArrayList<>(
  	            Arrays.asList("characters", "news", "pictures", "stats")));
  	    extendedRequests.put("person", new ArrayList<>(
  	            Arrays.asList("pictures")));
  	    extendedRequests.put("character", new ArrayList<>(
  	            Arrays.asList("pictures")));
  	}
  
	private int id; 


	@Override
	public String run(String[] arr){
		String input = arr[1]; 
		if(isResource(input)){
			if(extendedRequest(arr[2])) {
                switch (input) {
                    case "anime":
                        return new Anime(Integer.valueOf(arr[3]), arr[2])
                                .toString();
                    case "manga":
                        return new Manga(Integer.valueOf(arr[3]), arr[2])
                                .toString();
                    case "person":
                        return new Person(Integer.valueOf(arr[3]), arr[2])
                                .toString();
                    case "character":
                        return new Character(Integer.valueOf(arr[3]), arr[2])
                                .toString();
                }
            } else {
			    return "That is not a valid request.";
            }
		} else {
            if(input.equals("search")){
                try {
                    System.out.println(new Search(
                            arr[2], arr[3], Integer.valueOf(arr[4])));
                } catch(IndexOutOfBoundsException e){
                    System.out.println(new Search(
                            arr[2], arr[3]));
                }
            } else {
                return "That is not a valid command.";
            }
        }
        return null;
	}

    public boolean extendedRequest(String input){
        for(Map.Entry<String, ArrayList<String>> entry : extendedRequests.entrySet()){
            if(entry.getKey().equals(input)){
                for(String str : entry.getValue()){
                    if(str.equals(input)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getInfo(){
        return "Gets anime information";
    }

}
