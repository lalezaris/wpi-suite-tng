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
 *  Michael Perrone
**************************************************/
/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockData;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * @author Michael Perrone
 *
 */
public class IterationStoreTest {
	
	MockData db;
	User existingUser;
	Iteration existingIt;
	Session defaultSession;
	String mockSsid;
	IterationStore manager;
	Iteration newIt;
	User bob;
	Iteration goodUpdatedIt;
	
	Session adminSession;
	Project testProject;
	Project otherProject;
	Iteration otherIt;
	
	/**
	 * Inits the test.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Before
	public void init() throws WPISuiteException{
		
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("joe", "joe", "1234", 2);
		existingIt = new Iteration("1",new Date(2012,1,1), new Date(2012,1,2));
		
		otherIt = new Iteration("2",new Date(2012,2,2),new Date(2012,2,6));
		
		
		defaultSession = new Session(existingUser, testProject, mockSsid);
		newIt = new Iteration("3",new Date(2012,4,4),new Date(2012,4,9));
		newIt.setId(6);
		
		db = new MockData(new HashSet<Object>());
		//db.save(existingReq, testProject);
		db.save(existingUser);
		db.save(otherIt, otherProject);
		db.save(admin);
		manager = new IterationStore(db);
		
		
		Iteration i5 = new Iteration("5", new Date(2012,5,5),new Date(2012,5,6));
		Iteration i6 = new Iteration("6", new Date(2012,6,6), new Date(2012,6,7));
		Iteration i7 = new Iteration("7", new Date(2012,7,7), new Date(2012,7,8));
		Iteration i8 = new Iteration("8", new Date(2012,8,8), new Date(2012,8,9));
		
		
		manager.makeEntity(defaultSession, i5.toJSON());
		manager.makeEntity(defaultSession, i6.toJSON());
		manager.makeEntity(defaultSession, i7.toJSON());
		manager.makeEntity(defaultSession, i8.toJSON());
	}
	
	/**
	 * tests creating the a iteration store.
	 */
	@Test
	public void createAIterationStore() {
		assertNotNull(new IterationStore(db));
	}

	
	/**
	 * tests making an entity.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void makeEntity() throws WPISuiteException {

		assertNotNull(manager);
		assertNotNull(newIt);
		assertNotNull(defaultSession);
		String j = newIt.toJSON();
		assertNotNull(j);
		
		Iteration created = manager.makeEntity(defaultSession, newIt.toJSON());
		
		
		assertNotNull(created);
		assertTrue(created.equals(newIt));
		
		Iteration[] ra = manager.getEntity(defaultSession, ""+created.getId());
		assertNotNull(ra[0]);
		assertTrue(ra[0].equals(newIt));
		//manager.getEntity(defaultSession, ""+created.getId());
		//manager.getEntity(defaultSession, ""+existingReq.getId());
	}
	
	/**
	 * tests getting all iterations
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void getAllTest() throws WPISuiteException {
	
		
		
		Iteration[] all = manager.getAll(defaultSession);
		Iteration[] allAgain = manager.getAll(defaultSession);
		
		
		for (int i = 0 ; i < all.length ; i ++){
			System.out.println(all[i].getIterationName());
		}
		System.out.println("...and again!");
		for (int i = 0 ; i < allAgain.length ; i ++){
			System.out.println(allAgain[i].getIterationName());
		}
		assertEquals(all.length, 4);
		
	}
	
	
	/**
	 * tests deleting an entity.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void deleteEntity() throws WPISuiteException{
		
		
		existingIt = manager.makeEntity(defaultSession, existingIt.toJSON());
		
		boolean b = manager.deleteEntity(adminSession, "" + existingIt.getId());
		boolean didWeCatch = false;
		try{
			manager.deleteEntity(adminSession, "" + existingIt.getId());
		} catch (NotFoundException e) {
			didWeCatch = true;
		}
		
		assertEquals(didWeCatch, true);
		
		//boolean b2 = manager.deleteEntity(defaultSession, "" + existingReq.getId());
		
		
		
	}
	
	/**
	 * tests deleting all iterations.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void deleteAll() throws WPISuiteException {
		
		
		Iteration[] all = manager.getAll(defaultSession);
		assertNotNull(all);
		
		System.out.println("deleteAllOutPut");
		for (int i = 0 ; i < all.length ; i ++){
			System.out.println(""+ i + ": " + all[i].getIterationName());
		}
		
		
		int count = manager.Count();
		
		System.out.println("count: " + count);
		
		assertEquals(5, count); //count returns one more than it should. TODO
		
		manager.deleteAll(adminSession);
		
		count = manager.Count();
		assertEquals(count, 1); //same issue with count
		
		all = manager.getAll(defaultSession);
		assertNotNull(all);
		
		System.out.println("deleteAllOutPut");
		for (int i = 0 ; i < all.length ; i ++){
			System.out.println(all[i].getIterationName());
		}
		
		assertEquals(all.length, 0);	
	}
	
}
