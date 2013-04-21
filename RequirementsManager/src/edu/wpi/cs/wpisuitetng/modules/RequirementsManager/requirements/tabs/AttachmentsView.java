/**************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * @author "Michael Perrone"
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * The Class to hold AssigneeView.
 *
 * @author Michael Perrone
 *
 * @version Apr 21, 2013
 *
 */
@SuppressWarnings("serial")
public class AttachmentsView extends RequirementTab{


	private RequirementView parent;
	private JButton addFileButton, uploadFileButton;
	private File[] files;

	/**
	 * Instantiates a new attachments view.
	 *
	 * @param parent the requirement view for the assignee view
	 */
	public AttachmentsView(RequirementView parent){
		this.parent = parent;
		
		addFileButton = new JButton("Add File");
		uploadFileButton = new JButton("Upload File");
		
		
		this.add(addFileButton);
		this.add(uploadFileButton);
	}

	
	
	/**
	 * Gets the addFileButton
	 * @return the addFileButton
	 */
	public JButton getAddFileButton() {
		return addFileButton;
	}



	/**
	 * Gets the uploadFileButton
	 * @return the uploadFileButton
	 */
	public JButton getUploadFileButton() {
		return uploadFileButton;
	}



	@Override
	public String getTabTitle() {
		return "Attachments";
	}

	@Override
	public ImageIcon getImageIcon() {
		return new ImageIcon();
	}

	@Override
	public String getTooltipText() {
		return "Add files to this requirement";
	}

	public List<Object> getAttachmentsList() {
		// TODO actually return the attachments list
		// TODO change 'Object' to 'Attachment'
		return null;
	}
	
	public void setAttachmentsList(List<Object> l) {
		// TODO actually save the attachments list
		// TODO change 'Object' to 'Attachment'
	}



	public void setFiles(File[] selectedFiles) {
		files = selectedFiles;
	}
		

}