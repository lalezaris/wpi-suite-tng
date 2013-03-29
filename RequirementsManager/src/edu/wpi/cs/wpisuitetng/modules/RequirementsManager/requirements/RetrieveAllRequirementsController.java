/**
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 * Sam Abradi
 * Michael French
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Retrieves all of the requirements from the database 
 *
 * @author Sam Abradi
 *
 * @version Mar 21, 2013
 *
 */
public class RetrieveAllRequirementsController {
	/** The Requirements data retrieved from the server */
	protected Requirement[] data = null;

	protected RefresherMode refreshMode;
	
	/**
	 * Constructs a new RetrieveAllRequirementsController
	 * 
	 * @param view the search Requirements view
	 */
	public RetrieveAllRequirementsController(RefresherMode refreshMode) {
		this.refreshMode = refreshMode;
	}

	/**
	 * Sends a request for all of the Requirements
	 */
	public void refreshData() {		
		final RequestObserver requestObserver = new RetrieveAllRequirementsRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement", /*is this ok? ->*/ HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
		//System.out.println("SENT REFRESH REQUEST");
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when the
	 * response is received
	 * 
	 * @param Requirements an array of Requirements returned by the server
	 */
	public void receivedData(Requirement[] Requirements) {	
		
		System.out.println("Entered recieve. Requirements = " + (Requirements != null));
		
		if (Requirements.length > 0) {
			// save the data
			this.data = Requirements;
			//this.view.addRequirements(Requirements);
			//System.out.println("Refresher = "+ (Refresher.getInstance() != null));
			//ALL TEH HAX
			for (int i = 0 ; i < Requirements.length; i ++){
				System.out.println("Fixing broken Iteration link. Think ID = " + Requirements[i].getIterationId());
				Requirements[i].setIteration(Iteration.getIterationById(Requirements[i].getIterationId()));
			}
			
			Refresher.getInstance().refreshRequirements(Requirements, refreshMode);
		}
		else {
			// do nothing, there are no Requirements
		}
		
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when an
	 * error occurs retrieving the Requirements from the server.
	 */
	public void errorReceivingData(String error) {
		//JOptionPane.showMessageDialog(view, "An error occurred retrieving Requirements from the server. " + error, 
				//"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}

}
