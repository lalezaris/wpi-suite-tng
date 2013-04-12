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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums;

/**
 * Possible values that a priority of a requirement can be set to.
 *
 * @author Chris Dunkers
 * @author Joe Spicola
 * @version Mar 17, 2013
 */
public enum RequirementPriority {
	BLANK(""),
	HIGH("HIGH"),       // requires immediate attention
	MEDIUM("MEDIUM"), // requires attention shortly
	LOW("LOW");   // requires attention at some point in the iteration
	
	private String name;
	
	/**
	 * Instantiates a new requirement priority.
	 *
	 * @param name the name
	 */
	RequirementPriority(String name){
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
	
	/* (non-Javadoc)
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
	public static RequirementPriority valueFromString(String name){
		if(name.equals("")) return BLANK;
		if(name.equals("HIGH")) return HIGH;
		if(name.equals("MEDIUM")) return MEDIUM;
		if(name.equals("LOW")) return LOW;
		return null;
	}
}
