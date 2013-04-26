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
public class AttachmentUploadAction extends AbstractAction{

	AttachmentController controller;
	public AttachmentUploadAction(AttachmentController controller, String name){
		super(name);
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.uploadClicked();
		
	}

	
	
}