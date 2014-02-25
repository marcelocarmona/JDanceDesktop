package model;

public abstract class Turn extends AbstractStep {
	
	public Turn(String name, int leftMotorVelocity, int rightMotorVelocity,
			int secondsDuration) {
		super(name, leftMotorVelocity, rightMotorVelocity, secondsDuration);
	}

}
