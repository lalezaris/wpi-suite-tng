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
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;

/**
 * @author Tushar Narayan
 *
 */
public class RequirementStatusListsTest {
	Requirement statusCheck = new Requirement();
	RequirementStatusLists reqList;
	
	RequirementStatus statusNew = RequirementStatus.NEW;
	RequirementStatus statusInProgress = RequirementStatus.INPROGRESS;
	RequirementStatus statusOpen = RequirementStatus.OPEN;
	RequirementStatus statusComplete = RequirementStatus.COMPLETE;
	RequirementStatus statusDeleted = RequirementStatus.DELETED;

	/**
	 * Sets up the tests.
	 */
	@Before
	public void setUp(){
		reqList = new RequirementStatusLists();
	}

	/**
	 * Test method for {link edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatusLists#getList(edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus)}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testGetList() {
		statusCheck.setStatus(statusNew);
		String[] arrNew = RequirementStatusLists.getList(statusCheck);
		statusCheck.setStatus(statusInProgress);
		String[] arrInProgress = RequirementStatusLists.getList(statusCheck);
		statusCheck.setStatus(statusOpen);
		String[] arrOpen= RequirementStatusLists.getList(statusCheck);
		statusCheck.setStatus(statusComplete);
		String[] arrComplete = RequirementStatusLists.getList(statusCheck);
		statusCheck.setStatus(statusDeleted);
		String[] arrDeleted = RequirementStatusLists.getList(statusCheck);

		assertEquals(arrNew, RequirementStatusLists.NewList);
		assertEquals(arrInProgress, RequirementStatusLists.InProgressList);
		assertEquals(arrOpen, RequirementStatusLists.OpenList);
		assertEquals(arrComplete, RequirementStatusLists.CompleteList);
		assertEquals(arrDeleted, RequirementStatusLists.DeletedList);

	}

}
