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

public class TaskStore implements EntityManager<Task> {
	Data db;
	ModelMapper updateMapper;
	
	public TaskStore(Data data){
		db = data;
		updateMapper = new ModelMapper();
	}

	@Override
	public Task makeEntity(Session s, String content)
			throws WPISuiteException {
		final Task newTask = Task.fromJSON(content);	//still need to get fromJSON working, then this will work
		
		// TODO: increment properly, ensure uniqueness using ID generator.  This is a gross hack.
		newTask.setId(Count() + 1);
		
		//		newRequirement.setIteration(Iteration.getBacklog());

		if(!db.save(newTask, s.getProject())) {
			throw new WPISuiteException();
		}
		

		
		return newTask;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tasks.length < 1 || tasks[0] == null) {
			throw new NotFoundException();
		}
		return tasks;
	}

	@Override
	public Task[] getAll(Session s){
		return db.retrieveAll(new Task(), s.getProject()).toArray(new Task[0]);
	}

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

	@Override
	public void save(Session s, Task model){
		db.save(model, s.getProject());
		
	}

	@Override
	public boolean deleteEntity(Session s, String id){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String advancedGet(Session s, String[] args){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s){
		// TODO Auto-generated method stub
		
	}

	@Override
	public int Count(){
		return db.retrieveAll(new Task()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content){
		// TODO Auto-generated method stub
		return null;
	}

}