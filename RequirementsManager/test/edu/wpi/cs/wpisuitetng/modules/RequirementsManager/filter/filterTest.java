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

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.Rule;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleComparisonMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleTargetException;
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
public class filterTest {

	@Test
	public void test() {
		System.out.println("running test");
		Requirement[] input = new Requirement[4];
		for (int i = 0 ;i <input.length;i++)
			input[i]=new Requirement();
		input[0].setStatus(RequirementStatus.COMPLETE);
		input[1].setStatus(RequirementStatus.DELETED);
		input[2].setStatus(RequirementStatus.COMPLETE);
		input[3].setStatus(RequirementStatus.COMPLETE);
		input[0].setEstimateEffort(2);
		input[1].setEstimateEffort(4);
		input[2].setEstimateEffort(5);
		input[3].setEstimateEffort(8);
		input[0].setTitle("hederprp");
		input[1].setTitle("nothing");
		input[2].setTitle("empty derp");
		input[3].setTitle("hellodehrp");
		
		
		Rule rule = new Rule(RequirementStatus.COMPLETE, RuleComparisonMode.EQUALS, "status");
		Rule ruleEstimate = new Rule(4, RuleComparisonMode.EQUALSGREATER, "estimate");
		Rule ruleName = new Rule("derp", RuleComparisonMode.CONTAINS, "title");
		
		
		rule.setIsAnd(false);
		ruleEstimate.setIsAnd(false);
		Filter filter = new Filter();
		//filter.addRule(rule);
		//filter.addRule(ruleEstimate);
		filter.addRule(ruleName);
		
		Requirement[] output = null;
		try {
			output = filter.getFilteredObjects(input);
		} catch (RuleTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < output.length; i ++)
			System.out.println(output[i]);
		
		assertEquals(2, output.length);
		
	}

}
