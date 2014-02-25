package gui;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import model.AnnotationsProcessor;
import model.Choreography;
import model.DanceFloor;
import model.Robot;
import model.Step;
import model.dataBase.DataBase;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JSeparator;


public class MainWindow {
	
	//Windows
	private StepWindow stepWindowFrame = new StepWindow();
	private ChoreographyWindow choreographyWindowFrame = new ChoreographyWindow();
	
	//Principal frame
	private JFrame frmJdance;
	
	//Jlists
	private JList<Step> stepList;
	private JList<Choreography> choreographyList;
	private JList<Robot> robotList;
	private JList<Robot> danceFloorList;
	
	//model danceFloor
	private DefaultListModel<Robot> danceFloormodel = new DefaultListModel<Robot>();

	//danceFloor
	private DanceFloor danceFloor = new DanceFloor(new HashSet<Robot>());
	private AnnotationsProcessor annotationsProcessor = new AnnotationsProcessor();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    JFrame.setDefaultLookAndFeelDecorated(true);
				    JDialog.setDefaultLookAndFeelDecorated(true);
				    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					MainWindow window = new MainWindow();
					window.frmJdance.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJdance = new JFrame();
		frmJdance.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmJdance.setTitle("JDance");
		frmJdance.setBounds(100, 100, 500, 299);
		frmJdance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmJdance.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu actionsMenu = new JMenu("Actions");
		menuBar.add(actionsMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Create coreography");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choreographyWindowFrame.setVisible(true);
			}
		});
		actionsMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Create Step");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stepWindowFrame.setVisible(true);
			}
		});
		actionsMenu.add(mntmNewMenuItem_1);
		
		JSeparator separator = new JSeparator();
		actionsMenu.add(separator);
		
		JMenuItem aboutMenuItem = new JMenuItem("About");
		actionsMenu.add(aboutMenuItem);
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//default title and icon
				JOptionPane.showMessageDialog(frmJdance,
				    "JDance\nLaboratorio de Software\nCarmona Marcelo\n2013");
				
			}
		});
		
		JPanel centerPanel = new JPanel();
		frmJdance.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JScrollPane scrollDanceFloorPane = new JScrollPane();
		scrollDanceFloorPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dance Floor", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		centerPanel.add(scrollDanceFloorPane);
		
		danceFloorList = new JList<Robot>();
		danceFloorList.setModel(danceFloormodel);
		danceFloorList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		danceFloorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollDanceFloorPane.setViewportView(danceFloorList);
		
		JScrollPane scrollRobotPane = new JScrollPane();
		scrollRobotPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollRobotPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Robots", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		centerPanel.add(scrollRobotPane);
		
		robotList = new JList<Robot>();
		robotList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollRobotPane.setViewportView(robotList);
		robotList.setModel(DataBase.getInstance().getRobots());
		robotList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		choreographyList = new JList<Choreography>();
		choreographyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		choreographyList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					DefaultListModel<Step> StepModel = new DefaultListModel<Step>();
					for(Step step : choreographyList.getSelectedValue().getSteps())
						StepModel.addElement(step);
					stepList.setModel(StepModel);
				}
			}
		});
		choreographyList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		choreographyList.setVisibleRowCount(3);
		choreographyList.setModel(DataBase.getInstance().getChoreographies());
		
		JScrollPane scrollChoreographyPane = new JScrollPane(choreographyList);
		scrollChoreographyPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChoreographyPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Choreographies", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		centerPanel.add(scrollChoreographyPane);
		
		JScrollPane scrollStepPane = new JScrollPane();
		scrollStepPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollStepPane.setViewportBorder(new TitledBorder(null, "Steps", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		centerPanel.add(scrollStepPane);
		
		
		stepList = new JList<Step>();
		stepList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stepList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollStepPane.setViewportView(stepList);

		
		JPanel southPanel = new JPanel();
		southPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		frmJdance.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		JButton btnLoadRobots = new JButton("Load Robots");
		btnLoadRobots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					annotationsProcessor.loadRobots();
				} catch ( IOException e1) {
					JOptionPane.showMessageDialog(frmJdance,
						    "Warning: start the server");
				}
			}
		});
		southPanel.add(btnLoadRobots);
		
		JButton btnPlay = new JButton("Go to the Dance Floor");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Robot robot = robotList.getSelectedValue();
				Choreography choreography = choreographyList.getSelectedValue();
				
				//Nothing is selected. 
				if (robot == null) {
					JOptionPane.showMessageDialog(frmJdance,"Warning: selects a robot");
					return;}

				if (choreography == null) {
					JOptionPane.showMessageDialog(frmJdance,"Warning: selects a choreograpy");
					return;
				}				
				
				robot.setChorepgraphy(choreography);
				boolean exist = danceFloor.getRobots().add(robot);
				if(exist)
				danceFloormodel.addElement(robot);
				
			}
		});
		southPanel.add(btnPlay);
		
		JButton btnNewButton = new JButton("Play");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					annotationsProcessor.process(danceFloor);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | IOException e1) {
					e1.printStackTrace();
				}
				
				danceFloor.getRobots().clear();
				danceFloormodel.clear();
			}
		});
		southPanel.add(btnNewButton);
	}

}
