package model.dataBase;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.Backward;
import model.Choreography;
import model.ComponentStep;
import model.Forward;
import model.Robot;
import model.Step;

public class DataBase {
	
	//Singleston
	private static DataBase INSTANCE = new DataBase();
	
	//List models
	private DefaultListModel<Robot> robots = new DefaultListModel<Robot>();
	private DefaultListModel<Choreography> choreographies = new DefaultListModel<Choreography>();
	
	//TreeModels for steps
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Steps");
	private DefaultTreeModel steps = new DefaultTreeModel(rootNode);

    public static DataBase getInstance() {
        return INSTANCE;
    }	
	
	/**
	 * Creates steps and choreographies for default
	 */
	private DataBase() {		
		//initialize steps
		Forward forward = new Forward("adelante", 50, 3);
		rootNode.add(new DefaultMutableTreeNode(forward));
		
		Backward backward = new Backward("atras", 50, 3);
		rootNode.add(new DefaultMutableTreeNode(new Backward("atras", 50, 3)));
		
		
		//initialize choreographies
		ArrayList<Step> steplist = new ArrayList<Step>();
		steplist.add(forward);
		steplist.add(backward);
		choreographies.addElement(new Choreography("coreo1",steplist));
	}

	public DefaultListModel<Robot> getRobots() {
		return robots;
	}

	public DefaultListModel<Choreography> getChoreographies() {
		return choreographies;
	}

	public DefaultTreeModel getSteps() {
		return steps;
	}

	/**
	 * to clear robots
	 */
	public void clearRobots() {
		this.robots.clear();
	}

	/**
	 * @param step elemento que se inserta en el model del Jtree
	 * 
	 * Metodo recursivo para generar los nodos del Jtree
	 */
	private void add(ComponentStep step, DefaultMutableTreeNode rootNode){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(step);
		rootNode.add(node);
		if(!step.isLeaf()){
			for(ComponentStep child : step.getChilds())
				this.add(child,node);
		}
		steps.reload();
	}
	
	public void addStep(ComponentStep step){
		this.add(step, this.rootNode);
	}
	
	
	
	
}
