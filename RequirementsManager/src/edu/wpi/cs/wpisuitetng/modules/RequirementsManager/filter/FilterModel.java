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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.Rule;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleComparisonMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

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
	Iteration[] iterations;
	User[] users;
	/**
	 * Create a new model
	 * 
	 */
	public FilterModel() {
		filter = new Filter();

		iterations = new Iteration[0];

		Rule test = new Rule(RequirementStatus.COMPLETE, RuleComparisonMode.EQUALS, "status");
		filter.addRule(test);
	}

	public Filter getFilter(){
		return filter;
	}

	/**
	 * Refresh the model from the information in a panel
	 * 
	 * @param panel
	 */
	public void setModelFromPanel(FilterPanel panel){
		filter.removeAllRules();
		System.out.println("Finding Filters");
		for (int i = 0 ; i < panel.getRules().size(); i ++){
			System.out.println(i + " Found Filter");
			if (panel.getRules().get(i).getIsEnabled()){
				Rule rule = panel.getRules().get(i).extractRule();
				if (rule!=null)
					filter.addRule(rule);
			}
		}
	}

	/**
	 * Gets the iterations
	 * @return the iterations
	 */
	public Iteration[] getIterations() {
		return iterations;
	}

	/**
	 * Sets the iterations
	 * @param iterations: sets the iterations 
	 */
	public void setIterations(Iteration[] iterations) {
		this.iterations = iterations;

	}

	/**
	 * Gets the users
	 * @return the users
	 */
	public User[] getUsers() {
		return users;
	}

	/**
	 * Sets the users
	 * @param users: sets the users 
	 */
	public void setUsers(User[] users) {
		this.users = users;
	}

}
