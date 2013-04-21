package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.AttachmentAddAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.AttachmentUploadAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AttachmentsView;

public class AttachmentController {

	AttachmentsView view;
	public AttachmentController (AttachmentsView view){
		this.view = view;
		
		this.view.getAddFileButton().setAction( new AttachmentAddAction(this, this.view.getAddFileButton().getText()));
		this.view.getUploadFileButton().setAction( new AttachmentUploadAction(this, this.view.getUploadFileButton().getText()));
		
	}
	
	
	public void addClicked(){
		
	}
	
	public void uploadClicked(){
		
	}
	
}
