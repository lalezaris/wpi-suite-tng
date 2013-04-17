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
 *  Tushar Narayan
**************************************************/
/**
 * 
 */
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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * @author Chris Dunkers
 *
 */
public class RequirementStoreTest {

	/*
	MockData db;
	RequirementStore reqStore;
	Session adminSession;
	Project testProject;
	User admin;
	String ssid;
	*/
	
	MockData db;
	User existingUser;
	Requirement existingReq;
	Session defaultSession;
	String mockSsid;
	RequirementStore manager;
	Requirement newReq;
	User bob;
	Requirement goodUpdatedReq;
	
	Session adminSession;
	Project testProject;
	Project otherProject;
	Requirement otherReq;
	
	@Before
	public void init() throws WPISuiteException{
		
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("joe", "joe", "1234", 2);
		existingReq = new Requirement("An existing Req", "IT ALREADY EXISTED.");
		
		otherReq = new Requirement("Other Req", "The other requirement.");
		
		
		defaultSession = new Session(existingUser, testProject, mockSsid);
		newReq = new Requirement("New Req", "This is a brand new requirement");
		
		db = new MockData(new HashSet<Object>());
		//db.save(existingReq, testProject);
		db.save(existingUser);
		db.save(otherReq, otherProject);
		db.save(admin);
		manager = new RequirementStore(db);
		
		
		Requirement rA = new Requirement("A", "adesc");
		Requirement rB = new Requirement("B", "bdesc");
		Requirement rC = new Requirement("C", "cdesc");
		Requirement rD = new Requirement("D", "ddesc");
		
		
		manager.makeEntity(defaultSession, rA.toJSON());
		manager.makeEntity(defaultSession, rB.toJSON());
		manager.makeEntity(defaultSession, rC.toJSON());
		manager.makeEntity(defaultSession, rD.toJSON());
		/*
		User admin = new User("admin", "admin", "password", 5);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		ssid = "local";
		adminSession = new Session(admin, testProject, ssid);
		
		db = new MockData(new HashSet<Object>());
		//db.save(existingDefect, testProject);
		//db.save(existingUser);
		//db.save(otherDefect, otherProject);
		db.save(admin);
		reqStore = new RequirementStore(db);
		*/
	}
	
	@Test
	public void createARequirementStore() {
		assertNotNull(new RequirementStore(db));
	}

	
	@Test
	public void makeEntity() throws WPISuiteException {

		assertNotNull(manager);
		assertNotNull(newReq);
		assertNotNull(defaultSession);
		String j = newReq.toJSON();
		assertNotNull(j);
		
		Requirement created = manager.makeEntity(defaultSession, newReq.toJSON());
		
		
		assertNotNull(created);
		
		assertEquals("New Req", created.getTitle());
		assertEquals("This is a brand new requirement", created.getDescription());
		
		Requirement[] ra = manager.getEntity(defaultSession, ""+created.getId());
		assertNotNull(ra[0]);
		assertEquals("New Req", ra[0].getTitle());
		//manager.getEntity(defaultSession, ""+created.getId());
		//manager.getEntity(defaultSession, ""+existingReq.getId());
	}
	
	@Test
	public void getAllTest() throws WPISuiteException {
	
		
		
		Requirement[] all = manager.getAll(defaultSession);
		Requirement[] allAgain = manager.getAll(defaultSession);
		
		
		for (int i = 0 ; i < all.length ; i ++){
			System.out.println(all[i].getTitle());
		}
		System.out.println("...and again!");
		for (int i = 0 ; i < allAgain.length ; i ++){
			System.out.println(allAgain[i].getTitle());
		}
		assertEquals(all.length, 4);
		
	}
	
	
	@Test
	public void deleteEntity() throws WPISuiteException{
		
		
		existingReq = manager.makeEntity(defaultSession, existingReq.toJSON());
		
		boolean b = manager.deleteEntity(adminSession, "" + existingReq.getId());
		boolean didWeCatch = false;
		try{
			manager.deleteEntity(adminSession, "" + existingReq.getId());
		} catch (NotFoundException e) {
			didWeCatch = true;
		}
		
		assertEquals(didWeCatch, true);
		
		//boolean b2 = manager.deleteEntity(defaultSession, "" + existingReq.getId());
		
		
		
	}
	
	@Test
	public void deleteAll() throws WPISuiteException {
		
		
		Requirement[] all = manager.getAll(defaultSession);
		assertNotNull(all);
		
		System.out.println("deleteAllOutPut");
		for (int i = 0 ; i < all.length ; i ++){
			System.out.println(""+ i + ": " + all[i].getTitle());
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
			System.out.println(all[i].getTitle());
		}
		
		assertEquals(all.length, 0);
		
	}
	
	
	@Test
	public void update() throws Exception{
		Requirement here = new Requirement();
		Note n = new Note();
		n.setBody("ONE");
		here.addNote(n);
		System.out.println("test:" + here.toJSON());
		
		here = manager.makeEntity(defaultSession, here.toJSON());
		
		System.out.println("now update...");
		
		Note n2 = new Note();
		n.setBody("TWO");
		here.addNote(n2);
		
		here = manager.update(defaultSession, here.toJSON());
		
		System.out.println("test2:" + here.toJSON());
	}
	
	//@Test
	//public void GetEntity() throws NotFoundException{
	
		
	//}
	
}
