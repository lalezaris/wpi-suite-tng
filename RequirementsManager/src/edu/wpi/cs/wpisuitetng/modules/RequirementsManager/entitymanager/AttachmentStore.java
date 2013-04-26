package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import java.io.File;
import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

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
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Attachment makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final Attachment newAttachment = Attachment.fromJSON(content);	//still need to get fromJSON working, then this will work
		// TODO: increment properly, ensure uniqueness using ID generator.  This is a gross hack.
		newAttachment.setId(Count() + 1);
		
		System.out.println("FILE NAME " +newAttachment.getFileName() + "ownerid " + newAttachment.getOwnerId());
		
		List<Model> oldRequirements = db.retrieve(Requirement.class, "id", newAttachment.getOwnerId(), s.getProject());
		if(oldRequirements.size() < 1 || oldRequirements.get(0) == null) {
			throw new WPISuiteException("ID not found");
		}
		Requirement serverReq = (Requirement) oldRequirements.get(0);
		serverReq.getAttachedFileId().add(newAttachment.getId());
		serverReq.getAttachedFileName().add(newAttachment.getFileName());
		
		
		
		db.save(serverReq.getAttachedFileId());
		System.out.println("this should be saved: " + serverReq.getAttachedFileId().get(0));
		db.save(serverReq.getAttachedFileName());
		if(!db.save(serverReq, s.getProject())) {
			System.out.println("there was an error while updating a requirement with the new attachment");
			throw new WPISuiteException();
		}
		
//		db.save(serverReq.getAttachedFileId());
//		db.save(serverReq.getAttachedFileName());
		
		if(!db.save(newAttachment, s.getProject())) {
			throw new WPISuiteException();
		}
		return newAttachment;
	}

	/**
	 * Accesses a Attachment by ID;
	 * returns an array of all Attachment that fit this ID
	 * 
	 * @param s A session (project)
	 * @param id The ID of the Attachment(s)
	 * @return An array of all Attachment(s) with the given username
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Attachment[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		Attachment[] attachments = null;
		try {
			attachments = db.retrieve(Attachment.class, "id", intId, s.getProject()).toArray(new Attachment[0]);
		} catch (WPISuiteException e) {
			// TODO Auto-generated catch block
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
		
		//TODO modify this function to use validators and make sure not to update if no 
		//changes have been made.
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

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int Count() throws WPISuiteException {
		// so this might run into huge issues with large files, if it does, we need to change the way everything does indexing into the DB, which will be /fun/
		return db.retrieveAll(new Attachment()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

}