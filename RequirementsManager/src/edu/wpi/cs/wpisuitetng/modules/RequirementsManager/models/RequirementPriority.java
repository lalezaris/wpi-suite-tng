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
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

/**
 * Possible values that a priority of a requirement can be set to
 *
 * @author Chris Dunkers
 * @author Joe Spicola
 *
 * @version Mar 17, 2013
 *
 */
public enum RequirementPriority {
	UNDECIDED,   // not sure if needed 
	HIGH,       // requires immediate attention
	MEDIUM, // requires attention shortly
	LOW   // requires attention at some point in the iteration
}
