package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History;

/**
 * @Author: Michael French
 * @Collaborators: Evan Polekoff, Sam Abradi
 * 
 * @version Mar 31, 2013
 */

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class HistoricalChange extends AbstractModel{
	
	private Date date; //date of change
	private String change; //human readable set of changes: "this was changed to that"
	private int idOfChangedObject; //id of the object that has been changed
	private int id; //id of this change object (for database)
	private User user; //User that made this change
	
	
	public HistoricalChange(Date date, /*String change,*/ int id, int idOfChangedObject, User user){
		this.date = date;
		//this.change = change;
		this.id = id;
		this.idOfChangedObject = idOfChangedObject;
		this.user = user;
		change = "";
	}
	
	public void updateChangeFromDiff(Requirement oldR, Requirement newR){
		int notesDifference = (newR.getNotes().size() - oldR.getNotes().size());
		boolean changedSubReqs = false;
		int reqsRemoved = 0;
		int reqsAdded = 0;
		
		
		//compare titles
		if (oldR.getTitle().compareTo(newR.getTitle()) != 0){//if old and new are not the same
			change += "Title changed from " + oldR.getTitle() + " to " + newR.getTitle() + ".\n";
		}
		
		//compare Release Numbers
		if (oldR.getReleaseNumber().compareTo(newR.getReleaseNumber()) != 0){//if old and new are not the same
			change += "Release Number changed from " + oldR.getReleaseNumber() + " to " + newR.getReleaseNumber() + ".\n";
		}
		
		//compare Iterations
		if (!oldR.getIteration().equals(newR.getIteration())){//if old and new are not the same
			change += "Iteration changed from " + oldR.getIteration().toString() + " to " + newR.getIteration().toString() + ".\n";
		}
		
		//compare Descriptions
		if (oldR.getDescription().compareTo(newR.getDescription()) != 0){//if old and new are not the same
			change += "Description changed from:\n\"" + oldR.getDescription() + "\"\n -TO- \n\"" + newR.getDescription() + ".\"\n";
		}
		
		//compare Statuses
		if (oldR.getStatus() != newR.getStatus()){//if old and new are not the same
			change += "Status changed from " + oldR.getStatus().toString() + " to " + newR.getStatus().toString() + ".\n";
		}
		
		//compare Priorities
		if (oldR.getPriority() != newR.getPriority()){//if old and new are not the same
			change += "Priority changed from " + oldR.getPriority().toString() + " to " + newR.getPriority().toString() + ".\n";
		}
		
		//compare estimate efforts
		if (oldR.getEstimateEffort() != newR.getEstimateEffort()){//if old and new are not the same
			change += "Estimate changed from " + oldR.getEstimateEffort() + " to " + newR.getEstimateEffort() + ".\n";
		}
		
		//compare actual efforts
		if (oldR.getActualEffort() != newR.getActualEffort()){//if old and new are not the same
			change += "Actual Effort changed from " + oldR.getActualEffort() + " to " + newR.getActualEffort() + ".\n";
		}
		
		//TODO: come back to this
		//compare sub-requirements [We'll come back to this]
		for (int i = 0; i < oldR.getSubRequirements().size(); i++){
			if (i < newR.getSubRequirements().size()){
				if (!newR.getSubRequirements().contains(oldR.getSubRequirements().get(i))){
					changedSubReqs = true;
					change += "Sub Requirement " + oldR.getSubRequirements().get(i).getTitle() + " removed\n";					
				}
			}else{
				changedSubReqs = true;
			}
		}
		for (int i = 0; i < newR.getSubRequirements().size(); i++){
				if (!oldR.getSubRequirements().contains(newR.getSubRequirements().get(i))){
					changedSubReqs = true;
					change += "Sub Requirement " + newR.getSubRequirements().get(i).getTitle() + " added\n";					
				}
		}
		
		
		//change += compareSubReqs(oldR, newR);?
		
		if (!oldR.getAssignee().equals(newR.getAssignee())){//if old and new are not the same
			change += "Assignee changed from " + oldR.getAssignee().getName() + " to " + newR.getAssignee().getName() + ".\n";
		}
		
		//TODO: come back to this
		//compare notes lists
		if (notesDifference != 0){//if old and new are not the same
			if (notesDifference == 1){
				change += notesDifference + " note added.\n";
			}else{
				change += notesDifference + " notes added.\n";
			}
		}
		//change += compareNotesLists(oldR, newR);
			
	}
	
	public void updateChangeFromDiff(User oldU, User NewU){
		
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
		
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getChange(){
		return change;
	}
	
}