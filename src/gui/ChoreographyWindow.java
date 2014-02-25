package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import model.Choreography;
import model.ComponentStep;
import model.Step;
import model.dataBase.DataBase;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.ListSelectionModel;
import javax.swing.JTree;


public class ChoreographyWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private DefaultListModel<Step> stepsOfChoreographyModel = new DefaultListModel<Step>();

	private JPanel contentPane;
	private JTree stepTree;
	private JList<Step> stepsOfChoreographyList;
	private JScrollPane newChoreographyScrollPane;

	/**
	 * Create the frame.
	 */
	public ChoreographyWindow() {
		setTitle("Create Choreography");
		setBounds(100, 100, 579, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblSteps = new JLabel("Steps");
		contentPane.add(lblSteps, "2, 2, center, default");
		
		JLabel lblNewCoreography = new JLabel("New Coreography");
		contentPane.add(lblNewCoreography, "6, 2, center, default");
		
		JScrollPane stepScrollPane = new JScrollPane();
		contentPane.add(stepScrollPane, "2, 4, 1, 7, fill, fill");
		
		stepTree = new JTree();
		stepTree.setModel(DataBase.getInstance().getSteps());
		stepScrollPane.setViewportView(stepTree);

		
		newChoreographyScrollPane = new JScrollPane();
		contentPane.add(newChoreographyScrollPane, "6, 4, 1, 7, fill, fill");
		
		stepsOfChoreographyList = new JList<Step>();
		stepsOfChoreographyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stepsOfChoreographyList.setModel(stepsOfChoreographyModel);
		newChoreographyScrollPane.setViewportView(stepsOfChoreographyList);
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(contentPane, "Ingrese nombre de la coreografía");
				if(name != null){
					//create choreography
					ArrayList<Step> steps = Collections.list(stepsOfChoreographyModel.elements());
					Choreography newChoreography = new Choreography(name,steps);
					DataBase.getInstance().getChoreographies().addElement(newChoreography);
					
					//new jlist->model
					stepsOfChoreographyModel = new DefaultListModel<Step>();
					stepsOfChoreographyList.setModel(stepsOfChoreographyModel);
				}
			}
		});
		
		JButton button_1 = new JButton("<");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = stepsOfChoreographyList.getSelectedIndex();
				stepsOfChoreographyModel.remove(index);
			}
		});
		contentPane.add(button_1, "4, 6");
		
		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) stepTree.getLastSelectedPathComponent();
				
				if (node == null)
				    //Nothing is selected.  
				    return;
				
				 Object nodeInfo = node.getUserObject();
				 stepsOfChoreographyModel.addElement((ComponentStep) nodeInfo);
				
			}
		});
		contentPane.add(button, "4, 8");
		contentPane.add(btnNewButton, "2, 12, 5, 1, default, bottom");
	}

}
