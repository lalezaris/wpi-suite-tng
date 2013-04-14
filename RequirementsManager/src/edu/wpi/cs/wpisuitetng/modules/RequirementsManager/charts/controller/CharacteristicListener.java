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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartPanel.characteristic;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartPanel.chartType;

/**
 * @author Evan Polekoff
 *
 */
public class CharacteristicListener implements ActionListener{
	protected BarChartView view;
	
	public CharacteristicListener(BarChartView view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		
		if(cb.getSelectedItem() == characteristic.Status){
			System.out.println("Selected Status.");
			view.repaintChart(view.getStatusDataset(), "Status");
		}
		else if(cb.getSelectedItem() == characteristic.Iteration){
			System.out.println("Selected Iteration.");
			view.repaintChart(view.getIterationDataset(), "Iteration");
		}
		else if(cb.getSelectedItem() == characteristic.Assignee){
			System.out.println("Selected Assignee.");
			view.repaintChart(view.getAssigneeDataset(), "Assignee");
		}
	}
}
