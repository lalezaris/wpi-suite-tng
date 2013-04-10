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

 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts;


import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;


/**
 * @author Evan Polekoff
 *
 */
public class BarChartPanel extends JPanel {

	protected BarChartView view;
	
	//Things needed for the bar chart.
	boolean urls;
	boolean tooltips;
	boolean legend;
	PlotOrientation orientation;
	CategoryDataset dataset;
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
		ChartPanel graphPanel;
		GridBagConstraints cGraph = new GridBagConstraints();	
		
		barGraph = ChartFactory.createBarChart3D("Bar Chart", xAxis, "Requirements", dataset, orientation, legend, tooltips, urls);
		cGraph.anchor = GridBagConstraints.FIRST_LINE_START; 
		cGraph.fill = GridBagConstraints.HORIZONTAL;
		cGraph.gridx = 0;
		cGraph.gridy = 3;
		cGraph.weightx = 0.5;
		cGraph.weighty = 0.5;
		cGraph.gridheight = 1;
		cGraph.insets = new Insets(10,10,10,0); //top,left,bottom,right
		
		graphPanel = new ChartPanel(barGraph);
		overallPanel.add(graphPanel, cGraph);
	}	
	
}
