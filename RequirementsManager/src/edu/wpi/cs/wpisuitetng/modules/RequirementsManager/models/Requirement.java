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
 *  Michael French
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;

/**
 * Class for storing a Requirement.
 * Adapted from Defect in project DefectManager.
 * 
 * @author Tushar Narayan
 * 
 * @version Mar 23, 2013
 *
 */

public class Requirement extends AbstractModel{

	private String title;
	private RequirementType type;
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
	private int id;
	private String creatorUsername;
	private ArrayList<String> assignee; //putting this in to keep track of user
	private ArrayList<Note> notes; //the list of notes on this requirement
	private ArrayList<Integer> childIDs;
	private ArrayList<HistoricalChange> history;
	private ArrayList<AcceptanceTest> acceptanceTests;

	/**
	 * Constructs a new Requirement with title and description.
	 *
	 * @param title the title
	 * @param description The description of the Requirement
	 */
	public Requirement(String title, String description){
		this();
		this.iteration = Iteration.getBacklog(); 
		this.title = title;
		this.description = description;
		this.notes = new ArrayList<Note>();
		this.history = new ArrayList<HistoricalChange>();
		this.acceptanceTests = new ArrayList<AcceptanceTest>();
	}

	/**
	 * Constructs a new Requirement with id, title, description and creatorUsername.
	 *
	 * @param id The id of the Requirement
	 * @param title the title
	 * @param description The description of the Requirement
	 * @param creatorUsername The username of the creator for the Requirement
	 */
	public Requirement(int id, String title, String description, String creatorUsername){
		this();
		this.iteration = Iteration.getBacklog(); //should be backlog
		this.id = id;
		this.title = title;
		this.description = description;
		this.creatorUsername = creatorUsername;
		this.notes = new ArrayList<Note>();
		this.history = new ArrayList<HistoricalChange>();
		this.acceptanceTests = new ArrayList<AcceptanceTest>();
	}

	/**
	 * Constructs a new Requirement with id, title, description and creatorUsername, and ArrayList of notes.
	 *
	 * @param id The id of the Requirement
	 * @param title the title
	 * @param description The description of the Requirement
	 * @param creatorUsername The username of the creator for the Requirement
	 * @param notes The notes attached with the requirements
	 */
	public Requirement(int id, String title, String description, String creatorUsername, ArrayList<Note> notes){
		this();
		this.iteration = Iteration.getBacklog(); //should be backlog
		this.id = id;
		this.title = title;
		this.description = description;
		this.creatorUsername = creatorUsername;
		this.notes = notes;
		this.history = new ArrayList<HistoricalChange>();
		this.acceptanceTests = new ArrayList<AcceptanceTest>();
	}

	/**
	 * Basic constructor with default arguments.
	 * 
	 */
	public Requirement(){
		this.releaseNumber = "";
		this.iteration = Iteration.getBacklog(); //should be backlog
		this.iterationId = 0;
		this.type = RequirementType.BLANK;		
		this.status = RequirementStatus.NEW; //default status is New
		this.priority = RequirementPriority.MEDIUM; //default priority is medium
		this.title = ""; //name is required
		this.description = ""; //description is required
		this.estimateEffort = 0; //default estimate set to 0
		this.actualEffort = 0; //default actualEffort set to 0
		this.creationDate = new Date();
		this.lastModifiedDate = new Date();
		this.parentRequirementId = -1; //-1 parent requirement id means no parent
		this.id = -1; //default id is -1
		this.creatorUsername = "";
		this.assignee = new ArrayList<String>();
		this.notes = new ArrayList<Note>();
		this.childIDs = new ArrayList<Integer>();
		this.history = new ArrayList<HistoricalChange>();
		this.acceptanceTests = new ArrayList<AcceptanceTest>();
	}

	/**
	 * Add an existing AcceptanceTest to this Requirement.
	 *
	 * @param a the AcceptanceTest
	 */
	public void addAcceptanceTest(AcceptanceTest a){
		acceptanceTests.add(a);
	}

	/**
	 * Gets the acceptance tests.
	 *
	 * replaces the current acceptance test list with the given one
	 * 
	 * @param a the array list of acceptance tests
	 */
	public void updateAcceptanceTests(ArrayList<AcceptanceTest> a){
		this.acceptanceTests = a;
	}

	/**
	 * Gets acceptance tests
	 * 
	 * @return the list of acceptance tests in this requirement
	 */
	public ArrayList<AcceptanceTest> getAcceptanceTests(){
		return this.acceptanceTests;
	}

	/**
	 * Add a child id to the requirement.
	 *
	 * @param childID the id to add to the list of children
	 */
	public void addChildRequirement(int childID) {
		childIDs.add(childID);
	}

	/**
	 * Gets the child requirement ids.
	 *
	 * @return a list of child ids
	 */
	public ArrayList<Integer> getChildRequirementIds() {
		return childIDs;
	}

	/**
	 * Gets the number of children.
	 *
	 * @return the number of children that the requirement has
	 */
	public int getNumChildren() {
		return childIDs.size();
	}

	/**
	 * Adds a note to the requirement.
	 * 
	 * @param n The note to be added
	 * @return The note added
	 */
	public Note addNote(Note n){
		notes.add(n);
		return n;
	}

	/**
	 * Retrieves the arraylist of notes.
	 * 
	 * @return ArrayList<Note>
	 */
	public ArrayList<Note> getNotes(){
		return notes;
	}

	/**
	 * Counts how many notes are attached to the requirement.
	 * 
	 * @return Amount of notes in this requirement
	 */
	public int countNotes(){
		return notes.size();
	}

	/**
	 * Replaces the ArrayList in this requirement with the given list.
	 * ONLY TO BE USED TO UPDATE THE NOTES LIST, NOT REPLACE IT.
	 * 
	 * @param n The list of notes to be replaced with
	 */
	public void updateNotes(ArrayList<Note> n){
		this.notes = n;
	}

	/**
	 * replaces the ArrayList in this requirement with the given list
	 * ONLY TO BE USED TO UPDATE THE HISTORY LIST, NOT REPLACE IT.
	 *
	 * @param h The list of HistroricalChange to replace with
	 */
	public void updateHistory(ArrayList<HistoricalChange> h){
		this.history = h;
	}

	/**
	 * Gets the releaseNumber.
	 *
	 * @return the releaseNumber
	 */
	public String getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Sets the releaseNumber.
	 * 
	 * @param releaseNumber The releaseNumber to be set
	 */
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	/**
	 * Gets the status.
	 * 
	 * @return The status
	 */
	public RequirementStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status The status to be set
	 */
	public void setStatus(RequirementStatus status) {
		this.status = status;
	}

	/**
	 * Gets the priority.
	 * 
	 * @return The priority
	 */
	public RequirementPriority getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 * 
	 * @param priority The priority to be set
	 */
	public void setPriority(RequirementPriority priority) {
		this.priority = priority;
	}

	/**
	 * Gets the name.
	 * 
	 * @return The name
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title The title to be set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description The description to be set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the estimate.
	 * 
	 * @return The estimate
	 */
	public int getEstimateEffort() {
		return estimateEffort;
	}

	/**
	 * Sets the estimate.
	 * 
	 * @param estimateEffort The estimate to be set
	 */
	public void setEstimateEffort(int estimateEffort) {
		this.estimateEffort = estimateEffort;
	}

	/**
	 * Gets the actualEffort.
	 * 
	 * @return the actualEffort
	 */
	public int  getActualEffort() {
		return actualEffort;
	}

	/**
	 * Sets the actualEffort.
	 * 
	 * @param actualEffort The actualEffort 
	 */
	public void setActualEffort(int actualEffort) {
		this.actualEffort = actualEffort;
	}

	/**
	 * Gets the creatorUsername.
	 * 
	 * @return The username of the user who created this Requirement
	 */
	public String getCreator() {
		return creatorUsername;
	}

	/**
	 * Sets the creator.
	 *
	 * @param creatorUsername the user who created this Requirement
	 */
	public void setCreator(String creatorUsername) {
		this.creatorUsername = creatorUsername;
	}

	/**
	 * Gets assignee.
	 *
	 * @return the user who is assigned to this Requirement
	 */
	public ArrayList<String> getAssignee() {
		return assignee;
	}

	/**
	 * Sets the assignee.
	 *
	 * @param assignee the user who is assigned to this Requirement
	 */
	public void setAssignee(ArrayList<String> assignee) {
		this.assignee = assignee;
	}

	/**
	 * Adds note to assignee
	 * 
	 * @param assignedTo who note is assigned to
	 * @return the user assigned to the note
	 */
	public String addNote(String assignedTo){
		assignee.add(assignedTo);
		return assignedTo;
	}

	/**
	 * Gets the creation date.
	 *
	 * @return the Date this Requirement was created on
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the Date this Requirement was created on
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the last modified date.
	 *
	 * @return the Date this Requirement was last modified on
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * Sets the modified list.
	 *
	 * @param lastModifiedDate the Date this Requirement was last modified on
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Gets the parent requirement id.
	 *
	 * @return the parentRequirementId
	 */
	public int getParentRequirementId() {
		return parentRequirementId;
	}

	/**
	 * Sets the parent requirement id.
	 *
	 * @param parentRequirementId the parentRequirementId to set
	 */
	public void setParentRequirementId(int parentRequirementId) {
		this.parentRequirementId = parentRequirementId;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id){
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return id number
	 */
	public int getId(){
		return id;
	}

	/**
	 * Gets the type of requirement.
	 *
	 * @return the type
	 */
	public RequirementType getType() {
		return type;
	}

	/**
	 * Sets the type of requirement.
	 *
	 * @param type the new type
	 */
	public void setType(RequirementType type) {
		this.type = type;
	}

	/**
	 * Gets the history.
	 * @return the history
	 */
	public ArrayList<HistoricalChange> getHistory() {
		return history;
	}

	/**
	 * Adds a change to the history.
	 * @param change the change being added to the history.
	 */
	public void addHistoricalChange(HistoricalChange change){
		history.add(change);
	}

	/**
	 * Sets the iteration.
	 *
	 * @param iteration the new iteration
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
	 * Gets the iteration.
	 *
	 * @return iteration number
	 */
	public Iteration getIteration(){
		return iteration;
	}

	/**
	 * Gets the iteration id.
	 *
	 * @return the iteration id
	 */
	public int getIterationId(){
		return this.iterationId;
	}

	/**
	 * Sets the iteration id.
	 *
	 * @param id the new iteration id
	 */
	public void setIterationId(int id){
		this.iterationId = id;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {

	}
	/**
	 * Converts this Requirement to a JSON string.
	 *
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
	 * Converts the given list of Requirements to a JSON string.
	 *
	 * @param dlist a list of Requirement
	 * @return a string in JSON representing the list of Requirements
	 */
	public static String toJSON(Requirement[] dlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(dlist, Requirement.class);
		return json;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getTitle();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}

	/**
	 * Convert a json string into a Requirement.
	 *
	 * @param json Json string to parse containing Requirement
	 * @return The Requirement given by json
	 */
	public static Requirement fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement.class);
	}

	/**
	 * Convert a json string to an array of requirements.
	 *
	 * @param json Json string to parse containing Requirement array
	 * @return The Requirement array given by json
	 */
	public static Requirement[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement[].class);
	}


	/**
	 * Add dependencies necessary for Gson to interact with this class.
	 *
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {

	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object requirement){
		if(		requirement instanceof Requirement &&
				this.title.equals(((Requirement)requirement).getTitle())&&
				this.releaseNumber.equals(((Requirement)requirement).getReleaseNumber()) &&
				this.iterationId == ((Requirement)requirement).iterationId &&
				this.description.equals(((Requirement)requirement).getDescription()) &&
				this.creationDate.equals(((Requirement)requirement).creationDate) && 
				this.id == ((Requirement)requirement).id)	
		{
			return true;
		}
		return false;
	}

	/**
	 * Check if this requirement is at the top of its hierarchy.
	 *
	 * @return true if requirement is at the top of its hierarchy, false if it has a parent
	 */
	public boolean isTopLevelRequirement(){
		if(this.getParentRequirementId() == -1)
			return true;
		else return false;
	}

	/**
	 * Sets sub-requirements.
	 * 
	 * @param newList the new list to set list of sub-requirements to
	 */
	@SuppressWarnings("unchecked")
	public void setSubRequirements(ArrayList<Integer> newList) {
		this.childIDs = (ArrayList<Integer>) newList.clone();
	}
}
