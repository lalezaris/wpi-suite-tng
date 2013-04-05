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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

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
	
	@Before
	public void setup() {
		MainTabView tabView = new MainTabView();
		Tab tab = new Tab(tabView, new JPanel());
		panel = new UserPermissionPanel(new UserPermissionView(tab));
		panel.noneUsersList.addElement("1");
		panel.noneUsersList.addElement("2");
		panel.noneUsersList.addElement("3");
		panel.updateUsersList.addElement("4");
		panel.updateUsersList.addElement("5");
		panel.updateUsersList.addElement("6");
		panel.adminUsersList.addElement("7");
		panel.adminUsersList.addElement("8");
		panel.adminUsersList.addElement("9");
	}
	
	@Test
	public void canMoveUpdateAndAdminUserToNoneUser() {
		assertEquals(3, panel.noneUsers.getModel().getSize());
		assertEquals(3, panel.updateUsers.getModel().getSize());
		assertEquals(3, panel.adminUsers.getModel().getSize());
		panel.updateUsers.setSelectedIndex(0);
		panel.adminUsers.setSelectedIndex(1);
		panel.btnNone.doClick();
		assertEquals(5, panel.noneUsers.getModel().getSize());
		assertEquals(2, panel.updateUsers.getModel().getSize());
		assertEquals(2, panel.adminUsers.getModel().getSize());
		String firstItemNone = (String) panel.noneUsers.getModel().getElementAt(0);
		String firstItemUpdate = (String) panel.updateUsers.getModel().getElementAt(0);
		String firstItemAdmin = (String) panel.adminUsers.getModel().getElementAt(0);
		assertEquals("1", firstItemNone);
		assertEquals("5", firstItemUpdate);
		assertEquals("7", firstItemAdmin);
	}
	
	@Test
	public void canMoveNoneAndAdminUserToUpdateUser() {
		assertEquals(3, panel.noneUsers.getModel().getSize());
		assertEquals(3, panel.updateUsers.getModel().getSize());
		assertEquals(3, panel.adminUsers.getModel().getSize());
		panel.noneUsers.setSelectedIndex(2);
		panel.adminUsers.setSelectedIndex(1);
		panel.btnUpdate.doClick();
		assertEquals(2, panel.noneUsers.getModel().getSize());
		assertEquals(5, panel.updateUsers.getModel().getSize());
		assertEquals(2, panel.adminUsers.getModel().getSize());
		String firstItemNone = (String) panel.noneUsers.getModel().getElementAt(0);
		String firstItemUpdate = (String) panel.updateUsers.getModel().getElementAt(0);
		String firstItemAdmin = (String) panel.adminUsers.getModel().getElementAt(0);
		assertEquals("1", firstItemNone);
		assertEquals("4", firstItemUpdate);
		assertEquals("7", firstItemAdmin);
	}
	
	@Test
	public void canMoveNoneAndUpdateUserToAdminUser() {
		assertEquals(3, panel.noneUsers.getModel().getSize());
		assertEquals(3, panel.updateUsers.getModel().getSize());
		assertEquals(3, panel.adminUsers.getModel().getSize());
		panel.noneUsers.setSelectedIndex(0);
		panel.updateUsers.setSelectedIndex(0);
		panel.btnAdmin.doClick();
		assertEquals(2, panel.noneUsers.getModel().getSize());
		assertEquals(2, panel.updateUsers.getModel().getSize());
		assertEquals(5, panel.adminUsers.getModel().getSize());
		String firstItemNone = (String) panel.noneUsers.getModel().getElementAt(0);
		String firstItemUpdate = (String) panel.updateUsers.getModel().getElementAt(0);
		String firstItemAdmin = (String) panel.adminUsers.getModel().getElementAt(0);
		assertEquals("2", firstItemNone);
		assertEquals("5", firstItemUpdate);
		assertEquals("7", firstItemAdmin);
	}
}

