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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.AssigneeChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.IterationChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.StatusChartController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.AdminPermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.NonePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.SetUpPermissionsPanelController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdateAllPermissionsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdatePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model.PermissionModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * @author Evan Polekoff
 *
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
	
	/**Constructs a Bar Chart View so the bar chart can be viewed.
	 * 
	 */
	public BarChartView(Tab tab){
		System.out.println("Bar Chart View Created!");
		
		containingTab = tab;
		containingTab.setTitle("Bar Chart");

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

}
