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
 * Evan Polekoff
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;

/**
 * Test the TasksView
 *
 * @author Evan Polekoff
 */
public class TasksViewTest {
	Task t;
	Task t2;
	Task t3;
	TasksView tv;
	Requirement req;
	ArrayList<Task> list;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup(){
		t = new Task("TwilightSparkle", "Is Best Pony", "Joey", 0, 1);
		t2 = new Task("DerpyHooves", "Is 2nd Best Pony", "Mike", 37, 1);
		t3 = new Task("", "", "", 37, 1);
		tv = new TasksView(null);
		req = new Requirement();
		
		list = new ArrayList<Task>();
		list.add(t);
		list.add(t2);
		list.add(t3);
		tv.setList(list);
	}
	
	/**
	 * Test the status availability
	 */
	@Test
	public void TestCmbStatus(){
		assertEquals(tv.getTaskPanelArray().get(0).getCmbStatus().isEnabled(), false);
		assertEquals(tv.getTaskPanelArray().get(1).getCmbStatus().isEnabled(), true);
	}
}
