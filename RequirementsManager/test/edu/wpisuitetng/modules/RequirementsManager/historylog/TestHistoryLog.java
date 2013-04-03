package edu.wpisuitetng.modules.RequirementsManager.historylog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager.MockData;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager.RequirementStore;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * 
 * @author Mike French
 * @author Evan Polekoff
 * @author Sam Abradi
 *
 */

public class TestHistoryLog {
	
	MockData db;
	RequirementStore manager;
	
	Requirement req1;
	Requirement req2;
	Requirement req3;
	Requirement req4;
	
	Requirement req5;
	Requirement req6;
	
	ArrayList<Integer> children = new ArrayList<Integer>();
	ArrayList<Integer> childrenPlusMore = new ArrayList<Integer>();
	
	
	ArrayList<Integer> rlist1 = new ArrayList<Integer>();
	ArrayList<Integer> rlist2 = new ArrayList<Integer>();
	
	ArrayList<Note> notes1 = new ArrayList<Note>();
	ArrayList<Note> notes2 = new ArrayList<Note>();
	
	@Before
	public void setUp(){
		req1 = new Requirement(1,"title1", "a desc", new User("bill", "bill", "bill", 0));
		req2 = new Requirement(2, "title2" , "not a desc", new User("bill", "bill", "bill", 0));
		req3 = new Requirement(3,"title3", "a desc", new User("bill", "bill", "bill", 0));
		req4 = new Requirement(69,"title4", "a desc", new User("bill", "bill", "bill", 0));
		
		req1.setAssignee(new User("bill", "bill", "bill", 0));
		req3.setAssignee(new User("bill", "bill", "bill", 0));
		req4.setAssignee(new User("bill", "bill", "bill", 0));
		req2.setAssignee(new User("Joe", "Joe", "Joe", 1));
		
		rlist1.add(req1.getId());
		rlist1.add(req2.getId());
		
		rlist2.add(req1.getId());
		rlist2.add(req3.getId());
		
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
		
		aChange1.updateChangeFromDiff(req1, req1, manager);
		System.out.println("CHANGE 1 " + aChange1.getChange());
		assertEquals(aChange1.getChange(), "");
		
		aChange2.updateChangeFromDiff(req1, req2, manager);
		System.out.println(aChange2.getChange());
		assertEquals(aChange2.getChange(), "Title changed from title1 to title2.\nDescription changed from:\n\"a desc\"\n -TO- \n\"not a desc.\"\nAssignee changed from bill to Joe.\n");
		
	}
	
	@Test
	public void testSubReqsUpdate(){
		HistoricalChange aChange = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		
		aChange.updateChangeFromDiff(req3, req4, manager);
		System.out.println("\n\nTEST 2" + aChange.getChange());
		assertEquals("Title changed from title3 to title4." +
				"\nSub Requirement 2 removed" +
				"\nSub Requirement 3 added\n", aChange.getChange());
	}
	
	@Test
	public void testMultipleSubReqsUpdate(){
		//Create a TON of children.
		for(int i = 0; i < 50; i ++){
			children.add(30+i);
			childrenPlusMore.add(30+i);
		}
		for(int i = 50; i < 70; i ++){
			childrenPlusMore.add(30+i);
		}
		
		req5 = new Requirement(1,"ParentTitle5", "a desc", new User("bill", "bill", "bill", 0));
		req6 = new Requirement(1,"ParentTitle6", "a desc", new User("bill", "bill", "bill", 0));
		
		//Overwrite the pre-made requirements for this one test.
		req5.setSubRequirements(children);
		req6.setSubRequirements(childrenPlusMore);
		
		HistoricalChange OldBigNewSmall = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		HistoricalChange OldSmallNewBig = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		HistoricalChange RemoveOne = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		
		
		OldSmallNewBig.updateChangeFromDiff(req5, req6, manager);
		assertEquals("Title changed from ParentTitle5 to ParentTitle6." +
				"\nSub Requirement 80 added" +
				"\nSub Requirement 81 added" +
				"\nSub Requirement 82 added" +
				"\nSub Requirement 83 added" +
				"\nSub Requirement 84 added" +
				"\nSub Requirement 85 added" +
				"\nSub Requirement 86 added" +
				"\nSub Requirement 87 added" +
				"\nSub Requirement 88 added" +
				"\nSub Requirement 89 added" +
				"\nSub Requirement 90 added" +
				"\nSub Requirement 91 added" +
				"\nSub Requirement 92 added" +
				"\nSub Requirement 93 added" +
				"\nSub Requirement 94 added" +
				"\nSub Requirement 95 added" +
				"\nSub Requirement 96 added" +
				"\nSub Requirement 97 added" +
				"\nSub Requirement 98 added" +
				"\nSub Requirement 99 added\n", OldSmallNewBig.getChange());
		
		OldBigNewSmall.updateChangeFromDiff(req6, req5, manager);
		assertEquals("Title changed from ParentTitle6 to ParentTitle5." +
				"\nSub Requirement 80 removed" +
				"\nSub Requirement 81 removed" +
				"\nSub Requirement 82 removed" +
				"\nSub Requirement 83 removed" +
				"\nSub Requirement 84 removed" +
				"\nSub Requirement 85 removed" +
				"\nSub Requirement 86 removed" +
				"\nSub Requirement 87 removed" +
				"\nSub Requirement 88 removed" +
				"\nSub Requirement 89 removed" +
				"\nSub Requirement 90 removed" +
				"\nSub Requirement 91 removed" +
				"\nSub Requirement 92 removed" +
				"\nSub Requirement 93 removed" +
				"\nSub Requirement 94 removed" +
				"\nSub Requirement 95 removed" +
				"\nSub Requirement 96 removed" +
				"\nSub Requirement 97 removed" +
				"\nSub Requirement 98 removed" +
				"\nSub Requirement 99 removed\n", OldBigNewSmall.getChange());
		
		childrenPlusMore.remove(30);
		req6.setSubRequirements(childrenPlusMore);
		RemoveOne.updateChangeFromDiff(req5, req6, manager);
		assertEquals("Title changed from ParentTitle5 to ParentTitle6." +
				"\nSub Requirement 60 removed" +
				"\nSub Requirement 80 added" +
				"\nSub Requirement 81 added" +
				"\nSub Requirement 82 added" +
				"\nSub Requirement 83 added" +
				"\nSub Requirement 84 added" +
				"\nSub Requirement 85 added" +
				"\nSub Requirement 86 added" +
				"\nSub Requirement 87 added" +
				"\nSub Requirement 88 added" +
				"\nSub Requirement 89 added" +
				"\nSub Requirement 90 added" +
				"\nSub Requirement 91 added" +
				"\nSub Requirement 92 added" +
				"\nSub Requirement 93 added" +
				"\nSub Requirement 94 added" +
				"\nSub Requirement 95 added" +
				"\nSub Requirement 96 added" +
				"\nSub Requirement 97 added" +
				"\nSub Requirement 98 added" +
				"\nSub Requirement 99 added\n", RemoveOne.getChange());
	}
	
	@Test
	public void testReqNotesUpdate(){
		HistoricalChange aChange = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		req3.updateNotes(notes1);
		req4.updateNotes(notes2);
		
		aChange.updateChangeFromDiff(req3, req4, manager);
		assertEquals("Title changed from title3 to title4.\nSub Requirement 2 removed\nSub Requirement 3 added\n1 note added.\n", aChange.getChange());
	}
	
	@Test
	public void testGetters(){
		HistoricalChange aChange = new HistoricalChange(new Date(), 47, 2, new User("TwilightSparkle", "", "", 2));
		System.out.println(aChange.getDate());
		System.out.println(aChange.getId());
		System.out.println(aChange.getUserName());
		
		assertEquals(aChange.getUserName(), "TwilightSparkle");
		assertEquals(aChange.getId(), 47);
	}
	
}
