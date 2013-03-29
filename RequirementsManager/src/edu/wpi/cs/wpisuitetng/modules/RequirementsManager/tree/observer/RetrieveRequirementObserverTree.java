package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.RetrieveRequirementControllerTree;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * @author Chris Hanna
 *
 */

public class RetrieveRequirementObserverTree implements RequestObserver{

	private RetrieveRequirementControllerTree controller;
	public RetrieveRequirementObserverTree(RetrieveRequirementControllerTree controller){
		this.controller = controller;
	}
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// check the response code of the request
		if (response.getStatusCode() != 200) {
			controller.errorRetrievingRequirement("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
			return;
		}

		// parse the defect received from the core
		Requirement[] requirements = Requirement.fromJSONArray(response.getBody());
		if (requirements.length > 0 && requirements[0] != null) {
			controller.recieveData(requirements[0]);
		}
		else {
			controller.errorRetrievingRequirement("No requirement received.");
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		controller.errorRetrievingRequirement("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with exception
		controller.errorRetrievingRequirement("Unable to complete request: " + exception.getMessage());
	}
}
