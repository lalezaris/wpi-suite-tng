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
 * Arica Liu
 * Tushar Narayan
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.NEW;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;

/**
 * Class for storing an iteration.
 * 
 * @author Arica Liu
 * @author Tushar Narayan
 * @author Michael Perrone
 * @edited Michael French
 * 
 */
public class Iteration extends AbstractModel implements Comparable<Iteration> {
	
	private static Iteration backlog;
	private String iterationName;
	private Date startDate, endDate;
	private List<Integer> requirements;
	private IterationStatus status;
	private int id;
	
	/**
	 * Constructor for Iteration.
	 * 
	 * @param iterationNumber The iteration number
	 * @param startDate The start date of the iteration
	 * @param endDate The end date of the iteration
	 */
	public Iteration(String iterationNumber, Date startDate, Date endDate) {
		super();
		this.iterationName = iterationNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.requirements = new ArrayList<Integer>();
		this.status = NEW;
	}
	
	/**
	 * Default constructor for iteration
	 * 
	 */
	public Iteration(){}
	
	
	/**
	 * Get the backlog for the given project.
	 * 
	 * @returns The backlog for the given project
	 */
	public static Iteration getBacklog(){
		if (backlog == null){
			backlog = new Iteration();
			backlog.setIterationName("Backlog");
			backlog.setRequirements(new ArrayList<Integer>());
			backlog.startDate = null;
			backlog.endDate = null;
		}
		return backlog;
	}
	
	/**
	 * Get an iteration by the designated ID number.
	 * 
	 * @param id The id of the iteration
	 */
	public static Iteration getIterationById(int id){
		Iteration[] allIterations = Refresher.getInstance().getInstantIterations();
		for (Iteration i : allIterations){
			if (i.getId() == id)
			{
				return i;
			}
		}
		return getBacklog();
	}
	
	/**
	 * Get the iterationNumber.
	 * 
	 * @return iterationNumber
	 */
	public String getIterationName() {
		return this.iterationName;
	}
	
	/**
	 * Set the iterationNumber.
	 * 
	 * @param iterationNumber The iterationNumber to set
	 */
	public void setIterationName(String iterationNumber) {
		this.iterationName = iterationNumber;
	}
	
	/**
	 * Get the start date.
	 * 
	 * @return startDate
	 */
	public Date getStartDate() {
		if(startDate == null)
			return new Date(0);
		return startDate;
	}

	/**
	 * Set the start date.
	 * 
	 * @param startDate The startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get the end date.
	 * 
	 * @return endDate
	 */
	public Date getEndDate() {
		if(endDate == null)
			return new Date(0);
		return endDate;
	}

	/**
	 * Set the end date.
	 * 
	 * @param endDate The endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get a list of requirements.
	 * 
	 * @return A list of requirements
	 */
	public List<Integer> getRequirements() {
		return requirements;
	}

	/**
	 * Set the requirements.
	 * 
	 * @param requirements The requirements to set
	 */
	public void setRequirements(List<Integer> requirements) {
		this.requirements = requirements;
	}
	
	/**
	 * Assign a requirement to this iteration.
	 * 
	 * @param requirement The ID of the requirement to be added
	 */
	public void addRequirement(Integer requirement){
		if (!this.requirements.contains(requirement)){
			this.requirements.add(requirement);
		}
	}
	
	/**
	 * Unassigns a requirement from this iteration.
	 * 
	 * @param requirement The ID of the requirement to be unassigns
	 */
	public void removeRequirement(Integer requirement) {
		this.requirements.remove(requirement);
	}

	/**
	 * Get the status.
	 * 
	 * @return status
	 */
	public IterationStatus getStatus() {
		return status;
	}

	/**
	 * Set the status.
	 * 
	 * @param status The status to set
	 */
	public void setStatus(IterationStatus status) {
		this.status = status;
	}

	@Override
	public void save() {
		
	}
	
	@Override
	public void delete() {
		
	}
	
	/**
	 * Convert this iteration into a JSON string.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Iteration.class);
		return json;
	}
	
	/**
	 * Convert the given list of Iterations to a JSON string.
	 * 
	 * @param dlist a list of Iteration
	 * @return a string in JSON representing the list of Iterations
	 */
	public static String toJSON(Iteration[] dlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(dlist, Iteration.class);
		return json;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.iterationName == Iteration.getBacklog().iterationName)
			return this.getIterationName();
		else
			return "Iteration " + this.getIterationName();
	}

	
	@Override
	public Boolean identify(Object o) {
		return null;
	}
	
	/**
	 * Set the id.
	 * 
	 * @param id The id to be set 
	 */
	public void setId(int id){
		this.id = id;
	
	}

	/**
	 * Get the ID.
	 * 
	 * @return The ID of this iteration.
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Check if the given iteration equals this iteration.
	 * Two Iterations are equal if all of their fields () are equal.
	 * 
	 * @param i Iteration to test equality against this iteration
	 * @return True if the Iterations are equal, false else
	 */
	public boolean equals(Iteration i){
		return this.endDate.equals(i.endDate) && this.id == i.id && this.startDate.equals(i.startDate) 
				&& this.iterationName.equals(i.iterationName) && this.status == i.status;
	}
	
	/** Convert a Json string to an Iteration.
	 * 
	 * @param json Json string to be parsed
	 * @return The corresponding Iteration
	 */
	public static Iteration fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Iteration.class);
	}
	
	/**
	 * Convert a Json string to a list of Iteration(s).
	 * 
	 * @param json Json string to parse containing Requirement array
	 * @return The Requirement array given by json
	 */
	public static Iteration[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Iteration[].class);
	}
	
	/*
	 * Add dependencies necessary for Gson to interact with this class.
	 * 
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Iteration o) {
		if(this.startDate == null)
			return 1;
		if(o.startDate == null)
			return -1;
		return (this.startDate.compareTo(o.startDate));
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof Iteration && this.id == ((Iteration)other).id){
			return true;
		}
		return false;
	}

}
