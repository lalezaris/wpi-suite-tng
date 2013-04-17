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
 *  Michael French
 *  Evan Polekoff
 *  Sam Abradi
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager.RequirementStore;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The Class to hold a HistoricalChange object for recording the history of a requirement. 
 * Contains the date, object id, user, change id.
 * 
 * @Author: Michael French
 * 
 * @version Mar 31, 2013
 */
public class HistoricalChange extends AbstractModel{
	
	/** The date. */
	private Date date; //date of change
	
	/** The change. */
	private String change; //human readable set of changes: "this was changed to that"
	
	/** The id of changed object. */
	private int idOfChangedObject; //id of the object that has been changed
	
	/** The id. */
	private int id; //id of this change object (for database)
	
	/** The user. */
	private User user; //User that made this change
	
	
	/**
	 * Instantiates a new historical change.
	 *
	 * @param date the date
	 * @param id the id
	 * @param idOfChangedObject the id of changed object
	 * @param user the user
	 */
	public HistoricalChange(Date date, int id, int idOfChangedObject, User user){
		this.date = date;
		this.id = id;
		this.idOfChangedObject = idOfChangedObject;
		this.user = user;
		change = "";
	}
	
	/**
	 * Instantiates a new historical change.
	 *
	 * @param user the user
	 */
	public HistoricalChange(User user){
		this.user = user;
		change = "";
	}
	
	/**
	 * Update on create.
	 *
	 * @param req the requirement
	 */
	public void updateOnCreate(Requirement req){
		change += "<p>" + "Requirement was created." + "</p>";
	}
	
	/**
	 * Update change from difference.
	 *
	 * @param oldR the old requirement
	 * @param newR the new requirement
	 * @param manager the manager
	 */
	public void updateChangeFromDiff(Requirement oldR, Requirement newR, RequirementStore manager){
		int notesDifference = (newR.getNotes().size() - oldR.getNotes().size());
		int acceptanceTestDifference = (newR.getAcceptanceTests().size() - oldR.getAcceptanceTests().size());
		
		//compare titles
		if (oldR.getTitle().compareTo(newR.getTitle()) != 0){//if old and new are not the same
			change += "<p> "+"Title changed from " + oldR.getTitle() + " to " + newR.getTitle() + ".</p>";
		}
		
		//compare Release Numbers
		if (!oldR.getReleaseNumber().equals(newR.getReleaseNumber())){//if old and new are not the same
			change +="<p> "+ "Release Number changed from " + oldR.getReleaseNumber() + " to " + newR.getReleaseNumber() + ".</p>";
			System.out.println("Checked Release Numbers.");
			System.out.println("Change So Far: " + change);
		}
		
		//compare type
		if (oldR.getType().compareTo(newR.getType()) != 0){//if old and new are not the same
			String os = oldR.getType().toString();
			String ns = newR.getType().toString();
			if(os.equals(""))
				os = "NO TYPE";
			if(ns.equals(""))
				ns = "NO TYPE";
			change +="<p> "+ "Type changed from " + os + " to " + ns + ".</p>";
		}
		
		//compare Iterations
		if (oldR.getIterationId()!=(newR.getIterationId())){//if old and new are not the same
			change += "<p> "+"Iteration changed from ID: " + oldR.getIterationId() + " to " + newR.getIterationId() + ".</p>";
		}
		
				
		//compare Descriptions
		if (oldR.getDescription().compareTo(newR.getDescription()) != 0){//if old and new are not the same
			change +="<p> "+ "Description changed from:\n\"" + oldR.getDescription() + "\"\n -TO- \n\"" + newR.getDescription() + ".\"</p>";
		}
		
		//compare Statuses
		if (oldR.getStatus() != newR.getStatus()){//if old and new are not the same
			change += "<p> "+"Status changed from " + oldR.getStatus().toString() + " to " + newR.getStatus().toString() + ".</p>";
		}
		
		//compare Priorities
		if (oldR.getPriority() != newR.getPriority()){//if old and new are not the same
			String op = oldR.getPriority().toString();
			if(op.equals(""))
				op = "NO PRIORITY";
			String np = newR.getPriority().toString();
			if(np.equals(""))
				np = "NO PRIORITY";
			change += "<p> "+"Priority changed from " + op + " to " + np + ".</p>";
		}
		
		//compare estimate efforts
		if (oldR.getEstimateEffort() != newR.getEstimateEffort()){//if old and new are not the same
			change += "<p> "+"Estimate changed from " + oldR.getEstimateEffort() + " to " + newR.getEstimateEffort() + ".</p>";
		}
		
		//compare actual efforts
		if (oldR.getActualEffort() != newR.getActualEffort()){//if old and new are not the same
			change += "<p> "+"Actual Effort changed from " + oldR.getActualEffort() + " to " + newR.getActualEffort() + ".</p>";
		}
		
		//TODO: come back to this
		//compare sub-requirements 
		for (int i = 0; i < oldR.getChildRequirementIds().size(); i++){
			if (!newR.getChildRequirementIds().contains(oldR.getChildRequirementIds().get(i))){
				change +="<p> "+"Sub Requirement " + oldR.getChildRequirementIds().get(i) + " removed</p>";					
			}
		}
		for (int i = 0; i < newR.getChildRequirementIds().size(); i++){
			if (!oldR.getChildRequirementIds().contains(newR.getChildRequirementIds().get(i))){
				change += "<p> "+"Sub Requirement " + newR.getChildRequirementIds().get(i) + " added</p>";					
			}
		}
	
		//compare assignee 
		if (!oldR.getAssignee().equals(newR.getAssignee())){//if old and new are not the same
			change += "<p> "+"Assignee changed from " + oldR.getAssignee() + " to " + newR.getAssignee() + ".</p>";
		}
		
		//TODO: come back to this
		//compare notes lists
		if (notesDifference != 0){//if old and new are not the same
			change += "<p> "+ notesDifference;
			if(notesDifference == 1)
				change += " note added.</p>";
			else
				change += " notes added.</p>";
		}
		
		//compare Acceptance Test list size
		if (acceptanceTestDifference != 0){//if old and new are not the same
			change += "<p> "+ acceptanceTestDifference+ " Acceptance Tests added.</p>";
		}
		
		//compare Acceptance Test list contents
		for (int i = 0; i < oldR.getAcceptanceTests().size(); i++){
			if (oldR.getAcceptanceTests().get(i).getBody().compareTo(newR.getAcceptanceTests().get(i).getBody()) != 0 ||
					oldR.getAcceptanceTests().get(i).getStatus().compareTo(newR.getAcceptanceTests().get(i).getStatus()) != 0 ){
				change += "<p> Acceptance Test " + oldR.getAcceptanceTests().get(i).getTitle() + " was updated";
			}
		}
			
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {
		return null;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("MMM dd HH:mm:ss zzz yyyy");
		return format.format(date);
	}

	/**
	 * Gets the id of changed object.
	 *
	 * @return id of changed object
	 */
	public int getIdOfChangedObject() {
		return idOfChangedObject;
	}

	/**
	 * Gets id.
	 *
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets user name.
	 *
	 * @return user name
	 */
	public String getUserName() {
		return user.getName();
	}
	
	/**
	 * Gets change.
	 *
	 * @return change
	 */
	public String getChange(){
		return change;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "<html style=\"padding:30px; border:1px solid black;\"><u>" + getUserName() + " on " + getDate()+":</u>" + getChange()+"</html>";
	}
}
