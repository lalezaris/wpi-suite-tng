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
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

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
		
		this.selectedFiles = new ArrayList<File>();
		this.attachedFiles = new ArrayList<File>();
		
		this.addFileButton = new JButton("Choose");
		this.uploadFileButton = new JButton("Upload");
		
		this.selectLabel = new JLabel("Select New Files  ");
		this.uploadLabel = new JLabel("Upload Files  ");
		this.selectedLabel = new JLabel("Selected Files");
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
		
		
		this.add(this.mainPanel);
		
		
		
		//TODO: remove the following line:
		this.setAttachedFiles(new File[]{new File("/test/dir/file.txt") , new File("/test/dir/stupid.txt") , new File("/test/dir/reallyawesomefilenameforthewin")});
		
	}

	/*
	 * A panel for each selected file.
	 */
	private class SelectedPanel extends JPanel{
		
		private JLabel text;
		private JButton delete;
		private File file;
		public SelectedPanel(File f){
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
		public AttachedPanel(File f){
			this.file = f;
			text = new JLabel(f.getName());
			delete = new JButton("delete");
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
	 * download the file to the user's computer
	 */
	public void downloadFile(File f){
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
        if( chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION ){
            //didn't pick anything.
        	return;
        }
        File target = chooser.getSelectedFile();
        target = new File(target.getPath()+f.getName());
        //TODO: something with abradi to actually get stuff
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
	
	/**
	 * Clear the attached files
	 * 
	 */
	public void clearAttachedFiles(){
		this.attachedFiles.clear();
		this.attachedPanel.removeAll();
	}
	
	
	/**
	 * Set the attached files
	 * 
	 * @param files
	 */
	public void setAttachedFiles(File[] files){
		this.clearAttachedFiles();
		this.addAttachedFiles(files);
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
	public File getFile(){

		//If you want ALL of the files, use this.selectedFiles, which is an arrayList.
		//the files array just contains the last selected files from the window. I should probably get 
		//rid of it... but eeeeh.
		if (this.selectedFiles != null && this.selectedFiles.size() > 0)
			return this.selectedFiles.get(0);
		else return null;

		
	}

	/**
	 * Get all of the files from the selected panel
	 * 
	 * @return
	 */
	public File[] getAllSelectedFiles(){
		File[] allFiles = new File[selectedFiles.size()];
		for (int i = 0 ; i < allFiles.length ; i ++)
			allFiles[i] = selectedFiles.get(i);
		return allFiles;
	}

	/**
	 * Get all the files from the attached panel
	 * 
	 * @return
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
		selectedFiles.remove(sp.file);
		selectedPanel.remove(sp);
		selectedPanel.revalidate();
		selectedPanel.repaint();
	}
	
	private void addFileToSelected(File f){
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

}