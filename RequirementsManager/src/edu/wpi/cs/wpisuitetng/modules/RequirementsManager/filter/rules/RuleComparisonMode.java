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
 *  Chris Hanna
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules;

/**
 * The different types of comparison modes. 
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
public enum RuleComparisonMode {
	EQUALS("EQUALS"),
	GREATER("IS GREATER THAN"),
	EQUALSGREATER("IS GREATER THAN OR EQUAL TO"),
	LESS("IS LESS THAN"),
	EQUALSLESS("IS LESS THAN OR EQUAL TO"),
	NOTEQUALS("IS NOT EQUAL TO"),
	CONTAINS("CONTAINS"),
	ASSIGNEDTO("IS ASSIGNED TO"),
	NOTASSIGNEDTO(" IS NOT ASSIGNED TO");

	private String name;

	/**
	 * Constructor for RuleComparisonMode
	 * 
	 * @param name the comparison mode
	 */
	private RuleComparisonMode(String name){
		this.name = name;
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString(){
		return name;
	}
}
