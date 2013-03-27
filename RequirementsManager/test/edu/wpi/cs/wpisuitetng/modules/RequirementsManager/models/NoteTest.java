package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus.*;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority.*;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class NoteTest {
	
	Requirement req = new Requirement();
	Note note1 = new Note("this is note 1", new User("","","",0));
	Note note2 = new Note("this is note 2", new User("","","",0));
	Note note3 = new Note("this is note 3", new User("","","",0));
	ArrayList<Note> notes = new ArrayList<Note>();
	
	@Before
	public void setUp(){
		System.out.println("SETUP!!!!");
		notes.add(note1);
		notes.add(note2);
		notes.add(note3);
	}
	
	@Test
	public void testAddNote(){
		System.out.println("adding note 1");
		req.addNote(note1);
		System.out.println("adding note 2");
		req.addNote(note2);
		System.out.println("adding note 3");
		req.addNote(note3);
		assertEquals(req.getNotes(), notes);
	}
	
	@Test
	public void testArrayLists(){
		assertEquals(notes.get(0), note1);
		assertEquals(notes.get(1), note2);
		assertEquals(notes.get(2), note3);
		
		
	}
	

}
