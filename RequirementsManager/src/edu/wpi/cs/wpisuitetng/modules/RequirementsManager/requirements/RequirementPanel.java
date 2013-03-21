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
 *  CDUNKERS
 *  Joe Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.awt.Font;
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
	
	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;
	
	/** The layout manager for this panel */
//	protected SpringLayout layout;
	protected GridBagLayout layout;
	
	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	/**
	 * Constructs a RequirementPanel for creating or editing a given Requirement.
	 * 
	 * @param parent The parent of the requirement
	 * @param requirement The Requirement to edit
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
		
		// Construct all of the components for the form
		txtTitle = new JTextField(20);
		txtReleaseNumber = new JTextField(12);
		txtDescription = new JTextArea(10,35);
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
		
		// set maximum widths of components so they are not stretched
		txtTitle.setMaximumSize(txtTitle.getPreferredSize());
		cmbStatus.setMaximumSize(cmbStatus.getPreferredSize());
		cmbPriority.setMaximumSize(cmbPriority.getPreferredSize());
		
		// Construct labels for the form fields
		JLabel lblTitle = new JLabel("Title:", LABEL_ALIGNMENT);
		JLabel lblReleaseNumber = new JLabel("Release Number:", LABEL_ALIGNMENT);
		JLabel lblDescription = new JLabel("Description:", LABEL_ALIGNMENT);
		JLabel lblStatus = new JLabel("Status:", LABEL_ALIGNMENT);
		JLabel lblPriority = new JLabel("Priority:", LABEL_ALIGNMENT);
		JLabel lblEstimate = new JLabel("Estimate:", LABEL_ALIGNMENT);
		JLabel lblActual = new JLabel("Actual:", LABEL_ALIGNMENT);
		
		int labelWidth = lblDescription.getPreferredSize().width;
		
		//set values that are used throughout for c
		c.anchor = GridBagConstraints.LINE_START;
		//c.weightx = 1;
		c.weighty = 0.5;
		c.insets = new Insets(0,5,0,0);
		layout.columnWeights = new double[] {.1, .9};
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		txtTitle.setFont(txtTitle.getFont().deriveFont(22f));
		this.add(txtTitle, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		this.add(lblReleaseNumber, c);
		
		c.gridx = 1;
		c.gridy = 1;
		this.add(txtReleaseNumber, c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(lblStatus, c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.add(cmbStatus, c);
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(lblPriority, c);
		
		c.gridx = 1;
		c.gridy = 3;
		cmbPriority.setSelectedItem(requirementPriorityValues[2]);
		this.add(cmbPriority, c);
		
		c.gridx = 0;
		c.gridy = 4;
		this.add(lblDescription, c);
	
		c.gridx = 1;
		c.gridy = 4;
		this.add(txtDescription, c);
		
		c.gridx = 0;
		c.gridy = 5;
		this.add(lblEstimate, c);
		
		c.gridx = 1;
		c.gridy = 5;
		this.add(txtEstimate, c);
		
		c.gridx = 0;
		c.gridy = 6;
		this.add(lblActual, c);
		
		c.gridx = 1;
		c.gridy = 6;
		this.add(txtActual, c);

	}
	
	/**
	 * Returns the parent RequirementsView.
	 * 
	 * @return the parent RequirementsView.
	 */
	public RequirementView getParent() {
		return parent;
	}
	
	
	/**
	 *Sets if the input is enabled
	 * 
	 * @param enabled shows if input is enabled
	 */
	protected void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;

		txtTitle.setEnabled(enabled);
		txtReleaseNumber.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
		cmbStatus.setEnabled(enabled);
		cmbPriority.setEnabled(enabled);
		txtEstimate.setEnabled(enabled);
		txtActual.setEnabled(enabled);
	}
	
	/**Commented out parts are not needed for iteration 1 but may be needed in the future
	 * Returns the model object represented by this view's fields.
	 * 
	 * TODO: Do some basic input verification
	 * @return the model represented by this view
	 */
	public Requirement getEditedModel() {
		Requirement requirement = new Requirement();
		requirement.setId(model.getId());
		requirement.setTitle(txtTitle.getText());
		//requirement.setReleaseNumber(txtReleaseNumber.getText());
		requirement.setDescription(txtDescription.getText());
		requirement.setStatus(RequirementStatus.valueOf((String) cmbStatus.getSelectedItem()));
		requirement.setPriority(RequirementPriority.valueOf((String) cmbPriority.getSelectedItem()));
		requirement.setEstimate(txtEstimate.getText());
		//requirement.setActualEffort(txtActual.getText());
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
	
	

