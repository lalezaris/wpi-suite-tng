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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Stores the user's permission level in the database
 * Adapted from DefectManager
 *
 * @author Chris Dunkers
 *
 * @version Apr 1, 2013
 *
 */
public class PermissionsStore implements EntityManager<UserPermission> {
	
Data db;
	

	public PermissionsStore(Data data){
	    db = data;
	}
	
	/**
	 * takes a string that is the JSON-ified representation of RMPermission, and a session (project)
	 * returns the RMPermission in object form
	 * also puts the object in the DB indexable by ID
	 *
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public UserPermission makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final UserPermission newUserPermission = UserPermission.fromJSON(content);	//still need to get fromJSON working, then this will work
		
		// TODO: increment properly, ensure uniqueness using ID generator.  This is a gross hack.
		if(!db.save(newUserPermission, s.getProject())) {
			throw new WPISuiteException();
		}
		return newUserPermission;
	}

	/*
	 * accesses a UserPermission by username
	 * returns an array of all UserPermission's that have that username
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public UserPermission[] getEntity(Session s, String username) throws NotFoundException {
		final int intId = Integer.parseInt(username);
		if(intId < 1) {
			throw new NotFoundException();
		}
		UserPermission[] userPermissions = null;
		try {
			userPermissions = db.retrieve(UserPermission.class, "id", intId, s.getProject()).toArray(new UserPermission[0]);
		} catch (WPISuiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(userPermissions.length < 1 || userPermissions[0] == null) {
			throw new NotFoundException();
		}
		return userPermissions;
	}

	/*
	 * literally returns an array of all user permissions in the DB
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public UserPermission[] getAll(Session s) throws WPISuiteException {
		return db.retrieveAll(new UserPermission(), s.getProject()).toArray(new UserPermission[0]);
	}

	/* Not necessary for iteration 1 but may be needed in the future
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public UserPermission update(Session s, String content)
			throws WPISuiteException {
		return null;
	}

	/*
	 * saves the given UserPermission into the database
	 *
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, UserPermission model) throws WPISuiteException {
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
	 * Removes a UserPermission from the DB based on ID
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		//TODO: check this function
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
		//TODO: check this function
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new Iteration(), s.getProject());
		
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() {
		// TODO: there must be a faster way to do this with db4o
		// note that this is not project-specific - ids are unique across projects
		return db.retrieveAll(new Iteration()).size();
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
