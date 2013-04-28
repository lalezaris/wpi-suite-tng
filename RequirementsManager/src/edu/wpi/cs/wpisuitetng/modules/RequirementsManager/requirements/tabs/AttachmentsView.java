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
 *
 * Contributors:
 *  Chris Dunkers
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAttachmentController;

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
	private Requirement requirement;
	private JButton addFileButton, uploadFileButton;

	private ArrayList<File> selectedFiles, attachedFiles;
	private JPanel mainPanel, selectedPanel, attachedPanel, selectedButtonPanel, attachedButtonPanel;
	
	private JLabel selectLabel, uploadLabel, selectedLabel, attachedLabel;
	
	private JLabel dummy = new JLabel(" ");
	int selectedDummyInc = 0, attachedDummyInc = 0;
	/**
	 * Instantiates a new attachments view.
	 *
	 * @param parent the requirement view for the assignee view
	 */
	public AttachmentsView(RequirementView parent){
		this.parent = parent;
		this.requirement = parent.getReqModel().getUneditedRequirement();
		
		this.selectedFiles = new ArrayList<File>();
		this.attachedFiles = new ArrayList<File>();
		
		this.addFileButton = new JButton("Choose");
		this.uploadFileButton = new JButton("Upload");
		this.uploadFileButton.setVisible(false);
		
		this.selectLabel = new JLabel("Select New File  ");
		this.uploadLabel = new JLabel("Upload File  ");
		this.selectedLabel = new JLabel("Selected File");
		this.attachedLabel = new JLabel("Attached Files");
		
		this.mainPanel = new JPanel();
		this.mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		this.selectedPanel = new JPanel();
		this.selectedPanel.setLayout(new GridBagLayout());

		JScrollPane selectedScrollPane = new JScrollPane(selectedPanel);
		selectedScrollPane.setPreferredSize(new Dimension(450,150));
		selectedScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.attachedPanel = new JPanel();
		this.attachedPanel.setLayout(new GridBagLayout());
		JScrollPane attachedScrollPane = new JScrollPane(attachedPanel);
		attachedScrollPane.setPreferredSize(new Dimension(450,150));
		attachedScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.selectedButtonPanel = new JPanel();
		this.selectedButtonPanel.setLayout(new GridBagLayout());
		
		attachedButtonPanel = new JPanel();
		attachedButtonPanel.setLayout(new GridBagLayout());
		
		c.weightx = .5;
		c.weighty = .5;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.selectedButtonPanel.add(selectLabel,c);
		
		c.gridy = 0;
		c.gridx = 1;
		this.selectedButtonPanel.add(addFileButton,c);
		
		c.gridx = 0;
		c.gridy = 1;

		this.selectedButtonPanel.add(selectedLabel, c);
		
		c.gridx = 0;
		c.gridy = 0;

		this.mainPanel.add(selectedButtonPanel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.mainPanel.add(selectedScrollPane, c);
		
		c.gridx = 0;
		c.gridy = 0;
		this.attachedButtonPanel.add(uploadLabel,c);
		c.gridx = 1;
		c.gridy = 0;

		this.attachedButtonPanel.add(uploadFileButton,c);
		

		c.gridx = 0;
		c.gridy = 1;

		this.attachedButtonPanel.add(attachedLabel,c);

		
		
		c.gridx = 0;
		c.gridy = 2;
		this.mainPanel.add(attachedButtonPanel, c);
		
		
		c.gridy = 3;
		c.gridx = 0;
		this.mainPanel.add(attachedScrollPane, c);

		ArrayList<String> attachmentNames = requirement.getAttachedFileNames();
		ArrayList<Integer> attachmentIDs = requirement.getAttachedFileId();
		for(int i = 0; i < attachmentIDs.size(); i++){
			this.addFileToAttached(new File(attachmentIDs.get(i)+"/"+attachmentNames.get(i)));
		}
		this.add(this.mainPanel);
		
	}

	/**
	 * A panel for each selected file.
	 */
	private class SelectedPanel extends JPanel{
		
		private JLabel text;
		private JButton delete;
		private File file;
		
		/**
		 * The panel in which files to be uploaded are displayed.
		 *
		 * @param f the file to be displayed.
		 */
		public SelectedPanel(File f){
			System.out.println("made a new selectedpanel called: " +f.getPath());
			this.file = f;
			text = new JLabel(f.getName());
			delete = new JButton("Remove");
			final SelectedPanel fp = this;
			delete.setAction( new AbstractAction("X"){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					removeFileFromSelected(fp);
				}
				
			});
			this.add(delete);
			this.add(text);	
		}	
	}
	
	/*
	 * Panel for attached files.
	 */
	private class AttachedPanel extends JPanel{
		
		private JLabel text;
		private JButton delete;
		public JButton download;
		private File file;
		
		/**
		 * The panel for files that are attached.
		 *
		 * @param f the file that is attached.
		 */
		public AttachedPanel(File f){
			System.out.println("made an attachedpanel with file: "+f.getPath());
			this.file = f;
			text = new JLabel(f.getName());
			delete = new JButton("delete");
			delete.setVisible(false);
			download = new JButton("download");
			download.setAction(new AbstractAction("download"){
				@Override
				public void actionPerformed(ActionEvent e) {
					downloadFile(file);
				}
			});
			
			final AttachedPanel ap = this;
			delete.setAction( new AbstractAction("delete"){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					removeFileFromAttached(ap);
				}
				
			});
			this.add(download);
			this.add(text);	
			this.add(delete);
		}	
	}
	
	/**
	 * download the file to the user's computer.
	 *
	 * @param f the file to download.
	 */
	public void downloadFile(File f){
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
        if( chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION ){
            //didn't pick anything.
        	return;
        }
        File target = chooser.getSelectedFile();
        target = new File(target.getPath()+"\\"+f.getName());
        RetrieveAttachmentController attcontroller = new RetrieveAttachmentController(f.getPath(), target);
        attcontroller.fetch();
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
	
	/**
	 * Sets the attachments list.
	 *
	 * @param l the new attachments list
	 */
	public void setAttachmentsList(List<Object> l) {
		// TODO actually save the attachments list
		// TODO change 'Object' to 'Attachment'
	}
	
	/**
	 * Clear the attached files
	 * 
	 */
	public void clearAttachedFiles(){
		this.attachedFiles.clear();
		this.attachedPanel.removeAll();
	}
	
	
	/**
	 * Append some attached files
	 * 
	 * @param files
	 */
	public void addAttachedFiles(File[] files){
		for (int i = 0 ; i < files.length; i ++)
			this.addFileToAttached(files[i]);
	}
	
	//returns the current file to upload
	//the "current" file should be set whenever the upload action is taken
	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile(){

		//If you want ALL of the files, use this.selectedFiles, which is an arrayList.
		//the files array just contains the last selected files from the window. I should probably get 
		//rid of it... but eeeeh.
		if (this.selectedFiles != null && this.selectedFiles.size() > 0)
			return this.selectedFiles.get(0);
		else return null;

		
	}

	/**
	 * Get all of the files from the selected panel.
	 *
	 * @return all selected files
	 */
	public File[] getAllSelectedFiles(){
		File[] allFiles = new File[selectedFiles.size()];
		for (int i = 0 ; i < allFiles.length ; i ++)
			allFiles[i] = selectedFiles.get(i);
		return allFiles;
	}

	/**
	 * Get all the files from the attached panel.
	 *
	 * @return all attached files
	 */
	public File[] getAllAttachedFiles(){
		File[] allFiles = new File[attachedFiles.size()];
		for (int i = 0 ; i < allFiles.length ; i ++)
			allFiles[i] = attachedFiles.get(i);
		return allFiles;
	}
	
	/**
	 * Clear the files from the selected panel
	 * 
	 */
	public void clearSelectedFiles(){
		this.selectedFiles.clear();
		this.selectedPanel.removeAll();
		System.out.println("just cleared all of the selected files");
	}
	
	/**
	 * Set the selected files
	 * 
	 * @param selectedFiles
	 */
	public void setSelectedFiles(File[] selectedFiles){
		this.clearSelectedFiles();
		this.addSelectedFiles(selectedFiles);
	}
	
	/**
	 * Add an array of files to the selected panel.
	 * 
	 * @param selectedFiles
	 */
	public void addSelectedFiles(File[] selectedFiles) {
		System.out.println("addSelectedFiles("+selectedFiles+") called");
		for (int i = 0 ; i < selectedFiles.length ; i ++){
			this.addFileToSelected(selectedFiles[i]);
		}
		
	}
		
	/**	
	 * Removes an attached file
	 * 
	 * @param ap 
	 */
	private void removeFileFromAttached(AttachedPanel ap){
		//TODO: hook this up with mister abradi's server stuff
	}
	
	private void removeFileFromSelected(SelectedPanel sp){
		if(selectedFiles.size() == 0){
			this.addFileButton.setVisible(true);
		}
		selectedFiles.remove(sp.file);
		selectedPanel.remove(sp);
		selectedPanel.revalidate();
		selectedPanel.repaint();
	}
	
	/**
	 * Adds the file to selected.
	 *
	 * @param f the file to add
	 */
	public void addFileToSelected(File f){
		this.uploadFileButton.setVisible(true);
		selectedFiles.add(f);
		SelectedPanel fp = new SelectedPanel(f);
		fp.setBorder(BorderFactory.createLineBorder(Color.black));
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridy = selectedDummyInc;
		selectedDummyInc++;
		selectedPanel.add(fp,c);
		for (int i = 0 ; i < selectedPanel.getComponentCount(); i ++)
			if (selectedPanel.getComponent(i).equals(dummy))
				selectedPanel.remove(dummy);
		c.weighty = 1;
		selectedPanel.add(dummy,c);
		selectedPanel.revalidate();
		selectedPanel.repaint();
	}
	
	private void addFileToAttached(File f){
		attachedFiles.add(f);
		AttachedPanel ap = new AttachedPanel(f);
		ap.setBorder(BorderFactory.createLineBorder(Color.black));

		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridy = attachedDummyInc;
		attachedDummyInc++;
		attachedPanel.add(ap,c);
		for (int i = 0 ; i < attachedPanel.getComponentCount(); i ++)
			if (attachedPanel.getComponent(i).equals(dummy))
				attachedPanel.remove(dummy);
		c.weighty = 1;
		attachedPanel.add(dummy,c);
		attachedPanel.revalidate();
		attachedPanel.repaint();
	}


	/**
	 * This will update the panel to show that the file has been saved.
	 * It assumes that the names of the selected files are unique.
	 *
	 * @param attachment the attachment
	 */
	public void attachmentSaveSuccess(Attachment attachment) {
		this.addFileToAttached(
				new File(attachment.getId()+"\\"+attachment.getFileName()));
		File added = null;
		for(int i=0; i<this.selectedFiles.size(); i++){
			if(selectedFiles.get(i).getName().equals(attachment.getFileName())){
				added = selectedFiles.remove(i);
				break;
			}
		}
		if(added != null){//shouldn't ever be null, right?
			ArrayList<File> toAdd = (ArrayList<File>) selectedFiles.clone();
			this.clearSelectedFiles();
			for(File f: toAdd){
				this.addFileToAttached(f);
			}
		}
	}

}
