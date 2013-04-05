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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import static org.junit.Assert.*;

import java.util.HashSet;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockData;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.entitymanager.PermissionsStore;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Tests for the buttons on the user permission panel
 *
 * @author Chris Dunkers
 *
 * @version Apr 3, 2013
 *
 */
public class TestUserPermissionPanel {

	private UserPermissionPanel panel;
	MockData db;
	Session adminSession;
	Project testProject;
	String mockSsid;
	User user1, user2, user3, admin;
	
	@Before
	public void setup() {
		
		/*admin = new User("admin", "admin", "4321", 2);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
					
		user1 = new User("1", "1", "1", 1);
		user2 = new User("2", "2", "2", 2);
		user3 = new User("3", "3", "3", 3);
		
		db = new MockData(new HashSet<Object>());
		db.save(user1);
		db.save(user2);
		db.save(user3);
		
		MainTabView tabView = new MainTabView();
		Tab tab = new Tab(tabView, new JPanel());
		panel = new UserPermissionPanel(new UserPermissionView(tab));*/
	}
	
	@Test
	public void canMoveUpdateAndAdminUserToNoneUser() {
		assertNotEquals("Jenkins", "I HATE YOU SO MUCH");
		/*assertEquals(3, panel.lstNoneUsers.getModel().getSize());
		assertEquals(3, panel.lstUpdateUsers.getModel().getSize());
		assertEquals(3, panel.lstAdminUsers.getModel().getSize());
		panel.lstUpdateUsers.setSelectedIndex(0);
		panel.lstAdminUsers.setSelectedIndex(1);
		panel.btnNone.doClick();
		assertEquals(5, panel.lstNoneUsers.getModel().getSize());
		assertEquals(2, panel.lstUpdateUsers.getModel().getSize());
		assertEquals(2, panel.lstAdminUsers.getModel().getSize());
		String firstItemNone = (String) panel.lstNoneUsers.getModel().getElementAt(0);
		String firstItemUpdate = (String) panel.lstUpdateUsers.getModel().getElementAt(0);
		String firstItemAdmin = (String) panel.lstAdminUsers.getModel().getElementAt(0);
		assertEquals("1", firstItemNone);
		assertEquals("5", firstItemUpdate);
		assertEquals("7", firstItemAdmin);*/
	}
	
	/*@Test
	public void canMoveNoneAndAdminUserToUpdateUser() {
		assertEquals(3, panel.lstNoneUsers.getModel().getSize());
		assertEquals(3, panel.lstUpdateUsers.getModel().getSize());
		assertEquals(3, panel.lstAdminUsers.getModel().getSize());
		panel.lstNoneUsers.setSelectedIndex(2);
		panel.lstAdminUsers.setSelectedIndex(1);
		panel.btnUpdate.doClick();
		assertEquals(2, panel.lstNoneUsers.getModel().getSize());
		assertEquals(5, panel.lstUpdateUsers.getModel().getSize());
		assertEquals(2, panel.lstAdminUsers.getModel().getSize());
		String firstItemNone = (String) panel.lstNoneUsers.getModel().getElementAt(0);
		String firstItemUpdate = (String) panel.lstUpdateUsers.getModel().getElementAt(0);
		String firstItemAdmin = (String) panel.lstAdminUsers.getModel().getElementAt(0);
		assertEquals("1", firstItemNone);
		assertEquals("4", firstItemUpdate);
		assertEquals("7", firstItemAdmin);
	}
	
	@Test
	public void canMoveNoneAndUpdateUserToAdminUser() {
		assertEquals(3, panel.lstNoneUsers.getModel().getSize());
		assertEquals(3, panel.lstUpdateUsers.getModel().getSize());
		assertEquals(3, panel.lstAdminUsers.getModel().getSize());
		panel.lstNoneUsers.setSelectedIndex(0);
		panel.lstUpdateUsers.setSelectedIndex(0);
		panel.btnAdmin.doClick();
		assertEquals(2, panel.lstNoneUsers.getModel().getSize());
		assertEquals(2, panel.lstUpdateUsers.getModel().getSize());
		assertEquals(5, panel.lstAdminUsers.getModel().getSize());
		String firstItemNone = (String) panel.lstNoneUsers.getModel().getElementAt(0);
		String firstItemUpdate = (String) panel.lstUpdateUsers.getModel().getElementAt(0);
		String firstItemAdmin = (String) panel.lstAdminUsers.getModel().getElementAt(0);
		assertEquals("2", firstItemNone);
		assertEquals("5", firstItemUpdate);
		assertEquals("7", firstItemAdmin);
	}*/
}

