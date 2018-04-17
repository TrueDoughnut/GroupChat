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
	public String run(String[] arr) throws IOException {
		String input = arr[1]; 
		if(isResource(input)){
			if(extendedRequest(arr[2])){

            } else {

            }
		}
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
