package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager.ModelMapper.MapCallback;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * 
 * @author Tianyu Li
 * 
 * @version Apr 2, 2013
 *
 */
public class ModelMapperTest {


	ModelMapper mapper;
	User user;
	Requirement a;
	Requirement b;
	
	@Before
	public void setUp() {
		mapper = new ModelMapper();
		user = new User("a", "a", "a", 1);
		a = new Requirement(1, "a", "a", user.getUsername());
		b = new Requirement(2, "b", "b", null);
	}

	@Test
	public void testRequirementMapping() {
		mapper.map(a, b);
		assertEquals(1, b.getId());
		assertEquals("a", b.getTitle());
		assertEquals("a", b.getDescription());
		assertSame(user.getUsername(), b.getCreator());
	}
	
	@Test(expected=RuntimeException.class)
	public void testBrokenMapping() {
		mapper.getBlacklist().remove("permission");
		mapper.map(a, b);
	}
	
	@Test
	public void testTypeMismatch() {
		mapper.map(user, a); // no shared fields, nothing happens, no exception
	}
	
	public abstract class TestMapCallback implements MapCallback{
		List<String> receivedNames = new ArrayList<String>();
	}
	
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
		
		//TODO Need to complete after the iteration be completed.
		
//		mapper.map(a, b, callback);
//		// not an exhaustive list
//		assertTrue(callback.receivedNames.containsAll(Arrays.asList("id", "title", "lastModifiedDate")));
//		// make sure mapper honored callback return value - again, not exhaustive
//		assertEquals(-1, b.getId());
//		assertNull(b.getTitle());
//		assertNull(b.getDescription());
//		assertNull(b.getCreator());
	}
}
