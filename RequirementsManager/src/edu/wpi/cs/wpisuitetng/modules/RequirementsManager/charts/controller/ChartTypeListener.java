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

import javax.swing.AbstractAction;
import javax.swing.JComboBox;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartPanel.TypeOfChart;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;

/**
 * @author Evan Polekoff
 *
 */
public class ChartTypeListener implements ActionListener{
	
	protected BarPieChartView view;
	
	public ChartTypeListener(BarPieChartView view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		
		if(cb.getSelectedItem() == TypeOfChart.Bar){
			System.out.println("Selected Bar Chart.");
			view.setChartType(TypeOfChart.Bar);
			view.repaintChart();
		}
		else if(cb.getSelectedItem() == TypeOfChart.Pie){
			System.out.println("Selected Pie Chart.");
			view.setChartType(TypeOfChart.Pie);
			view.repaintChart();
		}
	}

}
