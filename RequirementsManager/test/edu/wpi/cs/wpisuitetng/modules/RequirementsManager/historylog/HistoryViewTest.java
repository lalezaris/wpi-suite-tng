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
 *  Michael French
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.historylog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockData;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager.RequirementStore;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.HistoryView;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 * @author Michael French
 *
 */

public class HistoryViewTest {
	Requirement req;
	RequirementView rv;
	HistoryView hv;
	HistoricalChange c1;
	HistoricalChange c2;
	HistoricalChange c3;
	
	@Before
	public void setup(){
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		
		req = new Requirement();
		c1 = new HistoricalChange(new User("", "", "", 0));
		c2 = new HistoricalChange(new User("", "", "", 0));
		c3 = new HistoricalChange(new User("", "", "", 0));
		ArrayList<HistoricalChange> al = new ArrayList<HistoricalChange>();
		al.add(c1);
		al.add(c2);
		al.add(c3);
		req.updateHistory(al);
		rv = new RequirementView(req, RequirementPanel.Mode.EDIT, null);
		hv = new HistoryView(rv);
		hv.setHistoryList(req.getHistory());
	}
	
	@Test
	public void testHasHistory(){
		assertEquals(true, hv.getHistoryList().contains(c1));
		assertEquals(true, hv.getHistoryList().contains(c2));
		assertEquals(true, hv.getHistoryList().contains(c3));
	}
	
}
