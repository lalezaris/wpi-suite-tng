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

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Insert Description Here
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
		GridBagConstraints cOverall = new GridBagConstraints();
		
		// Construct all of the components for the form
		panelOverall = new JPanel();
		
		txtNotes = new JTextArea(4, 40);
		txtNotes.setLineWrap(true);
		//txtNotes.setBorder(txtTitle.getBorder()); TODO: Set borders
		txtNotesSaved = new JTextArea(1, 40);
		txtNotesSaved.setLineWrap(true);
		//txtNotesSaved.setBorder(txtTitle.getBorder()); TODO: Set borders
		JLabel lblNotes = new JLabel("Notes:", LABEL_ALIGNMENT);
		
		//c.insets = new Insets(40,10,5,10);
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		this.add(lblNotes, c);
		
		//c.insets = new Insets(0,50,10,0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		this.add(txtNotes, c);
					
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		txtNotesSaved.setText("This is a default note \n \n \n This is trying to get extra space");
		txtNotesSaved.setEnabled(false);
		this.add(txtNotesSaved, c);
		//add completed notes here...

	}
}
