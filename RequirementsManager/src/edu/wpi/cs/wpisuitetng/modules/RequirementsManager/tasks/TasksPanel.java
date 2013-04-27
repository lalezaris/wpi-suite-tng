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
 *  Spicola
 *  Evan Polekoff
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.IntegerField;

/**
 * Panel for each individual Task
 *
 * @author Evan Polekoff
 * @author Spicola
 *
 * @version Apr 24, 2013
 *
 */
@SuppressWarnings("serial")
public class TasksPanel extends JPanel {
	
	//Stuff for entering fields.
	protected JTextField txtName;
	protected JTextArea txtDescription;
	protected JComboBox<String> cmbAssignee;
	protected JTextField txtEffort;
	protected JComboBox<TaskStatus> cmbStatus;
	protected TaskStatus[] taskStatusArray = {TaskStatus.BLANK, TaskStatus.INPROGRESS, TaskStatus.OPEN, TaskStatus.CLOSED, TaskStatus.ACCEPTED};
	protected JButton saveButton;
	
	
	//Labels
	private JLabel lblName;
	private JLabel lblDescription;
	private JLabel lblAssignee;
	private JLabel lblEffort;
	private JLabel lblStatus;
	
	/*layout manager for this panel*/
	protected BorderLayout layout;
	
	/**
	 * Layout managers for other panels
	 */
	protected GridBagLayout layoutBtn;
	protected GridBagLayout layoutOverall;
	
	//panels
	protected JPanel fieldPanel;
	protected JPanel overallPanel;
	
	//Array of users to display.
	String[] users;
	
	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	/**
	 * Instantiates a new tasks panel.
	 *
	 * @param users the users
	 */
	public TasksPanel(String[] users) {
		//Use a grid bag layout manager
		this.layout = new BorderLayout();
		this.setLayout(layout);
		
		//Set variables
		this.users = users;

		// Add all components to this panel
		addComponents();
	}
	
	private void addComponents() {
		//create constraint variables
		GridBagConstraints cFields = new GridBagConstraints();
		GridBagConstraints cOverall = new GridBagConstraints();
		
		//construct panels
		overallPanel = new JPanel();
		fieldPanel = new JPanel();
		
		//make fields
		txtName = new JTextField(12);
		
		txtDescription = new JTextArea(10, 20);
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		
		cmbAssignee = new JComboBox<String>(users);
		cmbAssignee.setBackground(Color.WHITE);
		
		txtEffort = new IntegerField(2);
		txtEffort.setText("0");//Default to 0
		
		cmbStatus = new JComboBox<TaskStatus>(taskStatusArray);
		cmbStatus.setBackground(Color.WHITE);
		
		saveButton = new JButton("Save");
		
		//set labels
		lblName = new JLabel("Name: *", JLabel.TRAILING);
		lblDescription = new JLabel("Description: *", JLabel.TRAILING);
		lblAssignee = new JLabel("Assignee: ", JLabel.TRAILING);
		lblEffort = new JLabel("Effort: ", JLabel.TRAILING);
		lblStatus = new JLabel("Status: ", JLabel.TRAILING);
		
		//set max size to prevent stretching
		txtName.setMaximumSize(txtName.getPreferredSize());
		
		//individual panel -------------------------------------
		layoutBtn = new GridBagLayout();
		fieldPanel.setLayout(layoutBtn);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 0;
		cFields.gridy = 0;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(lblName, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 1;
		cFields.gridy = 0;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(txtName, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 0;
		cFields.gridy = 1;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(lblDescription, cFields);
		
		JScrollPane scrollPaneDescription = new JScrollPane(txtDescription);
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 1;
		cFields.gridy = 1;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(scrollPaneDescription, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 0;
		cFields.gridy = 2;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(lblAssignee, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 1;
		cFields.gridy = 2;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(cmbAssignee, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 0;
		cFields.gridy = 3;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(lblEffort, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 1;
		cFields.gridy = 3;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(txtEffort, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 0;
		cFields.gridy = 4;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(lblStatus, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 1;
		cFields.gridy = 4;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(cmbStatus, cFields);
		
		cFields.anchor = GridBagConstraints.LINE_START; 
		cFields.gridx = 0;
		cFields.gridy = 5;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.insets = new Insets(5,10,5,0); //top,left,bottom,right
		fieldPanel.add(saveButton, cFields);
		
		//add fieldPanel to overall ---------------------
		layoutOverall = new GridBagLayout();
		overallPanel.setLayout(layoutOverall);
		
		cOverall.anchor = GridBagConstraints.LINE_START; 
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.insets = new Insets(5,10,5,0); //top,left,bottom,right
		overallPanel.add(fieldPanel, cOverall);
		
		overallPanel.setMinimumSize(new Dimension((int)overallPanel.getPreferredSize().getWidth() + 2, (int)overallPanel.getPreferredSize().getHeight()));
		overallPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//add to this panel
		this.add(overallPanel,BorderLayout.CENTER);
	}
	
	/**
	 * Gets the save button
	 * 
	 * @return the save button
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * @return the txtName
	 */
	public JTextField getTxtName() {
		return txtName;
	}

	/**
	 * @return the txtDescription
	 */
	public JTextArea getTxtDescription() {
		return txtDescription;
	}

	/**
	 * @return the txtAssignee
	 */
	public JComboBox<String> getCmbAssignee() {
		return cmbAssignee;
	}

	/**
	 * @return the txtEffort
	 */
	public JTextField getTxtEffort() {
		return txtEffort;
	}

	/**
	 * @return the cmbStatus
	 */
	public JComboBox<TaskStatus> getCmbStatus() {
		return cmbStatus;
	}
}
