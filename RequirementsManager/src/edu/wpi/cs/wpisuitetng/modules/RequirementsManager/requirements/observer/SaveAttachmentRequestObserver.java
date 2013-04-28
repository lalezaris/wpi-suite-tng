package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveAttachmentController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class SaveAttachmentRequestObserver implements RequestObserver {
	SaveAttachmentController controller;
	
	public SaveAttachmentRequestObserver(SaveAttachmentController controller){
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {

		Request request = (Request) iReq;
		
		ResponseModel response = request.getResponse();
		TreeView.getInstance().refreshTree();
		if((response.getStatusCode() >= 200)&&(response.getStatusCode() < 300)){
			Attachment attachments = Attachment.fromJSON(response.getBody());
			System.out.println("file is stored with this id: " + attachments.getId());
			

			
			controller.recievedData(attachments);
		}

	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Error on:");
		System.out.println(iReq.getBody());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method flub
		System.out.println("FAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAIL");
	}

}
