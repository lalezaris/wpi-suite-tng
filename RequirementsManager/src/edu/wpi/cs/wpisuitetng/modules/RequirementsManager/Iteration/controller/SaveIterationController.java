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
 *  Arica Liu
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer.CheckIterationFieldsObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer.CreateIterationRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer.UpdateIterationRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
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
//		final IterationPanel panel = (IterationPanel) getView().getIterationPanel();
//		final RequestObserver requestObserver = (panel.getEditMode() == Mode.CREATE) ? new CreateIterationRequestObserver(getView()) : new UpdateIterationRequestObserver(getView());
//		Request request;
//		request = Network.getInstance().makeRequest("iterationsmanager/iteration", (panel.getEditMode() == Mode.CREATE) ? HttpMethod.PUT : HttpMethod.POST);
//
//		if(view.checkRequiredFields() == 0){
//			String JsonRequest = view.getIterationModel().getEditedModel().toJSON();
//			request.setBody(JsonRequest);
//			System.out.println("Sending REQ to server:" +JsonRequest );
//			request.addObserver(requestObserver);
//			request.send();
//			//close tab
//			view.getTab().getView().removeTabAt(this.getView().getTab().getThisIndex());
//			System.out.println("SAVE ITERATION");
//		}
		
		Request request;
		request = Network.getInstance().makeRequest("iterationsmanager/iteration", HttpMethod.GET);
		request.addObserver(new CheckIterationFieldsObserver(this));
		request.send();
		
	} 
	
	public void checkIterationField(Iteration[] iterations){
		final IterationPanel panel = (IterationPanel) view.getIterationPanel();
		final RequestObserver requestObserver = (panel.getEditMode() == Mode.CREATE) ? new CreateIterationRequestObserver(view) : new UpdateIterationRequestObserver(view);
		Request requestSave;
		requestSave = Network.getInstance().makeRequest("iterationsmanager/iteration", (panel.getEditMode() == Mode.CREATE) ? HttpMethod.PUT : HttpMethod.POST);

		if(view.checkRequiredFields(iterations) == 0){
			String JsonRequest = view.getIterationModel().getEditedModel().toJSON();
			requestSave.setBody(JsonRequest);
			System.out.println("Sending REQ to server:" +JsonRequest );
			requestSave.addObserver(requestObserver);
			requestSave.send();
			//close tab
			view.getTab().getView().removeTabAt(view.getTab().getThisIndex());
			System.out.println("SAVE ITERATION");
		}
	}
	
	/**
	 * Gets view.
	 * 
	 * @return the view
	 */
	public IterationView getView() {
		return view;
	}
}