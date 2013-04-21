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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;

/**
 * Tests for the Iteration View and Panel
 *
 * @author Chris Dunkers
 *
 * @version Apr 13, 2013
 *
 */
public class IterationPanelTest {
	
	IterationView parent;
	Iteration iteration1, iteration2, iteration3, iteration4, iteration5; 
	Iteration[] iterationsArray;
		
	@Before 
	public void setup(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		parent = new IterationView(new Iteration(), Mode.CREATE, null);
		iteration1 = new Iteration();
		iteration1.setId(1);
		iteration1.setIterationName("1");
		try {
			iteration1.setStartDate(dateFormat.parse("01-04-2013"));
			iteration1.setEndDate(dateFormat.parse("03-04-2013"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		iteration2 = new Iteration();
		iteration2.setId(2);
		iteration2.setIterationName("2");
		try {
			iteration2.setStartDate(dateFormat.parse("04-04-2013"));
			iteration2.setEndDate(dateFormat.parse("06-04-2013"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		iteration3 = new Iteration();
		iteration3.setId(3);
		iteration3.setIterationName("3");
		try {
			iteration3.setStartDate(dateFormat.parse("07-04-2013"));
			iteration3.setEndDate(dateFormat.parse("09-04-2013"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		iteration4 = new Iteration();
		iteration4.setId(4);
		iteration4.setIterationName("4");
		try {
			iteration4.setStartDate(dateFormat.parse("15-04-2013"));
			iteration4.setEndDate(dateFormat.parse("17-04-2013"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 

		iteration5 = new Iteration();
		iteration5.setId(5);
		iteration5.setIterationName("5");
		try {
			iteration5.setStartDate(dateFormat.parse("20-04-2013"));
			iteration5.setEndDate(dateFormat.parse("23-04-2013"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		iterationsArray = new Iteration[5];
		iterationsArray[0] = iteration1;
		iterationsArray[1] = iteration2;
		iterationsArray[2] = iteration3;
		iterationsArray[3] = iteration4;
		iterationsArray[4] = iteration5;
	}
	
	@Test
	public void testRequiredFields(){
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("10-04-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("12-04-2013");
		
		assertEquals(0,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("10-04-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("12-04-2013");
		
		assertEquals(1,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("12-04-2013");
		
		assertEquals(1,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("10-04-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("");
		
		assertEquals(1,parent.checkRequiredFields(iterationsArray));
		
	}
	
	@Test
	public void testStartEndDateErrors(){
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("12-04-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("10-04-2013");
		
		assertEquals(2,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("10-05-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("12-04-2013");
		
		assertEquals(2,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("12-04-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("12-04-2013");
		
		assertEquals(0,parent.checkRequiredFields(iterationsArray));
		
	}
	
	@Test
	public void testOverLappingAndDuplicate(){
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("09-04-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("12-04-2013");
		
		assertEquals(0,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("1");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("09-04-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("12-04-2013");
		
		assertEquals(3,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("10-03-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("12-04-2013");
		
		assertEquals(4,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("12-04-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("16-04-2013");
		
		assertEquals(4,parent.checkRequiredFields(iterationsArray));
		
		((IterationPanel) (parent.getIterationPanel())).txtIterationName.setText("6");
		((IterationPanel) (parent.getIterationPanel())).txtStartDate.setText("12-03-2013");
		((IterationPanel) (parent.getIterationPanel())).txtEndDate.setText("16-05-2013");
		
		assertEquals(4,parent.checkRequiredFields(iterationsArray));
		
	}
	
	@Test
	public void testEditingPanel(){
		
		IterationView view = new IterationView(iteration1, Mode.EDIT, null);
		
		assertEquals(view.getIterationModel().getEditedModel(),view.getIterationModel().getUneditedModel());
	}
	
	@Test
	public void testIterationCreate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Iteration test = new Iteration();
		IterationView view = new IterationView(new Iteration(), Mode.CREATE, null);
		
		test.setIterationName("test");
		try {
			test.setStartDate(dateFormat.parse("01-04-2013"));
			test.setEndDate(dateFormat.parse("03-04-2013"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		((IterationPanel) (view.getIterationPanel())).txtIterationName.setText("test");
		((IterationPanel) (view.getIterationPanel())).txtStartDate.setText("01-04-2013");
		((IterationPanel) (view.getIterationPanel())).txtEndDate.setText("03-04-2013");
		
		assertEquals(test.getIterationName(),view.getIterationModel().getEditedModel().getIterationName());
		assertEquals(test.getStartDate(),view.getIterationModel().getEditedModel().getStartDate());
		assertEquals(test.getEndDate(),view.getIterationModel().getEditedModel().getEndDate());
	}

}
