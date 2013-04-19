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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.AdminPermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.NonePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdatePermissionController;

/**
 * Tests for the buttons on the user permission panel
 *
 * @author Chris Dunkers
 *
 * @version Apr 3, 2013
 *
 */
public class UserPermissionPanelTest {

	private UserPermissionPanel panel;
		
	@Before
	public void setup() {
		
		panel = new UserPermissionPanel(null);
		panel.getNoneUsersList().addElement("1");
		panel.noneUsersList.addElement("2");
		panel.noneUsersList.addElement("3");
		panel.getUpdateUsersList().addElement("4");
		panel.updateUsersList.addElement("5");
		panel.updateUsersList.addElement("6");
		panel.getAdminUsersList().addElement("7");
		panel.adminUsersList.addElement("8");
		panel.adminUsersList.addElement("9");
		panel.getBtnAdmin().addActionListener(new AdminPermissionController(panel));
		panel.getBtnNone().addActionListener(new NonePermissionController(panel));
		panel.getBtnUpdate().addActionListener(new UpdatePermissionController(panel));
	}
	
	@Test
	public void canMoveUpdateAndAdminUserToNoneUser() {
		assertEquals(3, panel.lstNoneUsers.getModel().getSize());
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
		assertEquals("7", firstItemAdmin);
	}
	
	@Test
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
	}
}

