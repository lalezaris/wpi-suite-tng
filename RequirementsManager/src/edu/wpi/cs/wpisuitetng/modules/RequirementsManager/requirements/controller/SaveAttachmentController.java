package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.CreateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.SaveAttachmentRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AttachmentsView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SaveAttachmentController {
	protected AttachmentsView view;
	
	public SaveAttachmentController(AttachmentsView view){
		this.view = view;
	}
	
	public void save(int reqId){
		final RequestObserver requestObserver =  new SaveAttachmentRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("attachmentcontroller/attachment", HttpMethod.PUT);//note, have not actually registered that in the core yet
		
		Attachment attachment = new Attachment(view.getFile(),reqId);
		attachment.loadFile(view.getFile());
		
		String JsonRequest = attachment.toJSON();
		request.setBody(JsonRequest);
		System.out.println("Sending REQ to server:" +JsonRequest );
		request.addObserver(requestObserver);
		request.send();
	}

	public void recievedData(Attachment attachment){
		System.out.println("attachment friggin returned:" + attachment.getId());
		RequirementView view = MainTabController.getController().getReqViewHashMap().get(attachment.getOwnerId());
		
		Requirement changeReq = view.getReqModel().getRequirement();


		changeReq.getAttachedFileId().add(attachment.getId());
		changeReq.getAttachedFileNames().add(attachment.getFileName());
//		System.out.println("is there a thing here?"+view.getReqModel().getUneditedRequirement().getAttachedFileId().get(0));
		
		SaveRequirementController saveReq = new SaveRequirementController(view);
		saveReq.save();
	}

}
