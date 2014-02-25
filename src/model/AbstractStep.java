package model;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

public abstract class  AbstractStep extends AbstractComponentStep {

	private int leftMotorVelocity;
	private int rightMotorVelocity;
	private int secondsDuration;

	public AbstractStep(String name, int leftMotorVelocity, int rightMotorVelocity,
			int secondsDuration) {
		super(name);
		this.leftMotorVelocity = leftMotorVelocity;
		this.rightMotorVelocity = rightMotorVelocity;
		this.secondsDuration = secondsDuration;
	}

	public int getLeftMotorVelocity() {
		return leftMotorVelocity;
	}

	public void setLeftMotorVelocity(int leftMotorVelocity) {
		this.leftMotorVelocity = leftMotorVelocity;
	}

	public int getRightMotorVelocity() {
		return rightMotorVelocity;
	}

	public void setRightMotorVelocity(int rightMotorVelocity) {
		this.rightMotorVelocity = rightMotorVelocity;
	}

	public int getSecondsDuration() {
		return secondsDuration;
	}

	public void setSecondsDuration(int secondsDuration) {
		this.secondsDuration = secondsDuration;
	}
	
	public List<ComponentStep> getChilds(){
		return null;
	}
	
	public ComponentStep getChild(int index){
		return null;
	}
	
	public boolean isLeaf(){
		return true;
	}
	

	/* (non-Javadoc)
	 * @see model.Step#toJson(int, java.lang.String, javax.json.JsonArrayBuilder)
	 */
	public void toJson(int robotId, String device,JsonArrayBuilder jsonArrayBuilder){
		jsonArrayBuilder.add(Json.createObjectBuilder()
		.add("target","robot")
		.add("board",Json.createObjectBuilder()
				.add("device", device))
		.add("id", robotId)
		.add("command", "motors")
		.add("args", Json.createArrayBuilder()
				.add(rightMotorVelocity)
				.add(leftMotorVelocity)
				.add(secondsDuration)));
	}
	

	public String toString(){
		return this.getClass().getSimpleName()+": "+getName()+" ("+leftMotorVelocity+", "+rightMotorVelocity+", "+secondsDuration+")";
	}
}
