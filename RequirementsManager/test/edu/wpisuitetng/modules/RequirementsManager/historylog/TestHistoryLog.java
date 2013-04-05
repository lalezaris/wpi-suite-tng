package edu.wpisuitetng.modules.RequirementsManager.historylog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockData;
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
		req1 = new Requirement(1,"title1", "a desc", "bill");
		req2 = new Requirement(1, "title2" , "not a desc", "bill");
		req3 = new Requirement(1,"title3", "a desc", "bill");
		req4 = new Requirement(1,"title4", "a desc", "bill");
/*

		req1 = new Requirement(1,"title1", "a desc", new User("bill", "bill", "bill", 0));
		req2 = new Requirement(2, "title2" , "not a desc", new User("bill", "bill", "bill", 0));
		req3 = new Requirement(3,"title3", "a desc", new User("bill", "bill", "bill", 0));
		req4 = new Requirement(69,"title4", "a desc", new User("bill", "bill", "bill", 0));

*/
		
		ArrayList<String> billAssignee = new ArrayList<String>();
		billAssignee.add("bill");
		ArrayList joeAssignee = new ArrayList<String>();
		joeAssignee.add("joe");
		
		req1.setAssignee(billAssignee);
		req3.setAssignee(billAssignee);
		req4.setAssignee(billAssignee);
		req2.setAssignee(joeAssignee);
		
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
		assertEquals(aChange2.getChange(), "<p> Title changed from title1 to title2.</p><p> Description changed from:\n\"a desc\"\n -TO- \n\"not a desc.\"</p><p> Assignee changed from [bill] to [joe].</p>");
	}
	
	@Test
	public void testSubReqsUpdate(){
		HistoricalChange aChange = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		
		aChange.updateChangeFromDiff(req3, req4, manager);
		System.out.println("\n\nTEST 2" + aChange.getChange());
		assertEquals("<p> Title changed from title3 to title4.</p>", aChange.getChange());
	}
	
	@Test
	public void testMultipleSubReqsUpdate(){
		//Create a TON of children.
		for(int i = 0; i < 50; i ++){
/*
			children.add(new Requirement(1,"Title"+i, "This is child: "+i, "Derpy"+1));
			childrenPlusMore.add(new Requirement(1,"Title"+i, "This is child: "+i, "Derpy"+1));
		}
		for(int i = 50; i < 70; i ++){
			childrenPlusMore.add(new Requirement(1,"Title"+i, "This is child: "+i, "Derpy+"+1));
*/			
			children.add(30+i);
			childrenPlusMore.add(30+i);
		}
		for(int i = 50; i < 70; i ++){
			childrenPlusMore.add(30+i);
		}
		
		req5 = new Requirement(1,"ParentTitle5", "a desc", "bill");
		req6 = new Requirement(1,"ParentTitle6", "a desc", "bill");
		
		//Overwrite the pre-made requirements for this one test.
		req5.setSubRequirements(children);
		req6.setSubRequirements(childrenPlusMore);
		
		HistoricalChange OldBigNewSmall = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		HistoricalChange OldSmallNewBig = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		HistoricalChange RemoveOne = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		
		
		OldSmallNewBig.updateChangeFromDiff(req5, req6, manager);
		assertEquals("<p> Title changed from ParentTitle5 to ParentTitle6.</p><p> Sub Requirement 80 added</p><p> Sub Requirement 81 added</p><p> Sub Requirement 82 added</p><p> Sub Requirement 83 added</p><p> Sub Requirement 84 added</p><p> Sub Requirement 85 added</p><p> Sub Requirement 86 added</p><p> Sub Requirement 87 added</p><p> Sub Requirement 88 added</p><p> Sub Requirement 89 added</p><p> Sub Requirement 90 added</p><p> Sub Requirement 91 added</p><p> Sub Requirement 92 added</p><p> Sub Requirement 93 added</p><p> Sub Requirement 94 added</p><p> Sub Requirement 95 added</p><p> Sub Requirement 96 added</p><p> Sub Requirement 97 added</p><p> Sub Requirement 98 added</p><p> Sub Requirement 99 added</p>", OldSmallNewBig.getChange());
		
		OldBigNewSmall.updateChangeFromDiff(req6, req5, manager);
		assertEquals("<p> Title changed from ParentTitle6 to ParentTitle5.</p><p> Sub Requirement 80 removed</p><p> Sub Requirement 81 removed</p><p> Sub Requirement 82 removed</p><p> Sub Requirement 83 removed</p><p> Sub Requirement 84 removed</p><p> Sub Requirement 85 removed</p><p> Sub Requirement 86 removed</p><p> Sub Requirement 87 removed</p><p> Sub Requirement 88 removed</p><p> Sub Requirement 89 removed</p><p> Sub Requirement 90 removed</p><p> Sub Requirement 91 removed</p><p> Sub Requirement 92 removed</p><p> Sub Requirement 93 removed</p><p> Sub Requirement 94 removed</p><p> Sub Requirement 95 removed</p><p> Sub Requirement 96 removed</p><p> Sub Requirement 97 removed</p><p> Sub Requirement 98 removed</p><p> Sub Requirement 99 removed</p>", OldBigNewSmall.getChange());
		
		childrenPlusMore.remove(30);
		req6.setSubRequirements(childrenPlusMore);
		RemoveOne.updateChangeFromDiff(req5, req6, manager);
		assertEquals("<p> Title changed from ParentTitle5 to ParentTitle6.</p><p> Sub Requirement 60 removed</p><p> Sub Requirement 80 added</p><p> Sub Requirement 81 added</p><p> Sub Requirement 82 added</p><p> Sub Requirement 83 added</p><p> Sub Requirement 84 added</p><p> Sub Requirement 85 added</p><p> Sub Requirement 86 added</p><p> Sub Requirement 87 added</p><p> Sub Requirement 88 added</p><p> Sub Requirement 89 added</p><p> Sub Requirement 90 added</p><p> Sub Requirement 91 added</p><p> Sub Requirement 92 added</p><p> Sub Requirement 93 added</p><p> Sub Requirement 94 added</p><p> Sub Requirement 95 added</p><p> Sub Requirement 96 added</p><p> Sub Requirement 97 added</p><p> Sub Requirement 98 added</p><p> Sub Requirement 99 added</p>", RemoveOne.getChange());
	}
	
	@Test
	public void testReqNotesUpdate(){
		HistoricalChange aChange = new HistoricalChange(new Date(), 1, 2, new User("billo", "", "", 2));
		req3.updateNotes(notes1);
		req4.updateNotes(notes2);
		
		aChange.updateChangeFromDiff(req3, req4, manager);
		assertEquals("<p> Title changed from title3 to title4.</p><p> 1 note added.</p>", aChange.getChange());
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
