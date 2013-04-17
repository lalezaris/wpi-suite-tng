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
 *  Chris
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules;

/**
 * Thrown when a field cannot be found.
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
public class RuleTargetException extends Exception {
	
	String msg;
	public RuleTargetException(String attempt){		
		this.msg = "Tried to make a rule with target: "+ attempt +", but it did not exist. Typo?";
	}
	
	@Override
	public String getMessage(){
		return msg;
	}
}
