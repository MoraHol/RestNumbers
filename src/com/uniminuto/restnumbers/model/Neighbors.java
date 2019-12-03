package com.uniminuto.restnumbers.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Neighbors {

	/**
	 * Devuelve los vecinos configurados en el archivos de configuración 
	 * @return
	 */
	public ArrayList<String> getNeighbors(){
		ArrayList<String> neighbors = new ArrayList<>();
		try {
			String filePath = new File("").getAbsolutePath();
			JsonReader reader = new JsonReader(new FileReader(filePath.concat("/eclipse-workspace/RestNumbers/src/json/neighbors.json")));
			JsonArray obj = JsonParser.parseReader(reader).getAsJsonObject().get("neighbors").getAsJsonArray();
			for (JsonElement objElement : obj) {
				neighbors.add(objElement.getAsString());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return neighbors;
	}
	public static void main(String[] args) {
		String filePath = new File("").getAbsolutePath();
		System.out.println(filePath);
	}
	
}
