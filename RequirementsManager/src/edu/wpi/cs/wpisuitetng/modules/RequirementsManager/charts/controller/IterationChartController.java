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

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;


/**
 * Prep the data to display based on iterations.
 *
 * @author Evan Polekoff
 * @author Ned Shelton
 *
 * @version Apr 10, 2013
 *
 */
public class IterationChartController extends AbstractAction{

	protected BarChartView view;
	
	/**
	 * Constructor for IterationChartController.
	 * 
	 * @param view The BarChartView
	 */
	public IterationChartController(BarChartView view){
		this.view = view;
	}
	
	/**
	 * Action to be performed when the Iteration Button is pressed.
	 * 
	 * @param e A ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
//		DefaultCategoryDataset testDataSet = new DefaultCategoryDataset();
//		
//		testDataSet.setValue(130, "Requirements", "Scootaloo");
//		testDataSet.setValue(130, "Requirements", "Apple Bloom");
//		testDataSet.setValue(130, "Requirements", "Sweetie Belle");
		view.repaintChart(view.getIterationDataset(), "Iteration");
	}
}