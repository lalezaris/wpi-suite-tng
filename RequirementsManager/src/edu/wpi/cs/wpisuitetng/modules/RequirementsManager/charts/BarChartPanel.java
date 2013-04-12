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
 * The Class BarChartPanel.
 *
 * @author Evan Polekoff
 * @author Ned Shelton
 */
public class BarChartPanel extends JPanel {

	/* the parent view*/
	protected BarChartView view;
	
	/*layout manager for this panel*/
	protected GridBagLayout layout;
	
//	//Things needed for the bar chart.
//	boolean urls;
//	boolean tooltips;
//	boolean legend;
//	PlotOrientation orientation;
//	DefaultCategoryDataset dataset;
//	String xAxis = "Iteration";
	/** The bar graph. */
JFreeChart barGraph;
	
	/** The status button. */
	private JButton statusButton;
	
	/** The assignee button. */
	private JButton assigneeButton;
	
	/** The iteration button. */
	private JButton iterationButton;
	
	/** The button panel. */
	JPanel btnPanel = new JPanel();
	
	/** The overall panel. */
	JPanel overallPanel = new JPanel();
	
	/** The graph panel. */
	ChartPanel graphPanel;
	
	/**
	 * Instantiates a new bar chart panel.
	 *
	 * @param view the view
	 * @param chart the chart
	 */
	public BarChartPanel(BarChartView view, JFreeChart chart){
		this.view = view;
		//this.barGraph = chart;
		graphPanel = new ChartPanel(chart);
		
		addComponents();
	}
	
	/**Put the buttons and stuff on the view.
	 * 
	 */
	private void addComponents(){
		//Make Buttons
		statusButton = new JButton("Status");
		assigneeButton = new JButton("Assignee");
		iterationButton = new JButton("Iteration");
		
		GridBagLayout layoutOverall = new GridBagLayout();
		overallPanel.setLayout(layoutOverall);
		
		/*set the layout manager for this an the nested panel*/
		layout = new GridBagLayout();
		this.setLayout(new BorderLayout());
		
		//barGraph = ChartFactory.createBarChart("Bar Chart", xAxis, "Number of Requirements", dataset, orientation, legend, tooltips, urls);
//		ChartPanel graphPanel = new ChartPanel(barGraph);
//		GridBagConstraints cGraph = new GridBagConstraints();
		
		GridBagConstraints cBtn = new GridBagConstraints();
		GridBagLayout layoutBtn = new GridBagLayout();
		btnPanel.setLayout(layoutBtn);
		
		GridBagConstraints cOverall = new GridBagConstraints();
		overallPanel.setLayout(layoutOverall);
		
//		GridBagLayout layoutGraph = new GridBagLayout();
//		graphPanel.setLayout(layoutGraph);
		
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
		
		setChart(barGraph);
		
		this.add(overallPanel,BorderLayout.CENTER);
		this.validate();
	}
	
	
	/**Set the bar graph to be what you pass in.
	 * @param newChart The chart you are overwriting with.
	 */
	public void setChart(JFreeChart newChart){
		overallPanel.remove(graphPanel);
		
		graphPanel = new ChartPanel(newChart);
		GridBagConstraints cGraph = new GridBagConstraints();
		
		GridBagLayout layoutGraph = new GridBagLayout();
		graphPanel.setLayout(layoutGraph);
		
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
		this.repaint();
		graphPanel.updateUI();
		
		this.add(overallPanel,BorderLayout.CENTER);
		this.validate();
	}
	
	//button getters
	/**
	 * Gets the status button.
	 *
	 * @return the status button
	 */
	public JButton getStatusButton(){
		return statusButton;
	}
	
	/**
	 * Gets the iteration button.
	 *
	 * @return the iteration button
	 */
	public JButton getIterationButton(){
		return iterationButton;
	}
	
	/**
	 * Gets the assignee button.
	 *
	 * @return the assignee button
	 */
	public JButton getAssigneeButton(){
		return assigneeButton;
	}
	
}
