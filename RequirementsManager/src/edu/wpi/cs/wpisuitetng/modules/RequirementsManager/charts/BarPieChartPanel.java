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
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PiePlot3D;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller.PieRotator;

/**
 * The panel for bar charts.
 *
 * @author Evan Polekoff
 * @author Ned Shelton
 * 
 * @version April 15, 2013
 */
@SuppressWarnings({"serial", "rawtypes"})
public class BarPieChartPanel extends JPanel {

	/*layout manager for this panel*/
	protected GridBagLayout layout;

	/** The chart. */
	private JFreeChart displayedChart;

	/** The chart box. */
	private JLabel chartLabel;
	private JComboBox chartBox;
	public enum TypeOfChart{
		Bar,
		Pie
	}
	TypeOfChart[] chartTypeArray = {TypeOfChart.Bar, TypeOfChart.Pie };

	/** The characteristic box. */
	private JLabel characteristicLabel;
	private JComboBox characteristicBox;
	public enum characteristic{
		Status,
		Iteration,
		Assignee
	}
	characteristic[] characteristicArray = {characteristic.Status, characteristic.Iteration, characteristic.Assignee };

	/** The characteristic box. */
	private JLabel subDivideLabel;
	private JComboBox subDivideBox;
	public enum SubDivision{
		None,
		Priority,
		Type
	}
	SubDivision[] subDivisionArray = {SubDivision.None, SubDivision.Priority, SubDivision.Type};

	/** The Spin Checkbox */
	JCheckBox spinCheckbox;
	PieRotator rotator;

	/** The button panel. */
	JPanel boxPanel = new JPanel();

	/** The overall panel. */
	JPanel overallPanel = new JPanel();

	/** The graph panel. */
	ChartPanel graphPanel;
	DefaultToolbarView toolbar;
	
	JPanel errorPanel;
	/**
	 * Instantiates a new bar chart panel.
	 *
	 * @param view the view
	 * @param chart the chart
	 */
	public BarPieChartPanel(BarPieChartView view, JFreeChart chart){
		graphPanel = new ChartPanel(chart);

		addComponents();
	}

	/**
	 * Put the buttons and other components in the view.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void addComponents(){

		JLabel noData = new JLabel("No data currently available", JLabel.TRAILING);
		noData.setFont(new Font("Serif", Font.PLAIN, 64));
		
		//Make ComboBoxes
		chartBox = new JComboBox(chartTypeArray);
		chartBox.setBackground(Color.WHITE);
		characteristicBox = new JComboBox(characteristicArray);
		characteristicBox.setBackground(Color.WHITE);
		subDivideBox = new JComboBox(subDivisionArray);
		subDivideBox.setBackground(Color.WHITE);
		spinCheckbox = new JCheckBox("Spin");//The button to spin the pie chart.
		
		//Make Labels
		chartLabel = new JLabel("Chart Type:", JLabel.TRAILING);
		characteristicLabel = new JLabel("Characteristics:", JLabel.TRAILING);
		subDivideLabel = new JLabel("Sub-Divide Bars:", JLabel.TRAILING);

		GridBagLayout layoutOverall = new GridBagLayout();
		overallPanel.setLayout(layoutOverall);

		/*set the layout manager for this an the nested panel*/
		layout = new GridBagLayout();
		this.setLayout(new BorderLayout());

		GridBagConstraints cBox = new GridBagConstraints();
		GridBagLayout layoutBtn = new GridBagLayout();
		boxPanel.setLayout(layoutBtn);

		GridBagConstraints cOverall = new GridBagConstraints();
		overallPanel.setLayout(layoutOverall);
		
		cBox.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBox.fill = GridBagConstraints.NONE;
		cBox.gridx = 0;
		cBox.gridy = 0;
		cBox.weightx = 0.5;
		cBox.weighty = 0.5;
		cBox.gridwidth = 2;
		cBox.insets = new Insets(10,10,5,0); //insets for top, left, bottom, and right
		boxPanel.add(chartLabel, cBox);
		
		cBox.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBox.fill = GridBagConstraints.NONE;
		cBox.gridx = 2;
		cBox.gridy = 0;
		cBox.weightx = 0.5;
		cBox.weighty = 0.5;
		cBox.gridwidth = 2;
		cBox.insets = new Insets(10,10,5,0); //insets for top, left, bottom, and right
		boxPanel.add(characteristicLabel, cBox);
		
		cBox.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBox.fill = GridBagConstraints.NONE;
		cBox.gridx = 4;
		cBox.gridy = 0;
		cBox.weightx = 0.5;
		cBox.weighty = 0.5;
		cBox.gridwidth = 2;
		cBox.insets = new Insets(10,10,5,0); //insets for top, left, bottom, and right
		boxPanel.add(subDivideLabel, cBox);
		
		cBox.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBox.fill = GridBagConstraints.HORIZONTAL;
		cBox.gridx = 0;
		cBox.gridy = 1;
		cBox.weightx = 0.5;
		cBox.weighty = 0.5;
		cBox.gridwidth = 2;
		cBox.insets = new Insets(5,10,5,0); //insets for top, left, bottom, and right
		boxPanel.add(chartBox, cBox);
		
		cBox.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBox.fill = GridBagConstraints.HORIZONTAL;
		cBox.gridx = 2;
		cBox.gridy = 1;
		cBox.weightx = 0.5;
		cBox.weighty = 0.5;
		cBox.gridwidth = 2;
		cBox.insets = new Insets(5,10,5,0); //top,left,bottom,right
		boxPanel.add(characteristicBox, cBox);
		
		cBox.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBox.fill = GridBagConstraints.HORIZONTAL;
		cBox.gridx = 4;
		cBox.gridy = 1;
		cBox.weightx = 0.5;
		cBox.weighty = 0.5;
		cBox.gridwidth = 2;
		cBox.insets = new Insets(5,10,5,0); //top,left,bottom,right
		boxPanel.add(subDivideBox, cBox);

		cBox.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBox.fill = GridBagConstraints.HORIZONTAL;
		cBox.gridx = 6;
		cBox.gridy = 1;
		cBox.weightx = 0.5;
		cBox.weighty = 0.5;
		cBox.gridwidth = 2;
		cBox.insets = new Insets(5,10,5,0); //top,left,bottom,right
		boxPanel.add(spinCheckbox, cBox);

		//the the panels to the overall panel
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START; 
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.weightx = 1;
		cOverall.weighty = 1;
		cOverall.gridwidth = 1;
		cOverall.insets = new Insets(0,0,0,0); //top,left,bottom,right
		overallPanel.add(boxPanel, cOverall);

		//Make the error message for when there is no graph.
		errorPanel = new JPanel();
		errorPanel.add(noData);
		
		setChart(displayedChart, TypeOfChart.Bar, false, false);

		this.add(overallPanel,BorderLayout.CENTER);
		this.validate();
	}

	/**
	 * Set the chart to be what you pass in.
	 *
	 * @param newChart The chart you are overwriting with.
	 * @param chartType type of chart to pass in
	 * @param pieSpin boolean for if the pie chart should spin
	 * @param visible boolean for if the pie chart is visible
	 */
	public void setChart(JFreeChart newChart, TypeOfChart chartType, boolean pieSpin, boolean visible){
		overallPanel.remove(graphPanel);
		overallPanel.remove(errorPanel);
		displayedChart = newChart;

		graphPanel = new ChartPanel(newChart);
		GridBagConstraints cGraph = new GridBagConstraints();

		GridBagLayout layoutGraph = new GridBagLayout();
		graphPanel.setLayout(layoutGraph);
		
		if(visible){
			//Set Chart Properties
			if(newChart != null){
				newChart.setBackgroundPaint(Color.white);
				if(chartType == TypeOfChart.Bar){
					final NumberAxis rangeAxis = (NumberAxis) newChart.getCategoryPlot().getRangeAxis();
					rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				}
				//Pie
				else{
					PiePlot3D plot = (PiePlot3D) newChart.getPlot();
					plot.setStartAngle(0);
					plot.setForegroundAlpha(1);
					rotator = new PieRotator(plot);
					if(pieSpin){
						rotator.start();
					}
				}
			}
			spinCheckbox.setEnabled(visible);
			
			//Add the Graph to the panel
			cGraph.anchor = GridBagConstraints.FIRST_LINE_START;
			cGraph.fill = GridBagConstraints.HORIZONTAL;
			cGraph.gridx = 0;
			cGraph.gridy = 3;
			cGraph.weightx = 0.5;
			cGraph.weighty = 0.5;
			cGraph.gridheight = 1;
			cGraph.insets = new Insets(10,10,10,0); //top,left,bottom,right
			overallPanel.add(graphPanel,cGraph);
			this.repaint();
			graphPanel.updateUI();
		}
		else{
			spinCheckbox.setEnabled(visible);
			
			GridBagConstraints cBox = new GridBagConstraints();
			cBox.anchor = GridBagConstraints.LINE_START; 
			cBox.gridx = 0;
			cBox.gridy = 0;
			cBox.weightx = .5;
			cBox.weighty = .5;
			cBox.gridwidth = 1;
			cBox.insets = new Insets(0,0,0,0);
			overallPanel.add(errorPanel,cBox);
		}
		this.remove(overallPanel);
		this.add(overallPanel,BorderLayout.CENTER);
		this.repaint();
		graphPanel.updateUI();
		this.validate();
	}

	/**
	 * Gray out or un-gray out the combo box.
	 * 
	 * @param enabled Whether or not the box is enabled.
	 */
	public void setSubDivideEnable(boolean enabled){
		subDivideBox.setVisible(enabled);
		subDivideLabel.setVisible(enabled);
		if(!enabled){
			subDivideBox.setSelectedIndex(0);
		}
	}

	/**
	 * Determine whether or not you can see the button.
	 * 
	 * @param enabled Whether or not it is visible
	 */
	public void setSpinVisible(boolean enabled){
		spinCheckbox.setVisible(enabled);
	}

	//Combo Box Getters
	/**
	 * Gets the chart box
	 * 
	 * @return the chartBox
	 */
	public JComboBox getChartBox() {
		return chartBox;
	}

	/**
	 * Gets the characteristic box
	 * 
	 * @return the characteristicBox
	 */
	public JComboBox getCharacteristicBox() {
		return characteristicBox;
	}


	/**
	 * Gets the sub divide box
	 * 
	 * @return the subDivideBox
	 */
	public JComboBox getSubDivideBox() {
		return subDivideBox;
	}

	/**
	 * Gets the displayed chart
	 * 
	 * @return the displayedChart
	 */
	public JFreeChart getDisplayedChart() {
		return displayedChart;
	}

	/**
	 * Gets the spin checkbox
	 * 
	 * @return the spinButton
	 */
	public JCheckBox getSpinCheckbox() {
		return spinCheckbox;
	}

	/**
	 * Gets the rotator
	 * 
	 * @return the rotator
	 */
	public PieRotator getRotator() {
		return rotator;
	}


}