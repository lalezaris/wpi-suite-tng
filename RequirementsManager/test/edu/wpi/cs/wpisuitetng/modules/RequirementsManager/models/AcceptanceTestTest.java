package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.*;

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
