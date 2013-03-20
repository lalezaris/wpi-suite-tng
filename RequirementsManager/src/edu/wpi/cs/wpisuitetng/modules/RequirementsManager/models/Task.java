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
 *  Lauren Kahn
 *  Michael Perrone
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

/**
 * Persistent Model that represents a Task on a Requirement
 *
 * @author Lauren Kahn
 *
 * @version Mar 20, 2013
 *
 */
public class Task {
	private String name;
	private String description;
	
	/**
	 * Create a Task with the given properties.
	 * Other properties are the same as the default constructor.
	 * 
	 * @param description
	 */
	public Task(String description){
		this.description = description;
		this.name = "";
	}

	/**
	 * Gets the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * @param name: sets the name 
	 */
	public void setName(String name) {
		this.name = name;
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
	
	

}
