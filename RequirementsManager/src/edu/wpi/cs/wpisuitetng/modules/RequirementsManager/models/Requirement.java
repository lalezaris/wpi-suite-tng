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
 *  Tushar Narayan
 *  Lauren Kahn
 *  Ned Shelton
 *  Michael Perrone
 *  Xia Li
 *  Michael French
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority.*;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus.*;


/**
 * Class for storing a Requirement
 * Adapted from Defect in project DefectManager
 * 
 * @author Tushar Narayan
 * Edited: Michael French
 * 			EDIT: Made class extend AbstractModel
 * 			REASON: This class MUST extent AbstractModel in order 
 * 			to be compatible with the DataStore class
 * @version Mar 17, 2013
 *
 */

public class Requirement extends AbstractModel{
	private int releaseNumber;
	private RequirementStatus status;
	private RequirementPriority priority;
	private String title;
	private String description;
	private String estimate; //should probably make this an int at some later date
	private String actualEffort;
	private ArrayList<Requirement> subRequirements;
	private Date creationDate, lastModifiedDate;
	private int id;
	private User creator, assignee; //putting this in to keep track of user
	
	/**
	 * Constructs a new Requirement with title and description
	 *  
	 * @param name
	 * @param description
	 */
	public Requirement(String title, String description){
		this();
		this.title = title;
		this.description = description;
	}
	
	/**
	 * Constructs a new Requirement with id, title and description
	 *  
	 * @param name
	 * @param description
	 */
	public Requirement(int id, String title, String description){
		this();
		this.id = id;
		this.title = title;
		this.description = description;
	}
	
	
	/**
	 * Basic constructor with default arguments
	 * 
	 */
	public Requirement(){
		this.releaseNumber = 0; //default release number is 0. This will eventually be set from the project's releaseNumber.
		this.status = NEW; //default status is New
		this.priority = MEDIUM; //default priority is medium
		this.title = ""; //name is required
		this.description = ""; //description is required
		this.estimate = ""; //default estimate set to an empty string
		this.actualEffort = ""; //default actualEffort set to an empty string
		this.subRequirements = new ArrayList<Requirement>();
		this.creationDate = new Date();
		this.lastModifiedDate = creationDate;
		this.id = -1; //default id is -1
		this.creator = new User("", "", "", -1);
	}

	/**
	 * Gets the releaseNumber
	 * @return the releaseNumber
	 */
	public int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Sets the releaseNumber
	 * @param releaseNumber: sets the releaseNumber 
	 */
	public void setReleaseNumber(int releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	/**
	 * Gets the status
	 * @return the status
	 */
	public RequirementStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status
	 * @param status: sets the status 
	 */
	public void setStatus(RequirementStatus status) {
		this.status = status;
	}

	/**
	 * Gets the priority
	 * @return the priority
	 */
	public RequirementPriority getPriority() {
		return priority;
	}

	/**
	 * Sets the priority
	 * @param priority: sets the priority 
	 */
	public void setPriority(RequirementPriority priority) {
		this.priority = priority;
	}

	/**
	 * Gets the name
	 * @return the name
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the name
	 * @param name: sets the name 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * @param description: sets the description 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the estimate
	 * @return the estimate
	 */
	public String getEstimate() {
		return estimate;
	}

	/**
	 * Sets the estimate
	 * @param estimate: sets the estimate 
	 */
	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}

	/**
	 * Gets the actualEffort
	 * @return the actualEffort
	 */
	public String  getActualEffort() {
		return actualEffort;
	}

	/**
	 * Sets the actualEffort
	 * @param actualEffort: sets the actualEffort 
	 */
	public void setActualEffort(String actualEffort) {
		this.actualEffort = actualEffort;
	}

	/**
	 * Gets the subRequirements
	 * @return the subRequirements
	 */
	public ArrayList<Requirement> getSubRequirements() {
		return subRequirements;
	}

	/**
	 * Sets the subRequirements
	 * @param subRequirements: sets the subRequirements 
	 */
	public void setSubRequirements(ArrayList<Requirement> subRequirements) {
		this.subRequirements = subRequirements;
	}
	
	/**
	 * Gets the creation date
	 * @return the Date this Requirement was created on
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * sets the creation date
	 * @param creationDate the Date this Requirement was created on
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * gets the modified date
	 * @return the Date this Requirement was last modified on
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * sets the modified list
	 * @param lastModifiedDate the Date this Requirement was last modified on
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	/**
	 * Sets the id
	 * @param id: sets the id 
	 */
	public void setId(int id){
		this.id =id;
	}
	
	/**
	 * gets the id
	 * @return id number 
	 */
	public int getId(){
		return id;
	}
	
	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Converts this Requirement to a JSON string
	 * @return a string in JSON representing this Requirement
	 */
	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Requirement.class);
		return json;
	}	
	
	/**
	 * Converts the given list of Requirements to a JSON string
	 * @param dlist a list of Requirement
	 * @return a string in JSON representing the list of Requirements
	 */
	public static String toJSON(Requirement[] dlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(dlist, Requirement.class);
		return json;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toJSON();
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param json Json string to parse containing Requirement
	 * @return The Requirement given by json
	 */
	public static Requirement fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement.class);
	}
	
	/**
	 * @param json Json string to parse containing Requirement array
	 * @return The Requirement array given by json
	 */
	public static Requirement[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement[].class);
	}
	
	
	/**
	 * Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {}

}
