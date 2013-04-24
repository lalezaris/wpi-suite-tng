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
 * 	Sam Abradi
 *  Evan Polekoff
 *  Ned Shelton
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums;

/**
 * Possible values that a status of a requirement can be set to.
 * Adapted from DefectStatus in project DefectTracker.
 *
 * @author Evan Polekoff
 * @author Sam Abradi
 * @author Ned Shelton
 * 
 * @version Apr 18, 2013
 */
public enum TaskStatus {
	BLANK(""),
	INPROGRESS("INPROGRESS"),
	OPEN("OPEN"),
	CLOSED("CLOSED"),
	ACCEPTED("ACCEPTED");

	private String name;

	/**
	 * Instantiates a new requirement priority.
	 *
	 * @param name the name
	 */
	TaskStatus(String name){
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){
		return name;
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString(){
		return name;
	}


	/**
	 * Get value from a string.
	 *
	 * @param name the name
	 * @return the requirement priority
	 */
	public static TaskStatus valueFromString(String name){
		if(name.equals("")) return BLANK;
		if(name.equals("INPROGRESS")) return INPROGRESS;
		if(name.equals("OPEN")) return OPEN;
		if(name.equals("ACCEPTED")) return ACCEPTED;
		if(name.equals("CLOSED")) return CLOSED;
		return BLANK;
	}

}
