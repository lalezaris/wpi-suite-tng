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
 *  Joe Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

/**
 * 
 *
 * @author Spicola
 *
 * @version Mar 18, 2013
 *
 */
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle the saving of a requirement
 * 
 * @author Joe Spicola
 *
 * @version Mar 18, 2013
 *
 */
public class SaveRequirementController {

	/** The view object containing the request fields */
	protected RequirementView view;

	/**
	 * Construct a new handler for the given view
	 * @param view the view containing the request fields
	 */
	public SaveRequirementController(RequirementView view) {
		this.view = view;
	}

	/**
	 * Save the view's Requirement model to the server (asynchronous).
	 */
	public void save() {
		final RequirementPanel panel = (RequirementPanel) view.getRequirementPanel();
		final RequestObserver requestObserver = /* (panel.getEditMode() == Mode.CREATE) ? new CreateRequirementRequestObserver(view) : */ new UpdateRequirementRequestObserver(view);
		Request request;
		panel.getParent().setInputEnabled(false);
		request = Network.getInstance().makeRequest("requirementsmanager/requirement", /* (panel.getEditMode() == Mode.CREATE) ? */ HttpMethod.PUT /* HttpMethod.POST */);
			request.setBody(panel.getEditedModel().toJSON());
			request.addObserver(requestObserver);
			request.send();
	} 



}
