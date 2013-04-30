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
 *  Chris Hanna
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AttachmentController;

/**
 * Save the attachments
 *
 * @author Chris Hanna
 *
 * @version Apr 21, 2013
 *
 */
@SuppressWarnings("serial")
public class AttachmentUploadAction extends AbstractAction{

	AttachmentController controller;
	
	/**
	 * Instantiates a new attachment upload action.
	 *
	 * @param controller the controller
	 * @param name the name
	 */
	public AttachmentUploadAction(AttachmentController controller, String name){
		super(name);
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.uploadClicked();
		
	}

	
	
}
