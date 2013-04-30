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
 * Michael Perrone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import javax.swing.JFileChooser;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.AttachmentAddAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.AttachmentUploadAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AttachmentsView;

/**
 * Controls the behaviors for adding and uploading attachments to a requirement.
 * 
 * @author Sam Abradi
 */
public class AttachmentController {
	private int reqID;

	AttachmentsView view;
	
	/**
	 * Instantiates a new attachment controller.
	 *
	 * @param view the parent view
	 * @param reqID the requirement id
	 */
	public AttachmentController (AttachmentsView view, int reqID){
		this.reqID =reqID;
		this.view = view;
		
		this.view.getAddFileButton().setAction( new AttachmentAddAction(this, this.view.getAddFileButton().getText()));
		this.view.getUploadFileButton().setAction( new AttachmentUploadAction(this, this.view.getUploadFileButton().getText()));
	}
	
	
	/**
	 * Adds a file to the clicked pane.
	 */
	public void addClicked(){
		JFileChooser j = new JFileChooser();
		j.setMultiSelectionEnabled(false);
        
        if( j.showOpenDialog(null) != JFileChooser.APPROVE_OPTION ){
            //didn't pick anything.
        	return;
        }
        view.getAddFileButton().setEnabled(false);
        view.getUploadFileButton().setEnabled(true);
        view.addFileToSelected(j.getSelectedFile());
	}
	
	/**
	 * Upload "clicked" files to the database.
	 */
	public void uploadClicked(){
		SaveAttachmentController controller = new SaveAttachmentController(view);
		controller.save(reqID);
		view.getUploadFileButton().setEnabled(false);
		view.getAddFileButton().setEnabled(true);
	}
	
}
