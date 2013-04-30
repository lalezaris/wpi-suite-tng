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
 * @author Michael French
 * @author Evan Polekoff
 * @author Sam Abradi
 * 
 * @version Mar 31, 2013
 */
public class HistoricalChange extends AbstractModel{

	private Date date; //date of change

	private String change; //human readable set of changes: "this was changed to that"

	private int idOfChangedObject; //id of the object that has been changed

	private int id; //id of this change object (for database)

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

		if (req.getNotes().size() != 0){//if a note was added at the time of creation
			change += "<p> " + req.getNotes().size();
			if(req.getNotes().size() == 1)
				change += " note added.</p>";
			else
				change += " notes added.</p>";
		}

		if (req.getAssignee().size() != 0){//if assignee(s) was(were) added at the time of creation
			change += "<p> " + req.getAssignee().size();
			if(req.getAssignee().size() == 1)
				change += " assignee added.</p>";
			else
				change += " assignees added.</p>";
		}

		if (req.getAcceptanceTests().size() != 0){//if acceptance test(s) was(were) added at the time of creation
			change += "<p> " + req.getAcceptanceTests().size();
			if(req.getAcceptanceTests().size() == 1)
				change += " acceptance test added.</p>";
			else
				change += " acceptance tests added.</p>";
		}
		
		if (req.getTasks().size() != 0){//if task(s) was(were) added at the time of creation
			change += "<p> " + req.getTasks().size();
			if(req.getTasks().size() == 1)
				change += " task added.</p>";
			else
				change += " tasks added.</p>";
		}
		
		//Attachments cannot be added on creation of requirement
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
		int taskDifference = (newR.getTasks().size() - oldR.getTasks().size());

		//compare titles
		if (oldR.getTitle().compareTo(newR.getTitle()) != 0){//if old and new are not the same
			String oldTitle = oldR.getTitle();
			String newTitle = newR.getTitle();
			if(oldTitle.equals(""))
				oldTitle = "NO TITLE";
			if(newTitle.equals("")) //should never be allowed, but checking for consistency
				newTitle = "NO TITLE";
			change += "<p> "+"Title changed from " + oldTitle + " to " + newTitle + ".</p>";
		}

		//compare Release Numbers
		if (!oldR.getReleaseNumber().equals(newR.getReleaseNumber())){//if old and new are not the same
			String oldReleaseNumber = oldR.getReleaseNumber();
			String newReleaseNumber = newR.getReleaseNumber();
			if(oldReleaseNumber.equals(""))
				oldReleaseNumber = "NO RELEASE NUMBER";
			if(newReleaseNumber.equals(""))
				newReleaseNumber = "NO RELEASE NUMBER";
			change +="<p> "+ "Release Number changed from " + oldReleaseNumber + " to " + newReleaseNumber + ".</p>";
		}

		//compare type
		if (oldR.getType().compareTo(newR.getType()) != 0){//if old and new are not the same
			String oldType = oldR.getType().toString();
			String newType = newR.getType().toString();
			if(oldType.equals(""))
				oldType = "NO TYPE";
			if(newType.equals(""))
				newType = "NO TYPE";
			change +="<p> "+ "Type changed from " + oldType + " to " + newType + ".</p>";
		}

		//compare Iterations
		if (oldR.getIterationId()!=(newR.getIterationId())){//if old and new are not the same
			int oldIterationId = oldR.getIterationId();
			int newIterationId = newR.getIterationId();
			String oldIteration = String.valueOf(oldIterationId);
			String newIteration = String.valueOf(newIterationId);
			if(oldIterationId == 0)
				oldIteration = "Backlog";
			if(newIterationId == 0)
				newIteration = "Backlog";
			change += "<p> "+"Iteration changed from ID: " + oldIteration + " to " + newIteration + ".</p>";
		}


		//compare Descriptions
		if (oldR.getDescription().compareTo(newR.getDescription()) != 0){//if old and new are not the same
			change +="<p> "+ "Description changed from: \"" + oldR.getDescription() + "\" to \"" + newR.getDescription() + ".\"</p>";
		}

		//compare Statuses
		if (oldR.getStatus() != newR.getStatus()){//if old and new are not the same
			change += "<p> "+"Status changed from " + oldR.getStatus().toString() + " to " + newR.getStatus().toString() + ".</p>";
		}

		//compare Priorities
		if (oldR.getPriority() != newR.getPriority()){//if old and new are not the same
			String oldPriority = oldR.getPriority().toString();
			String newPriority = newR.getPriority().toString();
			if(oldPriority.equals(""))
				oldPriority = "NO PRIORITY";
			if(newPriority.equals(""))
				newPriority = "NO PRIORITY";
			change += "<p> "+"Priority changed from " + oldPriority + " to " + newPriority + ".</p>";
		}

		//compare estimate efforts
		if (oldR.getEstimateEffort() != newR.getEstimateEffort()){//if old and new are not the same
			change += "<p> "+"Estimate changed from " + oldR.getEstimateEffort() + " to " + newR.getEstimateEffort() + ".</p>";
		}

		//compare actual efforts
		if (oldR.getActualEffort() != newR.getActualEffort()){//if old and new are not the same
			change += "<p> "+"Actual Effort changed from " + oldR.getActualEffort() + " to " + newR.getActualEffort() + ".</p>";
		}	

		//compare sub-requirements, add comments to the history log
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
			change += "<p> "+"Assignee changed from ";
			change += (oldR.getAssignee() == null)? "NONE" : oldR.getAssignee();
			change += " to ";
			change += (newR.getAssignee() == null)? "NONE" : newR.getAssignee() + ".</p>";
		}

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
			if(acceptanceTestDifference == 1)
				change += "<p> "+ acceptanceTestDifference+ " Acceptance Test added.</p>";
			else
				change += "<p> "+ acceptanceTestDifference+ " Acceptance Tests added.</p>";
		}

		//compare Acceptance Test list contents
		for (int i = 0; i < oldR.getAcceptanceTests().size(); i++){
			if (oldR.getAcceptanceTests().get(i).getBody().compareTo(newR.getAcceptanceTests().get(i).getBody()) != 0 ||
					oldR.getAcceptanceTests().get(i).getStatus().compareTo(newR.getAcceptanceTests().get(i).getStatus()) != 0 ){
				change += "<p> Acceptance Test " + oldR.getAcceptanceTests().get(i).getTitle() + " was updated";
			}
		}

		//compare Task list size
		if (taskDifference != 0){//if old and new are not the same
			if(taskDifference == 1)
				change += "<p> "+ taskDifference+ " task added.</p>";
			else
				change += "<p> "+ taskDifference+ " tasks added.</p>";
		}
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

	public void setChange(String c){
		change = c;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "<html style=\"padding:30px; border:1px solid black;\"><u>" + getUserName() + " on " + getDate()+":</u>" + getChange()+"</html>";
	}
}
