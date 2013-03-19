package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority.*;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus.*;


/**
 * Class for storing a Requirement
 *
 * @author Tushar
 * @edited Michael French
 * 			EDIT: Made class extend AbstractModel
 * 			REASON: This class MUST extent AbstractModel in order 
 * 			to be compatible with the DataStore class
 * @version Mar 17, 2013
 *
 */

public class Requirement extends AbstractModel{
	private int releaseNumber;
	private RequirementStatus status;
	private RequirementPriority priority;
	private String title;
	private String description;
	private String estimate;
	private int actualEffort;
	private ArrayList<Requirement> subRequirements;
	private Date creationDate, lastModifiedDate;
	private String type;
	private int id;
	
	/**
	 * Basic constructor: at least name and description are always required
	 * 
	 * @param name
	 * @param description
	 */
	public Requirement(String name, String description){
		this.releaseNumber = 0;
		this.status = CLOSED; //default status is New
		this.priority = MEDIUM; //default priority is blank
		this.title = title; //name is required
		this.description = description; //description is required
		this.estimate = 0; //default estimate set to 0
		this.actualEffort = 0; //default actualEffort set to 0
		this.subRequirements = new ArrayList<Requirement>();
		this.type = "Requirement"; //
		/*default type is Requirement:
		 * 	can be one of Requirement, User Story or Epic
		 * */
	}

	/**
	 * Gets the releaseNumber
	 * @return the releaseNumber
	 */
	public int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Sets the releaseNumber
	 * @param releaseNumber: sets the releaseNumber 
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
	public RequirementPriority getPriority() {
		return priority;
	}

	/**
	 * Sets the priority
	 * @param priority: sets the priority 
	 */
	public void setPriority(RequirementPriority priority) {
		this.priority = priority;
	}

	/**
	 * Gets the name
	 * @return the name
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the name
	 * @param name: sets the name 
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the subRequirements
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
	 * Gets the creation date
	 * @return the Date this Requirement was created on
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * sets the creation date
	 * @param creationDate the Date this Requirement was created on
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * gets the modified date
	 * @return the Date this Requirement was last modified on
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * sets the modified list
	 * @param lastModifiedDate the Date this Requirement was last modified on
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	/**
	 * Sets the id
	 * @param id: sets the id 
	 */
	public void setId(int id){
		this.id =id;
	}
	
	/**
	 * gets the id
	 * @return id number 
	 */
	public int getId(){
		return id;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param json Json string to parse containing Defect
	 * @return The Defect given by json
	 */
	public static Requirement fromJSON(String json) {
		//GsonBuilder builder = new GsonBuilder();
		//addGsonDependencies(builder);
		//return builder.create().fromJson(json, Requirement.class);
		return null;
	}
	
	/**
	 * Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		//RequirementEvent.addGsonDependencies(builder);
	}

}
