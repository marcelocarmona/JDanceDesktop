package model;

public class Backward extends Straight{

	public Backward(String name, int motorVelocity, int secondsDuration) {
		super(name, -motorVelocity, secondsDuration);
	}

}