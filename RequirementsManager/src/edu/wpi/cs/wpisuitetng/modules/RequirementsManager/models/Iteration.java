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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IterationStatus.*;


/**
 * Class for storing an iteration.
 * 
 * @author Arica Liu
 * @author Tushar Narayan
 */
public class Iteration extends AbstractModel{
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
		return toJSON();
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
