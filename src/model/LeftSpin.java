package model;

public class LeftSpin extends Spin {
	
	public LeftSpin(String name, int velocity, int secondsDuration) {
		super(name, velocity, -velocity, secondsDuration);
	}

}
