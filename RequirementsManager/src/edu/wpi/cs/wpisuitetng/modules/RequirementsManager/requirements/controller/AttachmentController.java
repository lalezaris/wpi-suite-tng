package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import javax.swing.JFileChooser;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.AttachmentAddAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.AttachmentUploadAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AttachmentsView;

public class AttachmentController {
	private int reqID;

	AttachmentsView view;
	public AttachmentController (AttachmentsView view, int reqID){
		this.reqID =reqID;
		this.view = view;
		
		this.view.getAddFileButton().setAction( new AttachmentAddAction(this, this.view.getAddFileButton().getText()));
		this.view.getUploadFileButton().setAction( new AttachmentUploadAction(this, this.view.getUploadFileButton().getText()));
	}
	
	
	public void addClicked(){
		JFileChooser j = new JFileChooser();
		j.setMultiSelectionEnabled(false);
        
        if( j.showOpenDialog(null) != JFileChooser.APPROVE_OPTION ){
            //didn't pick anything.
        	return;
        }
        this.view.addFileToSelected(j.getSelectedFile());
	}
	
	public void uploadClicked(){
		SaveAttachmentController controller = new SaveAttachmentController(view);
		controller.save(reqID);
		view.getUploadFileButton().setVisible(false);
		view.getAddFileButton().setVisible(true);
	}
	
}
