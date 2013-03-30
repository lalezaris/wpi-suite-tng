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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Tushar Narayan
 *
 */
public class RequirementStatusListsTest {
	RequirementStatusLists reqList;
	RequirementStatus statusNew = RequirementStatus.NEW;
	RequirementStatus statusInProgress = RequirementStatus.INPROGRESS;
	RequirementStatus statusOpen = RequirementStatus.OPEN;
	RequirementStatus statusComplete = RequirementStatus.COMPLETE;
	RequirementStatus statusDeleted = RequirementStatus.DELETED;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		reqList = new RequirementStatusLists();
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatusLists#getList(edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus)}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testGetList() {
		String[] arrNew = RequirementStatusLists.getList(statusNew);
		String[] arrInProgress = RequirementStatusLists.getList(statusInProgress);
		String[] arrOpen= RequirementStatusLists.getList(statusOpen);
		String[] arrComplete = RequirementStatusLists.getList(statusComplete);
		String[] arrDeleted = RequirementStatusLists.getList(statusDeleted);

		assertEquals(arrNew, RequirementStatusLists.NewList);
		assertEquals(arrInProgress, RequirementStatusLists.InProgressList);
		assertEquals(arrOpen, RequirementStatusLists.OpenList);
		assertEquals(arrComplete, RequirementStatusLists.CompleteList);
		assertEquals(arrDeleted, RequirementStatusLists.DeletedList);

	}

}
