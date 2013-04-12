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

 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.AssigneeChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.RequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.StatusChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.UserController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * @author Evan Polekoff
 * @author Ned Shelton
 * @author Chris Hannah
 */
public class BarChartView extends JPanel implements IToolbarGroupProvider {

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
	
	//Data to pass to the different controllers
	private DefaultCategoryDataset iterationDataset = new DefaultCategoryDataset();;
	private DefaultCategoryDataset statusDataset = new DefaultCategoryDataset();;
	private DefaultCategoryDataset assigneeDataset = new DefaultCategoryDataset();;

	/**Constructs a Bar Chart View so the bar chart can be viewed.
	 * 
	 */
	public BarChartView(Tab tab){
		System.out.println("Bar Chart View Created!");

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

		this.add(mainPanelScrollPane, BorderLayout.CENTER);
	}

	/**Makes the chart from the fields in this class.
	 * @return the bar chart that was created from the fields.
	 */
	private JFreeChart makeBarChart(DefaultCategoryDataset dataset, String xAxis){		
		return ChartFactory.createBarChart("Number of Requirements for Each " + xAxis, xAxis, "Requirements", dataset, orientation, legend, tooltips, urls);
	}

	/**Update and repaint the bar chart in the panel.
	 * 
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


	private void doWhenRecievedAll(){
		//update iteration chart
		System.out.println("Called doWhenRecievedAll: " + gotUsers + gotIterations + gotRequirements);
		if (gotUsers /*&& gotIterations*/ && gotRequirements){
			System.out.println("All things loaded!");
			//==========
			//Iteration
			//==========
			//Look at each iteration name and count the requirements in each iteration.
			ArrayList<Integer> allIterationsHack = new ArrayList<Integer>();
			//Populate the initial list by looking at the requirements.
			for(int r=0; r<allRequirements.length;r++){
				if(!allIterationsHack.contains(allRequirements[r].getIterationId())){
					allIterationsHack.add(allRequirements[r].getIterationId());
				}
			}
			
			int [] iterationCount = new int[allIterationsHack.size()];
			for(int r=0; r<allRequirements.length;r++){
				for(int i=0; i<allIterationsHack.size(); i++){
					if(allIterationsHack.get(i) == allRequirements[r].getIterationId())
							iterationCount[i]++;
				}
			}
			for(int i=0; i<allIterationsHack.size();i++){
				String iterationName = Iteration.getIterationById(allIterationsHack.get(i)).getName();
				iterationDataset.setValue(iterationCount[i],"", "Iteration:" + iterationName);
			}
			System.out.println("Got past the Iteration loop.");
			//==========
			//Status
			//==========
			//Look at each status name and count the requirements of each status.
			int [] statusCount = new int[allStatuses.length];
			for(int r=0; r<allRequirements.length; r++){
				for(int i=0; i<allStatuses.length; i++){
					if(allRequirements[r].getStatus() == allStatuses[i]){
						statusCount[i] ++;
					}
				}
			}
			for(int i=0; i<allStatuses.length;i++){
				statusDataset.setValue(statusCount[i],"", allStatuses[i].toString());
			}
			System.out.println("Got past the Status loop.");
			
			
//			//==========
			//Assignee
			//==========
			//Look at each status name and count the requirements of each status.
			int [] assigneeCount = new int[allUsers.length];
			for(int r=0; r<allRequirements.length; r++){
				for(int i=0; i<allUsers.length; i++){
					for(int j = 0; j < allRequirements[r].getAssignee().size(); j ++){
						System.out.println("Assignee: " + allRequirements[r].getAssignee().get(j));
						if(allRequirements[r].getAssignee().get(j).equals(allUsers[i].getUsername())){
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

	public void recieveServerUsers(User[] users) {
		System.out.println("recieveUsers.");
		gotUsers = true;
		allUsers = users;
		doWhenRecievedAll();
	}

	public void recieveServerIterations(Iteration[] iterations) {
		System.out.println("recieveIterations.");
		gotIterations = true;
		allIterations = iterations;
		doWhenRecievedAll();

	}

	public void recieveServerRequirements(Requirement[] reqs) {
		System.out.println("recieveRequirements.");
		gotRequirements = true;
		allRequirements = reqs;
		doWhenRecievedAll();

	}
	
	/**
	 * @return the iterationDataset
	 */
	public DefaultCategoryDataset getIterationDataset() {
		return iterationDataset;
	}

	/**
	 * @return the statusDataset
	 */
	public DefaultCategoryDataset getStatusDataset() {
		return statusDataset;
	}

	/**
	 * @return the assigneeDataset
	 */
	public DefaultCategoryDataset getAssigneeDataset() {
		return assigneeDataset;
	}

}
