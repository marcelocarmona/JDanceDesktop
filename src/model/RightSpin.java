package model;

public class RightSpin extends Spin {

	public RightSpin(String name, int velocity, int secondsDuration) {
		super(name, -velocity, velocity, secondsDuration);
	}

}
