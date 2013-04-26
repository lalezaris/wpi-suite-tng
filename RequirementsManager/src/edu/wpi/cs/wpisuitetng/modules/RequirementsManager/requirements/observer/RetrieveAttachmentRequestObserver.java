package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer;

import java.io.File;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAttachmentController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class RetrieveAttachmentRequestObserver implements RequestObserver  {
	RetrieveAttachmentController controller;
	
	public RetrieveAttachmentRequestObserver(RetrieveAttachmentController controller){
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
				Request request = (Request) iReq;

				// get the response from the request
				ResponseModel response = request.getResponse();


				// parse the requirement received from the core
				Attachment[] attachment = Attachment.fromJSONArray(response.getBody());
				if (attachment.length > 0 && attachment[0] != null) {
					attachment[0].saveFile(controller.getFile());
				}
				else {
					//controller.errorRetrievingRequirement("No requirement received.");
				}
		
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
