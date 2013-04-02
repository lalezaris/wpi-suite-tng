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
 *  Evan Polekoff
 *  Ned Shelton
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority.MEDIUM;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus.NEW;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


/**
 * Class for storing a Requirement
 * Adapted from Defect in project DefectManager
 * 
 * @author Tushar Narayan
 * 
 *  Mar 17, 2013
 * 
 * @edited Michael French
 * added Notes functionality
 * @version Mar 23, 2013
 *
 */
public class Requirement extends AbstractModel{
	private String title;
	private String releaseNumber;
	private int iterationId; //TODO: refactor to improve implementation
	private Iteration iteration;
	private String description;
	private RequirementStatus status;
	private RequirementPriority priority;
	private int estimateEffort; 
	private int actualEffort;
	private Date creationDate, lastModifiedDate;
	private int parentRequirementId;
	private ArrayList<Requirement> subRequirements;
	private int id;
	private User creator, assignee; //putting this in to keep track of user
	private ArrayList<Note> notes; //the list of notes on this requirement
	
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
		this.notes = new ArrayList<Note>();
	}
	
	/**
	 * Constructs a new Requirement with id, title, description and creator
	 *  
	 * @param name
	 * @param description
	 */
	public Requirement(int id, String title, String description, User creator){
		this();
		this.id = id;
		this.title = title;
		this.description = description;
		this.creator = creator;
		this.notes = new ArrayList<Note>();
	}
	
	/**
	 * Constructs a new Requirement with id, title, description and creator, and ArrayList of notes
	 *  
	 * @param name
	 * @param description
	 */
	public Requirement(int id, String title, String description, User creator, ArrayList<Note> notes){
		this();
		this.id = id;
		this.title = title;
		this.description = description;
		this.creator = creator;
		this.notes = notes;
	}
	
	
	/**
	 * Basic constructor with default arguments
	 * 
	 */
	public Requirement(){
		this.releaseNumber = "";
		this.iteration = Iteration.getBacklog(); //should be backlog
		this.iterationId = -1; //still hacking around
		
		this.status = NEW; //default status is New
		this.priority = MEDIUM; //default priority is medium
		this.title = ""; //name is required
		this.description = ""; //description is required
		this.estimateEffort = 0; //default estimate set to 0
		this.actualEffort = 0; //default actualEffort set to 0
		this.subRequirements = new ArrayList<Requirement>();
		this.creationDate = new Date();
		this.lastModifiedDate = new Date();
		this.id = -1; //default id is -1
		this.creator = new User("", "", "", -1);
		this.assignee = new User("", "", "", -1);
		this.notes = new ArrayList<Note>();
	}
	
	/**
	 * Adds a note to the requirement
	 * @return the note added
	 */
	public Note addNote(Note n){
		notes.add(n);
		return n;
	}
	
	/**
	 * retrieves the arraylist of notes
	 * @return ArrayList<Note>
	 */
	public ArrayList<Note> getNotes(){
		return notes;
	}
	/**
	 * Counts how many notes are attached to the requirement
	 * @return amount of notes in this requirement
	 */
	public int countNotes(){
		return notes.size();
	}
	/**
	 * replaces the ArrayList in this requirement with the given list
	 * ONLY TO BE USED TO UPDATE THE NOTES LIST, NOT REPLACE IT
	 */
	public void updateNotes(ArrayList<Note> n){
		this.notes = n;
	}

	/**
	 * Gets the releaseNumber
	 * @return the releaseNumber
	 */
	public String getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Sets the releaseNumber
	 * @param releaseNumber: sets the releaseNumber 
	 */
	public void setReleaseNumber(String releaseNumber) {
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
	public int getEstimateEffort() {
		return estimateEffort;
	}

	/**
	 * Sets the estimate
	 * @param estimate: sets the estimate 
	 */
	public void setEstimateEffort(int estimateEffort) {
		this.estimateEffort = estimateEffort;
	}

	/**
	 * Gets the actualEffort
	 * @return the actualEffort
	 */
	public int  getActualEffort() {
		return actualEffort;
	}

	/**
	 * Sets the actualEffort
	 * @param actualEffort: sets the actualEffort 
	 */
	public void setActualEffort(int actualEffort) {
		this.actualEffort = actualEffort;
	}
	
	/**
	 * @return the user who created this Requirement
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the user who created this Requirement
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	/**
	 * @return the user who is assigned to this Requirement
	 */
	public User getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee the user who is assigned to this Requirement
	 */
	public void setAssignee(User assignee) {
		this.assignee = assignee;
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
	 * @return the parentRequirementId
	 */
	public int getParentRequirementId() {
		return parentRequirementId;
	}

	/**
	 * @param parentRequirementId the parentRequirementId to set
	 */
	public void setParentRequirementId(int parentRequirementId) {
		this.parentRequirementId = parentRequirementId;
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
	
	/**
	 * Sets the iteration
	 * @param iteration: sets the iteration 
	 */
	public void setIteration(Iteration iteration){
		if (this.iteration != null){
			this.iteration.removeRequirement(this.getId());
		}
			this.iteration = iteration;
			this.iterationId = this.iteration.getId();
			
			this.iteration.addRequirement(this.getId());
			
	}
	
	/**
	 * gets the iteration
	 * @return iteration number 
	 */
	public Iteration getIteration(){
		return iteration;
	}

	public int getIterationId(){
		return this.iterationId;
	}

	public void setIterationId(int id){
		this.iterationId = id;
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
		
		this.iteration = null; //TODO: less hacking

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
		return this.getTitle();
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
	public static void addGsonDependencies(GsonBuilder builder) {
		
	}

}
