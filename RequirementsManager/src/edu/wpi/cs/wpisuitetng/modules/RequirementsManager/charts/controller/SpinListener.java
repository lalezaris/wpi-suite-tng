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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;

/**
 * Listener for the spin button to spin Pie charts.
 * 
 * @author Evan Polekoff
 * 
 */
public class SpinListener implements ActionListener{

	protected BarPieChartView view;

	public SpinListener(BarPieChartView view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.setPieSpin(!view.isPieSpin());
		if(view.isPieSpin())
			view.getMainPanel().getRotator().start();
		else
			view.getMainPanel().getRotator().stop();
	}


}
