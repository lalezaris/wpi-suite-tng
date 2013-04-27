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
 *  Michael French
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * Tab panel for adding and viewing notes.
 *
 * @author Joe Spicola
 * @version Mar 27, 2013
 */
@SuppressWarnings("serial")
public class NotesView extends RequirementTab implements FocusListener {

	/** The layout manager for this panel */
	protected GridBagLayout layout;

	protected JTextArea txtNotes;
	protected JTextArea txtNotesSaved;
	protected JButton addNote;

	/** The ArrayList of Notes*. */
	protected ArrayList<Note> notes = new ArrayList<Note>();

	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;


	protected RequirementView parent;

	/**
	 * Instantiates a new notes view.
	 *
	 * @param parent the requirement view holding the notes
	 */
	public NotesView(RequirementView parent) {
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		layout.columnWeights = new double[]{.2, .8};
		this.setLayout(layout);
		this.parent = parent;
		// Add all components to this panel
		addComponents();		
	}

	/**
	 * Instantiates a new notes view.
	 *
	 * @param notes the notes shown in the view
	 */
	public NotesView(ArrayList<Note> notes){
		this.notes = notes;
		addComponents();
	}

	/**
	 * Adds the components to the panel and places constraints on them
	 * for the GridBagLayout manager.
	 *
	 */
	protected void addComponents() {
		//create a new constrain variable
		GridBagConstraints c = new GridBagConstraints();

		//TODO: Set borders

		/* begin panel styling */
		txtNotes = new JTextArea(4, 40);
		txtNotes.addFocusListener(this);
		txtNotes.setLineWrap(true);
		txtNotesSaved = new JTextArea(4, 40);
		txtNotesSaved.setLineWrap(true);
		JLabel lblNotes = new JLabel("Enter Note:", LABEL_ALIGNMENT);
		JLabel lblNotesSaved = new JLabel("Saved Notes:", LABEL_ALIGNMENT);

		addNote = new JButton("Add Note");
		//addNote.addActionListener(new AddNoteController(this));

		c.anchor = GridBagConstraints.LINE_START;
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

		JScrollPane scrollPaneNotes = new JScrollPane(txtNotes);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add(scrollPaneNotes, c);

		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5,0,5,0);
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		this.add(lblNotesSaved, c);

		JScrollPane scrollPaneNotesSaved = new JScrollPane(txtNotesSaved);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		txtNotesSaved.setText(notesListToString());
		txtNotesSaved.setEnabled(false);
		txtNotesSaved.setDisabledTextColor(Color.BLACK);
		this.add(scrollPaneNotesSaved, c);
		/* end panel styling */

	}

	/**
	 * Set the notes textbox with the notes list.
	 */
	public void setTxtNotesSaved() {
		txtNotesSaved.setText(notesListToString());
	}

	/**
	 * Initialize the notes text area.
	 */
	public void setTxtNotes(){
		txtNotes.setText("");
	}

	/**
	 * Gets the note string.
	 *
	 * @return txtNotes in string format
	 */
	public String getNoteString(){
		return txtNotes.getText().trim();
	}

	/**
	 * Returns the ArrayList of notes in the current view.
	 *
	 * @return the ArrayList of notes
	 */
	public ArrayList<Note> getNotesList(){
		return notes;
	}

	/**
	 * Sets the notes list.
	 *
	 * @param aln arraylist of notes
	 */
	public void setNotesList(ArrayList<Note> aln){
		this.notes = aln;
		if (txtNotesSaved!=null)
			txtNotesSaved.setText(notesListToString());
	}

	/**
	 * Adds a Note to the ArrayList of notes.
	 *
	 * @param n the note
	 * @return the note
	 */
	public Note addNoteToList(Note n){
		notes.add(n);
		return n;
	}

	/**
	 * Iterates through the notes in the ArrayList and makes
	 * it into a printable string.
	 *
	 * @return notes in the form of a String
	 */
	//earliest note on bottom v
	public String notesListToString(){
		String list = "";
		for (int i = notes.size() - 1; i >= 0; i--){
			if (i > 0){
				list += ">[" + notes.get(i).getCreationDate().toString() + "] " + 
						notes.get(i).getCreator() + 
						": " + notes.get(i).getBody() + "\n \n";
			}else{
				list += ">[" + notes.get(i).getCreationDate().toString() + "] "
						+ notes.get(i).getCreator() + 
						": " + notes.get(i).getBody();
			}
		}
		return list;
	}

	/**
	 * Takes the given string and converts it to a Note,
	 * setting it's creator to the current user.
	 *
	 * @param s the string
	 * @return note the new note object converted from the string
	 */
	public Note stringToNote(String s){
		Note note = new Note(s, ConfigManager.getConfig().getUserName()); //TODO: Pull user info
		return note;
	}

	/**
	 * Repaint the Note.
	 */
	public void repaintNote() {
		this.repaint();
	}


	/**
	 * Gets the save button.
	 *
	 * @return returns the button which will save a note when fired
	 */
	public JButton getSaveButton(){
		return this.addNote;
	}

	/**
	 * Gets the text area.
	 *
	 * @return returns the text area which is used to write the note
	 */
	public JTextArea getTextArea(){
		return this.txtNotes;
	}

	/**
	 * Gets the text area for displaying the saved notes
	 * 
	 * @return returns the text area for displaying the saved notes
	 */
	public JTextArea getSavedTextArea(){
		return this.txtNotesSaved;
	}
	
	/**
	 * Sets the txt notes background color.
	 *
	 * @param c the new txt notes background color
	 */
	public void setTxtNotesBackgroundColor(Color c) {
		this.txtNotes.setBackground(c);
	}
	
	/**
	 * Sets the txt notes saved background color.
	 *
	 * @param c the new txt notes saved background color
	 */
	public void setTxtNotesSavedBackgroundColor(Color c) {
		this.txtNotesSaved.setBackground(c);
	}

	/**
	 * Refresh backgrounds.
	 */
	public void refreshBackgrounds() {
		this.parent.getReqModel().updateBackgrounds();
	}
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		this.refreshBackgrounds();		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		this.refreshBackgrounds();		
	}

	@Override
	public String getTabTitle() {
		return "Notes";
	}

	@Override
	public ImageIcon getImageIcon() {
		return new ImageIcon();
	}

	@Override
	public String getTooltipText() {
		return "Add and modify notes";
	}
}