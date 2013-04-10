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
 *  Michael French
 *  Sam Abradi
 *  Evan Polekoff
 *  Tianyu Li
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Stores the Requirements in the database
 * Adapted from DefectManager
 * 
 * @author Michael French
 *
 * @version Mar 20, 2013
 *
 */
public class RequirementStore implements EntityManager<Requirement>{
	Data db;
	ModelMapper updateMapper;
	
	/**
	 * Class constructor to store requirement data
	 * 
	 * @param data the Data instance to use
	 */
	public RequirementStore(Data data){
		db = data;
		updateMapper = new ModelMapper();
	}
	
	/**
	 * Takes a string that is the JSON-ified representation of Requirement, and a session (project);
	 * return the requirement in object form;
	 * Also puts the object in the DB indexable by ID.
	 * 
	 * @param s A session (project)
	 * @param content The JSON-ified representation of Requirement
	 * @return The requirement in object form
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Requirement makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		int parent;
		final Requirement newRequirement = Requirement.fromJSON(content);	//still need to get fromJSON working, then this will work
		
		// TODO: increment properly, ensure uniqueness using ID generator.  This is a gross hack.
		newRequirement.setId(Count() + 1);
		
		HistoricalChange HChange = new HistoricalChange(new Date(), newRequirement.getId(), newRequirement.getId(), (User) db.retrieve(User.class, "username", s.getUsername()).get(0));
		HChange.updateOnCreate(newRequirement);
		newRequirement.addHistoricalChange(HChange);
		
		//		newRequirement.setIteration(Iteration.getBacklog());
		System.out.println("THIS IS THEREQUIREMENT" + newRequirement.toJSON());
		if(!db.save(newRequirement, s.getProject())) {
			throw new WPISuiteException();
		}
		
		parent = newRequirement.getParentRequirementId();
		if(parent!=-1){
			List<Model> parentRequirements = db.retrieve(Requirement.class, "id", parent, s.getProject());
			if(parentRequirements.size() < 1 || parentRequirements.get(0) == null) {
				throw new WPISuiteException("ID not found");
			}
			Requirement parentReq = (Requirement) parentRequirements.get(0);
			parentReq.getChildRequirementIds().add(newRequirement.getId());
			
			if(!db.save(parentReq, s.getProject())) {
				throw new WPISuiteException();
			}
		}
		
		return newRequirement;
	}

	/**
	 * Accesses a requirement by ID from requirement;
	 * returns an array of all requirements that fit this ID.
	 * 
	 * @param s A session (project)
	 * @param id The ID of the Requirement(s)
	 * @return An array of all Requirement(s) with the given username
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Requirement[] getEntity(Session s, String id) throws NotFoundException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		Requirement[] requirements = null;
		try {
			requirements = db.retrieve(Requirement.class, "id", intId, s.getProject()).toArray(new Requirement[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(requirements.length < 1 || requirements[0] == null) {
			throw new NotFoundException();
		}
		return requirements;
	}

	/**
	 * Returns an array of all requirements stored in the DB.
	 * 
	 * @param s A session (project)
	 * @return An array of all requirements stored in the DB
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Requirement[] getAll(Session s) throws WPISuiteException {
		return db.retrieveAll(new Requirement("FU","UU"), s.getProject()).toArray(new Requirement[0]);
	}

	/**
	 * Updates the given model with the content passed in.
	 * 
	 * @param s A session (project)
	 * @param content The content to be passed in
	 * @return The updated Requirement
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Requirement update(Session s, String content)
			throws WPISuiteException {
		
		//get requirement user wants to update
		Requirement req = Requirement.fromJSON(content);
		
		
		//get requirement from server
		List<Model> oldRequirements = db.retrieve(Requirement.class, "id", req.getId(), s.getProject());
		if(oldRequirements.size() < 1 || oldRequirements.get(0) == null) {
			throw new WPISuiteException("ID not found");
		}
		Requirement serverReq = (Requirement) oldRequirements.get(0);
		
		HistoricalChange HChange = new HistoricalChange(new Date(), req.getId(), serverReq.getId(), (User) db.retrieve(User.class, "username", s.getUsername()).get(0));
		
		Date originalLastModified = serverReq.getLastModifiedDate();
		
		if(serverReq.getIteration()==null)System.out.print("++++++ null serverReq iteration\n");
		if(req.getIteration()==null)System.out.print("===== null req.iteration req\n");
		
		HChange.updateChangeFromDiff(serverReq,req, this);
		
		// copy values to old requirement and fill in our changeset appropriately
		updateMapper.map(req, serverReq);

		serverReq.setIterationId(req.getIterationId());
		
		if (!HChange.getChange().equals("")){
			serverReq.addHistoricalChange(HChange);
		}
		
		//update the Notes List
		serverReq.updateNotes(req.getNotes());
	
		//apply the changes
		if(!db.save(serverReq, s.getProject()) || !db.save(serverReq.getHistory())) {
			throw new WPISuiteException();
		}
		
		//TODO modify this function to use validators and make sure not to update if no 
		//changes have been made.
		
		return serverReq;
	}

	/**
	 * Saves the given requirement into the database.
	 *
	 * @param s A session (project)
	 * @param model The Requirement to be saved
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Requirement model) throws WPISuiteException {
		db.save(model, s.getProject());
	}

	/**
	 * Tests to make sure you are accessing things that you should be.
	 * 
	 * @param session The current session
	 * @param role The role that is being ensured
	 * @throws WPISuiteException
	 */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}
	
	/**
	 * Removes a requirement from the DB based on ID.
	 * 
	 * @param s A session (project)
	 * @param id The id of the Requirement to be removed
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(edu.wpi.cs.wpisuitetng.Session, java.lang.String[])
	 */
	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		return null;
	}

	/**
	 * Deletes everything in DB.
	 * 
	 * @param s A session (project)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new Requirement("",""), s.getProject());
		
	}

	/** 
	 * Returns the number of Requirement(s).
	 * 
	 * @return The number of Requirement(s)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() {
		// TODO: there must be a faster way to do this with db4o
		// note that this is not project-specific - ids are unique across projects
		return db.retrieveAll(new Requirement("foo", "foo")).size();
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(edu.wpi.cs.wpisuitetng.Session, java.lang.String[], java.lang.String)
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		return null;
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(edu.wpi.cs.wpisuitetng.Session, java.lang.String, java.lang.String)
	 */
	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		return null;
	}
}
