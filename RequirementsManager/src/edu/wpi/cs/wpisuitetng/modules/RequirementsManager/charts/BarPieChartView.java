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

import javax.swing.JComboBox;
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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartPanel.SubDivision;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartPanel.TypeOfChart;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.CharacteristicListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.ChartTypeListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.RequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.SpinListener;
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
 * The view for bar charts.
 *
 * @author Evan Polekoff
 * @author Ned Shelton
 * @author Chris Hanna
 */

@SuppressWarnings("serial")
public class BarPieChartView extends JPanel implements IToolbarGroupProvider {
	
	private static BarPieChartView instance;
	
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
		iterationPrioBarDataset.clear();
		statusPrioBarDataset.clear();
		assigneePrioBarDataset.clear();
		userController.retrieve();
		requirementController.retrieve();
		iterationController.retreive();
	}
	
	private BarPieChartPanel mainPanel;
	private JComboBox chartBox;
	private JComboBox characteristicBox;
	private JComboBox subDivideBox;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private TypeOfChart chartType = TypeOfChart.Bar;
	private String currentCharacteristic = "Status";
	private SubDivision currentSubDivision = SubDivision.None;
	private boolean urls;
	private boolean tooltips;
	private boolean legend;
	private PlotOrientation orientation;
	private boolean pieSpin;
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
	private DefaultCategoryDataset iterationNoneBarDataset = new DefaultCategoryDataset();
	private DefaultCategoryDataset statusNoneBarDataset = new DefaultCategoryDataset();
	private DefaultCategoryDataset assigneeNoneBarDataset = new DefaultCategoryDataset();
	
	private DefaultCategoryDataset iterationPrioBarDataset = new DefaultCategoryDataset();
	private DefaultCategoryDataset statusPrioBarDataset = new DefaultCategoryDataset();
	private DefaultCategoryDataset assigneePrioBarDataset = new DefaultCategoryDataset();
	
	private DefaultCategoryDataset iterationTypeBarDataset = new DefaultCategoryDataset();
	private DefaultCategoryDataset statusTypeBarDataset = new DefaultCategoryDataset();
	private DefaultCategoryDataset assigneeTypeBarDataset = new DefaultCategoryDataset();
	
	private DefaultPieDataset iterationPieDataset = new DefaultPieDataset();
	private DefaultPieDataset statusPieDataset = new DefaultPieDataset();
	private DefaultPieDataset assigneePieDataset = new DefaultPieDataset();

	/**
	 * Constructs a Bar Chart View so the bar chart can be viewed.
	 *
	 * @param tab the tab
	 */
	public BarPieChartView(Tab tab){
		this();

		containingTab = tab;
		containingTab.setTitle("Bar Chart");
		
		
	}
	public BarPieChartView(){

		instance = this;
		
		//make controllers and send requests
		gotUsers = false;
		gotIterations = false;
		gotRequirements = false;
		pieSpin = false;
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
		mainPanel = new BarPieChartPanel(this, makeBarChart(dataset, ""));

		//Deal with the Combo Boxes
		mainPanel.getChartBox().addActionListener(new ChartTypeListener(this));
		mainPanel.getCharacteristicBox().addActionListener(new CharacteristicListener(this));
		mainPanel.getSubDivideBox().addActionListener(new SubDivisionListener(this));
		mainPanel.getSpinButton().addActionListener(new SpinListener(this));
		chartBox = mainPanel.getChartBox();
		characteristicBox = mainPanel.getCharacteristicBox();
		subDivideBox = mainPanel.getSubDivideBox();

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
		final BarPieChartView bv = this;
		bv.addHierarchyListener(new HierarchyListener() {
			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if (HierarchyEvent.SHOWING_CHANGED != 0 && bv.isShowing()) {
					gotUsers = false;
					gotRequirements = false;
					gotIterations = false;
					iterationPrioBarDataset.clear();
					statusPrioBarDataset.clear();
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
				if(currentSubDivision == SubDivision.Type) mainPanel.setChart(makeBarChart(statusTypeBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
				if(currentSubDivision == SubDivision.Priority) mainPanel.setChart(makeBarChart(statusPrioBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
				if(currentSubDivision == SubDivision.None) mainPanel.setChart(makeBarChart(statusNoneBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
			}
			else if(currentCharacteristic.equals("Assignee")){
				if(currentSubDivision == SubDivision.Type) mainPanel.setChart(makeBarChart(assigneeTypeBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
				if(currentSubDivision == SubDivision.Priority) mainPanel.setChart(makeBarChart(assigneePrioBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
				if(currentSubDivision == SubDivision.None) mainPanel.setChart(makeBarChart(assigneeNoneBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
			}
			else if (currentCharacteristic.equals("Iteration")){
				if(currentSubDivision == SubDivision.Type) mainPanel.setChart(makeBarChart(iterationTypeBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
				if(currentSubDivision == SubDivision.Priority) mainPanel.setChart(makeBarChart(iterationPrioBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
				if(currentSubDivision == SubDivision.None) mainPanel.setChart(makeBarChart(iterationNoneBarDataset, currentCharacteristic), TypeOfChart.Bar, false);
			}
			//Set the subdivide to be ungrayed out.
			mainPanel.setSubDivideEnable(true);
			mainPanel.setSpinVisible(false);
		}
		else if(chartType == TypeOfChart.Pie){
			if(currentCharacteristic.equals("Status")){
				mainPanel.setChart(makePieChart(statusPieDataset, currentCharacteristic), TypeOfChart.Pie, pieSpin);
			}
			else if(currentCharacteristic.equals("Assignee")){
				mainPanel.setChart(makePieChart(assigneePieDataset, currentCharacteristic), TypeOfChart.Pie, pieSpin);
			}
			else if (currentCharacteristic.equals("Iteration")){
				mainPanel.setChart(makePieChart(iterationPieDataset, currentCharacteristic), TypeOfChart.Pie, pieSpin);
			}
			//Set the subdivide to be grayed out.
			mainPanel.setSubDivideEnable(false);
			mainPanel.setSpinVisible(true);
		}
			
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider#getGroup()
	 */
	@Override
	public ToolbarGroupView getGroup() {
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
			int [][] typeCount = new int[allIterations.length][allTypes.length];
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
						//Set the Type
						for(int j = 0; j < allTypes.length; j ++){
							if(allTypes[j] == allRequirements[r].getType()){
								typeCount[i][j]++;
							}
						}
					}
				}
			}
			for(int i=0; i<allIterations.length;i++){//Get the names of the iterations by their ID numbers for the chart.
				String iterationName = Iteration.getIterationById(allIterations[i].getId()).getName();
				int cumulativeData = 0;
				for(int j = 0; j < allPriorities.length; j++){
					cumulativeData += priorityCount[i][j];//Accumulate the variables to get the total.
					iterationPrioBarDataset.setValue(priorityCount[i][j], allPriorities[j], "Iteration: " + iterationName);
					iterationNoneBarDataset.setValue(cumulativeData, "Requirement", "Iteration: " + iterationName);
					iterationPieDataset.setValue("Iteration: " + iterationName, cumulativeData);
				}
				//Tack type on
				for(int j = 0; j < allTypes.length; j++){
					iterationTypeBarDataset.setValue(typeCount[i][j], allTypes[j], "Iteration: " + iterationName);
				}
			}
			
			//==========
			//Status
			//==========
			//Look at each status name and count the requirements of each status.
			priorityCount = new int[allStatuses.length][allPriorities.length];
			typeCount = new int[allStatuses.length][allTypes.length];
			for(int r=0; r<allRequirements.length; r++){
				for(int i=0; i<allStatuses.length; i++){
					if(allRequirements[r].getStatus() == allStatuses[i]){
						//Set the priority
						for(int j = 0; j < allPriorities.length; j ++){
							if(allPriorities[j] == allRequirements[r].getPriority()){
								priorityCount[i][j] ++;
							}
						}
						//Set the Type
						for(int j = 0; j < allTypes.length; j ++){
							if(allTypes[j] == allRequirements[r].getType()){
								typeCount[i][j]++;
							}
						}
					}
				}
			}
			for(int i=0; i<allStatuses.length;i++){
				int cumulativeData = 0;
				for(int j = 0; j < allPriorities.length; j++){
					cumulativeData += priorityCount[i][j];//Accumulate the variables to get the total.
					statusPrioBarDataset.setValue(priorityCount[i][j], allPriorities[j], allStatuses[i].toString());
					statusNoneBarDataset.setValue(cumulativeData, "Requirement", allStatuses[i].toString());
					statusPieDataset.setValue(allStatuses[i].toString(), cumulativeData);
				}
				//Tack type on
				for(int j = 0; j < allTypes.length; j++){
					statusTypeBarDataset.setValue(typeCount[i][j], allTypes[j], allStatuses[i].toString());
				}
			}
			
			//==========
			//Assignee
			//==========
			//Look at each user name and count the requirements of each user.
			priorityCount = new int[allUsers.length][allPriorities.length];
			typeCount = new int[allUsers.length][allTypes.length];
			for(int r=0; r<allRequirements.length; r++){
				for(int i=0; i<allUsers.length; i++){
					for(int j = 0; j < allRequirements[r].getAssignee().size(); j ++){
						System.out.println("Assignee: " + allRequirements[r].getAssignee().get(j));
						if(allRequirements[r].getAssignee().get(j).equals(allUsers[i].getUsername())&&
								allRequirements[r].getStatus() != RequirementStatus.DELETED){
							//Set the priority
							for(int k = 0; k < allPriorities.length; k ++){
								if(allPriorities[k] == allRequirements[r].getPriority()){
									priorityCount[i][k] ++;
								}
							}
							//Set the Type
							for(int k = 0; k < allTypes.length; k ++){
								if(allTypes[k] == allRequirements[r].getType()){
									typeCount[i][k]++;
								}
							}
						}
					}
				}
			}
			for(int i=0; i<allUsers.length;i++){
				int cumulativeData = 0;
				for(int j = 0; j < allPriorities.length; j++){
					cumulativeData += priorityCount[i][j];//Accumulate the variables to get the total.
					assigneePrioBarDataset.setValue(priorityCount[i][j], allPriorities[j], allUsers[i].getName());
					assigneeNoneBarDataset.setValue(cumulativeData, "Requirement", allUsers[i].getName());
					assigneePieDataset.setValue(allUsers[i].getName(), cumulativeData);
				}
				//Tack type on
				for(int j = 0; j < allTypes.length; j++){
					assigneeTypeBarDataset.setValue(typeCount[i][j], allTypes[j], allUsers[i].getName());
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
	
	/**Set the sub division to display.
	 * @param newSub The sub division to set it to.
	 */
	public void setCurrentSubDivision(SubDivision newSub){
		currentSubDivision = newSub;
	}

	/**
	 * @return the iterationNoneBarDataset
	 */
	public DefaultCategoryDataset getIterationNoneBarDataset() {
		return iterationNoneBarDataset;
	}

	/**
	 * @return the statusNoneBarDataset
	 */
	public DefaultCategoryDataset getStatusNoneBarDataset() {
		return statusNoneBarDataset;
	}

	/**
	 * @return the assigneeNoneBarDataset
	 */
	public DefaultCategoryDataset getAssigneeNoneBarDataset() {
		return assigneeNoneBarDataset;
	}


	/**
	 * @return the iterationPrioBarDataset
	 */
	public DefaultCategoryDataset getIterationPrioBarDataset() {
		return iterationPrioBarDataset;
	}


	/**
	 * @return the statusPrioBarDataset
	 */
	public DefaultCategoryDataset getStatusPrioBarDataset() {
		return statusPrioBarDataset;
	}

	/**
	 * @return the assigneePrioBarDataset
	 */
	public DefaultCategoryDataset getAssigneePrioBarDataset() {
		return assigneePrioBarDataset;
	}


	/**
	 * @return the iterationTypeBarDataset
	 */
	public DefaultCategoryDataset getIterationTypeBarDataset() {
		return iterationTypeBarDataset;
	}

	/**
	 * @return the statusTypeBarDataset
	 */
	public DefaultCategoryDataset getStatusTypeBarDataset() {
		return statusTypeBarDataset;
	}

	/**
	 * @return the assigneeTypeBarDataset
	 */
	public DefaultCategoryDataset getAssigneeTypeBarDataset() {
		return assigneeTypeBarDataset;
	}


	/**
	 * @return the iterationPieDataset
	 */
	public DefaultPieDataset getIterationPieDataset() {
		return iterationPieDataset;
	}

	/**
	 * @return the statusPieDataset
	 */
	public DefaultPieDataset getStatusPieDataset() {
		return statusPieDataset;
	}

	/**
	 * @return the assigneePieDataset
	 */
	public DefaultPieDataset getAssigneePieDataset() {
		return assigneePieDataset;
	}

	/**
	 * @return the chartType
	 */
	public TypeOfChart getChartType() {
		return chartType;
	}

	/**
	 * @return the currentCharacteristic
	 */
	public String getCurrentCharacteristic() {
		return currentCharacteristic;
	}

	/**
	 * @return the currentSubDivision
	 */
	public SubDivision getCurrentSubDivision() {
		return currentSubDivision;
	}

	/**
	 * @return the chartBox
	 */
	public JComboBox getChartBox() {
		return chartBox;
	}

	/**
	 * @return the characteristicBox
	 */
	public JComboBox getCharacteristicBox() {
		return characteristicBox;
	}

	/**
	 * @return the subDivideBox
	 */
	public JComboBox getSubDivideBox() {
		return subDivideBox;
	}


	/**
	 * @return the mainPanel
	 */
	public BarPieChartPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * @return the pieSpin
	 */
	public boolean isPieSpin() {
		return pieSpin;
	}

	/**
	 * @param pieSpin the pieSpin to set
	 */
	public void setPieSpin(boolean pieSpin) {
		this.pieSpin = pieSpin;
	}
	
}
