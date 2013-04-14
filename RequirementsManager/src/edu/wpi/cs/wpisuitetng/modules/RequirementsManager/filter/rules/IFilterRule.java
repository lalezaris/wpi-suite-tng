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
 * Insert Description Here
 *
 * @author Chris
 *
 * @version Apr 14, 2013
 *
 */
public interface IFilterRule<T> {
	/**
	 * Call this function. 
	 * If the object meets the rule, then it is returned
	 * If the object does not meet the rule, then null is returned
	 * 
	 * @param object
	 * @return
	 */
	T applyRule(T object);
}
