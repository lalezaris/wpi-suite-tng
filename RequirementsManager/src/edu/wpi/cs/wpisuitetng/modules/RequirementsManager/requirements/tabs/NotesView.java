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
 *  Chris Dunkers
 *  Joe Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.AddNoteController;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
/**
 * Tab panel for adding and viewing notes
 *
 * @author Joe Spicola
 * @edited Michael French
 *
 * @version Mar 27, 2013
 *
 */
public class NotesView extends JPanel {
	
	/** The layout manager for this panel */
	protected GridBagLayout layout;
	
	//protected JPanel panelOverall;
	
	protected JTextArea txtNotes;
	protected JTextArea txtNotesSaved;
	protected JButton addNote;
	
	/** The ArrayList of Notes**/
	protected ArrayList<Note> notes = new ArrayList<Note>();
	
	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;

	public NotesView() {
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		// Add all components to this panel
		addComponents();
		
	}
	
	/**
	 * Adds the components to the panel and places constraints on them
	 * for the SpringLayout manager.
	 * @param layout the layout manager
	 */
	protected void addComponents() {
		//create a new constrain variable
		GridBagConstraints c = new GridBagConstraints();
		
		// Construct all of the components for the form
		//panelOverall = new JPanel();
		
		txtNotes = new JTextArea(4, 40);
		txtNotes.setLineWrap(true);
		//txtNotes.setBorder(txtTitle.getBorder()); TODO: Set borders
		txtNotesSaved = new JTextArea(4, 40);
		txtNotesSaved.setLineWrap(true);
		//txtNotesSaved.setBorder(txtTitle.getBorder()); TODO: Set borders
		JLabel lblNotes = new JLabel("Notes:", LABEL_ALIGNMENT);
		
		addNote = new JButton("Add Note");
		//addNote.setAction(new SaveChangesAction(new SaveRequirementController(this.getParent())));
		addNote.addActionListener(new AddNoteController(this));
		//c.insets = new Insets(40,10,5,10);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(lblNotes, c);
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		this.add(addNote, c);
		
		c.insets = new Insets(20,10,10,0);
		JScrollPane scrollPaneNotes = new JScrollPane(txtNotes);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add(scrollPaneNotes, c);
					
		JScrollPane scrollPaneNotesSaved = new JScrollPane(txtNotesSaved);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		txtNotesSaved.setText(notesListToString());
		txtNotesSaved.setEnabled(false);
		this.add(scrollPaneNotesSaved, c);
		//add completed notes here...

	}
	
	public void setTxtNotesSaved() {
		txtNotesSaved.setText(notesListToString());
	}
	
	public void setTxtNotes(){
		txtNotes.setText("");
	}
	public String getNoteString(){
		return txtNotes.getText();
	}
	
	/**
	 * returns the ArrayList of notes in the current view
	 * @return the ArrayList of notes
	 */
	public ArrayList<Note> getNotesList(){
		return notes;
	}
	
	/**
	 * adds a Note to the ArrayList of notes
	 * @param n
	 * @return n
	 */
	public Note addNoteToList(Note n){
		notes.add(n);
		//System.out.println("Note Added");
		//System.out.println(n.getBody());
		return n;
	}
	
	/**
	 * iterates through the notes in the ArrayList and makes 
	 * it into a printable string
	 * @return notes in the form of a String
	 */
	//earliest note on top v
	/*public String notesListToString(){
		String list = "";
		for (int i = 0; i < notes.size(); i++){
			if (i < notes.size() -1){
				list = list + ">" + notes.get(i).getCreator() + 
						": " + notes.get(i).getBody() + "\n \n";
			}else{
				list = list + ">" + notes.get(i).getCreator() + 
						": " + notes.get(i).getBody();
			}
		}
		
		return list;
	}*/
	
	//earliest note on bottom v
	public String notesListToString(){
		String list = "";
		for (int i = notes.size() - 1; i >= 0; i--){
			if (i > 0){
				list = list + ">[" + notes.get(i).getCreationDate().toString() + "] " + 
						notes.get(i).getCreator() + 
						": " + notes.get(i).getBody() + "\n \n";
			}else{
				list = list + ">[" + notes.get(i).getCreationDate().toString() + "] "
						+ notes.get(i).getCreator() + 
						": " + notes.get(i).getBody();
			}
		}
		
		return list;
	}
	
	/**
	 * takes the given string and converts it to a Note, 
	 * setting it's creator as the current user
	 * @param s
	 * @return
	 */
	public Note stringToNote(String s){
		Note note = new Note(s, ConfigManager.getConfig().getUserName()); //TODO: Pull user info
		return note;
	}
	
	//repaint the Note
	public void repaintNote() {
		this.repaint();
	}
}
