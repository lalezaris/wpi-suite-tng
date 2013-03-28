/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * @author Chris Hanna
 *
 */
public class RetrieveRequirementControllerTree {

	private IRetrieveRequirementController caller;
	
	public RetrieveRequirementControllerTree(IRetrieveRequirementController caller)
	{
		this.caller = caller;
	}
	
	public void retrieve(){
	
		int id = caller.getID();
		if (id > -1){
			Request request;
			request = Network.getInstance().makeRequest("requirementsmanager/requirement/" + id, HttpMethod.GET);
			request.addObserver(new RetrieveRequirementObserverTree(this));
			request.send();
		} else recieveData(null);
	}
	
	public void recieveData(Requirement r){
		caller.runWhenRecieved(r);
	}
	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 */
	public void errorRetrievingRequirement(String error) {
		//TODO
	}
}
