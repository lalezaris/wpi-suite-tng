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
 *  Tianyu Li
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager.ModelMapper.MapCallback;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The Class ModelMapperTest.
 *
 * @author Tianyu Li
 * @version Apr 2, 2013
 */
public class ModelMapperTest {


	ModelMapper mapper;
	User user;
	Requirement a;
	Requirement b;
	
	/**
	 * Sets up the model mapper test.
	 */
	@Before
	public void setUp() {
		mapper = new ModelMapper();
		user = new User("a", "a", "a", 1);
		a = new Requirement(1, "a", "a", user.getUsername());
		b = new Requirement(2, "b", "b", null);
	}

	/**
	 * Test requirement mapping.
	 */
	@Test
	public void testRequirementMapping() {
		mapper.map(a, b);
		assertEquals(1, b.getId());
		assertEquals("a", b.getTitle());
		assertEquals("a", b.getDescription());
		assertSame(user.getUsername(), b.getCreator());
	}
	
	/**
	 * Test broken mapping.
	 */
	@Test(expected=RuntimeException.class)
	public void testBrokenMapping() {
		mapper.getBlacklist().remove("permission");
		mapper.map(a, b);
	}
	
	/**
	 * Test type mismatch.
	 */
	@Test
	public void testTypeMismatch() {
		mapper.map(user, a); // no shared fields, nothing happens, no exception
	}
	
	/**
	 * The Class TestMapCallback.
	 */
	public abstract class TestMapCallback implements MapCallback{
		List<String> receivedNames = new ArrayList<String>();
	}
	
	/**
	 * Test callback.
	 */
	@Test
	public void testCallback() {
		TestMapCallback callback = new TestMapCallback() {
			@Override
			public Object call(Model source, Model destination, String fieldName, Object sourceValue,
					Object destinationValue) {
				assertSame(a, source);
				assertSame(b, destination);
				receivedNames.add(fieldName);
				if(fieldName.equals("title")) {
					assertEquals("a", sourceValue);
					assertEquals("b", destinationValue);
				} else if(fieldName.equals("id")) {
					return -1;
				}
				return null;
			}
		};
		
	}
}
