package edu.wpisuitetng.modules.RequirementsManager.historylog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
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
	
	Requirement req1;
	Requirement req2;
	Requirement req3;
	Requirement req4;
	
	Requirement req5;
	Requirement req6;
	
	ArrayList<Requirement> children = new ArrayList<Requirement>();
	ArrayList<Requirement> childrenPlusMore = new ArrayList<Requirement>();
	
	
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
	public void testMultipleSubReqsUpdate(){
		//Create a TON of children.
		for(int i = 0; i < 50; i ++){
			children.add(new Requirement(1,"Title"+i, "This is child: "+i, new User("Derpy"+1, "Derpy"+1, "Derpy"+1, 2+1)));
			childrenPlusMore.add(new Requirement(1,"Title"+i, "This is child: "+i, new User("Derpy"+1, "Derpy"+1, "Derpy"+1, 2+1)));
		}
		for(int i = 50; i < 70; i ++){
			childrenPlusMore.add(new Requirement(1,"Title"+i, "This is child: "+i, new User("Derpy+"+1, "Derpy+"+1, "Derpy+"+1, 2+1)));
		}
		
		req5 = new Requirement(1,"ParentTitle5", "a desc", new User("bill", "bill", "bill", 0));
		req6 = new Requirement(1,"ParentTitle6", "a desc", new User("bill", "bill", "bill", 0));
		
		//Overwrite the pre-made requirements for this one test.
		req5.setSubRequirements(children);
		req6.setSubRequirements(childrenPlusMore);
		
		HistoricalChange OldBigNewSmall = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		HistoricalChange OldSmallNewBig = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		HistoricalChange RemoveOne = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		
		
		OldSmallNewBig.updateChangeFromDiff(req5, req6);
		assertEquals("Title changed from ParentTitle5 to ParentTitle6." +
				"\nSub Requirement Title50 added" +
				"\nSub Requirement Title51 added" +
				"\nSub Requirement Title52 added" +
				"\nSub Requirement Title53 added" +
				"\nSub Requirement Title54 added" +
				"\nSub Requirement Title55 added" +
				"\nSub Requirement Title56 added" +
				"\nSub Requirement Title57 added" +
				"\nSub Requirement Title58 added" +
				"\nSub Requirement Title59 added" +
				"\nSub Requirement Title60 added" +
				"\nSub Requirement Title61 added" +
				"\nSub Requirement Title62 added" +
				"\nSub Requirement Title63 added" +
				"\nSub Requirement Title64 added" +
				"\nSub Requirement Title65 added" +
				"\nSub Requirement Title66 added" +
				"\nSub Requirement Title67 added" +
				"\nSub Requirement Title68 added" +
				"\nSub Requirement Title69 added\n", OldSmallNewBig.getChange());
		
		OldBigNewSmall.updateChangeFromDiff(req6, req5);
		assertEquals("Title changed from ParentTitle6 to ParentTitle5." +
				"\nSub Requirement Title50 removed" +
				"\nSub Requirement Title51 removed" +
				"\nSub Requirement Title52 removed" +
				"\nSub Requirement Title53 removed" +
				"\nSub Requirement Title54 removed" +
				"\nSub Requirement Title55 removed" +
				"\nSub Requirement Title56 removed" +
				"\nSub Requirement Title57 removed" +
				"\nSub Requirement Title58 removed" +
				"\nSub Requirement Title59 removed" +
				"\nSub Requirement Title60 removed" +
				"\nSub Requirement Title61 removed" +
				"\nSub Requirement Title62 removed" +
				"\nSub Requirement Title63 removed" +
				"\nSub Requirement Title64 removed" +
				"\nSub Requirement Title65 removed" +
				"\nSub Requirement Title66 removed" +
				"\nSub Requirement Title67 removed" +
				"\nSub Requirement Title68 removed" +
				"\nSub Requirement Title69 removed\n", OldBigNewSmall.getChange());
		
		childrenPlusMore.remove(30);
		req6.setSubRequirements(childrenPlusMore);
		RemoveOne.updateChangeFromDiff(req5, req6);
		assertEquals("Title changed from ParentTitle5 to ParentTitle6." +
				"\nSub Requirement Title30 removed" +
				"\nSub Requirement Title50 added" +
				"\nSub Requirement Title51 added" +
				"\nSub Requirement Title52 added" +
				"\nSub Requirement Title53 added" +
				"\nSub Requirement Title54 added" +
				"\nSub Requirement Title55 added" +
				"\nSub Requirement Title56 added" +
				"\nSub Requirement Title57 added" +
				"\nSub Requirement Title58 added" +
				"\nSub Requirement Title59 added" +
				"\nSub Requirement Title60 added" +
				"\nSub Requirement Title61 added" +
				"\nSub Requirement Title62 added" +
				"\nSub Requirement Title63 added" +
				"\nSub Requirement Title64 added" +
				"\nSub Requirement Title65 added" +
				"\nSub Requirement Title66 added" +
				"\nSub Requirement Title67 added" +
				"\nSub Requirement Title68 added" +
				"\nSub Requirement Title69 added\n", RemoveOne.getChange());
	}
	
	@Test
	public void testReqNotesUpdate(){
		HistoricalChange aChange = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		req3.updateNotes(notes1);
		req4.updateNotes(notes2);
		
		aChange.updateChangeFromDiff(req3, req4);
		assertEquals("Title changed from title3 to title4.\nSub Requirement title2 removed\nSub Requirement title3 added\n1 note added.\n", aChange.getChange());
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
