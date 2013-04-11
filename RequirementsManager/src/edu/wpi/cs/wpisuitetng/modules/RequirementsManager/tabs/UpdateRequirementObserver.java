package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UpdateRequirementObserver implements RequestObserver{

	UpdateAllRequirementsController controller;
	public UpdateRequirementObserver(UpdateAllRequirementsController controller){
		this.controller = controller;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println("Requirement updated");
		
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		
	}

}
