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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;


/**
 * Validates Requirements so that they fulfill the requirements guidelines
 * Adapted from DefectValidator in the project DefectTracker
 *
 * @author Chris Dunkers
 *
 * @version Apr 16, 2013
 *
 */
@SuppressWarnings("unused")
public class RequirementValidator {
	
	private Data data;
	private Requirement requirement;
	
	public RequirementValidator(Data data){
		this.data = data;
	}
	
	public List<ValidationIssue> validate(Requirement requirement, RequirementPanel.Mode mode){
		List<ValidationIssue> issues = new ArrayList<ValidationIssue>();
		if(requirement == null){
			issues.add(new ValidationIssue("Cannot be null", "Requirement"));
			return issues;
		}
		
		if(mode == Mode.CREATE){
			if(requirement.getIterationId() == Iteration.getBacklog().getId()){
				if(!requirement.getStatus().equals(RequirementStatus.NEW)){
					issues.add(new ValidationIssue("On Create, must be NEW when in the backlog", "Status"));
				}
			}else {
				if(requirement.getEstimateEffort() == 0){
					issues.add(new ValidationIssue("Cannot be 0 if assigned to a iteration", "Estimate"));
				} else {
					requirement.setStatus(RequirementStatus.INPROGRESS);
				}
			}
		} else if(requirement.getStatus() == null){
				issues.add(new ValidationIssue("Cannot be null", "Status"));
		}
		
		if(requirement.getTitle() == null || requirement.getTitle().equals("") || requirement.getTitle().length() > 100) {
			issues.add(new ValidationIssue("Required, must be 5-100 characters", "Title"));
		}
		
		if(requirement.getDescription() == null || requirement.getDescription().equals("")) {
			issues.add(new ValidationIssue("Required, must not be empty", "Description"));
		}
		
		if(mode == Mode.EDIT){
			if(requirement.getIterationId() == Iteration.getBacklog().getId()){
				if(!requirement.getStatus().equals(RequirementStatus.NEW) && !requirement.getStatus().equals(RequirementStatus.OPEN)){
					issues.add(new ValidationIssue("Must be NEW or OPEN in the backlog", "Status"));
				}
			} else {
				if(requirement.getEstimateEffort() == 0){
					issues.add(new ValidationIssue("Cannot be 0 while assigned to a iteration", "Estimate"));
				}
				if(requirement.getStatus().equals(RequirementStatus.NEW) && requirement.getStatus().equals(RequirementStatus.OPEN)){
					issues.add(new ValidationIssue("Cannot be NEW or OPEN when assigned to a iteration", "Status"));
				}
			}
		}
		
		if(requirement.getEstimateEffort() < 0){
			issues.add(new ValidationIssue("Invalid estimate", "Estimate"));
		}
		
		return issues;
	}
	
	
}
