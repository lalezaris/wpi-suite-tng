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
 *  Sam Abradi
 *  Evan Polekoff
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;

/**
 * Stores the task in the database
 *
 * @author Evan Polekoff
 *
 * @version Apr 20, 2013
 *
 */
public class TaskStore implements EntityManager<Task> {
	Data db;
	ModelMapper updateMapper;

	/**
	 * Constructor for TaskStore.
	 * 
	 * @param data The data to be stored
	 */
	public TaskStore(Data data){
		db = data;
		updateMapper = new ModelMapper();
	}

	/**
	 * Takes a string that is the JSON-ified representation of Task, and a session (project);
	 * returns the task in object form.
	 * Also puts the object in the DB indexable by ID.
	 * 
	 * @param s A session (project)
	 * @param content The JSON-ified representation of Task
	 * @return The task in object form
	 * @throws BadRequestException
	 * @throws ConflictException
	 * @throws WPISuiteException 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Task makeEntity(Session s, String content)
			throws WPISuiteException {
		final Task newTask = Task.fromJSON(content);	//still need to get fromJSON working, then this will work

		// TODO: increment properly, ensure uniqueness using ID generator.  This is a gross hack.
		newTask.setId(Count() + 1);

		if(!db.save(newTask, s.getProject())) {
			throw new WPISuiteException();
		}



		return newTask;
	}

	/**
	 * Accesses a Task by task ID;
	 * returns an array of all tasks that fit this ID
	 * 
	 * @param s A session (project)
	 * @param id The ID of the Task(s)
	 * @return An array of all Task(s) with the given username
	 * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Task[] getEntity(Session s, String id) throws NotFoundException{
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		Task[] tasks = null;
		try {
			tasks = db.retrieve(Task.class, "id", intId, s.getProject()).toArray(new Task[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(tasks.length < 1 || tasks[0] == null) {
			throw new NotFoundException();
		}
		return tasks;
	}

	/**
	 * Returns an array of all tasks stored in the DB.
	 * 
	 * @param s A session (project)
	 * @return An array of all tasks stored in the DB
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Task[] getAll(Session s){
		return db.retrieveAll(new Task(), s.getProject()).toArray(new Task[0]);
	}

	/**
	 * Updates the given model with the content passed in.
	 * 
	 * @param s A session (project)
	 * @param content The content to be passed in
	 * @return The updated Task
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Task update(Session s, String content) throws WPISuiteException {
		Task it = Task.fromJSON(content);

		//get requirement from server
		List<Model> oldTasks = db.retrieve(Task.class, "id", it.getId(), s.getProject());
		if(oldTasks.size() < 1 || oldTasks.get(0) == null) {
			throw new WPISuiteException("ID not found");
		}
		Task serverTask = (Task) oldTasks.get(0);

		// copy values to old iteration and fill in our changeset appropriately
		updateMapper.map(it, serverTask);

		//serverIt.setIterationId(it.getId());

		//apply the changes
		if(!db.save(serverTask, s.getProject())) {
			throw new WPISuiteException();
		}

		//TODO modify this function to use validators and make sure not to update if no 
		//changes have been made.
		return serverTask;
	}

	/**
	 * Saves the given task into the database.
	 *
	 * @param s A session (project)
	 * @param model The Task to be saved
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Task model){
		db.save(model, s.getProject());

	}

	/**
	 * Removes an task from the DB based on ID.
	 * 
	 * @param s A session (project)
	 * @param id The id of the Task to be removed
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id){
		return false;
	}

	/**
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
	public void deleteAll(Session s){
	}

	/**
	 * Returns the number of Task(s).
	 * 
	 * @return The number of Tasks(s)
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count(){
		return db.retrieveAll(new Task()).size();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(edu.wpi.cs.wpisuitetng.Session, java.lang.String[], java.lang.String)
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content){
		return null;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(edu.wpi.cs.wpisuitetng.Session, java.lang.String, java.lang.String)
	 */
	@Override
	public String advancedPost(Session s, String string, String content){
		return null;
	}

}
