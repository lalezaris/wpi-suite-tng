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
 *  Evan Polekoff
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.IntegerField;

/**
 * Panel for each individual Task
 *
 * @author Evan Polekoff
 */
@SuppressWarnings("serial")
public class TasksPanel extends JPanel{

	/*layout manager for this panel*/
	protected GridBagLayout layout;

	//Stuff for entering fields.
	private JTextField txtName;
	private JTextField txtDescription;
	private JTextField txtAssignee;
	private JTextField txtEffort;
	private JComboBox<TaskStatus> cmbStatus;
	TaskStatus[] taskStatusArray = {TaskStatus.BLANK, TaskStatus.INPROGRESS, TaskStatus.OPEN, TaskStatus.CLOSED, TaskStatus.ACCEPTED};
	private JButton saveButton;
	
	//Labels
	private JLabel lblName;
	private JLabel lblDescription;
	private JLabel lblAssignee;
	private JLabel lblEffort;
	private JLabel lblStatus;
	
	
	//Panel for entry fields.
	JPanel fieldPanel = new JPanel();

	//Overall Panel
	JPanel overallPanel = new JPanel();

	public TasksPanel(){

		addComponents();
	}

	private void addComponents(){

		//Make fields
		txtName = new JTextField();
		txtDescription = new JTextField();
		txtAssignee = new JTextField();
		txtEffort = new IntegerField(4);
		txtEffort.setText("0");//Default to 0
		cmbStatus = new JComboBox<TaskStatus>(taskStatusArray);
		cmbStatus.setBackground(Color.white);
		saveButton = new JButton("Save");
		
		lblName = new JLabel("Name: *", JLabel.TRAILING);
		lblDescription = new JLabel("Description: *", JLabel.TRAILING);
		lblAssignee = new JLabel("Assignee: ", JLabel.TRAILING);
		lblEffort = new JLabel("Effort: ", JLabel.TRAILING);
		lblStatus = new JLabel("Status: ", JLabel.TRAILING);
		
		
		//Gridbag stuff
		GridBagLayout layoutOverall = new GridBagLayout();
		overallPanel.setLayout(layoutOverall);

		layout = new GridBagLayout();
		this.setLayout(new BorderLayout());

		GridBagConstraints cFields = new GridBagConstraints();
		GridBagLayout layoutBtn = new GridBagLayout();
		fieldPanel.setLayout(layoutBtn);

		GridBagConstraints cOverall = new GridBagConstraints();
		overallPanel.setLayout(layoutOverall);

		//Place all of the fields!!!
		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 0;
		cFields.gridy = 0;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(lblName, cFields);
		
		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 1;
		cFields.gridy = 0;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(txtName, cFields);

		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 0;
		cFields.gridy = 2;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(lblDescription, cFields);
		
		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 1;
		cFields.gridy = 2;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(txtDescription, cFields);

		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 0;
		cFields.gridy = 4;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(lblAssignee, cFields);
		
		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 1;
		cFields.gridy = 4;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(txtAssignee, cFields);

		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 0;
		cFields.gridy = 6;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(lblEffort, cFields);
		
		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 1;
		cFields.gridy = 6;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(txtEffort, cFields);

		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 0;
		cFields.gridy = 8;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(lblStatus, cFields);
		
		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 1;
		cFields.gridy = 8;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(cmbStatus, cFields);

		cFields.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFields.fill = GridBagConstraints.HORIZONTAL;
		cFields.gridx = 0;
		cFields.gridy = 10;
		cFields.weightx = 0.5;
		cFields.weighty = 0.5;
		cFields.gridheight = 1;
		cFields.insets = new Insets(10,10,10,0); //top,left,bottom,right
		fieldPanel.add(saveButton, cFields);

		//Wrap them all in one overall panel.
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START; 
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.weightx = 0.1;
		cOverall.weighty = 0.1;
		cOverall.gridwidth = 1;
		cOverall.insets = new Insets(10,10,10,0); //top,left,bottom,right
		overallPanel.add(fieldPanel, cOverall);

		add(overallPanel,BorderLayout.CENTER);
		validate();
		repaint();
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
	public JTextField getTxtDescription() {
		return txtDescription;
	}

	/**
	 * @return the txtAssignee
	 */
	public JTextField getTxtAssignee() {
		return txtAssignee;
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
