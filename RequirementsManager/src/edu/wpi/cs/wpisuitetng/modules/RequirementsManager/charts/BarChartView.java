/**************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 *  Evan Polekoff
 *  Ned Shelton
 *  Chris Hannah
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts;

import java.awt.BorderLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.AssigneeChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.RequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.StatusChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.UserController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The Class to hold BarChartView.
 *
 * @author Evan Polekoff
 * @author Ned Shelton
 * @author Chris Hannah
 */

public class BarChartView extends JPanel implements IToolbarGroupProvider {

	private static BarChartView instance;
	
	/**
	 * Update instance from server.
	 */
	public static void update(){
		if (instance!=null)
			instance.updateFromServer();
	}
	
	/**
	 * Update from server.
	 */
	public void updateFromServer(){
		gotUsers = false;
		gotRequirements = false;
		gotIterations = false;
		iterationDataset.clear();
		statusDataset.clear();
		assigneeDataset.clear();
		userController.retrieve();
		requirementController.retrieve();
	}
	
	private BarChartPanel mainPanel;
	private RetrieveAllRequirementsController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	private boolean urls;
	private boolean tooltips;
	private boolean legend;
	private PlotOrientation orientation;
	private DefaultCategoryDataset dataset;
	private UserController userController;
	private RequirementController requirementController;
	private IterationController iterationController;
	private boolean gotUsers, gotIterations, gotRequirements;
	private User[] allUsers;
	private Iteration[] allIterations;
	private Requirement[] allRequirements;
	private RequirementStatus[] allStatuses = {RequirementStatus.NEW, RequirementStatus.INPROGRESS, RequirementStatus.OPEN, RequirementStatus.DELETED, RequirementStatus.COMPLETE};
	private RequirementPriority[] allPriorities = {RequirementPriority.HIGH, RequirementPriority.MEDIUM, RequirementPriority.LOW, RequirementPriority.BLANK};
	private RequirementType[] allTypes = {RequirementType.BLANK, RequirementType.EPIC, RequirementType.THEME, RequirementType.USERSTORY, RequirementType.NONFUNCTIONAL, RequirementType.SCENARIO};
	
	//Data to pass to the different controllers
	private DefaultCategoryDataset iterationDataset = new DefaultCategoryDataset();;
	private DefaultCategoryDataset statusDataset = new DefaultCategoryDataset();;
	private DefaultCategoryDataset assigneeDataset = new DefaultCategoryDataset();;

	/**
	 * Constructs a Bar Chart View so the bar chart can be viewed.
	 *
	 * @param tab the tab
	 */
	public BarChartView(Tab tab){
		System.out.println("Bar Chart View Created!");

		instance = this;
		
		containingTab = tab;
		containingTab.setTitle("Bar Chart");

		//make controllers and send requests
		gotUsers = false;
		gotIterations = false;
		gotRequirements = false;
		userController = new UserController(this);
		requirementController = new RequirementController(this);
		iterationController = new IterationController(this);
		userController.retrieve();
		requirementController.retrieve();
		//iterationController.retreive();
		inputEnabled = true;

		//Default chart fields.
		urls = false;
		tooltips = true;
		legend = true;
		orientation = PlotOrientation.VERTICAL;
		DefaultCategoryDataset dataset;
		dataset = new DefaultCategoryDataset();

		// Instantiate the main create requirement panel
		mainPanel = new BarChartPanel(this, makeBarChart(dataset, ""));

		mainPanel.getStatusButton().addActionListener(new StatusChartController(this));
		mainPanel.getAssigneeButton().addActionListener(new AssigneeChartController(this));
		mainPanel.getIterationButton().addActionListener(new IterationChartController(this));

		//Start out with the status graph displayed.
		mainPanel.getStatusButton().doClick();

		this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(mainPanel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);

		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				mainPanelScrollPane.repaint();
			}
		});
		
		// Updates the tree view when it is first focused
		final BarChartView bv = this;
		bv.addHierarchyListener(new HierarchyListener() {
			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if (HierarchyEvent.SHOWING_CHANGED != 0 && bv.isShowing()) {
					//mainPanel.getStatusButton().doClick();
					gotUsers = false;
					gotRequirements = false;
					gotIterations = false;
					iterationDataset.clear();
					statusDataset.clear();
					userController.retrieve();
					requirementController.retrieve();
					//iterationController.retreive();
				}
			}
		});
		
		this.add(mainPanelScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Makes the chart from the fields in this class.
	 *
	 * @param dataset the dataset
	 * @param xAxis the x axis
	 * @return the bar chart that was created from the fields.
	 */
	private JFreeChart makeBarChart(DefaultCategoryDataset dataset, String xAxis){		
		return ChartFactory.createStackedBarChart3D("Number of Requirements for Each " + xAxis, xAxis, "Requirements", dataset, orientation, legend, tooltips, urls);
	}

	/**
	 * Update and repaint the bar chart in the panel.
	 *
	 * @param dataset the dataset
	 * @param xAxis the x axis
	 */
	public void repaintChart(DefaultCategoryDataset dataset, String xAxis){
		mainPanel.setChart(makeBarChart(dataset, xAxis));
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider#getGroup()
	 */
	@Override
	public ToolbarGroupView getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Implement when recieved all.
	 */
	private void doWhenRecievedAll(){
		//update iteration chart
		System.out.println("Called doWhenRecievedAll: " + gotUsers + gotIterations + gotRequirements);
		if (gotUsers /*&& gotIterations*/ && gotRequirements){
			//==========
			//Iteration
			//==========
			//Look at each iteration name and count the requirements in each iteration.
			ArrayList<Integer> allIterationsHack = new ArrayList<Integer>();
			//Populate the initial list by looking at the requirements.
			for(int r=0; r<allRequirements.length;r++){
				if(!allIterationsHack.contains(allRequirements[r].getIterationId()) &&
						allRequirements[r].getStatus() != RequirementStatus.DELETED){
					allIterationsHack.add(allRequirements[r].getIterationId());
				}
			}
			
			int [] iterationCount = new int[allIterationsHack.size()];
			for(int r=0; r<allRequirements.length;r++){
				for(int i=0; i<allIterationsHack.size(); i++){
					if(allIterationsHack.get(i) == allRequirements[r].getIterationId()&&
							allRequirements[r].getStatus() != RequirementStatus.DELETED)
							iterationCount[i]++;
				}
			}
			
			
			
			for(int i=0; i<allIterationsHack.size();i++){
				String iterationName = Iteration.getIterationById(allIterationsHack.get(i)).getName();
				iterationDataset.setValue(iterationCount[i],"", "Iteration:" + iterationName);
			}
			
			//==========
			//Status
			//==========
			//Look at each status name and count the requirements of each status.
			int [][] priorityCount = new int[allStatuses.length][allPriorities.length];
			for(int r=0; r<allRequirements.length; r++){
				for(int i=0; i<allStatuses.length; i++){
					if(allRequirements[r].getStatus() == allStatuses[i]){
						//Set the priority
						for(int j = 0; j < allPriorities.length; j ++){
							if(allPriorities[j] == allRequirements[r].getPriority()){
								priorityCount[i][j] ++;
							}
						}
					}
				}
			}
			for(int i=0; i<allStatuses.length;i++){
				for(int j = 0; j < allPriorities.length; j++){
					statusDataset.setValue(priorityCount[i][j],allPriorities[j], allStatuses[i].toString());
				}
			}
			
			//==========
			//Assignee
			//==========
			//Look at each status name and count the requirements of each status.
			int [] assigneeCount = new int[allUsers.length];
			for(int r=0; r<allRequirements.length; r++){
				for(int i=0; i<allUsers.length; i++){
					for(int j = 0; j < allRequirements[r].getAssignee().size(); j ++){
						System.out.println("Assignee: " + allRequirements[r].getAssignee().get(j));
						if(allRequirements[r].getAssignee().get(j).equals(allUsers[i].getUsername())&&
								allRequirements[r].getStatus() != RequirementStatus.DELETED){
							assigneeCount[i] ++;
						}
					}
				}
			}
			for(int i=0; i<allUsers.length;i++){
				assigneeDataset.setValue(assigneeCount[i],"", allUsers[i].getName());
			}
		}

	}

	/**
	 * Recieve server users.
	 *
	 * @param users the users
	 */
	public void recieveServerUsers(User[] users) {
		System.out.println("recieveUsers.");
		gotUsers = true;
		allUsers = users;
		doWhenRecievedAll();
	}

	/**
	 * Recieve server iterations.
	 *
	 * @param iterations the iterations
	 */
	public void recieveServerIterations(Iteration[] iterations) {
		System.out.println("recieveIterations.");
		gotIterations = true;
		allIterations = iterations;
		doWhenRecievedAll();

	}

	/**
	 * Recieve server requirements.
	 *
	 * @param reqs the reqs
	 */
	public void recieveServerRequirements(Requirement[] reqs) {
		System.out.println("recieveRequirements.");
		gotRequirements = true;
		allRequirements = reqs;
		doWhenRecievedAll();

	}
	
	/**
	 * Gets the iteration dataset.
	 *
	 * @return the iterationDataset
	 */
	public DefaultCategoryDataset getIterationDataset() {
		return iterationDataset;
	}

	/**
	 * Gets the status dataset.
	 *
	 * @return the statusDataset
	 */
	public DefaultCategoryDataset getStatusDataset() {
		return statusDataset;
	}

	/**
	 * Gets the assignee dataset.
	 *
	 * @return the assigneeDataset
	 */
	public DefaultCategoryDataset getAssigneeDataset() {
		return assigneeDataset;
	}
}
