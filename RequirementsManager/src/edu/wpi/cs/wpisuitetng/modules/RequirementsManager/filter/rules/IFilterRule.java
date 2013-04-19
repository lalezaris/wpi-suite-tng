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
 * 
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
public interface IFilterRule{

	
	/**
	 * Call this function.
	 * If the rule should be ANDed together with other rules, this returns true
	 * If the rule should be ORed together with other rules, this returns false
	 * 
	 * @return
	 */
	boolean isAnd();
	
	void setIsAnd(boolean isAnd);

	/**
	 * Returns true if the parent meets the rule, or false otherwise
	 * 
	 * @param parent
	 * @return
	 * @throws RuleTargetException
	 */
	boolean apply(Object parent) throws RuleTargetException;

	
	
}
