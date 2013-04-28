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
 * Chris Hanna
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

/**
 * Interface for retrieve requirement controllers.
 *
 * @author Chris Hanna
 * @param <T> generic type
 */
public interface IRetrieveRequirementController<T> {
	/**
	 * Run certain code when a given requirement is received.
	 * 
	 * @param content The recieved requirement.
	 */
	void runWhenRecieved(String content);

	/**
	 * Return the ID of the requirement.
	 * 
	 * @return the ID of the requirement
	 */
	String getID();
}