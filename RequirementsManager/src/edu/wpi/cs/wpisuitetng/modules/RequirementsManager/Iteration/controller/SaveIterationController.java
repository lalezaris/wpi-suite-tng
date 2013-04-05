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
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer.SaveIterationRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle the saving of an iteration.
 * Adapted from SaveIterationController in project IterationsManager.
 * 
 * @author Tushar Narayan
 * @author Arica Liu
 * 
 * @version Mar 26, 2013
 *
 */
public class SaveIterationController {
	/** The view object containing the request fields */
	protected IterationView view;

	/**
	 * Construct a new handler for the given view.
	 * 
	 * @param view the view containing the request fields
	 */
	public SaveIterationController(IterationView view) {
		this.view = view;
	}

	/**
	 * Save the view's Iteration model to the server (asynchronous).
	 */
	public void save() {
		final IterationPanel panel = (IterationPanel) view.getIterationPanel();
		final RequestObserver requestObserver = new SaveIterationRequestObserver(view);
		Request request;
		request = Network.getInstance().makeRequest("iterationsmanager/iteration",  HttpMethod.PUT );
		System.out.println("Saving Iteartion, and Refresher is " + (Refresher.getInstance()!=null));
		
		if(panel.checkRequiredFields() == 0){
			if (panel.ValidateFields() == 0) {
				request.setBody(panel.getEditedModel().toJSON());
				request.addObserver(requestObserver);
				request.send();
				//close tab
				this.view.getTab().getView().removeTabAt(this.view.getTab().getThisIndex());
			}
		} 		
	} 
}
