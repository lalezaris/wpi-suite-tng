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
 *  Michael Perrone
 *  Sam Abradi
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.io.File;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveAttachmentRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 *  Controls retrieving attachments from the database.
 *
 * @author Sam Abradi
 *
 * @version Apr 24, 2013
 *
 */
public class RetrieveAttachmentController {
	private File file;
	String s;
	
	/**
	 * Instantiates a new retrieve attachment controller.
	 *
	 * @param path the file path.
	 * @param f the file to retrieve
	 */
	public RetrieveAttachmentController(String path,File f){
		file = f;
		s = path;
	}

	/**
	 * Makes a network request to fetch a requirement from the database.
	 * 
	 */
	public void fetch(){
		int attachmentId = Integer.parseInt(s.substring(0,s.indexOf('\\')));
		
		
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/attachment/" + attachmentId, HttpMethod.GET);
		request.addObserver(new RetrieveAttachmentRequestObserver(this));
		request.send();
	}
	
	/**
	 * Gets the file. Called by the RetrieveAttachmentRequesetObserver.
	 *
	 * @return the file
	 */
	public File getFile(){
		return file;
	}

}
