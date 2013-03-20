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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
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
	
	/** The Defect displayed in this panel */
	protected Requirement model; 
	
	/*
	 * Form elements
	 */
	protected JTextField txtTitle;
	protected JTextField txtReleaseNumber;
	protected JComboBox cmbStatus;
	protected JComboBox cmbPriority;
	protected JTextArea txtDescription;	
	protected JTextField txtEstimate;
		
	/** The layout manager for this panel */
//	protected SpringLayout layout;
	protected GridLayout layout;
	
	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	/**
	 * Constructs a RequirementPanel for creating or editing a given Requirement.
	 * 
	 * @param parent	The parent RequirementView.
	 * @param defect	The Defect to edit.
	 * @param mode		Whether or not the given Requirement should be treated as if it already exists 
	 * 					on the server ({@link Mode#EDIT}) or not ({@link Mode#CREATE}).
	 */
	public RequirementPanel(Requirement requirement) {
		this.model = requirement;
		
		//Use a grid layout manager
		//layout = new GridLayout(5,2); //7 rows, 4 columns
		//this.setLayout(layout);
		
		// Add all components to this panel
		addComponents();
		
	}
	
	/**
	 * Adds the components to the panel and places constraints on them
	 * for the SpringLayout manager.
	 * @param layout the layout manager
	 */
	protected void addComponents() {
		// Construct all of the components for the form
		txtTitle = new JTextField(50);
		txtReleaseNumber = new JTextField(50);
		txtDescription = new JTextArea();
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
		txtEstimate = new JTextField(50);
		
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
		JLabel lblEstimate = new JLabel("Estimate Date", LABEL_ALIGNMENT);
		
		int labelWidth = lblDescription.getPreferredSize().width;
		
		this.add(lblTitle);
		this.add(txtTitle);
		this.add(lblReleaseNumber);
		this.add(txtReleaseNumber);
		this.add(lblDescription);
		this.add(txtDescription);
		this.add(lblStatus);
		this.add(cmbStatus);
		this.add(lblPriority);
		this.add(cmbPriority);
		
	}
	
}
	
	

