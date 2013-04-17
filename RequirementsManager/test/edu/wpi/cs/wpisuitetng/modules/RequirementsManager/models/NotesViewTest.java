package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddNoteController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.NotesView;
//import edu.wpi.cs.wpisuitetng.modules.defecttracker.MockNetwork;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 * @author Michael
 * Test the Notes View
 *
 */

public class NotesViewTest {
	NotesView nv;
	Requirement req1;
	
	@Before
	public void setup(){
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		
		req1 = new Requirement();
		req1.addNote(new Note("n1", "creator1"));
		req1.addNote(new Note("n2", "creator2"));
		req1.addNote(new Note("n3", "creator3"));
		
		RequirementView rv = new RequirementView(req1, RequirementPanel.Mode.EDIT, null);
		nv = new NotesView(rv);
		nv.setNotesList(req1.getNotes());
		nv.getSaveButton().addActionListener(new AddNoteController(nv));
	}
	
	//Test the list functionalities
	@Test
	public void TestList(){
		ArrayList<Note> n = req1.getNotes();
		assertEquals(nv.getNotesList(), n);
		Note note1 = new Note("new note", "lulz");
		nv.addNoteToList(note1);
		n.add(note1);
		assertEquals(nv.getNotesList(), n);
		nv.setNotesList(nv.getNotesList());//to update the text field
	}
	
	
	//Test the things that happen when the AddNote Button is pressed
	@Test
	public void TestButton(){
		nv.getTextArea().setText("another Note!");
		nv.getSaveButton().doClick();
		assertEquals(nv.getSavedTextArea().getText(), nv.notesListToString());
	}

}
