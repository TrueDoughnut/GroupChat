package com.cfs.bots.Jikan; 

import com.cfs.bots.Bot;

import java.util.ArrayList; 

public class JikanBot extends Bot {
  
	private static ArrayList<String> resources = new ArrayList<>(
            Arrays.asList("anime", "manga", "person", "character"));
	
  	public JikanBot(DataInputStream dis, DataOutputStream dos){
  		super("jikan", dis, dos); 
  		name = "Jikan Bot"; 
  	}
  
	private int id; 
	private String[] search;
	private boolean isSearch; 
	
	@Override
	public String run(String[] arr) throws IOException {
		String input = arr[1]; 
		if(isResource(input)){
			
		}
	}
	
	private boolean isResource(String input){
		for (String str : resources) {
            if (input.equals(str)) {
                return true;
            }
        }
        return false;	
	}
  
}
