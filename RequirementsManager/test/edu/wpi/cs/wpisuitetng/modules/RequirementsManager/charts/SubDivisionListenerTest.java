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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockData;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartPanel.SubDivision;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;


/**
 * @author Evan Polekoff
 *
 */
public class SubDivisionListenerTest {
	
	BarPieChartView view;
	MockData db;
	
	@Before
	public void init(){
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		view = new BarPieChartView();
	}
	
	@Test
	public void testSubDivisionListener(){
		
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()));
		assertEquals(view.getCurrentSubDivision(), SubDivision.None);
		
		//Select the next dropdown option.
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()+1));
		assertEquals(view.getCurrentSubDivision(), SubDivision.Priority);
		
		//Select the next dropdown option.
		view.getSubDivideBox().setSelectedIndex((view.getSubDivideBox().getSelectedIndex()+1));
		assertEquals(view.getCurrentSubDivision(), SubDivision.Type);
	}
}
