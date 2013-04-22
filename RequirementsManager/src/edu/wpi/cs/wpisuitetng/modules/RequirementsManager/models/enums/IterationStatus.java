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
 *  Arica Liu
 *  Tushar Narayan
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums;

/**
 * Possible values that a status of an iteration can be set to
 * Adapted from RequirementStatus in project RequirementsManager
 *
 * @author Arica Liu 
 * @author Tushar Narayan
 * 
 * @version Mar 24, 2013
 *
 */
public enum IterationStatus {
	NEW,       // new iteration - created but not scheduled
	OPEN, // scheduled iteration
	CLOSED   // completed iteration
}