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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * Insert Description Here
 *
 * @author Chris
 *
 * @version Apr 14, 2013
 *
 */
public class FilterTable{

	private static FilterTable table;
	
	public static FilterTable getInstance(){
		if (table == null)
			table = new FilterTable();
		return table;
	}
	
	private FilterTable(){
		
	}
	
	public Comparable getSource(String input, Object parent) throws RuleTargetException{
		
		input = input.toLowerCase();

		if (parent instanceof Requirement)
			return getSourceRequirement(input, parent);
		
		
		return null;
	}
	
	public Comparable getSourceRequirement(String input, Object parent) throws RuleTargetException{
		
		if (parent instanceof Requirement){
			Requirement req = (Requirement)parent;
			
			if (input.equals("status"))
				return req.getStatus();
			else if (input.equals("estimate"))
				return (Integer)req.getEstimateEffort();
			else if (input.equals("priority"))
				return req.getPriority();
			else if (input.equals("actual"))
				return (Integer)req.getActualEffort();
			else if (input.equals("type"))
				return req.getType();
			else if (input.equals("title"))
				return req.getTitle();
			
		}
		
		throw new RuleTargetException(input);
	}
	
	
}
