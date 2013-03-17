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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Mar 17, 2013
 *
 */
public enum RequirementStatus {
	NEW,       // new requirements has not been worked on yet
	INPROGRESS, // requirement that is currently being worked on
	DONE,   // a finished requirement but not tested
	ACCEPTED   // requirement has passed acceptance tests and been accepted
	// note that there is no "ASSIGNED" status - this would be redundant since we have an assignee field
}
	
		
