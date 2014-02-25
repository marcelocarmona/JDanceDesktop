package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import model.dataBase.DataBase;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import anotation.RemoteBot;
import anotation.Ritmo;
import anotation.Robots;

/**
 * @author mclo
 * 
 * clase encargada de enviar los requerimientos al servidor
 *
 */
public class AnnotationsProcessor {
	
	
	/**
	 * @param danceFloor
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * 
	 * Metodo encargado de enviar los requerimientos al servidor segun lo pedido en la entrega
	 */
	public void process(DanceFloor danceFloor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClientProtocolException, IOException{
		
		//property reflection
		Class<? extends DanceFloor> danceFloorClass = danceFloor.getClass();
		RemoteBot remoteBotAnotation = danceFloorClass.getAnnotation(RemoteBot.class);
		String conf = remoteBotAnotation.value();
		//property
		Properties properties = new Properties();  
		FileInputStream in = new FileInputStream(conf);  
		properties.load(in);  
		String ip = properties.getProperty("ip");
		String port = properties.getProperty("port");
		String device = properties.getProperty("device");
		
		
		
		for (Method danceFloorMethod : danceFloorClass.getDeclaredMethods()) {
			if (danceFloorMethod.isAnnotationPresent(Robots.class)) {
				@SuppressWarnings("unchecked")
				Set<Robot>	robots =  (Set<Robot>) danceFloorMethod.invoke(danceFloor);
				for (Robot robot : robots) {
					for (Method RobotMethod : Robot.class.getDeclaredMethods()){
						if (RobotMethod.isAnnotationPresent(Ritmo.class)) {
							JsonArrayBuilder jsonMainArray = jsonMainArray(device);
							
							RobotMethod.invoke(robot, device, jsonMainArray);
							
							
							//send request
							HttpClient httpclient = HttpClientBuilder.create().build();
							HttpPost request = new HttpPost("http://"+ip+":"+port);
							request.addHeader("Content-Type", "application/x-www-form-urlencoded");
							request.setEntity(new StringEntity("commands=" +jsonMainArray.build()));
							httpclient.execute(request);							
							
						}
					}
				}
			}
		}
	}

	/**
	 * @throws ClientProtocolException
	 * @throws IOException
	 * 
	 * carga los robots del servidor en la clase que simula la base de datos con los models de los jComponents
	 */
	public void loadRobots() throws ClientProtocolException, IOException {
		
		//clear robots
		DataBase.getInstance().clearRobots();
		
		//property
		Properties properties = new Properties(); 
		FileInputStream in = new FileInputStream("conf.properties");  
		properties.load(in);  
		String ip = properties.getProperty("ip");
		String port = properties.getProperty("port");
		String device = properties.getProperty("device");
		
		//send request
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost("http://"+ip+":"+port);
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setEntity(new StringEntity("commands=" +jsonMainArray(device).add(jsonBoardReport(device)).build()));

		//return request
		HttpResponse response = httpclient.execute(request);
		
		String json = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent())).readLine();

		//parse request
		JsonParser parser = Json.createParser(new StringReader(json));
		while (parser.hasNext()) {
		   JsonParser.Event event = parser.next();
		   if(event == Event.VALUE_NUMBER){
			   String robotId = parser.getString();
			   Robot robot = new Robot(Integer.parseInt(robotId),"Robot"+robotId);
			   DataBase.getInstance().getRobots().addElement(robot);
		   }
		}
	}
	
	
	/**
	 * 
	 * @return how many robots are connected
	 * 
	 *looks like 
	 * 
	 *{
	 *	"target": "board",
	 *	"board": {"device": "/dev/ttyUSB0"},
	 *	"command": "report"	
	 *}
	 */
	private JsonObjectBuilder jsonBoardReport(String device){
		return 	Json.createObjectBuilder()
    			.add("target","board")
    			.add("board",Json.createObjectBuilder()
    					.add("device", device))
    			.add("command", "report");
	}
	
	
	/**
	 * @return JsonObjectBuilder looks like
	 * 
	 *{
	 *	"target": "board",
	 *	"board": {"device": "/dev/ttyUSB0"},
	 *	"command": "__init__"	
	 *}
	 *
	 *
	 *initializes the board
	 */
	private JsonObjectBuilder jsonInitBoard(String device){
		return 	Json.createObjectBuilder()
    			.add("target","board")
    			.add("board",Json.createObjectBuilder()
    					.add("device", device))
    			.add("command", "__init__");
	}
	
	

	
	/**
	 * @return JsonArrayBuilder contain all json to send the server
	 * 
	 * looks like
	 * 
	 * [
	 * 	{
	 * 	"target": "board",
	 * 		"board": {"device": "/dev/ttyUSB0"},
	 * 		"command": "__init__"	
	 * 	},
	 * 	{
	 * 		"target": "robot",
	 * 		"board": {"device": "/dev/ttyUSB0"},
	 * 		"command": "__init__",
	 * 		"id": 1
	 * 	},
	 * 	{
	 * 		"target": "robot",
	 * 		"board": {"device": "/dev/ttyUSB0"},
	 * 		"id": 1,
	 * 		"command": "forward",
	 * 		"args": [50, 2]
	 * 	},
	 * 	{
	 * 		"target": "robot",
	 * 		"board": {"device": "/dev/ttyUSB0"},
	 * 		"id": 1,
	 * 		"command": "turnLeft",
	 * 		"args": [50, 2]
	 * 	}
	 * ]
	 * 
	 */
	private JsonArrayBuilder jsonMainArray(String device){
		return Json.createArrayBuilder()
				.add(jsonInitBoard(device));
	}
}
