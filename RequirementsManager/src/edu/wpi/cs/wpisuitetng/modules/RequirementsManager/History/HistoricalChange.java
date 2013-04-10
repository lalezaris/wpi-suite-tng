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

/**
 * @Author: Michael French
 * 
 * @version Mar 31, 2013
 */

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager.RequirementStore;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class HistoricalChange extends AbstractModel{
	
	private Date date; //date of change
	private String change; //human readable set of changes: "this was changed to that"
	private int idOfChangedObject; //id of the object that has been changed
	private int id; //id of this change object (for database)
	private User user; //User that made this change
	
	
	public HistoricalChange(Date date, int id, int idOfChangedObject, User user){
		this.date = date;
		this.id = id;
		this.idOfChangedObject = idOfChangedObject;
		this.user = user;
		change = "";
	}
	
	public HistoricalChange(User user){
		this.user = user;
		change = "";
	}
	
	public void updateOnCreate(Requirement req){
		change += "<p>" + "Requirement was created." + "</p>";
	}
	
	public void updateChangeFromDiff(Requirement oldR, Requirement newR, RequirementStore manager){
		int notesDifference = (newR.getNotes().size() - oldR.getNotes().size());
		
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
			change +="<p> "+ "Type changed from " + oldR.getType() + " to " + newR.getType() + ".</p>";
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
			change += "<p> "+"Priority changed from " + oldR.getPriority().toString() + " to " + newR.getPriority().toString() + ".</p>";
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
		for (int i = 0; i < oldR.getChildRequirementId().size(); i++){
			if (!newR.getChildRequirementId().contains(oldR.getChildRequirementId().get(i))){
				change +="<p> "+"Sub Requirement " + oldR.getChildRequirementId().get(i) + " removed</p>";					
			}
		}
		for (int i = 0; i < newR.getChildRequirementId().size(); i++){
			if (!oldR.getChildRequirementId().contains(newR.getChildRequirementId().get(i))){
				change += "<p> "+"Sub Requirement " + newR.getChildRequirementId().get(i) + " added</p>";					
			}
		}
	
		//compare assignee 
		if (!oldR.getAssignee().equals(newR.getAssignee())){//if old and new are not the same
			change += "<p> "+"Assignee changed from " + oldR.getAssignee() + " to " + newR.getAssignee() + ".</p>";
		}
		
		//TODO: come back to this
		//compare notes lists
		if (notesDifference != 0){//if old and new are not the same
			change += "<p> "+notesDifference + " notes added.</p>";
		}
			
	}
	
	@Override
	public void save() {
		
	}

	@Override
	public void delete() {
		
	}

	@Override
	public String toJSON() {
		return null;
	}

	/**
	 * Gets the date
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date.toGMTString();
	}

	/**
	 * Gets the id of changed object
	 * 
	 * @return id of changed object
	 */
	public int getIdOfChangedObject() {
		return idOfChangedObject;
	}

	/**
	 * Gets id
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets user name
	 * 
	 * @return user name
	 */
	public String getUserName() {
		return user.getName();
	}
	
	/**
	 * Gets change
	 * 
	 * @return change
	 */
	public String getChange(){
		return change;
	}

	@Override
	public Boolean identify(Object o) {
		return null;
	}
	
	@Override
	public String toString(){
		return "<html style=\"padding:30px; border:1px solid black;\"><u>" + getUserName() + " on " + getDate()+":</u>" + getChange()+"</html>";
	}
	
	
	
}
