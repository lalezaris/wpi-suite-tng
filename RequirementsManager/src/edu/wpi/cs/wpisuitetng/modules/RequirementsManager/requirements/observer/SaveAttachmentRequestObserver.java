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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveAttachmentController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An asynchronous update interface for receiving notifications
 * about SaveAttachmentRequest information as the SaveAttachmentRequest is constructed.
 */
public class SaveAttachmentRequestObserver implements RequestObserver {
	SaveAttachmentController controller;
	
	/**
	 * This method is called when information about an SaveAttachmentRequest
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param controller the calling controller, passed for callbacks
	 * 
	 * 
	 */
	public SaveAttachmentRequestObserver(SaveAttachmentController controller){
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {

		Request request = (Request) iReq;
		
		ResponseModel response = request.getResponse();
		TreeView.getInstance().refreshTree();
		if((response.getStatusCode() >= 200)&&(response.getStatusCode() < 300)){
			Attachment attachments = Attachment.fromJSON(response.getBody());
			
			controller.recievedData(attachments);
		}

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Error on:");
		System.out.println(iReq.getBody());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("network fail");
	}

}
