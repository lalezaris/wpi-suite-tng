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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.AssigneeChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.RequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.StatusChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.UserController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.AdminPermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.NonePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.SetUpPermissionsPanelController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdateAllPermissionsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdatePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model.PermissionModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
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

		mainPanel.getStatusButton().addActionListener(new StatusChartController(mainPanel, this));
		mainPanel.getAssigneeButton().addActionListener(new AssigneeChartController(mainPanel, this));
		mainPanel.getIterationButton().addActionListener(new IterationChartController(mainPanel, this));

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
		if (gotUsers && gotIterations && gotRequirements){

			DefaultCategoryDataset data = new DefaultCategoryDataset();
			
			
			int[] iterationCount = new int[allIterations.length];
			for(int r=0; r<allRequirements.length;r++){
				for(int i=0; i<allIterations.length; i++){
					if(allIterations[i].getName().equals(allRequirements[r].getIteration().getName()))
							iterationCount[i]++;
				}
			}
			for(int i=0; i<allIterations.length;i++){
				data.setValue(iterationCount[i],"Iteration", allIterations[i].getName());
			}
			
			//mainPanel.setChart(data);
			this.repaintChart(data, "Iterations");
//			//update user chart
//			int[] userCount = new int[allUsers.length];
//			for(int r=0; r<allRequirements.length;r++){
//				for(int i=0; i<allUsers.length; i++){
//					if(allUsers[i].getName().equals(allRequirements[r].getUser().getName()))
//							iterationCount[i]++;
//				}
//			}
//			for(int i=0; i<allUsers.length;i++){
//				chart.addData(userCount[i],"User", allUsers[i].getName());
//			}
		}

	}




	public void recieveServerUsers(User[] users) {
		gotUsers = true;
		allUsers = users;
		doWhenRecievedAll();
	}

	public void recieveServerIterations(Iteration[] iterations) {
		gotIterations = true;
		allIterations = iterations;
		doWhenRecievedAll();

	}

	public void recieveServerRequirements(Requirement[] reqs) {
		gotRequirements = true;
		allRequirements = reqs;
		doWhenRecievedAll();

	}

}
