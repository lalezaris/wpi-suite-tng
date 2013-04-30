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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The Class AttachmentStore stores attachment data.
 */
public class AttachmentStore implements EntityManager<Attachment> {
	Data db;
	ModelMapper updateMapper;
	
	
	/**
	 * Class constructor to store attachment data.
	 * 
	 * @param data the Data instance to use
	 */
	public AttachmentStore(Data data){
		db = data;
		updateMapper = new ModelMapper();
	}
	
	
	/**
	 * Takes a string that is the JSON-ified representation of Attachment, and a session (project);
	 * returns the attachment in object form.
	 * Also puts the object in the DB indexable by ID.
	 *
	 * @param s A session (project)
	 * @param content The JSON-ified representation of Attachment
	 * @return The attachment in object form
	 * @throws BadRequestException the bad request exception
	 * @throws ConflictException the conflict exception
	 * @throws WPISuiteException the wPI suite exception
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Attachment makeEntity(Session s, String content)
			throws WPISuiteException {
		final Attachment newAttachment = Attachment.fromJSON(content);	//still need to get fromJSON working, then this will work
		newAttachment.setId(Count() + 1);
				
		List<Model> oldRequirements = db.retrieve(Requirement.class, "id", newAttachment.getOwnerId(), s.getProject());
		if(oldRequirements.size() < 1 || oldRequirements.get(0) == null) {
			throw new WPISuiteException("ID not found");
		}
		Requirement serverReq = (Requirement) oldRequirements.get(0);
		
		ArrayList<Integer> fileId = new ArrayList<Integer>();
		fileId.add(newAttachment.getId());
		serverReq.setAttachedFileId(fileId);
		
		
		ArrayList<String> fileNames = serverReq.getAttachedFileNames();
		fileNames.add(newAttachment.getFileName());
		serverReq.setAttachedFileName(fileNames);
		
		
		HistoricalChange HChange = new HistoricalChange(new Date(), Count() + 2, serverReq.getId(), (User) db.retrieve(User.class, "username", s.getUsername()).get(0));
		HChange.setChange("<p>added file: " + newAttachment.getFileName() + "</p>");
		serverReq.addHistoricalChange(HChange);
		
		if(!db.save(serverReq, s.getProject())) {
			System.out.println("There was an error while updating the requirement with the new attachment.");
			throw new WPISuiteException();
		}
		
		
		if(!db.save(newAttachment, s.getProject())) {
			throw new WPISuiteException();
		}
		return newAttachment;
	}

	/**
	 * Accesses a Attachment by ID;
	 * returns an array of all Attachment that fit this ID.
	 *
	 * @param s A session (project)
	 * @param id The ID of the Attachment(s)
	 * @return An array of all Attachment(s) with the given username
	 * @throws NotFoundException the not found exception
	 * @throws WPISuiteException the wPI suite exception
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Attachment[] getEntity(Session s, String id)
			throws NotFoundException{
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		Attachment[] attachments = null;
		try {
			attachments = db.retrieve(Attachment.class, "id", intId, s.getProject()).toArray(new Attachment[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(attachments.length < 1 || attachments[0] == null) {
			throw new NotFoundException();
		}
		return attachments;
	}

	/**
	 * Returns an array of all iterations stored in the DB.
	 * 
	 * @param s A session (project)
	 * @return An array of all iterations stored in the DB
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Attachment[] getAll(Session s){
		return db.retrieveAll(new Attachment(), s.getProject()).toArray(new Attachment[0]);
	}

	/**
	 * Updates the given model with the content passed in.
	 *
	 * @param s A session (project)
	 * @param content The content to be passed in
	 * @return The updated Attachment
	 * @throws WPISuiteException the wPI suite exception
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Attachment update(Session s, String content)
			throws WPISuiteException {
		//get iteration user wants to update
		Iteration it = Iteration.fromJSON(content);

		//get requirement from server
		List<Model> oldAttachments = db.retrieve(Attachment.class, "id", it.getId(), s.getProject());
		if(oldAttachments.size() < 1 || oldAttachments.get(0) == null) {
			throw new WPISuiteException("ID not found");
		}
		Attachment serverAt = (Attachment) oldAttachments.get(0);
		
		// copy values to old Attachment and fill in our changeset appropriately
		updateMapper.map(it, serverAt);

		
		//apply the changes
		if(!db.save(serverAt, s.getProject())) {
			throw new WPISuiteException();
		}
		
		return serverAt;
	}

	/**
	 * Saves the given attachment into the database.
	 *
	 * @param s A session (project)
	 * @param model The Attachment to be saved
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Attachment model) {
		db.save(model, s.getProject());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id){
		return false;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(edu.wpi.cs.wpisuitetng.Session, java.lang.String[])
	 */
	@Override
	public String advancedGet(Session s, String[] args){
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public void deleteAll(Session s){
		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count(){
		// so this might run into huge issues with large files, if it does, we need to change the way everything does indexing into the DB, which will be /fun/
		return db.retrieveAll(new Attachment()).size();
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(edu.wpi.cs.wpisuitetng.Session, java.lang.String[], java.lang.String)
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content){
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(edu.wpi.cs.wpisuitetng.Session, java.lang.String, java.lang.String)
	 */
	@Override
	public String advancedPost(Session s, String string, String content){
		return null;
	}

}
