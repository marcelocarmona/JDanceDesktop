package model;

import java.util.List;

import javax.json.JsonArrayBuilder;

public class CompositeStep extends AbstractComponentStep {
	List<ComponentStep> steps;
	
	public CompositeStep(String name){
		super(name);
	}
	
	public CompositeStep(String name, List<ComponentStep> steps){
		super(name);
		this.steps = steps;
	}
	
	public CompositeStep(String name, ComponentStep componentStep){
		super(name);
		this.steps.add(componentStep);
	}

	public List<ComponentStep> getSteps() {
		return steps;
	}

	public void setSteps(List<ComponentStep> steps) {
		this.steps = steps;
	}

	/* (non-Javadoc)
	 * @see model.Step#toJson(int, java.lang.String, javax.json.JsonArrayBuilder)
	 */
	@Override
	public void toJson(int robotId, String device, JsonArrayBuilder jsonArrayBuilder) {
		for (ComponentStep step : steps) {
			step.toJson(robotId, device, jsonArrayBuilder);
		}
	}
	
	@Override
	public List<ComponentStep> getChilds() {
		return steps;
	}
	
	@Override
	public ComponentStep getChild(int index) {
		return steps.get(index);
	}	
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+": "+getName()+" ("+steps.size()+")";
	}

}
