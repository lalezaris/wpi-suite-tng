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
 * snake men of old.
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake.SnakeModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Stores the snake highscore in the database.
 * Adapted from defect tracker.
 * 
 * @author SNAKE MAN!!!
 *
 */
public class SnakeStore implements EntityManager<SnakeModel> {
	Data db;
	ModelMapper updateMapper;

	/**
	 * Constructor for IterationStore.
	 * 
	 * @param data The data to be stored
	 */
	public SnakeStore(Data data){
	    db = data;
	    updateMapper = new ModelMapper(); 
	}
	
	/**
	 * Takes a string that is the JSON-ified representation of SnakeModel, and a session (project);
	 * returns the model in object form.
	 * Also puts the object in the DB indexable by ID.
	 * 
	 * @param s A session (project)
	 * @param content The JSON-ified representation of model
	 * @return The model in object form
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public SnakeModel makeEntity(Session s, String content)
			throws WPISuiteException {
		final SnakeModel newModel = SnakeModel.fromJSON(content);
		newModel.setId(1);
		
		List<Model> models = db.retrieve(SnakeModel.class, "id", newModel.getId(), s.getProject());
		if(models.size() < 1 || models.get(0) == null) {
			//NO HIGHSCORE EXISTS YET
			if(!db.save(newModel, s.getProject())) {
				throw new WPISuiteException();
			}
			return newModel;
			
		} else{
			//WE HAVE A HIGH SCORE.
			
			SnakeModel champ = (SnakeModel)models.get(0);
			
			if (newModel.getScore() > champ.getScore()){
				champ.setScore(newModel.getScore());
				champ.setName(newModel.getName());
			}
			
			if(!db.save(champ, s.getProject())) {
				throw new WPISuiteException();
			}
			
			return champ;
		}
	}

	/**
	 * Accesses a Iteration by iteration ID;
	 * returns an array of all iterations that fit this ID
	 * 
	 * @param s A session (project)
	 * @param id The ID of the Iteration(s)
	 * @return An array of all Iteration(s) with the given username
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public SnakeModel[] getEntity(Session s, String id) throws NotFoundException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		SnakeModel[] models = null;
		try {
			models = db.retrieve(SnakeModel.class, "id", intId, s.getProject()).toArray(new SnakeModel[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(models.length < 1 || models[0] == null) {
			throw new NotFoundException();
		}
		return models;
	}

	/**
	 * Returns an array of all iterations stored in the DB.
	 * 
	 * @param s A session (project)
	 * @return An array of all iterations stored in the DB
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public SnakeModel[] getAll(Session s){
		return db.retrieveAll(new SnakeModel(), s.getProject()).toArray(new SnakeModel[0]);
	}

	/**
	 * Updates the given model with the content passed in.
	 * 
	 * @param s A session (project)
	 * @param content The content to be passed in
	 * @return The updated Iteration
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public SnakeModel update(Session s, String content)
			throws WPISuiteException {
		//get iteration user wants to update
		SnakeModel it = SnakeModel.fromJSON(content);

		//get requirement from server
		List<Model> oldModels = db.retrieve(SnakeModel.class, "id", it.getId(), s.getProject());
		if(oldModels.size() < 1 || oldModels.get(0) == null) {
			throw new WPISuiteException("ID not found");
		}
		SnakeModel serverIt = (SnakeModel) oldModels.get(0);
		
		// copy values to old iteration and fill in our changeset appropriately
		updateMapper.map(it, serverIt);

		//apply the changes
		if(!db.save(serverIt, s.getProject())) {
			throw new WPISuiteException();
		}
		
		return serverIt;
	}

	/**
	 * Saves the given iteration into the database.
	 *
	 * @param s A session (project)
	 * @param model The Iteration to be saved
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, SnakeModel model){
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
	 * Removes an iteration from the DB based on ID.
	 * 
	 * @param s A session (project)
	 * @param id The id of the Iteration to be removed
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
		db.deleteAll(new SnakeModel(), s.getProject());
		
	}

	/** 
	 * Returns the number of Iteration(s).
	 * 
	 * @return The number of Iteration(s)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() {
		// note that this is not project-specific - ids are unique across projects
		return db.retrieveAll(new SnakeModel()).size();
	}
	
	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(edu.wpi.cs.wpisuitetng.Session, java.lang.String[], java.lang.String)
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content){
		return null;
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(edu.wpi.cs.wpisuitetng.Session, java.lang.String, java.lang.String)
	 */
	@Override
	public String advancedPost(Session s, String string, String content){
		return null;
	}
}