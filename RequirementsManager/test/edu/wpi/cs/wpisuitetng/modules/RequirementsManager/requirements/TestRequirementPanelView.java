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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 13, 2013
 *
 */
public class TestRequirementPanelView {
	
	RequirementView view;
	
	@Before
	public void setUp() {
		view = new RequirementView(new Requirement(), Mode.CREATE, null);
	}
	
	@Test
	public void testChangeStatus(){
		view.getRequirementPanel().txtEstimate.setText("5");
		
		assertEquals(true, view.getRequirementPanel().getCmbIteration().isEnabled());
	}

}
