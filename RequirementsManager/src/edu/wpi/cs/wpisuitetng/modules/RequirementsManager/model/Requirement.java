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
 *  Mike Perrone
 *  Ned Shelton
 *  Xia Li
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model.RequirementStatus.*;

/**
 * Persistent Model that represents a Requirement.
 *
 * @author Tushar Narayan
 *
 * @version Mar 17, 2013
 *
 */
public class Requirement extends AbstractModel{
	private int releaseNumber;
	private RequirementStatus status;
	private String priority;
	private String name;
	private String description;
	private int estimate;
	private int actualEffort;
	private ArrayList<Requirement> subRequirements;
	private String type;
	private int id;
	//TODO
	//private User creator, assingee;
	//private Date creationDate, lostModifiedDate;
	private List<RequirementEvent> events;
	
	
	
	/**
	 * Constructs a new Requirement with default properties
	 */
	public Requirement(){
		releaseNumber = 0;
		status = FUTURE;
		priority = name = description = "";
		estimate = actualEffort = 0;
		subRequirements = new ArrayList<Requirement>();
		type = "Requirement"; /*default type is Requirement, can be one of:
		 							* Requirement, User Story or Epic
		 							* */
		id = -1; //default id is -1
	}
	

	/**
	 * Constructs a new Requirement with the given properties
	 * Other properties are the same as the default constructor
	 * 
	 * @param id the unique id of the Requirement
	 * @param name the title of the Requirement
	 * @param description the description of the Requirement
	 */ 
	public Requirement(int id, String name, String description) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	
	/**
	 * Gets the releaseNumber
	 * @return releaseNumber
	 */
	public int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Sets the releaseNumber
	 * @param releaseNumber the releaseNumber of the Requirement
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
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the priority
	 * @param priority: sets the priority 
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Gets the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * @param name: sets the name 
	 */
	public void setName(String name) {
		this.name = name;
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
	public int getEstimate() {
		return estimate;
	}

	/**
	 * Sets the estimate
	 * @param estimate: sets the estimate 
	 */
	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	/**
	 * Gets the actualEffort
	 * @return the actualEffort
	 */
	public int getActualEffort() {
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
	 * Gets the subRequirements
	 * @return subRequirements an ArrayList of the associated sub-requirements
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
	 * Gets the type
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type
	 * @param type: sets the type 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the unique id of this Requirement (-1 if this is a new Requirement)
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id of this Requirement
	 */
	public void setId(int id) {
		this.id = id;
	}

	// note that save and delete don't do anything at the moment, even in the core's models
	@Override
	public void save() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

	/**
	 * Converts this Requirement to a JSON string
	 * Based off toJSON() from Defect.java
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
	 * @param dlist a list of Requirements
	 * @return a string in JSON representing the list of Requirements
	 */
	public static String toJSON(Requirement[] dlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(dlist, Requirement.class);
		return json;
	}
	
	@Override
	public String toString() {
		return toJSON();
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
	//	RequirementEvent.addGsonDependencies(builder);
	}
	
	// interface documentation says this is necessary for the mock database
	// not sure if this is still needed otherwise
	@Override
	public Boolean identify(Object o) {
		Boolean returnValue = false;
		if(o instanceof Requirement && id == ((Requirement) o).getId()) {
			returnValue = true;
		}
		if(o instanceof String && Integer.toString(id).equals(o)) {
			returnValue = true;
		}
		return returnValue;
	}
}
