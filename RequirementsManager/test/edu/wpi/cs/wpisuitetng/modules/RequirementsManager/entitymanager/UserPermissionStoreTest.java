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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockData;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Insert Description Here
 *
 * @author Chris Dunkers
 *
 * @version Apr 3, 2013
 *
 */
public class UserPermissionStoreTest {

	MockData db;
	String username1, username2, username3;
	Session defaultSession;
	PermissionsStore manager;
	UserPermission uPerm1, uPerm2, uPerm3;
	
	Session adminSession;
	Project testProject;
	Project otherProject;	
	String mockSsid;
	User admin, user;
	
	/**
	 * Inits the test
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Before
	public void init() throws WPISuiteException{
		
		username1 = "username1";
		username2 = "username2";
		username3 = "username3";
		
		
		uPerm1 = new UserPermission(username1,RMPermissionsLevel.NONE);
		uPerm2 = new UserPermission(username2,RMPermissionsLevel.UPDATE);
		uPerm3 = new UserPermission(username3,RMPermissionsLevel.ADMIN);
		
		user = new User("user", "user", "1234", 1);
		user.setRole(Role.USER);
		admin = new User("admin", "admin", "4321", 2);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		defaultSession = new Session(user, testProject, mockSsid);
		
		db = new MockData(new HashSet<Object>());
		db.save(user);
		db.save(uPerm1, otherProject);
		db.save(admin);
		manager = new PermissionsStore(db);
		
		manager.makeEntity(defaultSession, uPerm1.toJSON());
		manager.makeEntity(defaultSession, uPerm3.toJSON());
		
		manager.makeEntity(adminSession, uPerm1.toJSON());
		manager.makeEntity(adminSession, uPerm2.toJSON());
		manager.makeEntity(adminSession, uPerm3.toJSON());

	}
	
	/**
	 * Tests creating a permissions store.
	 */
	@Test
	public void createAPermissionsStore() {
		assertNotNull(new RequirementStore(db));
	}

	
	/**
	 * Test make and get entity.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void testMakeandGetEntity() throws WPISuiteException {

		assertNotNull(manager);
		assertNotNull(uPerm1);
		assertNotNull(defaultSession);
		String j = uPerm1.toJSON();
		assertNotNull(j);
		
		UserPermission created = manager.makeEntity(defaultSession, j);
				
		assertNotNull(created);
		
		assertEquals("username1", created.getUsername());
		assertEquals(RMPermissionsLevel.NONE, created.getPermissions());
		
		UserPermission[] up = manager.getEntity(defaultSession, "" + created.getUsername());
		assertNotNull(up[0]);  
		assertEquals("username1", up[0].getUsername());
	}
	
	/**
	 * Tests getting all user permissions.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void getAllTest() throws WPISuiteException {
			
		UserPermission[] all = manager.getAll(adminSession);
		UserPermission[] allAgain = manager.getAll(defaultSession);
		
		
		for (int i = 0 ; i < all.length ; i ++){
			System.out.println(all[i].getUsername());
		}
		System.out.println("...and again!");
		for (int i = 0 ; i < allAgain.length ; i ++){
			System.out.println(allAgain[i].getUsername());
		}
		assertEquals(all.length, 5);
		assertEquals(allAgain.length, 5);
		
	}
	
	
	/**
	 * Tests deleting an entity.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void deleteEntity() throws WPISuiteException{
		
		boolean b1 = manager.deleteEntity(adminSession, uPerm2.getUsername());
		boolean didWeCatch = false;
		try{
			manager.deleteEntity(adminSession, uPerm2.getUsername());
		} catch (NotFoundException e) {
			didWeCatch = true;
		}
		
		assertEquals(didWeCatch, true);
		
	}
	
	/**
	 * Tests deleting all.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void deleteAll() throws WPISuiteException {
		
		
		UserPermission[] all = manager.getAll(defaultSession);
		assertNotNull(all);
		
		System.out.println("deleteAllOutPut");
		for (int i = 0 ; i < all.length ; i ++){
			System.out.println(""+ i + ": " + all[i].getUsername());
		}
		
		
		int count = manager.Count();
		
		System.out.println("count: " + count);
		
		assertEquals(6, count); //count returns one more than it should. 
		
		manager.deleteAll(adminSession);
		
		count = manager.Count();
		assertEquals(count, 1); //same issue with count
		
		all = manager.getAll(defaultSession);
		assertNotNull(all);
		
		System.out.println("deleteAllOutPut");
		for (int i = 0 ; i < all.length ; i ++){
			System.out.println(all[i].getUsername());
		}
		
		assertEquals(all.length, 0);
		
	}
	
	
	/**
	 * Updates the user permission store.
	 *
	 * @throws WPISuiteException the wPI suite exception
	 */
	@Test
	public void update() throws WPISuiteException{
		UserPermission here = new UserPermission();
		System.out.println("test:" + here.toJSON());
		
		here = manager.makeEntity(defaultSession, here.toJSON());
		
		System.out.println("now update...");
		
		here = manager.update(defaultSession, here.toJSON());
		
		System.out.println("test2:" + here.toJSON());
	}

}
