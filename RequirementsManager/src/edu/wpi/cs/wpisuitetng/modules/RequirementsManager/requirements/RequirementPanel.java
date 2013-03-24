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
 *  Chris Dunkers
 *  Joe Spicola
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
import javax.swing.text.MaskFormatter;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SpringLayout;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;


import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
/**
 * Panel to display and edit the basic fields for a requirement
 * Adapted from DefectPanel in project DefectTracker
 *
 * @author Chris Dunkers 
 * @author Joe Spicola
 *
 * @version Mar 17, 2013
 *
 */
public class RequirementPanel extends JPanel {
	public enum Mode {
		CREATE,
		EDIT;
	}
	
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
	protected IntegerField txtEstimate;
	protected IntegerField txtActual;
	protected JTextField txtCreatedDate;
	protected JTextField txtModifiedDate;
	protected JTextField txtCreator;
	protected JTextField txtAssignee;
	protected JButton saveRequirementTop;
	protected JButton saveRequirementBottom;
	
	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;
	
	/** The data model for the ChangeSet list */
	//protected RequirementEventListModel requirementEventListModel;
	
	/**Error labels*/
	JLabel lblTitleError = new JLabel("Must have a title", LABEL_ALIGNMENT);
	JLabel lblDescriptionError = new JLabel("Must have a description", LABEL_ALIGNMENT);
	
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
	
	/** An enum indicating if the form is in create mode or edit mode */
	protected Mode editMode;
	
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
	public RequirementPanel(RequirementView parent, Requirement requirement, Mode mode) {
		this.model = requirement;
		this.parent = parent;
		editMode = mode;
		
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
		txtEstimate = new IntegerField(3);
		txtActual = new IntegerField(3);
		txtCreatedDate = new JTextField(15);
		txtModifiedDate = new JTextField(15);
		txtCreator = new JTextField(15);
		txtAssignee = new JTextField(15);
		
		/**Save Button*/
		saveRequirementTop = new JButton("Save");
		saveRequirementTop.setAction(new SaveChangesAction(new SaveRequirementController(this.getParent())));
		saveRequirementBottom = new JButton("Save");
		saveRequirementBottom.setAction(new SaveChangesAction(new SaveRequirementController(this.getParent())));
		
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
		cOne.gridwidth = 1;
		cOne.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panelOne.add(saveRequirementTop, cOne);
		
		cOne.anchor = GridBagConstraints.LINE_START; 
		cOne.gridx = 0;
		cOne.gridy = 1;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 2;
		cOne.insets = new Insets(10,10,10,0); //top,left,bottom,right
		//txtTitle.setFont(txtTitle.getFont().deriveFont(18f));
		panelOne.add(txtTitle, cOne);
		
		cOne.gridx = 2;
		cOne.gridy = 1;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		lblTitleError.setVisible(false);
		panelOne.add(lblTitleError, cOne);
		
		cOne.gridx = 0;
		cOne.gridy = 2;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		panelOne.add(lblReleaseNumber, cOne);
		
		cOne.gridx = 1;
		cOne.gridy = 2;
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
		
		cTwo.insets = new Insets(10,10,5,0);
		cTwo.anchor = GridBagConstraints.FIRST_LINE_START; 
		cTwo.gridx = 1;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		lblDescriptionError.setVisible(false);
		panelTwo.add(lblDescriptionError, cTwo);

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
		
		cThree.fill = GridBagConstraints.HORIZONTAL;
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 2;
		//txtEstimate.setText("0");
		panelThree.add(txtEstimate, cThree);
		
		cThree.fill = GridBagConstraints.NONE;
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
		
		cThree.fill = GridBagConstraints.HORIZONTAL;
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 3;
		//txtActual.setText("0");
		panelThree.add(txtActual, cThree);
		
		cThree.fill = GridBagConstraints.NONE;
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
		txtCreatedDate.setText(model.getCreationDate().toString());
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
		txtCreator.setText(model.getCreator().getUsername());
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

		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 8;
		panelThree.add(saveRequirementBottom, cThree);
		
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
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//c.gridcolumn something like this
		this.add(panelOverall, c);		
		
		//depending on the mode, disable certain components
		if (editMode == Mode.CREATE) {
			cmbStatus.setEnabled(false);
			txtActual.setEnabled(false);
		}
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
	}
	
	/**
	 * Updates the RequirementPanel's model to contain the values of the given Requirement and sets the 
	 * RequirementPanel's editMode to {@link Mode#EDIT}.
	 * 
	 * @param requirement	The Requirement which contains the new values for the model.
	 */
	protected void updateModel(Requirement requirement) {
		updateModel(requirement, Mode.EDIT);
	}
	
	/**
	 * Updates the DefectPanel's model to contain the values of the given Defect.
	 * 
	 * @param	defect	The Defect which contains the new values for the model.
	 * @param	mode	The new editMode.
	 */
	protected void updateModel(Requirement requirement, Mode mode) {
		editMode = mode;
		
		model.setId(requirement.getId());
		model.setTitle(requirement.getTitle());
		model.setDescription(requirement.getDescription());
		model.setAssignee(requirement.getAssignee());
		model.setCreator(requirement.getCreator());
		model.setCreationDate(requirement.getCreationDate());
		model.setLastModifiedDate(requirement.getLastModifiedDate());
		model.setStatus(requirement.getStatus());
		
		updateFields();
		//requirementEventListModel.update(requirement);
		this.revalidate();
		layout.invalidateLayout(this);
		layout.layoutContainer(this);
		this.repaint();
		parent.refreshScrollPane();
	}
	
	/**
	 * checks to see if it is an empty string returns -1 if the string is less than 0 or blank and returns the integer value otherwise
	 * 
	 * @param intf the IntergerField in question
	 * @return the integer that is either -1 or the integer value of the string
	 */
	protected int getValue(IntegerField intf){
		if(intf.getText().equals(null) || intf.getText().equals("")){
			return -1;
		} else {
			return Integer.parseInt(intf.getText());
		}		
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
		requirement.setReleaseNumber(txtReleaseNumber.getText()); 
		requirement.setDescription(txtDescription.getText());
		requirement.setStatus(RequirementStatus.valueOf((String) cmbStatus.getSelectedItem()));
		requirement.setPriority(RequirementPriority.valueOf((String) cmbPriority.getSelectedItem()));
		requirement.setEstimateEffort(getValue(txtEstimate)); // return -1 if the field was left blank
		requirement.setActualEffort(getValue(txtActual)); // return -1 if the field was left blank
		if (!(txtAssignee.getText().equals(""))) {
			requirement.setAssignee(new User("", txtAssignee.getText(), "", -1));
		}
		if (!(txtCreator.getText().equals(""))) {
			requirement.setCreator(new User("", txtCreator.getText(), "", -1));
		}
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
	
	/**
	 * Checks to make sure the title and description are filled in 
	 * 
	 * @return 3 if both title and description not filled in, 2 if only title, 1 if only description, 0 otherwise 
	 */
	public int checkRequiredFields(){
		if((txtTitle.getText().equals(null) || txtTitle.getText().equals("")) && 
				(txtDescription.getText().equals(null) || txtDescription.getText().equals(""))){
			lblTitleError.setVisible(true);
			lblDescriptionError.setVisible(true);
			return 3;
		} else if(txtTitle.getText().equals(null) || txtTitle.getText().equals("")){
			lblTitleError.setVisible(true);
			lblDescriptionError.setVisible(false);
			return 2; 
		} else if(txtDescription.getText().equals(null) || txtDescription.getText().equals("")){
			lblDescriptionError.setVisible(true);
			lblTitleError.setVisible(false);
			return 1;
		} else 
			return 0;
	}
	
	private void updateFields() {
		txtTitle.setText(model.getTitle());
		txtDescription.setText(model.getDescription());
		for (int i = 0; i < cmbStatus.getItemCount(); i++) {
			if (model.getStatus() == RequirementStatus.valueOf((String) cmbStatus.getItemAt(i))) {
				cmbStatus.setSelectedIndex(i);
			}
		}
		if (editMode == Mode.EDIT) {
			txtCreatedDate.setText(model.getCreationDate().toString());
			txtModifiedDate.setText(model.getLastModifiedDate().toString());
		}
		if (model.getCreator() != null) {
			txtCreator.setText(model.getCreator().getUsername());
		}
		if (model.getAssignee() != null) {
			txtAssignee.setText(model.getAssignee().getUsername());
		}
		
		//txtTitleListener.checkIfUpdated();
		//txtDescriptionListener.checkIfUpdated();
		//cmbStatusListener.checkIfUpdated();
		//txtCreatorListener.checkIfUpdated();
		//txtAssigneeListener.checkIfUpdated();
	}

	public Mode getEditMode() {
		return editMode;
	}
}
	
	

