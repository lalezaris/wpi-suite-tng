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

import javax.swing.JComboBox;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartPanel.SubDivision;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;

/**
 * Sub division listener for charts.
 * 
 * @author Evan Polekoff
 *
 * @version April 29, 2013
 */
public class SubDivisionListener implements ActionListener{

	protected BarPieChartView view;

	/**
	 * Instantiates a new sub division listener.
	 *
	 * @param view the view
	 */
	public SubDivisionListener(BarPieChartView view){
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();

		if(cb.getSelectedItem() == SubDivision.None){
			view.setCurrentSubDivision(SubDivision.None);
			view.repaintChart();
		}
		else if(cb.getSelectedItem() == SubDivision.Priority){
			view.setCurrentSubDivision(SubDivision.Priority);
			view.repaintChart();
		}
		else if(cb.getSelectedItem() == SubDivision.Type){
			view.setCurrentSubDivision(SubDivision.Type);
			view.repaintChart();
		}
	}
}
