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
 * Prep the data to display based on asignee.
 *
 * @author Evan Polekoff
 * @author Ned Shelton
 *
 * @version Apr 10, 2013
 *
 */
public class AssigneeChartController extends AbstractAction{

	protected BarChartView view;
	
	public AssigneeChartController(BarChartView view){
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		DefaultCategoryDataset testDataSet = new DefaultCategoryDataset();
//		testDataSet.setValue(110, "Requirements", "Twilight Sparkle");
//		testDataSet.setValue(200, "Requirements", "Luna");
//		testDataSet.setValue(87, "Requirements", "Celestia");
//		testDataSet.setValue(32, "Requirements", "Cadence");
		view.repaintChart(view.getAssigneeDataset(), "Assignee");
	}

}
