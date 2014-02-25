package model;

import javax.json.JsonArrayBuilder;


public interface Step {

	/**
	 * @param robotId for example 0,1,2
	 * @param device to send the step for example /dev/ttyUSB0
	 * @param jsonArrayBuilder main json array looks like [] when empty
	 * 
	 * this method put steps in this jsonArrayBuilder
	 * 
	 * jsonArrayBuilder could contain a step for example:
	 * [
	 * 	{
	 * 	"target": "robot",
	 * 	"board": {"device": "/dev/ttyUSB0"},
	 * 	"id": 1,
	 * 	"command": "forward",
	 * 	"args": [50, 2]
	 * 	}
	 * ]
	 */
	public void toJson(int robotId, String device, JsonArrayBuilder jsonArrayBuilder);

}
