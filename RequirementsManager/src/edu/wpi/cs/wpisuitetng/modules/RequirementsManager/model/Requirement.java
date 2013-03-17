package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import java.util.ArrayList;

/**
 * Class for storing a Requirement
 *
 * @author Tushar
 *
 * @version Mar 17, 2013
 *
 */
public class Requirement {
	private int releaseNumber;
	private String status;
	private String priority;
	private String name;
	private String description;
	private int estimate;
	private int actualEffort;
	private ArrayList<Requirement> subRequirements;
	private String type;
	
	//basic contructor: at least name and description are always required
	public Requirement(String name, String description){
		this.releaseNumber = 0;
		this.status = "New"; //default status is New
		this.priority = ""; //default priority is blank
		this.name = name; //name is required
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
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the releaseNumber
	 */
	public int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param releaseNumber: the releaseNumber to set
	 */
	public void setReleaseNumber(int releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param status: the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param priority: the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param name: the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param description: the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the estimate
	 */
	public int getEstimate() {
		return estimate;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param estimate: the estimate to set
	 */
	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the actualEffort
	 */
	public int getActualEffort() {
		return actualEffort;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param actualEffort: the actualEffort to set
	 */
	public void setActualEffort(int actualEffort) {
		this.actualEffort = actualEffort;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the subRequirements
	 */
	public ArrayList<Requirement> getSubRequirements() {
		return subRequirements;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param subRequirements: the subRequirements to set
	 */
	public void setSubRequirements(ArrayList<Requirement> subRequirements) {
		this.subRequirements = subRequirements;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Enter description here.
	 * Make sure the method's name starts with get (delete this statement)
	 * @param type: the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
