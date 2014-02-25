package model;

import java.util.List;

public interface ComponentStep extends Step {
	public List<ComponentStep> getChilds();
	public ComponentStep getChild(int index);
	public boolean isLeaf();
}
