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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 6, 2013
 *
 */
public enum RequirementType {
	BLANK(""),
	EPIC("EPIC"),       // requires immediate attention
	THEME("THEME"), // requires attention shortly
	USERSTORY("USER STORY"),
	NONFUNCTIONAL("NON FUNCTIONAL"),
	SCENARIO("SCENARIO");   // requires attention at some point in the iteration
	
	private String name;
	RequirementType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public static RequirementType valueFromString(String name){
		if(name.equals("")) return BLANK;
		if(name.equals("EPIC")) return EPIC;
		if(name.equals("THEME")) return THEME;
		if(name.equals("USER STORY")) return USERSTORY;
		if(name.equals("NON FUNCTIONAL")) return NONFUNCTIONAL;
		if(name.equals("SCENARIO")) return SCENARIO;
		return BLANK;
	}
}
