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
 * Prep the data to display based on status.
 *
 * @author Evan Polekoff
 * @author Ned Shelton
 *
 * @version Apr 10, 2013
 *
 */
public class StatusChartController extends AbstractAction{

	protected BarChartView view;
	
	/**
	 * Constructer for StatusChartController.
	 * 
	 * @param view The BarChartView
	 */
	public StatusChartController(BarChartView view){
		this.view = view;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		view.repaintChart(view.getStatusDataset(), "Status");
	}

}
