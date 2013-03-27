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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.*;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.Defect;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.validators.ValidationIssue;

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
	/* The commented out part of the code is not needed for iteration 1 but may be needed in the future
	 * 
	 * takes a string that is the JSON-ified representation of Requirement, and a session (project)
	 * returns the requirement in object form
	 * also puts the object in the DB indexable by ID
	 *
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Requirement makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final Requirement newRequirement = Requirement.fromJSON(content);	//still need to get fromJSON working, then this will work
		
		// TODO: increment properly, ensure uniqueness using ID generator.  This is a gross hack.
		newRequirement.setId(Count() + 1);
		/*
		List<ValidationIssue> issues = validator.validate(s, newRequirement, Mode.CREATE);
		if(issues.size() > 0) {
			// TODO: pass errors to client through exception
			for (ValidationIssue issue : issues) {
				System.out.println("Validation issue: " + issue.getMessage());
			}
			throw new BadRequestException();
		}
*/
		if(!db.save(newRequirement, s.getProject())) {
			throw new WPISuiteException();
		}
		return newRequirement;
	}

	/*
	 * accesses a requirement by ID from requirement
	 * returns an array of all requirements that fit this ID
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(requirements.length < 1 || requirements[0] == null) {
			throw new NotFoundException();
		}
		return requirements;
	}

	/*
	 * literally returns an array of all requirements in the DB
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Requirement[] getAll(Session s) throws WPISuiteException {
		return db.retrieveAll(new Requirement("FU","UU"), s.getProject()).toArray(new Requirement[0]);
	}

	/* Not necessary for iteration 1 but may be needed in the future
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
		
		Date originalLastModified = serverReq.getLastModifiedDate();
		
		// copy values to old defect and fill in our changeset appropriately
		updateMapper.map(req, serverReq);
		
		//apply the changes
		if(!db.save(serverReq, s.getProject())) {
			throw new WPISuiteException();
		}
		
		//TODO modify this function to use validators and make sure not to update if no 
		//changes have been made.
		
		return serverReq;
	}

	/*
	 * saves the given requirement into the database
	 *
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Requirement model) throws WPISuiteException {
		db.save(model, s.getProject());
	}

	/*
	 * This just tests to make sure you are accessing things that you should be
	 * @param session the current session
	 * @param role the role that is being ensured
	 * @throws WPISuiteException
	 */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}
	
	/*
	 * Removes a requirement from the DB based on ID
	 * 
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Deletes all the things in DB
	 *
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new Requirement("",""), s.getProject());
		
	}

	/* 
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
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(edu.wpi.cs.wpisuitetng.Session, java.lang.String, java.lang.String)
	 */
	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}


}
