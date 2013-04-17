/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveAllIterationsObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveAllRequirementsRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * @author Chris Hanna
 *
 */
public class RetrieveAllIterationsController {

	RequirementView view;
	public RetrieveAllIterationsController(RequirementView view){
		this.view = view;
	}
	
	public void retrieve(){
		final RequestObserver requestObserver = new RetrieveAllIterationsObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("iterationsmanager/iteration", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}
	
	public void received(Iteration[] iterations){
		view.setIterationComboBox(iterations);
		
		if (view.getReqModel().getRequirement().getParentRequirementId() != -1) {
			RetrieveParentRequirementController recieveParentController = new RetrieveParentRequirementController(view);
			recieveParentController.retrieveParent();
		}
	}
}
