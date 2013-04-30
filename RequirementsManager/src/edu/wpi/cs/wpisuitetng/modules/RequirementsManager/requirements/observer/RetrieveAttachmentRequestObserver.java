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

import java.io.File;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAttachmentController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;


/**
 * An asynchronous update interface for receiving notifications
 * about RetrieveAttachmentRequest information as the RetrieveAttachmentRequest is constructed.
 * @author Sam Abradi
 */
public class RetrieveAttachmentRequestObserver implements RequestObserver  {
	RetrieveAttachmentController controller;
	
	/**
	 * This method is called when information about an RetrieveAttachmentRequest
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param controller the controller that called this, so that it can make call backs
	 */
	public RetrieveAttachmentRequestObserver(RetrieveAttachmentController controller){
		this.controller = controller;
	}

	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
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

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {

		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {

		
	}
	
	

}
