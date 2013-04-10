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
	
	
	public BarChartPanel(BarChartView view){
		this.view = view;
		
		addComponents();
	}
	
	/**Put the buttons and stuff on the view.
	 * 
	 */
	private void addComponents(){
		JPanel overallPanel = new JPanel();
		GridBagLayout layoutOverall = new GridBagLayout();	
		overallPanel.setLayout(layoutOverall);
		
		/*set the layout manager for this an the nested panel*/
		GridBagConstraints c = new GridBagConstraints();	
		layout = new GridBagLayout();	
		this.setLayout(new BorderLayout());
		
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
		
		barGraph = ChartFactory.createBarChart("Bar Chart", xAxis, "Number of Requirements", dataset, orientation, legend, tooltips, urls);
		ChartPanel graphPanel = new ChartPanel(barGraph);
		GridBagConstraints cGraph = new GridBagConstraints();
		
		GridBagLayout layoutGraph = new GridBagLayout();	
		graphPanel.setLayout(layoutGraph);
		
		
		
		
		
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
	
}
