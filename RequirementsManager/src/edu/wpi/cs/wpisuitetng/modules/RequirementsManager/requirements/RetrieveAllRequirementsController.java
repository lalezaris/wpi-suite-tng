/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.*;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RetrieveAllRequirementsController {
	/** The search Requirements view */
	protected RequirementListView view;

	/** The Requirements data retrieved from the server */
	protected Requirement[] data = null;

	/**
	 * Constructs a new RetrieveAllRequirementsController
	 * 
	 * @param view the search Requirements view
	 */
	public RetrieveAllRequirementsController(RequirementListView view) {
		this.view = view;
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
		System.out.println("SENT REFRESH REQUEST");
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when the
	 * response is received
	 * 
	 * @param Requirements an array of Requirements returned by the server
	 */
	public void receivedData(Requirement[] Requirements) {	
		if (Requirements.length > 0) {
			// save the data
			this.data = Requirements;
			this.view.addRequirements(Requirements);
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
