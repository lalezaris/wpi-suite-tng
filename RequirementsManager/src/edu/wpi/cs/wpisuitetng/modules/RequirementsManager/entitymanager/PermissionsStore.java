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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Stores the user's permission level in the database.
 * Adapted from DefectManager.
 *
 * @author Chris Dunkers
 *
 * @version Apr 1, 2013
 *
 */
public class PermissionsStore implements EntityManager<UserPermission> {
	Data db;
	ModelMapper updateMapper;

	/**
	 * Constructor for PermissionsStore.
	 * 
	 * @param data The data to be stored
	 */
	public PermissionsStore(Data data){
		db = data;
		updateMapper = new ModelMapper();
	}

	/**
	 * Takes a string that is the JSON-ified representation of RMPermission, and a session (project);
	 * returns the RMPermission in object form.
	 * Also puts the object in the DB indexable by ID.
	 *
	 * @param s A session (project)
	 * @param content The JSON-ified representation of RMPermission
	 * @return The RMPermission in object form
	 * @throws BadRequestException
	 * @throws ConflictException
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public UserPermission makeEntity(Session s, String content)
			throws WPISuiteException {
		final UserPermission newUserPermission = UserPermission.fromJSON(content);	//still need to get fromJSON working, then this will work

		newUserPermission.setId(Count() + 1);

		if(!db.save(newUserPermission, s.getProject())) {
			throw new WPISuiteException();
		}
		return newUserPermission;
	}

	/**
	 * Accesses a UserPermission by username;
	 * returns an array of all UserPermission's that have that username.
	 * 
	 * @param s A session (project)
	 * @param username The username of the UserPermission(s)
	 * @return An array of all UserPermission(s) with the given username
	 * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public UserPermission[] getEntity(Session s, String username) throws NotFoundException {
		final String stringUsername = username; 
		if(stringUsername.length() < 1) {
			throw new NotFoundException();
		}
		UserPermission[] userPermissions = null;
		try {
			userPermissions = db.retrieve(UserPermission.class, "username", stringUsername, s.getProject()).toArray(new UserPermission[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(userPermissions.length < 1 || userPermissions[0] == null) {
			throw new NotFoundException();
		}
		return userPermissions;
	}

	/**
	 * Returns an array of all user permissions stored in the DB.
	 * 
	 * @param s A session (project)
	 * @return An array of all user permissions stored in the DB
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public UserPermission[] getAll(Session s){
		return db.retrieveAll(new UserPermission(), s.getProject()).toArray(new UserPermission[0]);
	}

	/**
	 * Update the database with the given content (permission).
	 * 
	 * @param s A session (project)
	 * @param content The content to be passed in
	 * @return The updated UserPermission
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public UserPermission update(Session s, String content)
			throws WPISuiteException {
		//get requirement user wants to update
		UserPermission per = UserPermission.fromJSON(content);

		String line = per.getMessage();
		try {
			String fileName = s.getProject().getName() + "_PermissionsLog.txt";
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			out.println(line + System.getProperty("line.separator"));
			out.close();
			//Close the output stream
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//LOG THE CHANGE

		//get requirement from server
		List<Model> oldPerms = db.retrieve(UserPermission.class, "id", per.getId(), s.getProject());

		if(oldPerms.size() < 1 || oldPerms.get(0) == null) {
			throw new WPISuiteException("ID not found");
		} 

		UserPermission serverPer = (UserPermission) oldPerms.get(0);

		// copy values to old requirement and fill in our changeset appropriately
		updateMapper.map(per, serverPer);


		//apply the changes
		if(!db.save(serverPer, s.getProject())) {
			throw new WPISuiteException();
		}


		return serverPer;
	}

	/**
	 * Saves the given UserPermission into the database.
	 *
	 * @param s A session (project)
	 * @param model The UserPermission to be saved
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, UserPermission model){
		db.save(model, s.getProject());
	}

	/**
	 * This just tests to make sure you are accessing things that you should be.
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
	 * Removes a UserPermission from the DB based on ID.
	 * 
	 * @param s A session (project)
	 * @param username The username of the UserPermission to be removed
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String username) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, username)[0]) != null) ? true : false;
	}

	/**
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(edu.wpi.cs.wpisuitetng.Session, java.lang.String[])
	 */
	@Override
	public String advancedGet(Session s, String[] args){
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
		db.deleteAll(new UserPermission(), s.getProject());
	}

	/** 
	 * Returns the number of UserPermission(s).
	 * 
	 * @return The number of UserPermission(s)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() {
		return db.retrieveAll(new UserPermission()).size();
	}

	/**
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(edu.wpi.cs.wpisuitetng.Session, java.lang.String[], java.lang.String)
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content){
		return null;
	}

	/**
	 * @throws WPISuiteException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(edu.wpi.cs.wpisuitetng.Session, java.lang.String, java.lang.String)
	 */
	@Override
	public String advancedPost(Session s, String string, String content){
		return null;
	}
}