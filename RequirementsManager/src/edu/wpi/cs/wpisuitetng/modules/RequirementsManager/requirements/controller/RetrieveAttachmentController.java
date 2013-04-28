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
 *  "Michael Perrone"
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.io.File;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveAttachmentRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * 
 *
 * @author "Sam Abradi"
 *
 * @version Apr 24, 2013
 *
 */
public class RetrieveAttachmentController {
	private File file;
	String s;
	
	public RetrieveAttachmentController(String path,File f){
		file = f;
		s = path;
	}

	public void fetch(){
		int attachmentId = Integer.parseInt(s.substring(0,s.indexOf('\\')));
		
		
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/attachment/" + attachmentId, HttpMethod.GET);
		request.addObserver(new RetrieveAttachmentRequestObserver(this));
		request.send();
	}
	public File getFile(){
		return file;
	}

}
