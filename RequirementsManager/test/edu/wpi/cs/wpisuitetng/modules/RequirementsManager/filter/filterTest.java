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

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.Rule;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleComparisonMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleEditableType;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleTargetException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Insert Description Here
 *
 * @author Chris
 *
 * @version Apr 14, 2013
 *
 */
public class filterTest {

	@Before
	public void setUp(){
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
	}
	
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
		
		assertEquals(2, output.length);
		
		filter.removeAllRules();
		filter.addRule(ruleEstimate);
		output = null;
		try {
			output = filter.getFilteredObjects(input);
		} catch (RuleTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(3, output.length);
	}

	
	/*@Test
	public void panel(){
		
		RequirementListPanel listPanel = new RequirementListPanel(MainTabController.getController());
		
		listPanel.getFilterController().getPanel().getAddButton().doClick();
		Rule r = listPanel.getFilterController().getModel().getFilter().getRules().get(0);
		RulePanel p = listPanel.getFilterController().getPanel().getRules().get(0);
		p.getField().setSelectedIndex(1);
		p.getCompareMode().setSelectedIndex(1);
		p.getPossibleValues().setSelectedIndex(1);
		p.updateCompareBox();
		//p.updatePossibleValues();
		
		p.getField().setSelectedItem("iteration");
		
		Iteration[] iterations = {Iteration.getBacklog()};
		listPanel.getFilterController().setIterations(iterations);
		p.getPossibleValues().setSelectedIndex(0);
		p.getValidComparisonModes();
		p.updateCompareBox();
		//p.updatePossibleValues();
		
		Rule r2 = p.extractRule();
		
		p.getField().setSelectedItem("estimate");
		p.updateCompareBox();
		//p.updatePossibleValues();
		p.getValidComparisonModes();
		r2 = p.extractRule();

		p.getField().setSelectedItem("name");
		p.updateCompareBox();
		//p.updatePossibleValues();
		
		r2 = p.extractRule();
		//listPanel.getFilterController().getPanel().triggerTableUpdate();
		
		assertEquals(listPanel.getFilterController().getModel().getFilter().getRules().size(), 1);
		
	}*/
}
