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

import org.jfree.data.category.DefaultCategoryDataset;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;


/**
 * Prep the data to display based on status.
 *
 * @author Evan Polekoff
 * @author Ned Shelton
 *
 * @version Apr 10, 2013
 *
 */
public class StatusChartController extends AbstractAction{

	protected BarChartPanel panel;
	protected BarChartView view;
	
	public StatusChartController(BarChartPanel panel, BarChartView view){
		
		this.panel = panel;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Actually implement stuff
		
		System.out.println("Status Button Pressed!");
		
		DefaultCategoryDataset testDataSet = new DefaultCategoryDataset();
		testDataSet.setValue(99, "Requirements", "Twilight Sparkle");
		testDataSet.setValue(87, "Requirements", "Fluttershy");
		testDataSet.setValue(130, "Requirements", "Rainbow Dash");
		testDataSet.setValue(111, "Requirements", "Pinkie Pie");
		testDataSet.setValue(65, "Requirements", "Applejack");
		testDataSet.setValue(2, "Requirements", "Rarity");
		view.repaintChart(testDataSet);
	}

}
