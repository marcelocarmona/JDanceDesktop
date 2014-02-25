package model;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import anotation.Ritmo;

public class Robot {
	private int id;
	private String name;
	private Choreography choreography;

	public Robot() {}

	public Robot(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public Choreography getChorepgraphy() {
		return choreography;
	}

	public void setChorepgraphy(Choreography chorepgraphy) {
		this.choreography = chorepgraphy;
	}
	

	/**
	 * @return JsonObjectBuilder looks like
	 * 
	 *{
	 *	"target": "board",
	 *	"board": {"device": "/dev/ttyUSB0"},
	 *	"command": "__init__"
	 *	"id": "1"
	 *}
	 *
	 *
	 *initializes the Robot
	 */
	private JsonObjectBuilder jsonInitRobot(String device){
		return 	Json.createObjectBuilder()
    			.add("target","robot")
    			.add("board",Json.createObjectBuilder()
    					.add("device", device))
    			.add("command", "__init__")
    			.add("id", id);
	}
	
	/**
	 * @param device to send the step for example /dev/ttyUSB0
	 * @param jsonArrayBuilder main json array looks like [] when empty
	 * 
	 * put json steps in this jsonArrayBuilder
	 * 
	 */
	@Ritmo
	public void toJson(String device, JsonArrayBuilder jsonArrayBuilder){
		jsonArrayBuilder.add(jsonInitRobot(device));
		choreography.toJson(id, device, jsonArrayBuilder);
	}
	
	public String toString(){
		return name;
	}
}
