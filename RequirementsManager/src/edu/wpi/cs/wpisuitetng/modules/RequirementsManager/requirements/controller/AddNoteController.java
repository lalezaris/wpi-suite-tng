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
 * Joe Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.NotesView;

/**
 * Controller to add a note, and update display
 *
 * @author Joe Spicola
 *
 * @version Mar 26, 2013
 *
 */
public class AddNoteController implements ActionListener {
		
	private final NotesView view;

	public AddNoteController(NotesView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ITWORKEDHOLYCRAP");
		view.addNoteToList(view.stringToNote(view.getNoteString()));
		view.setTxtNotesSaved();
		view.setTxtNotes();
		System.out.println(view.getNotesList().toString());
	}
}

