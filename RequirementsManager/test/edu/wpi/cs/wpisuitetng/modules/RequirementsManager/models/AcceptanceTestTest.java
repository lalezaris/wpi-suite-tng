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
 *  Mike French
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AcceptanceTestsView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests for the AcceptanceTest class
 * 
 * @author Mike French
 * 
 * @version April 14, 2013
 *
 */
public class AcceptanceTestTest {
	AcceptanceTest a;
	AcceptanceTestsView av;
	Requirement req;
	RequirementView rv;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup(){
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		
		a = new AcceptanceTest("A Title", "bodybodybodybodybodybody");
		req = new Requirement();
		rv = new RequirementView(req, RequirementPanel.Mode.CREATE, null);
		Iteration[] iterations = {Iteration.getBacklog()};
		rv.getRequirementPanel().setIterations(iterations);
		av = new AcceptanceTestsView(rv);
		
		//due to permission conflicts, the buttons must manually be enabled
		av.getAddButton().setEnabled(true);
		//av.getEditButton().setEnabled(true);
		av.getTitleField().setEnabled(true);
		av.getBodyField().setEnabled(true);
		av.getStatusField().setEnabled(true);
	}
	
	/**
	 * Test constructors.
	 */
	@Test
	public void testConstructor(){
		assertEquals(a.getTitle(), "A Title");
		assertEquals(a.getBody(), "bodybodybodybodybodybody");
	}
	
	/**
	 * Test changing fields.
	 */
	@Test
	public void testChangeFields(){
		a.setBody("New Body");
		a.setStatus("Passed");
		assertEquals("New Body", a.getBody());
		assertEquals("Passed", a.getStatus());
	}
	
	
	/**
	 * Test adding and editing.
	 */
	@Test
	public void testAddandEdit(){
		av.getTitleField().setText("Test1");
		av.getBodyField().setText("Test1 body");
		av.getAddButton().doClick();
		assertEquals(1, av.getListSize());
		assertEquals("Test1", av.getList().get(0).getTitle());
		assertEquals("Test1 body", av.getList().get(0).getBody());
		assertEquals("", av.getTitleField().getText());
		assertEquals("", av.getBodyField().getText());
		
		//try to add the same test again, the list of tests should not get bigger
		av.getTitleField().setText("Test1");
		av.getBodyField().setText("Test1 body");
		av.getAddButton().doClick();
		assertEquals(1, av.getListSize());
		
		av.getTitleField().setText("Test1");
		av.getTitleField().getKeyListeners()[0].keyReleased(null);
		av.getBodyField().setText("new text");
		av.getAddButton().doClick();
		assertEquals("new text", av.getList().get(0).getBody());
	}
	
	/**
	 * Test if not ready.
	 */
	@Test
	public void testNotReady(){
		av.getTitleField().setText("");
		av.getBodyField().setText("");
		assertEquals(true, av.notReady());
		av.getTitleField().setText("text");
		assertEquals(true, av.notReady());
		av.getBodyField().setText("text");
		assertEquals(false, av.notReady());
		av.getTitleField().setText("");
		assertEquals(true, av.notReady());
	}
	
	//this test relies on the location of the mouse when clicked. not sure how to simulate that
//	@Test
//	public void testListDisplayMouseCLicked(){
//		AcceptanceTest aTemp = new AcceptanceTest("title", "body");
//		av.addTestToList(aTemp);
//		assertEquals(true, av.getList().contains(aTemp));
//		av.getListDisplay().getMouseListeners()[0].mouseClicked(null);
//		//assertEquals(false, av.getTitleField().getText() == "");
//		//assertEquals(false, av.getBodyField().getText() == "");
//	}

}
