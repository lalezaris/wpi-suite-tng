package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks;

import static org.junit.Assert.*;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;

public class TasksTest {
	Task t;
	TasksView tv;
	Requirement req;
	Task t1;
	Task t2;
	Task t3;
	
	@Before
	public void setup(){
		t = new Task("rage", "do something with derp", "herp", 99, 9001);
		tv = new TasksView(null);
		req = new Requirement();
		t1 = new Task("a", "a", "a", 0, 0);
	}
	
	@Test
	public void TestConstructor(){
		assertEquals("rage", t.getName());
		assertEquals("do something with derp", t.getDescription());
		assertEquals("herp", t.getAssigneeName());
		assertEquals(99, t.getEffort());
		assertEquals(9001, t.getId());
	}
	
	@Test
	public void testAddingTasksToRequirement(){
		req.addTask(t1);
		assertEquals(true, req.getTasks().contains(t1));
		System.out.println(req.getTasks().get(0).getName());
	}
}
