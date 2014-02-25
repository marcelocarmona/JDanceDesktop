package model;

public abstract class Straight extends AbstractStep {

	public Straight(String name, int motorVelocity, int secondsDuration) {
		super(name, motorVelocity, motorVelocity, secondsDuration);
	}
	

}
