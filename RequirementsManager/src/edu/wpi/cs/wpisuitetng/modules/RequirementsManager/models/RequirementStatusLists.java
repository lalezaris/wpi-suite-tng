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
 *  Evan Polekoff
 *  Ned Shelton
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;

/**
 * Possible values that a status of a requirement can be set to
 * Adapted from DefectStatus in project DefectTracker
 *
 * @author Evan Polekoff 
 * @author Ned Shelton
 * 
 * @version Mar 27, 2013
 *
 */
public class RequirementStatusLists{
	
	static String[] NewList = {"NEW"};
	static String[] CompleteList = {"INPROGRESS", "OPEN", "COMPLETE"};
	static String[] DeletedList = {"DELETED", "OPEN"};
	static String[] OpenList = {"OPEN"};
	static String[] InProgressList = {"INPROGRESS", "COMPLETE"};
	
	RequirementStatusLists(){
		
	}
	
	/**
	 * Gets the list of a requirement status
	 * 
	 * @param status The requirement status that we are looking for the correct list for.
	 * @return the list for that requirement status
	 */
	public static String[] getList(RequirementStatus status){
		switch(status){
		case NEW:
			return NewList;
		case INPROGRESS:
			return InProgressList;
		case OPEN:
			return OpenList;
		case COMPLETE:
			return CompleteList;
		case DELETED:
			return DeletedList;
		default:
			return new String[]{};//Return an empty string array so you don't have the option to choose anything.
		}
	}
	
}

