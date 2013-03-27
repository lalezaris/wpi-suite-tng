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
 *  Joe Spicola
 *  Tushar Narayan
 *  Evan Polekoff
 *  Ned Shelton
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

/**
 * Possible values that a status of a requirement can be set to
 * Adapted from DefectStatus in project DefectTracker
 *
 * @author Chris Dunkers 
 * @author Joe Spicola
 * 
 * @version Mar 17, 2013
 *
 */
public enum RequirementStatus {
	NEW,       // new requirements has not been worked on yet
	INPROGRESS, // requirement that is currently being worked on
	OPEN,	// requirement is ready to be worked on but is not assigned to an iteration yet.
	COMPLETE,   // a finished requirement but not tested
	DELETED		// requirement has been deleted from the list.
}
		
