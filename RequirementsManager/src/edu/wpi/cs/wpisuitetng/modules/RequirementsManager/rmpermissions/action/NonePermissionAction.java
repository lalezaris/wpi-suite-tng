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
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CancelRequirementController;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 2, 2013
 *
 */
public class NonePermissionAction extends AbstractAction {
	
	protected JList project, none;
	
	public NonePermissionAction(JList project, JList none){
		super(" Move to None ");
		this.project = project;
		this.none = none;
	} 
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("None button pressed");
//		JButton sourceButton = (JButton) ae.getSource();
		none.setListData(project.getSelectedValues());		
		System.out.println("None button execued");
	}
}
