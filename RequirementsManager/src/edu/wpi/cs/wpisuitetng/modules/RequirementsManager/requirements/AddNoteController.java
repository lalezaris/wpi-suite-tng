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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.NotesView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Button to add a note, and update display
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
		//view.repaint();
		
		//when button is pressed
		//pull from txtNotes
		//push to arrayList
		//refresh tab
		
		// Send a request to the core to save this message
		//final Request request = Network.getInstance().makeRequest("postboard/postboardmessage", HttpMethod.GET); // GET == read
		//request.addObserver(new GetMessagesRequestObserver(this)); // add an observer to process the response
		//request.send(); // send the request
	}
}

