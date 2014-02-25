package model;

public abstract class AbstractComponentStep implements ComponentStep {

	private String name;
	
	public AbstractComponentStep(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
