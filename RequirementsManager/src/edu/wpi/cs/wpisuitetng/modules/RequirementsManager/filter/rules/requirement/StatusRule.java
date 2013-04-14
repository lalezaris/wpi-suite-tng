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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.requirement;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.IFilterRule;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;

/**
 * Insert Description Here
 *
 * @author Chris
 *
 * @version Apr 14, 2013
 *
 */
public class StatusRule implements IFilterRule<Requirement>{

	private RequirementStatus target;
	public StatusRule(RequirementStatus target){
		this.target = target;
	}

	public Requirement applyRule(Requirement object) {
		if (object.getStatus() == target){
			return object;
		} else{
			return null;
		}
	}


}
