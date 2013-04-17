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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.Rule;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleComparisonMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;

/**
 * The filter model
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
public class FilterModel {

	Filter filter;
	
	/**
	 * create a new model
	 * 
	 */
	public FilterModel() {
		filter = new Filter();
		
		Rule test = new Rule(RequirementStatus.COMPLETE, RuleComparisonMode.EQUALS, "status");
		filter.addRule(test);
	}

	public Filter getFilter(){
		return this.filter;
	}
	
	/**
	 * Refresh the model from the information in a panel
	 * 
	 * @param panel
	 */
	public void setModelFromPanel(FilterPanel panel){
		this.filter.removeAllRules();
		System.out.println("Finding Filters");
		for (int i = 0 ; i < panel.getRules().size(); i ++){
			System.out.println(i + " Found Filter");
			if (panel.getRules().get(i).getIsEnabled())
				
				this.filter.addRule(panel.getRules().get(i).extractRule());
			}
		
	}
	
}
