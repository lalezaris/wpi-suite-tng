package edu.wpisuitetng.modules.RequirementsManager.historylog;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class TestHistoryLog {
	
	Requirement req1;
	Requirement req2;

	@Before
	public void setUp(){
		req1 = new Requirement(1,"title1", "a desc", new User("bill", "bill", "bill", 0));
		req2 = new Requirement(1, "title2" , "not a desc", new User("bill", "bill", "bill", 0));
		req1.setAssignee(new User("bill", "bill", "bill", 0));
		req2.setAssignee(new User("Joe", "Joe", "Joe", 1));
		
	}
	
	@Test
	public void testReqUpdateChangeFromDiff(){
		HistoricalChange aChange1 = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		HistoricalChange aChange2 = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		
		aChange1.updateChangeFromDiff(req1, req1);
		System.out.println(aChange1.getChange());
		assertEquals(aChange1.getChange(), "");
		
		aChange2.updateChangeFromDiff(req1, req2);
		System.out.println(aChange2.getChange());
		assertEquals(aChange2.getChange(), "Title changed from title1 to title2.\nDescription changed from:\n\"a desc\"\n -TO- \n\"not a desc.\"\nAssignee changed from bill to Joe.\n");
		
	}
	
}
