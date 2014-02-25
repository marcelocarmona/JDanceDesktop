package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;

import com.jgoodies.forms.factories.FormFactory;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import model.Backward;
import model.ComponentStep;
import model.CompositeStep;
import model.DefaultStep;
import model.Forward;
import model.LeftSpin;
import model.RightSpin;
import model.TurnLeft;
import model.TurnRight;
import model.Wait;
import model.dataBase.DataBase;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class StepWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField defaultStepNameTextField;
	private JSpinner defaultStepLeftMotorVelocitySpinner;
	private JSpinner defaultStepRightMotorVelocitySpinner;
	private JSpinner defaultStepSecondsDurationSpinner;
	private JTextField waitNameTextField;
	private JSpinner waitSecondsDurationSpinner;
	private JTextField straightNameTextField;
	private JSpinner straightVelocitySpinner;
	private JSpinner straightSecondsDurationSpinner;
	private JComboBox<Class<?>> straightComboBox;
	private JTextField turnNameTextField;
	private JSpinner turnVelocitySpinner;
	private JSpinner turnSecondsDurationSpinner;
	private JComboBox<Class<?>> turnComboBox;
	private JTextField spinNameTextField;
	private JSpinner spinVelocitySpinner;
	private JSpinner spinSecondsDurationSpinner;
	private JComboBox<Class<?>> spinComboBox;
	private JTree stepTree;
	private JList<ComponentStep> compositeStepList;
	private DefaultListModel<ComponentStep> compositeStepModel = new DefaultListModel<ComponentStep>();


	/**
	 * Create the frame.
	 */
	public StepWindow() {
		setTitle("Create Step");
		setMinimumSize(new Dimension(300, 240));
		setBounds(100, 100, 569, 245);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "2, 2, fill, fill");
		
		JPanel defaultStepPanel = new JPanel();
		tabbedPane.addTab("DefaultStep", null, defaultStepPanel, null);
		defaultStepPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("86px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:default"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel label = new JLabel("Name");
		defaultStepPanel.add(label, "2, 2, left, default");
		
		defaultStepNameTextField = new JTextField();
		defaultStepPanel.add(defaultStepNameTextField, "4, 2");
		defaultStepNameTextField.setColumns(10);
		
		JLabel lblLeftmotorvelocity = new JLabel("LeftMotorVelocity");
		defaultStepPanel.add(lblLeftmotorvelocity, "2, 4, left, center");
		
		defaultStepLeftMotorVelocitySpinner = new JSpinner();
		defaultStepPanel.add(defaultStepLeftMotorVelocitySpinner, "4, 4, default, top");
		
		JLabel lblRightmotorvelocity = new JLabel("RightMotorVelocity");
		defaultStepPanel.add(lblRightmotorvelocity, "2, 6, left, center");
		
		defaultStepRightMotorVelocitySpinner = new JSpinner();
		defaultStepPanel.add(defaultStepRightMotorVelocitySpinner, "4, 6, default, top");
		
		JLabel lblSecondsduration = new JLabel("SecondsDuration");
		defaultStepPanel.add(lblSecondsduration, "2, 8, left, center");
		
		defaultStepSecondsDurationSpinner = new JSpinner();
		defaultStepPanel.add(defaultStepSecondsDurationSpinner, "4, 8, default, top");
		
		JButton btnDefaultStepCreateStep = new JButton("Create Step");
		defaultStepPanel.add(btnDefaultStepCreateStep, "2, 10, 3, 1, default, center");
		btnDefaultStepCreateStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create DefaultStep
				String name = defaultStepNameTextField.getText();
				int leftMotorVelocity = (int) defaultStepLeftMotorVelocitySpinner.getValue();
				int rightMotorVelocity = (int) defaultStepRightMotorVelocitySpinner.getValue();
				int secondsDuration = (int) defaultStepSecondsDurationSpinner.getValue();
				
				DefaultStep defaultStep = new DefaultStep(name,leftMotorVelocity,rightMotorVelocity,secondsDuration);
				DataBase.getInstance().addStep(defaultStep);
			}
		});
		
		JPanel WaitPanel = new JPanel();
		tabbedPane.addTab("Wait", null, WaitPanel, null);
		WaitPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:default"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblName = new JLabel("Name");
		WaitPanel.add(lblName, "2, 2, left, default");
		
		waitNameTextField = new JTextField();
		WaitPanel.add(waitNameTextField, "4, 2, fill, default");
		waitNameTextField.setColumns(10);
		
		JLabel lblTime = new JLabel("SecondsDuration");
		WaitPanel.add(lblTime, "2, 4, left, center");
		
		waitSecondsDurationSpinner = new JSpinner();
		WaitPanel.add(waitSecondsDurationSpinner, "4, 4, default, center");
		
		JButton btnWaitCreateStep = new JButton("Create Step");
		btnWaitCreateStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create Wait step
				String name = waitNameTextField.getText();
				int secondsDuration = (int) waitSecondsDurationSpinner.getValue();
				
				Wait wait = new Wait(name,secondsDuration);
				DataBase.getInstance().addStep(wait);
			}
		});
		WaitPanel.add(btnWaitCreateStep, "2, 6, 3, 1");
		
		JPanel StraightPanel = new JPanel();
		tabbedPane.addTab("Straight", null, StraightPanel, null);
		StraightPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:default"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblName_1 = new JLabel("Name");
		StraightPanel.add(lblName_1, "2, 2, left, default");
		
		straightNameTextField = new JTextField();
		StraightPanel.add(straightNameTextField, "4, 2, fill, default");
		straightNameTextField.setColumns(10);
		
		JLabel lblVelocity = new JLabel("Velocity");
		StraightPanel.add(lblVelocity, "2, 4");
		
		straightVelocitySpinner = new JSpinner();
		StraightPanel.add(straightVelocitySpinner, "4, 4");
		
		JLabel lblSecondsduration_1 = new JLabel("SecondsDuration");
		StraightPanel.add(lblSecondsduration_1, "2, 6");
		
		straightSecondsDurationSpinner = new JSpinner();
		StraightPanel.add(straightSecondsDurationSpinner, "4, 6");
		
		straightComboBox = new JComboBox<Class<?>>();
		straightComboBox.setModel(new DefaultComboBoxModel<Class<?>>(new Class[] {Forward.class, Backward.class,}));
		StraightPanel.add(straightComboBox, "2, 8, 3, 1, fill, default");
		
		JButton btnStraightCreateStep = new JButton("Create Step");
		btnStraightCreateStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create Forward or Backward usando reflection
				String name = straightNameTextField.getText();
				int velocity = (int) straightVelocitySpinner.getValue();
				int secondsDuration = (int) straightSecondsDurationSpinner.getValue();
				
				int index = straightComboBox.getSelectedIndex();
				Class<?> straightClass = straightComboBox.getModel().getElementAt(index);
				try {
					Object straightObject = straightClass.getConstructor(String.class,int.class,int.class).newInstance(name,velocity,secondsDuration);
					DataBase.getInstance().addStep((ComponentStep) straightObject);

				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e1) {

					e1.printStackTrace();
				}			
			}
		});
		StraightPanel.add(btnStraightCreateStep, "2, 10, 3, 1");
		
		JPanel TurnPanel = new JPanel();
		tabbedPane.addTab("Turn", null, TurnPanel, null);
		TurnPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:default"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblName_2 = new JLabel("Name");
		TurnPanel.add(lblName_2, "2, 2, left, default");
		
		turnNameTextField = new JTextField();
		TurnPanel.add(turnNameTextField, "4, 2, fill, default");
		turnNameTextField.setColumns(10);
		
		JLabel lblGrades = new JLabel("Velocity");
		TurnPanel.add(lblGrades, "2, 4, left, default");
		
		turnVelocitySpinner = new JSpinner();
		TurnPanel.add(turnVelocitySpinner, "4, 4");
		
		JLabel lblSecondduration = new JLabel("SecondsDuration");
		TurnPanel.add(lblSecondduration, "2, 6");
		
		turnSecondsDurationSpinner = new JSpinner();
		TurnPanel.add(turnSecondsDurationSpinner, "4, 6");
		
		turnComboBox = new JComboBox<Class<?>>();
		turnComboBox.setModel(new DefaultComboBoxModel<Class<?>>(new Class[] {TurnLeft.class, TurnRight.class}));
		TurnPanel.add(turnComboBox, "2, 8, 3, 1, fill, default");
		
		JButton btnTurnCreateStep = new JButton("Create Step");
		btnTurnCreateStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create turnLeft or turnRight usando reflection
				String name = turnNameTextField.getText();
				int velocity = (int) turnVelocitySpinner.getValue();
				int secondsDuration = (int) turnSecondsDurationSpinner.getValue();
				
				int index = turnComboBox.getSelectedIndex();
				Class<?> turnClass = turnComboBox.getModel().getElementAt(index);
				try {
					Object turnObject = turnClass.getConstructor(String.class,int.class,int.class).newInstance(name,velocity,secondsDuration);
					DataBase.getInstance().addStep((ComponentStep) turnObject);

				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e1) {

					e1.printStackTrace();
				}	
			}
		});
		TurnPanel.add(btnTurnCreateStep, "2, 10, 3, 1");
		
		JPanel SpinPanel = new JPanel();
		tabbedPane.addTab("Spin", null, SpinPanel, null);
		SpinPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:default"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel label_1 = new JLabel("Name");
		SpinPanel.add(label_1, "2, 2, left, default");
		
		spinNameTextField = new JTextField();
		SpinPanel.add(spinNameTextField, "4, 2, fill, default");
		spinNameTextField.setColumns(10);
		
		JLabel lblVelocity_1 = new JLabel("Velocity");
		SpinPanel.add(lblVelocity_1, "2, 4, left, default");
		
		spinVelocitySpinner = new JSpinner();
		SpinPanel.add(spinVelocitySpinner, "4, 4, fill, default");
		
		JLabel lblSecondsduration_2 = new JLabel("SecondsDuration");
		SpinPanel.add(lblSecondsduration_2, "2, 6, left, default");
		
		spinSecondsDurationSpinner = new JSpinner();
		SpinPanel.add(spinSecondsDurationSpinner, "4, 6, fill, default");
		
		spinComboBox = new JComboBox<Class<?>>();
		spinComboBox.setModel(new DefaultComboBoxModel<Class<?>>(new Class[] {LeftSpin.class, RightSpin.class}));
		SpinPanel.add(spinComboBox, "2, 8, 3, 1");
		
		JButton btnSpinCreateStep = new JButton("Create Step");
		btnSpinCreateStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create LeftSpin or RigthSpin usando reflection
				String name = spinNameTextField.getText();
				int velocity = (int) spinVelocitySpinner.getValue();
				int secondsDuration = (int) spinSecondsDurationSpinner.getValue();
				
				int index = spinComboBox.getSelectedIndex();
				Class<?> spinClass = spinComboBox.getModel().getElementAt(index);
				try {
					Object spinObject = spinClass.getConstructor(String.class,int.class,int.class).newInstance(name,velocity,secondsDuration);
					DataBase.getInstance().addStep((ComponentStep) spinObject);

				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e1) {

					e1.printStackTrace();
				}
			}
		});
		SpinPanel.add(btnSpinCreateStep, "2, 10, 3, 1");
		
		JPanel CompositeStepPanel = new JPanel();
		tabbedPane.addTab("CompositeStep", null, CompositeStepPanel, null);
		CompositeStepPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
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
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:default"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblSteps = new JLabel("Steps");
		CompositeStepPanel.add(lblSteps, "2, 2, center, default");
		
		JLabel lblNewCompositestep = new JLabel("New CompositeStep");
		CompositeStepPanel.add(lblNewCompositestep, "6, 2, center, default");
		
		JScrollPane scrollPane = new JScrollPane();
		CompositeStepPanel.add(scrollPane, "2, 4, 1, 7, fill, fill");
		
		stepTree = new JTree();
		stepTree.setModel(DataBase.getInstance().getSteps());
		stepTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		scrollPane.setViewportView(stepTree);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		CompositeStepPanel.add(scrollPane_1, "6, 4, 1, 7, fill, fill");
		
		compositeStepList = new JList<ComponentStep>();
		scrollPane_1.setViewportView(compositeStepList);
		compositeStepList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		compositeStepList.setModel(compositeStepModel);
		
		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) stepTree.getLastSelectedPathComponent();
				
				if (node == null)
				    //Nothing is selected.  
				    return;
				
				 Object nodeInfo = node.getUserObject();
				 compositeStepModel.addElement((ComponentStep) nodeInfo);

			}
		});
		CompositeStepPanel.add(button, "4, 6");
		
		JButton button_1 = new JButton("<");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = compositeStepList.getSelectedIndex();				
				compositeStepModel.remove(index);
			}
		});
		CompositeStepPanel.add(button_1, "4, 8");
		
		JButton btnNewButton = new JButton("Create Step");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(contentPane, "Enter the name of the new Step");
				if(name != null){
					//create compositeStep
					List<ComponentStep> list = Collections.list(compositeStepModel.elements());
					CompositeStep compositeStep = new CompositeStep(name,list);
					DataBase.getInstance().addStep(compositeStep);
					
					//new jlist->model
					compositeStepModel = new DefaultListModel<ComponentStep>();
					compositeStepList.setModel(compositeStepModel);
				}
			}
		});
		CompositeStepPanel.add(btnNewButton, "2, 12, 5, 1");
		
	}

}
