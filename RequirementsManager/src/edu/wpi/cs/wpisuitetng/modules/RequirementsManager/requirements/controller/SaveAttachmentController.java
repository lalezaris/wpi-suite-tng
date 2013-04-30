/**************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 * Sam Abradi
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.SaveAttachmentRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AttachmentsView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * The Class SaveAttachmentController. makes the requests to save the attachment in the database
 * @author Sam Abradi
 */
public class SaveAttachmentController {
	protected AttachmentsView view;
	
	
	/**
	 * Instantiates a new save attachment controller.
	 *
	 * @param view the parent view
	 */
	public SaveAttachmentController(AttachmentsView view){
		this.view = view;
	}
	
	/**
	 * Save. this is the method that makes the save request to the server
	 *
	 * @param reqId the ID of the requirement that this is being attached to
	 */
	public void save(int reqId){
		final RequestObserver requestObserver =  new SaveAttachmentRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/attachment", HttpMethod.PUT);//note, have not actually registered that in the core yet
		
		Attachment attachment = new Attachment(view.getFile(),reqId);
		attachment.loadFile(view.getFile());
		
		String JsonRequest = attachment.toJSON();
		request.setBody(JsonRequest);
		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * Recieved data. this is what handles the data when you get it back from the server
	 *
	 * @param attachment get the attachment back so that you know what ID the server assigned it
	 */
	public void recievedData(Attachment attachment){
		RequirementView reqview = MainTabController.getController().getReqViewHashMap().get(attachment.getOwnerId());
		
		Requirement changeReq = reqview.getReqModel().getRequirement();


		changeReq.getAttachedFileId().add(attachment.getId());
		changeReq.getAttachedFileNames().add(attachment.getFileName());
		
		SaveRequirementController saveReq = new SaveRequirementController(reqview);
		
		view.attachmentSaveSuccess(attachment);
		view.repaint();
	}

}
