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
 *  CDUNKERS and Joe Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.*;
import java.applet.Applet;


import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
/**
 * Panel to display and edit the basic fields for a requirement
 *
 * @author CDUNKERS and Joe Spicola
 *
 * @version Mar 17, 2013
 *
 */
public class RequirementPanel extends JPanel {
	
	/** The Requirement displayed in this panel */
	protected Requirement model; 
	
	/** The parent view **/
	protected RequirementView parent;
	
	/*
	 * Form elements
	 */
	protected JTextField txtTitle;
	protected JTextField txtReleaseNumber;
	protected JComboBox cmbStatus;
	protected JComboBox cmbPriority;
	protected JTextArea txtDescription;	
	protected JTextField txtEstimate;
	protected JTextField txtActual;
	protected JTextField txtCreatedDate;
	protected JTextField txtModifiedDate;
	protected JTextField txtCreator;
	protected JTextField txtAssignee;
	
	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;
	
	/** The layout manager for this panel */
	protected GridBagLayout layout;
	
	/** The other panels */
	protected JPanel panelOverall;
	protected JPanel panelOne;
	protected JPanel panelTwo;
	protected JPanel panelThree;
	protected JPanel panelFour;
	
	/** The layout managers for other panels */
	protected GridBagLayout layoutOverall;
	protected GridBagLayout layoutOne;
	protected GridBagLayout layoutTwo;
	protected GridBagLayout layoutThree;
	protected GridBagLayout layoutFour;
	
	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	/**
	 * Constructs a RequirementPanel for creating or editing a given Requirement.
	 * 
	 * @param requirement	The Requirement to edit.
	 */
	public RequirementPanel(RequirementView parent, Requirement requirement) {
		this.model = requirement;
		this.parent = parent;
		
		// Indicate that input is enabled
		inputEnabled = true;
		
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		// Add all components to this panel
		addComponents();
		
	}
	
	/**
	 * Adds the components to the panel and places constraints on them
	 * for the SpringLayout manager.
	 * @param layout the layout manager
	 */
	protected void addComponents() {
		//create a new constrain variable
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints cOverall = new GridBagConstraints();
		GridBagConstraints cOne = new GridBagConstraints();
		GridBagConstraints cTwo = new GridBagConstraints();
		GridBagConstraints cThree = new GridBagConstraints();
				
		// Construct all of the components for the form
		panelOverall = new JPanel();
		panelOne = new JPanel();
		panelTwo = new JPanel();
		panelThree = new JPanel();
		txtTitle = new JTextField(20);
		txtReleaseNumber = new JTextField(20);
		txtDescription = new JTextArea(7,75);
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		txtDescription.setBorder(txtTitle.getBorder());
		String[] requirementStatusValues = new String[RequirementStatus.values().length];
		for (int i = 0; i < RequirementStatus.values().length; i++) {
			requirementStatusValues[i] = RequirementStatus.values()[i].toString();
		}
		cmbStatus = new JComboBox(requirementStatusValues);
		String[] requirementPriorityValues = new String[RequirementPriority.values().length];
		for (int i = 0; i < RequirementPriority.values().length; i++) {
			requirementPriorityValues[i] = RequirementPriority.values()[i].toString();
		}
		cmbPriority = new JComboBox(requirementPriorityValues);
		txtEstimate = new JTextField(12);
		txtActual = new JTextField(12);
		txtCreatedDate = new JTextField(12);
		txtModifiedDate = new JTextField(12);
		txtCreator = new JTextField(20);
		txtAssignee = new JTextField(20);
		
		// set maximum widths of components so they are not stretched
		txtTitle.setMaximumSize(txtTitle.getPreferredSize());
		cmbStatus.setMaximumSize(cmbStatus.getPreferredSize());
		cmbStatus.setMaximumSize(cmbPriority.getPreferredSize());
		
		// Construct labels for the form fields
		JLabel lblTitle = new JLabel("Title:", LABEL_ALIGNMENT);
		JLabel lblReleaseNumber = new JLabel("Release Number:", LABEL_ALIGNMENT);
		JLabel lblDescription = new JLabel("Description:", LABEL_ALIGNMENT);
		JLabel lblStatus = new JLabel("Status:", LABEL_ALIGNMENT);
		JLabel lblPriority = new JLabel("Priority:", LABEL_ALIGNMENT);
		JLabel lblEstimate = new JLabel("Estimate:", LABEL_ALIGNMENT);
		JLabel lblEUnits = new JLabel("Units", LABEL_ALIGNMENT);
		JLabel lblActual = new JLabel("Actual:", LABEL_ALIGNMENT);
		JLabel lblAUnits = new JLabel("Units", LABEL_ALIGNMENT);
		JLabel lblCreatedDate = new JLabel("Date Created:", LABEL_ALIGNMENT);
		JLabel lblModifiedDate = new JLabel("Date Modified:", LABEL_ALIGNMENT);
		JLabel lblCreator = new JLabel("Creator:", LABEL_ALIGNMENT);
		JLabel lblAssignee = new JLabel("Assignee:", LABEL_ALIGNMENT);
		
		int labelWidth = lblDescription.getPreferredSize().width;
		
		
		//Panel One - panel at the top --------------------------------------------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutOne = new GridBagLayout();
		panelOne.setLayout(layoutOne);	
		
		cOne.anchor = GridBagConstraints.LINE_START; 
		cOne.gridx = 0;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 2;
		cOne.insets = new Insets(10,10,10,0); //top,left,bottom,right
		//txtTitle.setFont(txtTitle.getFont().deriveFont(18f));
		panelOne.add(txtTitle, cOne);
		
		cOne.gridx = 0;
		cOne.gridy = 1;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		panelOne.add(lblReleaseNumber, cOne);
		
		cOne.gridx = 1;
		cOne.gridy = 1;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		panelOne.add(txtReleaseNumber, cOne);
		
		//Panel Two - panel below panel one ------------------------------------------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutTwo = new GridBagLayout();
		panelTwo.setLayout(layoutTwo);
		
		cTwo.insets = new Insets(10,10,5,0);
		cTwo.anchor = GridBagConstraints.FIRST_LINE_START; 
		cTwo.gridx = 0;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		panelTwo.add(lblDescription, cTwo);
		
		cTwo.anchor = GridBagConstraints.LAST_LINE_START; 
		cTwo.insets = new Insets(0,10,10,0);
		cTwo.gridx = 0;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		//cTwo.gridwidth = 2;
		panelTwo.add(txtDescription, cTwo);
		
		//Panel Three - panel below panel one -------------------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutThree = new GridBagLayout();
		panelThree.setLayout(layoutThree);
		
		cThree.insets = new Insets(10,10,10,0);
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 0;
		cThree.anchor = GridBagConstraints.LINE_START;
		panelThree.add(lblStatus, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 0;
		cmbStatus.setSelectedItem(requirementStatusValues[0]);
		cmbStatus.setEnabled(false);
		panelThree.add(cmbStatus, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 1;
		panelThree.add(lblPriority, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 1;
		cmbPriority.setSelectedItem(requirementPriorityValues[2]);
		panelThree.add(cmbPriority, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 2;
		panelThree.add(lblEstimate, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 2;
		panelThree.add(txtEstimate, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 2;
		cThree.gridy = 2;
		panelThree.add(lblEUnits, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 3;
		panelThree.add(lblActual, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 3;
		panelThree.add(txtActual, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 2;
		cThree.gridy = 3;
		panelThree.add(lblAUnits, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 4;
		panelThree.add(lblCreatedDate, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 4;
		txtCreatedDate.setEnabled(false);
		//txtCreator.setText(model.getCreator().getUsername());
		panelThree.add(txtCreatedDate, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 5;
		panelThree.add(lblModifiedDate, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 5;
		txtModifiedDate.setEnabled(false);
		panelThree.add(txtModifiedDate, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 6;
		panelThree.add(lblCreator, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 6;
		txtCreator.setEnabled(false);
		//txtCreator.setText(model.getCreator().getUsername());
		panelThree.add(txtCreator, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 7;
		panelThree.add(lblAssignee, cThree);
		
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 7;
		panelThree.add(txtAssignee, cThree);
		
		//txtCreatedDate.setText(model.getCreationDate().toString());
		

		//Panel Overall - panel holding all other panels --------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutOverall = new GridBagLayout();
		panelOverall.setLayout(layoutOverall);
		//Overall Panel
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		//c.gridcolumn something like this
		cOverall.anchor = GridBagConstraints.LINE_START;
		panelOverall.add(panelOne, cOverall);
		
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 1;
		cOverall.anchor = GridBagConstraints.LINE_START;
		//c.gridcolumn something like this
		panelOverall.add(panelTwo, cOverall);
		
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 2;
		cOverall.anchor = GridBagConstraints.LINE_START;
		//c.gridcolumn something like this
		panelOverall.add(panelThree, cOverall);
		
		// add to this Panel -----------------------------------------------------------------------------------------------------------------
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//c.gridcolumn something like this
		this.add(panelOverall, c);
		
		
	}
	
	/**
	 * Returns the parent RequirementsView.
	 * 
	 * @return the parent RequirementsView.
	 */
	public RequirementView getParent() {
		return parent;
	}
	
	
	protected void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;

		txtTitle.setEnabled(enabled);
		txtReleaseNumber.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
		cmbStatus.setEnabled(enabled);
		cmbPriority.setEnabled(enabled);
		txtEstimate.setEnabled(enabled);
	}
	
	/**
	 * Returns the model object represented by this view's fields.
	 * 
	 * TODO: Do some basic input verification
	 * @return the model represented by this view
	 */
	public Requirement getEditedModel() {
		Requirement requirement = new Requirement();
		requirement.setId(model.getId());
		requirement.setTitle(txtTitle.getText());
		//requirement.setReleaseNumber(Integer.parseInt(txtReleaseNumber.getText())); //need to catch exception in the save funtion
		requirement.setDescription(txtDescription.getText());
		requirement.setStatus(RequirementStatus.valueOf((String) cmbStatus.getSelectedItem()));
		requirement.setPriority(RequirementPriority.valueOf((String) cmbPriority.getSelectedItem()));
		requirement.setEstimate(txtEstimate.getText());
//		if (!(txtAssignee.getText().equals(""))) {
//			requirement.setAssignee(new User("", txtAssignee.getText(), "", -1));
//		}
//		if (!(txtCreator.getText().equals(""))) {
//			requirement.setCreator(new User("", txtCreator.getText(), "", -1));
//		}
//		HashSet<Tag> tags = new HashSet<Tag>();
//		for (int i = 0; i < tagPanel.lmTags.getSize(); i++) {
//			tags.add(new Tag((String)tagPanel.lmTags.get(i)));
//		}
//		requirement.setTags(tags);
		
		return requirement;
	}
	
}
	
	

