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

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartPanel.TypeOfChart;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartPanel.characteristic;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.CharacteristicListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.ChartTypeListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.RequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.SubDivisionListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.UserController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;
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
		iterationBarDataset.clear();
		statusBarDataset.clear();
		assigneeBarDataset.clear();
		userController.retrieve();
		requirementController.retrieve();
		iterationController.retreive();
	}
	
	private BarChartPanel mainPanel;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private TypeOfChart chartType = TypeOfChart.Bar;
	private String currentCharacteristic = "Status";
	private boolean urls;
	private boolean tooltips;
	private boolean legend;
	private PlotOrientation orientation;
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
	private DefaultCategoryDataset iterationBarDataset = new DefaultCategoryDataset();
	private DefaultCategoryDataset statusBarDataset = new DefaultCategoryDataset();
	private DefaultCategoryDataset assigneeBarDataset = new DefaultCategoryDataset();
	
	private DefaultPieDataset iterationPieDataset = new DefaultPieDataset();
	private DefaultPieDataset statusPieDataset = new DefaultPieDataset();
	private DefaultPieDataset assigneePieDataset = new DefaultPieDataset();

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
		iterationController.retreive();

		//Default chart fields.
		urls = false;
		tooltips = true;
		legend = true;
		orientation = PlotOrientation.VERTICAL;
		DefaultCategoryDataset dataset;
		dataset = new DefaultCategoryDataset();

		// Instantiate the main create requirement panel
		mainPanel = new BarChartPanel(this, makeBarChart(dataset, ""));

		mainPanel.getChartBox().addActionListener(new ChartTypeListener(this));
		mainPanel.getCharacteristicBox().addActionListener(new CharacteristicListener(this));
		mainPanel.getSubDivideBox().addActionListener(new SubDivisionListener(this));

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
					gotUsers = false;
					gotRequirements = false;
					gotIterations = false;
					iterationBarDataset.clear();
					statusBarDataset.clear();
					userController.retrieve();
					requirementController.retrieve();
					iterationController.retreive();
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
	 * Makes the chart from the fields in this class.
	 *
	 * @param dataset the dataset
	 * @param characterisitc the characteristic being filtered by.
	 * @return the pie chart that was created from the fields.
	 */
	private JFreeChart makePieChart(PieDataset dataset, String characteristic){
		return ChartFactory.createPieChart3D("Number of Requirements for Each " + characteristic, dataset, legend, tooltips, urls);
	}

	/**
	 * Update and repaint the bar chart in the panel.
	 */
	public void repaintChart(){
		if(chartType == TypeOfChart.Bar){
			if(currentCharacteristic.equals("Status")){
				mainPanel.setChart(makeBarChart(statusBarDataset, currentCharacteristic), TypeOfChart.Bar);
			}
			else if(currentCharacteristic.equals("Assignee")){
				mainPanel.setChart(makeBarChart(assigneeBarDataset, currentCharacteristic), TypeOfChart.Bar);
			}
			else if (currentCharacteristic.equals("Iteration")){
				mainPanel.setChart(makeBarChart(iterationBarDataset, currentCharacteristic), TypeOfChart.Bar);
			}
			//Set the subdivide to be ungrayed out.
			mainPanel.setSubDivideEnable(true);
		}
		else if(chartType == TypeOfChart.Pie){
			if(currentCharacteristic.equals("Status")){
				mainPanel.setChart(makePieChart(statusPieDataset, currentCharacteristic), TypeOfChart.Pie);
			}
			else if(currentCharacteristic.equals("Assignee")){
				mainPanel.setChart(makePieChart(assigneePieDataset, currentCharacteristic), TypeOfChart.Pie);
			}
			else if (currentCharacteristic.equals("Iteration")){
				mainPanel.setChart(makePieChart(iterationPieDataset, currentCharacteristic), TypeOfChart.Pie);
			}
			//Set the subdivide to be grayed out.
			mainPanel.setSubDivideEnable(false);
		}
			
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
	 * Implement when received all.
	 */
	private void doWhenReceivedAll(){
		//update iteration chart
		if (gotUsers && gotIterations && gotRequirements){
			//==========
			//Iteration
			//==========
			//Look at each iteration name and count the requirements in each iteration.
			int [][] priorityCount = new int[allIterations.length][allPriorities.length];
			for(int r=0; r<allRequirements.length;r++){
				for(int i=0; i<allIterations.length; i++){
					if(allIterations[i].getId() == allRequirements[r].getIterationId()&&
							allRequirements[r].getStatus() != RequirementStatus.DELETED){//Ignore the iterations of deleted requirements.
						//Set the priority
						for(int j = 0; j < allPriorities.length; j ++){
							if(allPriorities[j] == allRequirements[r].getPriority()){
								priorityCount[i][j]++;
							}
						}
					}
				}
			}
			
			//Get the names of the iterations by their ID numbers for the chart.
			for(int i=0; i<allIterations.length;i++){
				String iterationName = Iteration.getIterationById(allIterations[i].getId()).getName();
				int cumulativePieData = 0;
				for(int j = 0; j < allPriorities.length; j++){
					cumulativePieData += priorityCount[i][j];//Accumulate the variables to get the total.
					iterationBarDataset.setValue(priorityCount[i][j], allPriorities[j], "Iteration: " + iterationName);
					iterationPieDataset.setValue("Iteration: " + iterationName, cumulativePieData);
				}
			}
			
			//==========
			//Status
			//==========
			//Look at each status name and count the requirements of each status.
			priorityCount = new int[allStatuses.length][allPriorities.length];
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
				int cumulativePieData = 0;
				for(int j = 0; j < allPriorities.length; j++){
					cumulativePieData += priorityCount[i][j];//Accumulate the variables to get the total.
					statusBarDataset.setValue(priorityCount[i][j], allPriorities[j], allStatuses[i].toString());
					statusPieDataset.setValue(allStatuses[i].toString(), cumulativePieData);
				}
			}
			
			//==========
			//Assignee
			//==========
			//Look at each user name and count the requirements of each user.
			priorityCount = new int[allUsers.length][allPriorities.length];
			for(int r=0; r<allRequirements.length; r++){
				for(int i=0; i<allUsers.length; i++){
					for(int j = 0; j < allRequirements[r].getAssignee().size(); j ++){
						System.out.println("Assignee: " + allRequirements[r].getAssignee().get(j));
						if(allRequirements[r].getAssignee().get(j).equals(allUsers[i].getUsername())&&
								allRequirements[r].getStatus() != RequirementStatus.DELETED){
							for(int k = 0; k < allPriorities.length; k ++){
								if(allPriorities[k] == allRequirements[r].getPriority()){
									priorityCount[i][k] ++;
								}
							}
						}
					}
				}
			}
			for(int i=0; i<allUsers.length;i++){
				int cumulativePieData = 0;
				for(int j = 0; j < allPriorities.length; j++){
					cumulativePieData += priorityCount[i][j];//Accumulate the variables to get the total.
					assigneeBarDataset.setValue(priorityCount[i][j], allPriorities[j], allUsers[i].getName());
					assigneePieDataset.setValue(allUsers[i].getName(), cumulativePieData);
				}
			}
		}
		
	}

	/**
	 * Receive server users.
	 *
	 * @param users the users
	 */
	public void receiveServerUsers(User[] users) {
		System.out.println("recieveUsers.");
		gotUsers = true;
		allUsers = users;
		doWhenReceivedAll();
	}

	/**
	 * Receive server iterations.
	 *
	 * @param iterations the iterations
	 */
	public void receiveServerIterations(Iteration[] iterations) {
		System.out.println("recieveIterations.");
		gotIterations = true;
		allIterations = iterations;
		doWhenReceivedAll();

	}

	/**
	 * Receive server requirements.
	 *
	 * @param reqs the reqs
	 */
	public void receiveServerRequirements(Requirement[] reqs) {
		System.out.println("recieveRequirements.");
		gotRequirements = true;
		allRequirements = reqs;
		doWhenReceivedAll();

	}
	

	/**Set the type of graph to display.
	 * @param newType The new type of graph to display.
	 */
	public void setChartType(TypeOfChart newType){
		chartType = newType;
	}
	
	/**Set the type of characteristic to display.
	 * @param newChar The characteristc to set it to.
	 */
	public void setCurrentCharacteristic(String newChar){
		currentCharacteristic = newChar;
	}
}
