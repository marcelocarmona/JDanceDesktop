package model;

import java.util.Set;

import anotation.RemoteBot;
import anotation.Robots;

@RemoteBot("conf.properties")
public class DanceFloor {
	Set<Robot> robots;

	
	public DanceFloor(Set<Robot> robots) {
		super();
		this.robots = robots;
	}

	@Robots
	public Set<Robot> getRobots() {
		return robots;
	}

	public void setRobots(Set<Robot> robots) {
		this.robots = robots;
	}

}
