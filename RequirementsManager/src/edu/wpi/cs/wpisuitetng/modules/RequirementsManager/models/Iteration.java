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

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IterationStatus.NEW;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.Refresher;


/**
 * Class for storing an iteration.
 * 
 * @author Arica Liu
 * @author Tushar Narayan
 * @author Michael Perrone
 */
public class Iteration extends AbstractModel{
	
	private static Iteration backlog;
	private int iterationNumber;
	private Date startDate, endDate;
	private List<Integer> requirements;
	private IterationStatus status;
	
	private int id;
	
	/**
	 * @param iterationNumber
	 * @param startDate
	 * @param endDate
	 */
	public Iteration(int iterationNumber, Date startDate, Date endDate) {
		super();
		this.iterationNumber = iterationNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.requirements = new ArrayList<Integer>();
		this.status = NEW;
	}
	
	public Iteration(){}
	
	
	/**
	 * @returns the backlog for the given project
	 */
	public static Iteration getBacklog(){
		if (backlog == null){
			backlog = new Iteration();
			backlog.setIterationNumber(-1);
			backlog.setRequirements(new ArrayList<Integer>());
		}
		return backlog;
	}
	
	/**
	 * Gets an iteration by the designated ID number
	 * 
	 * @param id the id of the 
	 */
	public static Iteration getIterationById(int id){
		System.out.println("Fetching Iteration by ID = " + id);
		Iteration[] allIterations = Refresher.getInstance().getInstantIterations();
		System.out.println("Found Some Iterations...");
		for (Iteration i : allIterations){
			System.out.println("Found Iteration ID = " + i.getId());
			if (i.getId() == id)
			{
				System.out.println("Success");
				return i;
			}
		}
		System.out.println("Iteration Not Found");
		return getBacklog();
	}
	
	/**
	 * @return the iterationNumber
	 */
	public int getIterationNumber() {
		return this.iterationNumber;
	}
	
	/**
	 * @param iterationNumber the iterationNumber to set
	 */
	public void setIterationNumber(int iterationNumber) {
		this.iterationNumber = iterationNumber;
	}
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the requirements
	 */
	public List<Integer> getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(List<Integer> requirements) {
		this.requirements = requirements;
	}
	
	/**
	 * Assign a requirement to this iteration
	 * @param requirement
	 */
	public void addRequirement(Integer requirement){
		if (!this.requirements.contains(requirement)){
			this.requirements.add(requirement);
		}
	}
	
	/**
	 * Unassigns a requirement from this iteration
	 * @param requirement
	 */
	public void removeRequirement(Integer requirement) {
		System.out.println("removing requirement from iteration");
		this.requirements.remove(requirement);
	}

	/**
	 * @return the status
	 */
	public IterationStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(IterationStatus status) {
		this.status = status;
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
		json = gson.toJson(this, Iteration.class);
		return json;
	}
	
	/**
	 * Converts the given list of Requirements to a JSON string
	 * @param dlist a list of Requirement
	 * @return a string in JSON representing the list of Requirements
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
		//return toJSON();
		if (this.iterationNumber == Iteration.getBacklog().iterationNumber)
			return "Backlog";
		else
			return "Iteration " + this.getIterationNumber();
	}

	
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Sets the id
	 * @param id: sets the id 
	 */
	public void setId(int id){
		this.id = id;
	
	}

	public int getId(){
		return this.id;
	}
	
	/**
	 * @param json Json string to parse containing Iteration
	 * @return The Iteration given by json
	 */
	public static Iteration fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Iteration.class);
	}
	
	/**
	 * @param json Json string to parse containing Requirement array
	 * @return The Requirement array given by json
	 */
	public static Iteration[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Iteration[].class);
	}
	
	
	/**
	 * Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
//		IterationEvent.addGsonDependencies(builder);
	}

	

}
