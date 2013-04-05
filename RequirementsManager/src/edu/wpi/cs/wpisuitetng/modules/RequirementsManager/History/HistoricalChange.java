package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History;

/**
 * @Author: Michael French
 * @Collaborators: Evan Polekoff, Sam Abradi
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
	
	public void updateChangeFromDiff(Requirement oldR, Requirement newR, RequirementStore manager){
		int notesDifference = (newR.getNotes().size() - oldR.getNotes().size());
		boolean changedSubReqs = false;
		int reqsRemoved = 0;
		int reqsAdded = 0;
		
		
		//compare titles
		if (oldR.getTitle().compareTo(newR.getTitle()) != 0){//if old and new are not the same
			change += "<p> "/*<pre>     </pre>*/+"Title changed from " + oldR.getTitle() + " to " + newR.getTitle() + ".</p>";
		}
		
		//compare Release Numbers
		if (oldR.getReleaseNumber().compareTo(newR.getReleaseNumber()) != 0){//if old and new are not the same
			change +="<p> "/*<pre>     </pre>*/+ "Release Number changed from " + oldR.getReleaseNumber() + " to " + newR.getReleaseNumber() + ".</p>";
		}
		
		//compare Iterations
		if (oldR.getIterationId()!=(newR.getIterationId())){//if old and new are not the same
			
			change += "<p> "/*<pre>     </pre>*/+"Iteration changed from ID: " + oldR.getIterationId() + " to " + newR.getIterationId() + ".</p>";
		}
		
		
		
		//compare Descriptions
		if (oldR.getDescription().compareTo(newR.getDescription()) != 0){//if old and new are not the same
			change +="<p> "/*<pre>     </pre>*/+ "Description changed from:\n\"" + oldR.getDescription() + "\"\n -TO- \n\"" + newR.getDescription() + ".\"</p>";
		}
		
		//compare Statuses
		if (oldR.getStatus() != newR.getStatus()){//if old and new are not the same
			change += "<p> "/*<pre>     </pre>*/+"Status changed from " + oldR.getStatus().toString() + " to " + newR.getStatus().toString() + ".</p>";
		}
		
		//compare Priorities
		if (oldR.getPriority() != newR.getPriority()){//if old and new are not the same
			change += "<p> "/*<pre>     </pre>*/+"Priority changed from " + oldR.getPriority().toString() + " to " + newR.getPriority().toString() + ".</p>";
		}
		
		//compare estimate efforts
		if (oldR.getEstimateEffort() != newR.getEstimateEffort()){//if old and new are not the same
			change += "<p> "/*<pre>     </pre>*/+"Estimate changed from " + oldR.getEstimateEffort() + " to " + newR.getEstimateEffort() + ".</p>";
		}
		
		//compare actual efforts
		if (oldR.getActualEffort() != newR.getActualEffort()){//if old and new are not the same
			change += "<p> "/*<pre>     </pre>*/+"Actual Effort changed from " + oldR.getActualEffort() + " to " + newR.getActualEffort() + ".</p>";
		}
		
		//TODO: come back to this
		//compare sub-requirements [We'll come back to this]
		for (int i = 0; i < oldR.getChildRequirementId().size(); i++){
			if (!newR.getChildRequirementId().contains(oldR.getChildRequirementId().get(i))){
				change +="<p> "/*<pre>     </pre>*/+"Sub Requirement " + oldR.getChildRequirementId().get(i) + " removed</p>";					
			}
		}
		for (int i = 0; i < newR.getChildRequirementId().size(); i++){
			if (!oldR.getChildRequirementId().contains(newR.getChildRequirementId().get(i))){
				change += "<p> "/*<pre>     </pre>*/+"Sub Requirement " + newR.getChildRequirementId().get(i) + " added</p>";					
			}
		}
		
		
		//change += compareSubReqs(oldR, newR);?
		
		if (!oldR.getAssignee().equals(newR.getAssignee())){//if old and new are not the same
			change += "<p> "/*<pre>     </pre>*/+"Assignee changed from " + oldR.getAssignee() + " to " + newR.getAssignee() + ".</p>";
		}
		
		//TODO: come back to this
		//compare notes lists
		if (notesDifference != 0){//if old and new are not the same
			if (notesDifference == 1){
				change += "<p> "/*<pre>     </pre>*/+notesDifference + " note added.</p>";
			}else{
				change += "<p> "/*<pre>     </pre>*/+notesDifference + " notes added.</p>";
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

	public String getDate() {
		return date.toGMTString();
	}

	public int getIdOfChangedObject() {
		return idOfChangedObject;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return user.getName();
	}
	
	public String getChange(){
		return change;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString(){
		return "<html><u>" + getUserName() + " made changes on " + getDate()+"</u>" + getChange()+"</html>";
	}
	
	
	
}
