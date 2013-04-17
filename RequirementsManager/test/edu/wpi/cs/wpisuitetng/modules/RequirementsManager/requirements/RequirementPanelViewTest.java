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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import static org.junit.Assert.assertEquals;

import java.awt.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for the Requirements Panel
 *
 * @author Chris Dunkers
 *
 * @version Apr 13, 2013
 *
 */
public class RequirementPanelViewTest {
	
	RequirementPanel view;
	
	@Before
	public void setUp() {
		view = new RequirementPanel(null,RequirementPanel.Mode.CREATE);
	}
	
	@Test
	public void testChangeStatus(){
		view.txtEstimate.setText("5");
		ActionEvent aEvent = new ActionEvent(view.txtEstimate, 1, 
				"2");
		view.txtEstimate.dispatchEvent(aEvent);
		
		assertEquals("5", view.txtEstimate.getText());
		assertEquals(true, view.getCmbIteration().isEnabled());
	}

}
