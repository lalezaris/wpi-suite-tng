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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;

/**
 * A singleton that is responsible for finding filterable elements
 *
 * @author Chris Hanna
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
	
	/**
	 * Get the object you are looking to compare to. The input tells what field to pull from the parent
	 * 
	 * @param input
	 * @param parent
	 * @return
	 * @throws RuleTargetException
	 */
	public Comparable getSource(String input, Object parent) throws RuleTargetException{
		
		input = input.toLowerCase();

		if (parent instanceof Requirement)
			return getSourceRequirement(input, parent);
		
		
		return null;
	}
	
	
	private static String[] reqTargetNames = {
		"all",
		"status", 
		"estimate",
		"priority",
		"actual",
		"type",
		"title"
	};
	/**
	 * The order of this array MUST match the order of the reqTargetNames array
	 */
	private static RuleEditableType[] reqTargetTypes = { 
		RuleEditableType.ALL,
		RuleEditableType.ENUM,
		RuleEditableType.NUMBER,
		RuleEditableType.ENUM,
		RuleEditableType.NUMBER,
		RuleEditableType.ENUM,
		RuleEditableType.STRING
	};
	
	
	public static String[] getRequirementTargets(){
		return reqTargetNames;
	}
	public static RuleEditableType[] getRequirementTargetTypes(){
		return reqTargetTypes;
	}
	
	/**
	 * parse a string to an array of enum values. 
	 * 
	 * @param input
	 * @return
	 */
	public Enum[] getEnumFromString(String input){
		input = input.toLowerCase();
		if (input.equals("all")){
			RuleEditableType[] all = {RuleEditableType.ALL};
			return all;
		}
		else if (input.equals("status"))
			return RequirementStatus.values();
		else if (input.equals("priority"))
			return RequirementPriority.values();
		else if (input.equals("type"))
			return RequirementType.values();
		return null;
	}
	
	/**
	 * Get the field of a requirement you are looking to compare to. The input tells what field to pull from the parent Requirement
	 * 
	 * @param input
	 * @param parent
	 * @return
	 * @throws RuleTargetException
	 */
	public Comparable getSourceRequirement(String input, Object parent) throws RuleTargetException{
		
		if (parent instanceof Requirement){
			Requirement req = (Requirement)parent;
			if (input.equals("all"))
				return RuleEditableType.ALL;
			else if (input.equals("status"))
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
