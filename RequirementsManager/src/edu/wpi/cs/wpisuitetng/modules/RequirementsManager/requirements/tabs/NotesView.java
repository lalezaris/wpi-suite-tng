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

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.AddNoteController;
/**
 * Tab panel for adding and viewing notes
 *
 * @author Joe Spicola
 *
 * @version Mar 25, 2013
 *
 */
public class NotesView extends JPanel {
	
	/** The layout manager for this panel */
	protected GridBagLayout layout;
	
	protected JPanel panelOverall;
	protected GridBagLayout layoutOverall;
	
	protected JTextArea txtNotes;
	protected JTextArea txtNotesSaved;
	protected JButton addNote;
	
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
		panelOverall = new JPanel();
		
		txtNotes = new JTextArea(3, 40);
		txtNotes.setLineWrap(true);
		//txtNotes.setBorder(txtTitle.getBorder()); TODO: Set borders
		txtNotesSaved = new JTextArea(3, 40);
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
		txtNotesSaved.setText(/*printNotesList()*/"");
		txtNotesSaved.setEnabled(false);
		this.add(scrollPaneNotesSaved, c);
		//add completed notes here...

	}
	/*
	public String getNoteString(){
		txtNotes.getText();
	}*/
}
