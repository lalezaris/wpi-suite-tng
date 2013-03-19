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
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

/**
 * Possible values for the status of a requirement.
 *
 * @author Tushar Narayan
 *
 * @version Mar 18, 2013
 *
 */
public enum RequirementStatus {
	NEW,       // new requirement
	INPROGRESS, // work on the requirement has started
	DONE,   // work on the requirement has been completed
	ACCEPTED // the work done on the requirement has been checked and accepted
}