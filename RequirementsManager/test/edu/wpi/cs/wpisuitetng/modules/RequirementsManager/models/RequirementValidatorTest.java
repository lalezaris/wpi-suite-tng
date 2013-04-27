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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;

/**
 * Testing the validator to make sure all of the cases are covered
 *
 * @author Chris Dunkers
 *
 * @version Apr 17, 2013
 *
 */
public class RequirementValidatorTest {
	Requirement req;
	RequirementValidator reqVal;
	List<ValidationIssue> issues;
	Data data;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup(){
		req = new Requirement();
		reqVal = new RequirementValidator(null);
		List<ValidationIssue> issues = new ArrayList<ValidationIssue>();
	}
	
	/**
	 * Test invalid titles and descriptions.
	 */
	@Test
	public void testInValidTitleAndDescription(){
		//requirement with too short of title and no description (invalid)
		//should report 2 issues no matter what mode it is in
		req.setTitle(""); //set a invalid title
		req.setDescription(""); //set a invalid description
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.CREATE);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(2,issues.size()); 
		
		try {
			issues = reqVal.validate(req, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(2,issues.size()); 
		
		try {
			issues = reqVal.validate(req, RequirementPanel.Mode.CHILD);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(2,issues.size());
	}
	
	/**
	 * Test various title description combos.
	 */
	@Test
	public void testVariousTitleDescriptionCombos(){
		//requirement with too short of title and no description (invalid)
		//should report 2 issues no matter what mode it is in
		req.setTitle("Requirement 1"); //set a valid title
		req.setDescription(""); //set a invalid description
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.CREATE);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(1,issues.size()); 
		
		req.setTitle(""); //set a invalid title
		req.setDescription("a das"); //set a valid description
		
		try {
			issues = reqVal.validate(req, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(1,issues.size()); 
		
		req.setTitle("Requirement 1"); //set a valid title
		req.setDescription("a das"); //set a valid description
		
		try {
			issues = reqVal.validate(req, RequirementPanel.Mode.CHILD);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(0,issues.size());
	}
	
	/**
	 * Test valid iteration backlog.
	 */
	@Test
	public void testValidIterationBacklog(){
		//requirement in the backlog with a estimate of 0 and status of NEW in the create mode(valid)
		req.setTitle("Req 1"); //set a valid title
		req.setDescription("This is the Description"); //set a valid description
		req.setIterationId(Iteration.getBacklog().getId()); //set a valid iteration
		req.setStatus(RequirementStatus.NEW); //set a valid status for the set iteration
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.CREATE);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(0,issues.size()); //should be 0 as a valid requirement
	}
	
	/**
	 * Test invalid requirement backlog.
	 */
	@Test
	public void testInValidRequirementBacklog(){
		//requirement in the backlog with a estimate of 0 and status of OPEN in the create mode (invalid)
		req.setTitle("Req 1"); //set a valid title
		req.setDescription("This is the Description"); //set a valid description
		req.setIterationId(Iteration.getBacklog().getId()); //set a valid iteration
		req.setStatus(RequirementStatus.OPEN); //set a invalid status for mode
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.CREATE);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(1,issues.size()); //should be 1 saying cannot be in Open in the backlog on create
	}
	
	/**
	 * Test edit conditions.
	 */
	@Test
	public void testEditConditions(){
		//requirement in the backlog with a estimate of 0 and status of OPEN in the edit mode (valid)
		req.setTitle("Req 1"); //set a valid title
		req.setDescription("This is the Description"); //set a valid description
		req.setIterationId(Iteration.getBacklog().getId()); //set a valid iteration
		req.setStatus(RequirementStatus.OPEN); //set a valid status for the set iteration
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(0,issues.size()); //should be 0 as corect values should be set
	}
	
	/**
	 * Test invalid estimate.
	 */
	@Test
	public void testInvalidEstimate(){
		//requirement in the backlog with a estimate of 0 and status of OPEN in the edit mode (valid)
		req.setTitle("Req 1"); //set a valid title
		req.setDescription("This is the Description"); //set a valid description
		req.setIterationId(1); //set a valid iteration
		req.setStatus(RequirementStatus.INPROGRESS); //set a valid status for the set iteration
		req.setEstimateEffort(0); //set a invalid estimate
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(1,issues.size()); //should be 1 as estimate cannot be 0 when assigned to a iteration
		
		try {
			issues = reqVal.validate(req, RequirementPanel.Mode.CREATE);
			} catch (Exception e){
				System.out.println(e);
			}
			assertEquals(1,issues.size()); //should be 1 as estimate cannot be 0 when assigned to a iteration
	}
	
	/**
	 * Test invalid status in backlog.
	 */
	@Test
	public void testInvalidStatusInBacklog(){
		//requirement in the backlog with a estimate of 0 and status of OPEN in the edit mode (valid)
		req.setTitle("Req 1"); //set a valid title
		req.setDescription("This is the Description"); //set a valid description
		req.setIterationId(Iteration.getBacklog().getId()); //set a valid iteration
		req.setStatus(RequirementStatus.INPROGRESS); //set a invalid status for the set iteration
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(1,issues.size()); //should be 1 as estimate cannot be 0 when assigned to a iteration
	}
	
	/**
	 * Test invalid status in iteration.
	 */
	@Test
	public void testInvalidStatusInIteration(){
		//requirement in the backlog with a estimate of 0 and status of OPEN in the edit mode (valid)
		req.setTitle("Req 1"); //set a valid title
		req.setDescription("This is the Description"); //set a valid description
		req.setIterationId(1); //set a valid iteration
		req.setStatus(RequirementStatus.OPEN); //set a invalid status for the set iteration
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(2, issues.size()); //should be 2 as estimate cannot be 0 & status should be in progress when assigned to a iteration
	}
	
	/**
	 * Test null requirement.
	 */
	@Test
	public void testNullRequirement(){
		try {
		issues = reqVal.validate(null, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(1,issues.size()); //should be 1 as estimate cannot be 0 when assigned to a iteration
	}
	
	/**
	 * Test valid creation.
	 */
	@Test
	public void testValidCreation(){
		req.setTitle("Req 1"); //set a valid title
		req.setDescription("This is the Description"); //set a valid description
		req.setIterationId(1); //set a valid iteration
		req.setStatus(RequirementStatus.INPROGRESS); //set a valid status for the set iteration
		req.setEstimateEffort(1); //set a invalid estimate
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(0,issues.size()); //should be 1 as estimate cannot be 0 when assigned to a iteration
	}
	
	/**
	 * Test invalid status.
	 */
	@Test
	public void testInvalidStatus(){
		req.setTitle("Req 1"); //set a valid title
		req.setDescription("This is the Description"); //set a valid description
		req.setIterationId(1); //set a valid iteration
		req.setStatus(RequirementStatus.OPEN); //set a invalid status for the set iteration
		req.setEstimateEffort(1); //set a valid estimate
		
		try {
		issues = reqVal.validate(req, RequirementPanel.Mode.EDIT);
		} catch (Exception e){
			System.out.println(e);
		}
		assertEquals(1,issues.size()); //should be 1 as estimate cannot be 0 when assigned to a iteration
	}
	
	

}
