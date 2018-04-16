package com.cfs.bots.Jikan; 

import com.cfs.bots.Bot;

import java.util.ArrayList; 
import java.util.Arrays; 
import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.IOException; 

public class JikanBot extends Bot {
  
	resources = new ArrayList<>(Arrays.asList("anime", "manga", "person", "character"));
	
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
	
	
  
}
