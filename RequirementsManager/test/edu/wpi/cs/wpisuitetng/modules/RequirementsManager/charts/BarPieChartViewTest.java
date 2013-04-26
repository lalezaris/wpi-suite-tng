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

import static org.junit.Assert.assertEquals;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockData;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * @author Evan Polekoff
 */
public class BarPieChartViewTest {
	
	BarPieChartView view;
	BarPieChartPanel panel;
	boolean urls;
	boolean tooltips;
	boolean legend;
	PlotOrientation orientation;
	DefaultCategoryDataset dataset;
	Plot plot;
	MockData db;
	
	/**
	 * Inits the test.
	 */
	@Before
	public void init(){
		//Default chart fields.
		urls = false;
		tooltips = true;
		legend = true;
		orientation = PlotOrientation.VERTICAL;
		dataset = new DefaultCategoryDataset();
		
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		view = new BarPieChartView();
		panel = view.getMainPanel();
	}
	
	/**
	 * Test repaint chart status.
	 */
	@Test
	public void testRepaintChartStatus(){
		//Click on Status in the dropdown
		view.getCharacteristicBox().setSelectedIndex((view.getCharacteristicBox().getSelectedIndex()));
		plot = panel.getDisplayedChart().getPlot();
		//Check if the created chart is what we expected.
		//assertEquals(panel.getDisplayedChart(), ChartFactory.createStackedBarChart3D("Number of Requirements for Each " + "Status", "Status", "Requirements", view.getStatusNoneBarDataset(), orientation, legend, tooltips, urls));
		assertEquals(panel.getDisplayedChart().getTitle().getText(), "Number of Requirements for Each Status");
		assertEquals(((CategoryPlot)plot).getDataset(), view.getStatusNoneBarDataset());
		
		//Click on the Priority SubDivide
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()+1));
		assertEquals(((CategoryPlot)plot).getDataset(), view.getStatusPrioBarDataset());
		
		//Click on the Type SubDivide
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()+1));
		assertEquals(((CategoryPlot)plot).getDataset(), view.getStatusTypeBarDataset());
		
		//Click on the Pie Chart dropdown
		view.getChartBox().setSelectedIndex((view.getChartBox().getSelectedIndex()+1));
		assertEquals(panel.getDisplayedChart().getTitle().getText(), "Number of Requirements for Each Status");//The same title for a different type of graph.
		//assertEquals(((PiePlot)plot).getDataset(), view.getStatusPieDataset());
	}
	
	/**
	 * Test repaint chart assignee.
	 */
	@Test
	public void testRepaintChartAssignee(){
		//Click on Assignee in the dropdown
		view.getCharacteristicBox().setSelectedIndex((view.getCharacteristicBox().getSelectedIndex()+2));
		plot = panel.getDisplayedChart().getPlot();
		//Check if the created chart is what we expected.
		assertEquals(panel.getDisplayedChart().getTitle().getText(), "Number of Requirements for Each Assignee");
		assertEquals(((CategoryPlot)plot).getDataset(), view.getAssigneeNoneBarDataset());
		
		//Click on the Priority SubDivide
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()+1));
		assertEquals(((CategoryPlot)plot).getDataset(), view.getAssigneePrioBarDataset());
		
		//Click on the Type SubDivide
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()+1));
		assertEquals(((CategoryPlot)plot).getDataset(), view.getAssigneeTypeBarDataset());
		
		//Click on the Pie Chart dropdown
		view.getChartBox().setSelectedIndex((view.getChartBox().getSelectedIndex()+1));
		assertEquals(panel.getDisplayedChart().getTitle().getText(), "Number of Requirements for Each Assignee");//The same title for a different type of graph.
		//assertEquals(((PiePlot)plot).getDataset(), view.getStatusPieDataset());
	}
	
	/**
	 * Test repaint chart iteration.
	 */
	@Test
	public void testRepaintChartIteration(){
		//Click on Assignee in the dropdown
		view.getCharacteristicBox().setSelectedIndex((view.getCharacteristicBox().getSelectedIndex()+1));
		plot = panel.getDisplayedChart().getPlot();
		//Check if the created chart is what we expected.
		assertEquals(panel.getDisplayedChart().getTitle().getText(), "Number of Requirements for Each Iteration");
		assertEquals(((CategoryPlot)plot).getDataset(), view.getIterationNoneBarDataset());
		
		//Click on the Priority SubDivide
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()+1));
		assertEquals(((CategoryPlot)plot).getDataset(), view.getIterationPrioBarDataset());
		
		//Click on the Type SubDivide
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()+1));
		assertEquals(((CategoryPlot)plot).getDataset(), view.getIterationTypeBarDataset());
		
		//Click on the Pie Chart dropdown
		view.getChartBox().setSelectedIndex((view.getChartBox().getSelectedIndex()+1));
		assertEquals(panel.getDisplayedChart().getTitle().getText(), "Number of Requirements for Each Iteration");//The same title for a different type of graph.
		//assertEquals(((PiePlot)plot).getDataset(), view.getStatusPieDataset());
	}
	
}
