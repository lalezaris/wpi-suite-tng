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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * @author Evan Polekoff
 *@author Ned Shelton
 */
public class BarChartPanel extends JPanel {

	/* the parent view*/
	protected BarChartView view;
	
	/*layout manager for this panel*/
	protected GridBagLayout layout;
	
	//Things needed for the bar chart.
	boolean urls;
	boolean tooltips;
	boolean legend;
	PlotOrientation orientation;
	DefaultCategoryDataset dataset;
	String xAxis = "Iteration";
	JFreeChart barGraph;
	
	private JButton statusButton;
	private JButton assigneeButton;
	private JButton iterationButton;
	
	public BarChartPanel(BarChartView view){
		this.view = view;
		
		/*Dummy Fields*/
		urls = false;
		tooltips = true;
		legend = true;
		orientation = PlotOrientation.VERTICAL;
		dataset = new DefaultCategoryDataset();
		dataset.setValue(6, "Requirements", "Jane");
		dataset.setValue(7, "Requirements", "Tom");
		dataset.setValue(8, "Requirements", "Jill");
		dataset.setValue(5, "Requirements", "John");
		dataset.setValue(12, "Requirements", "Fred");
		
		addComponents();
	}
	
	/**Put the buttons and stuff on the view.
	 * 
	 */
	private void addComponents(){
		JPanel btnPanel = new JPanel();
		JPanel overallPanel = new JPanel();
		
		//Make Buttons
		statusButton = new JButton("Status");
		assigneeButton = new JButton("Assignee");
		iterationButton = new JButton("Iteration");
		
		GridBagLayout layoutOverall = new GridBagLayout();
		overallPanel.setLayout(layoutOverall);
		
		/*set the layout manager for this an the nested panel*/
		layout = new GridBagLayout();
		this.setLayout(new BorderLayout());
		
		barGraph = ChartFactory.createBarChart("Bar Chart", xAxis, "Number of Requirements", dataset, orientation, legend, tooltips, urls);
		ChartPanel graphPanel = new ChartPanel(barGraph);
		GridBagConstraints cGraph = new GridBagConstraints();
		
		GridBagConstraints cBtn = new GridBagConstraints();
		GridBagLayout layoutBtn = new GridBagLayout();
		btnPanel.setLayout(layoutBtn);
		
		GridBagConstraints cOverall = new GridBagConstraints();
		overallPanel.setLayout(layoutOverall);
		
		GridBagLayout layoutGraph = new GridBagLayout();
		graphPanel.setLayout(layoutGraph);
		
		/*add all of the components to the btnPanel*/
		cBtn.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBtn.fill = GridBagConstraints.HORIZONTAL;
		cBtn.gridx = 0;
		cBtn.gridy = 0;
		cBtn.weightx = 0.5;
		cBtn.weighty = 0.5;
		cBtn.gridheight = 1;
		cBtn.insets = new Insets(10,10,10,0); //top,left,bottom,right
		btnPanel.add(statusButton, cBtn);
		
		cBtn.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBtn.fill = GridBagConstraints.HORIZONTAL;
		cBtn.gridx = 2;
		cBtn.gridy = 0;
		cBtn.weightx = 0.5;
		cBtn.weighty = 0.5;
		cBtn.gridheight = 1;
		cBtn.insets = new Insets(10,10,10,0); //top,left,bottom,right
		btnPanel.add(assigneeButton, cBtn);
		
		cBtn.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBtn.fill = GridBagConstraints.HORIZONTAL;
		cBtn.gridx = 4;
		cBtn.gridy = 0;
		cBtn.weightx = 0.5;
		cBtn.weighty = 0.5;
		cBtn.gridheight = 1;
		cBtn.insets = new Insets(10,10,10,0); //top,left,bottom,right
		btnPanel.add(iterationButton, cBtn);
		
		//the the panels to the overall panel
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START; 
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.weightx = 0.1;
		cOverall.weighty = 0.1;
		cOverall.gridwidth = 1;
		cOverall.insets = new Insets(10,10,10,0); //top,left,bottom,right
		overallPanel.add(btnPanel, cOverall);
		
		//Add the Graph to the panel
		cGraph.anchor = GridBagConstraints.FIRST_LINE_START;
		cGraph.fill = GridBagConstraints.HORIZONTAL;
		cGraph.gridx = 0;
		cGraph.gridy = 3;
		cGraph.weightx = 0.5;
		cGraph.weighty = 0.5;
		cGraph.gridheight = 1;
		cGraph.insets = new Insets(10,10,10,0); //top,left,bottom,right
		overallPanel.add(graphPanel,cGraph);
		
		this.add(overallPanel,BorderLayout.CENTER);
		this.validate();
	}
	
	//button getters
	public JButton getStatusButton(){
		return statusButton;
	}
	
	public JButton getIterationButton(){
		return iterationButton;
	}
	
	public JButton getAssigneeButton(){
		return assigneeButton;
	}
	
}
