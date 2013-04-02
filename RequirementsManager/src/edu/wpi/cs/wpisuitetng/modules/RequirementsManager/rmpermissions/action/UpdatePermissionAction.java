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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JList;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 2, 2013
 *
 */
public class UpdatePermissionAction extends AbstractAction {
	
	protected JList project, update;
	
	public UpdatePermissionAction(JList project, JList update){
		super("Move to Update");
		this.project = project;
		this.update = update;
	}
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		update.setListData(project.getSelectedValues());	
		
	}
}
