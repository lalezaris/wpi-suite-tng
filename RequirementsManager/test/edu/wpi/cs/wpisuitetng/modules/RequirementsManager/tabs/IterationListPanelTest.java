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
 *  Tianyu Li
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.IterationTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * @author Tianyu Li
 *
 */
public class IterationListPanelTest {
	IterationListPanel panel;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
	SimpleDateFormat tableDateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
	Date date1, date2, date3, date4;
	
	@Before
	public void setUp() {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		
		try {
			date1 = dateFormat.parse("01-04-2013");
			date2 = dateFormat.parse("02-04-2013");
			date3 = dateFormat.parse("03-04-2013");
			date4 = dateFormat.parse("04-04-2013");
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		panel = new IterationListPanel(MainTabController.getController());
		Iteration ite1 = new Iteration("Iteration1", date1, date2);
		Iteration ite2 = new Iteration("Iteration2", date3, date4);
		Iteration[] ites = {ite1, ite2};
		panel.addIterations(ites);
	}
	
	@Test
	public void panelNotNullTest() {
		assertNotNull(panel);
	}
	
	@Test
	public void addIterationsTest() {
		assertEquals(2, ((IterationTableModel)panel.getTable().getModel()).getData().size());
		assertEquals("Iteration1", ((IterationTableModel)panel.getTable().getModel()).getData().get(1)[1]);
		assertEquals(tableDateFormat.format(date1), ((IterationTableModel)panel.getTable().getModel()).getData().get(1)[2]);
		assertEquals(tableDateFormat.format(date2), ((IterationTableModel)panel.getTable().getModel()).getData().get(1)[3]);
	}
}
