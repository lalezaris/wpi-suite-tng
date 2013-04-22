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
 *  Evan Polekoff
 *  Sam Abradi
 *  Ned Shelton
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.TaskStatus;

/**
 * Model for Tasks
 * @author Evan Polekoff
 * @author Sam Abradi
 *
 * @version April 18, 2013
 *
 */
public class Task extends AbstractModel{
	
	int id;
	String name;
	String description;
	String assigneeName;
	int effort;
	TaskStatus status;
	
	//make a dummy task for db4o
	public Task(){}
	public Task(String name, String description, String assigneeName, int effort){
		this.name = name;
		this.description = description;
		this.assigneeName = assigneeName;
		this.effort = effort;
		this.status = TaskStatus.BLANK;
	}
	
	
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Task.class);
		return json;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}



	/** Convert a Json string to a Task.
	 * 
	 * @param json Json string to be parsed
	 * @return The corresponding Task
	 */
	public static Task fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Task.class);
	}	


	/**setter for id
	 * @param i The ID
	 */
	public void setId(int i) {
		this.id = i;
		
	}
	/**Gett for id
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**Getter for the name.
	 * @return the name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the assigneeName
	 */
	public String getAssigneeName() {
		return assigneeName;
	}
	/**
	 * @param assigneeName the assigneeName to set
	 */
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	/**
	 * @return the effort
	 */
	public int getEffort() {
		return effort;
	}
	/**
	 * @param effort the effort to set
	 */
	public void setEffort(int effort) {
		this.effort = effort;
	}
	/**
	 * @return the status
	 */
	public TaskStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Add dependencies necessary for Gson to interact with this class.
	 * 
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
	}
	public static Task[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Task[].class);
	}

}
