package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AcceptanceTestTest {
	
	AcceptanceTest a;
	
	@Before
	public void setup(){
		a = new AcceptanceTest("A Title", "bodybodybodybodybodybody");
	}
	
	@Test
	public void testConstructor(){
		assertEquals(a.getTitle(), "A Title");
		assertEquals(a.getBody(), "bodybodybodybodybodybody");
	}

}
