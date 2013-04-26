/**
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Michael French
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;

public class AssigneeViewTest {
	AssigneeView av;
	ArrayList<String> asn;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup(){
		av = new AssigneeView(null);
		asn = new ArrayList<String>();
		
	}
	
	/**
	 * Test set assignee list.
	 */
	@Test
	public void testSetAssigneeList(){
		asn.add("Jenkins");
		asn.add("Kent");
		asn.add("Leroy");
		asn.add("Queen");
		av.setAssigneeList(asn);
		assertEquals(true, av.getAssignedUserAL().contains("Jenkins"));
		assertEquals(true, av.getAssignedUserAL().contains("Kent"));
		assertEquals(true, av.getAssignedUserAL().contains("Leroy"));
		assertEquals(true, av.getAssignedUserAL().contains("Queen"));
		asn = new ArrayList<String>();
	}
	
	/**
	 * Test set all user list.
	 */
	@Test
	public void testSetAllUserList(){
		asn.add("Us");
		asn.add("Them");
		asn.add("Ordinary Men");
		av.setAllList(asn);
		assertEquals(true, av.getAllUserLM().contains("Us"));
		assertEquals(true, av.getAllUserLM().contains("Them"));
		assertEquals(true, av.getAllUserLM().contains("Ordinary Men"));
		asn = new ArrayList<String>();
	}
	
	//TODO: figure out how to test these
	/**
	 * Test set list.
	 */
	@Test
	public void testSetList(){
		
	}
	
	/**
	 * Update not assigned.
	 */
	@Test
	public void updateNotAssigned(){}
}
