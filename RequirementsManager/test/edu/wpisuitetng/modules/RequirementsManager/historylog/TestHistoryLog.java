package edu.wpisuitetng.modules.RequirementsManager.historylog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class TestHistoryLog {
	
	Requirement req1;
	Requirement req2;
	Requirement req3;
	Requirement req4;
	
	ArrayList<Requirement> rlist1 = new ArrayList<Requirement>();
	ArrayList<Requirement> rlist2 = new ArrayList<Requirement>();
	
	ArrayList<Note> notes1 = new ArrayList<Note>();
	ArrayList<Note> notes2 = new ArrayList<Note>();
	
	@Before
	public void setUp(){
		req1 = new Requirement(1,"title1", "a desc", new User("bill", "bill", "bill", 0));
		req2 = new Requirement(1, "title2" , "not a desc", new User("bill", "bill", "bill", 0));
		req3 = new Requirement(1,"title3", "a desc", new User("bill", "bill", "bill", 0));
		req4 = new Requirement(1,"title4", "a desc", new User("bill", "bill", "bill", 0));
		
		req1.setAssignee(new User("bill", "bill", "bill", 0));
		req3.setAssignee(new User("bill", "bill", "bill", 0));
		req4.setAssignee(new User("bill", "bill", "bill", 0));
		req2.setAssignee(new User("Joe", "Joe", "Joe", 1));
		
		rlist1.add(req1);
		rlist1.add(req2);
		
		rlist2.add(req1);
		rlist2.add(req3);
		
		req3.setSubRequirements(rlist1);
		req4.setSubRequirements(rlist2);
		
		notes1.add(new Note("A Note", "A Note"));
		notes2.add(new Note("A Note", "A Note"));
		notes2.add(new Note("A Note", "A Note"));
		
		
		
		
		
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
	
	@Test
	public void testSubReqsUpdate(){
		HistoricalChange aChange = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		
		aChange.updateChangeFromDiff(req3, req4);
		System.out.println("\n" + aChange.getChange());
		assertEquals("Title changed from title3 to title4.\nSub Requirement title2 removed\nSub Requirement title3 added\n", aChange.getChange());
	}
	
	@Test
	public void testReqNotesUpdate(){
		HistoricalChange aChange = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		req3.updateNotes(notes1);
		req4.updateNotes(notes2);
		
		aChange.updateChangeFromDiff(req3, req4);
		System.out.println("\n" + aChange.getChange());
		assertEquals("Title changed from title3 to title4.\nSub Requirement title2 removed\nSub Requirement title3 added\n1 note added.\n", aChange.getChange());
	}
	
}
