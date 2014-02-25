package model;

public abstract class Spin extends AbstractStep {
	
	public Spin(String name, int leftMotorVelocity, int rightMotorVelocity,
			int secondsDuration) {
		super(name, leftMotorVelocity, rightMotorVelocity, secondsDuration);
	}

}
