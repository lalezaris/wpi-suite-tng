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
 * 	Mike French
 *  Michael Perrone
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the Notes View
 * 
 * @author Mike French
 */

public class NoteTest {

	Requirement req;
	Note note1;
	Note note2;
	Note note3;
	Note note4;
	ArrayList<Note> notes;

	/**
	 * Sets up the test.
	 */
	@Before
	public void setUp(){
		req = new Requirement();
		note1  = new Note("this is note 1", "");
		note2 = new Note("this is note 2", "");
		note3 = new Note("this is note 3", "");
		note4 = new Note();
		notes = new ArrayList<Note>();
		System.out.println("SETUP!!!!");
		notes.add(note1);
		notes.add(note2);
		notes.add(note3);
	}

	/**
	 * Test add note.
	 */
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

	/**
	 * Test array lists.
	 */
	@Test
	public void testArrayLists(){
		assertEquals(notes.get(0), note1);
		assertEquals(notes.get(1), note2);
		assertEquals(notes.get(2), note3);
	}

	/**
	 * Test note constructor.
	 */
	@Test
	public void testNoteConstructor(){
		Note n = new Note("contents","name");
		assertEquals(n.getBody(), "contents");
		assertEquals(n.getCreator(), "name");
		assertTrue(java.lang.Math.abs((new Date().getTime()) - (n.getCreationDate()).getTime()) < 100);
	}
	
	/**
	 * Test blank note.
	 */
	@Test
	public void testBlankNote(){
		assertEquals(note4.getBody(), "");
		assertEquals(note4.getCreator(), "[USER UNKNOWN]");
	}
	
	/**
	 * Test setters.
	 */
	@Test
	public void testSetters(){
		Date testCreationDate = new Date();
		String testBodyString = "This is a test body string for note 4.";
		note4.setBody(testBodyString);
		note4.setCreationDate(testCreationDate);
		assertEquals(note4.getBody(), testBodyString);
		assertEquals(note4.getCreationDate(),testCreationDate);
	}


}
