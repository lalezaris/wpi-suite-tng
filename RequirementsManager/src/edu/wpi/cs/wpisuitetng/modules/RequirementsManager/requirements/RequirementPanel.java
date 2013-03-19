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

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.ComboUpdateListener;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.TextUpdateListener;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.DefectPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.Defect;
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
	
	public enum Mode {
		CREATE,
		EDIT;
	}

	/** The parent view **/
	protected RequirementView parent;
	
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
	protected JLabel txtCreatedDate;
	protected JLabel txtModifiedDate;
	protected JTextField txtCreator;
	protected JTextField txtAssignee;
	protected JLabel lblCreatedDate;
	protected JLabel lblModifiedDate;
	
	/** The layout manager for this panel */
	protected SpringLayout layout;
	
	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;
	
	/** An enum indicating if the form is in create mode or edit mode */
	protected Mode editMode;
	
//	/*
//	 * Listeners for the form fields
//	 */
//	protected final TextUpdateListener txtTitleListener;
//	protected final TextUpdateListener txtReleaseNumberListener;
//	protected final TextUpdateListener txtDescriptionListener;
//	protected final ComboUpdateListener cmbStatusListener;
//	protected final ComboUpdateListener cmbPrioritysListener;
//	protected final TextUpdateListener txtCreatorListener;
//	protected final TextUpdateListener txtAssigneeListener;
	
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
	public RequirementPanel(RequirementView parent, Requirement requirement, Mode mode) {
		this.parent = parent;
		this.model = requirement;
		editMode = mode;
		
		// Indicate that input is enabled
		inputEnabled = true;

		// Use a SpringLayout manager
		layout = new SpringLayout();
		this.setLayout(layout);

		// Add all components to this panel
		addComponents();
		
//		// Add TextUpdateListeners. These check if the text component's text differs from the panel's Defect 
//		// model and highlights them accordingly every time a key is pressed.
//		txtTitleListener = new TextUpdateListener(this, txtTitle);
//		txtTitle.addKeyListener(txtTitleListener);
//		
//		txtReleaseNumberListener = new TextUpdateListener(this, txtReleaseNumber);
//		txtReleaseNumber.addKeyListener(txtTitleListener);
//
//		txtDescriptionListener = new TextUpdateListener(this, txtDescription);
//		txtDescription.addKeyListener(txtDescriptionListener);
//		
//		cmbStatusListener = new ComboUpdateListener(this, cmbStatus);
//		cmbStatus.addItemListener(cmbStatusListener);
//
//		cmbPriorityListener = new ComboUpdateListener(this, cmbPriority);
//		cmbPriority.addItemListener(cmbPriorityListener);
//
//		txtCreatorListener = new TextUpdateListener(this, txtCreator);
//		txtCreator.addKeyListener(txtCreatorListener);
//
//		txtAssigneeListener = new TextUpdateListener(this, txtAssignee);
//		txtAssignee.addKeyListener(txtAssigneeListener);

		// prevent tab key from inserting tab characters into the description field
		txtDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_TAB) {
					if (event.getModifiers() == 0) {
						txtDescription.transferFocus();
					}
					else {
						txtDescription.transferFocusBackward();
					}
					event.consume();
				}
			}
		});
		
		// Populate the form with the contents of the Requirements model.
		updateFields();
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
		txtCreatedDate = new JLabel();
		txtModifiedDate = new JLabel("");
		txtCreator = new JTextField(20);
		txtCreator.setEnabled(false);
		txtAssignee = new JTextField(20);
		
		// Set text component names. These names correspond to method names in the Defect model (ex: "Title" => Defect#getTitle()).
		// These are required for TextUpdateListener to be able to get the correct field from panel's Defect model.
		txtTitle.setName("Title");
		txtDescription.setName("Description");
		txtReleaseNumber.setName("Release Number");
		cmbStatus.setName("Status");
		cmbPriority.setName("Priority");
		txtCreator.setName("Estimate");
		txtCreator.setName("Creator");
		txtAssignee.setName("Assignee");
		
		// set maximum widths of components so they are not stretched
		txtTitle.setMaximumSize(txtTitle.getPreferredSize());
		cmbStatus.setMaximumSize(cmbStatus.getPreferredSize());
		cmbStatus.setMaximumSize(cmbPriority.getPreferredSize());
		txtCreator.setMaximumSize(txtCreator.getPreferredSize());
		txtAssignee.setMaximumSize(txtAssignee.getPreferredSize());
		
		// Construct labels for the form fields
		JLabel lblTitle = new JLabel("Title:", LABEL_ALIGNMENT);
		JLabel lblReleaseNumber = new JLabel("Release Number:", LABEL_ALIGNMENT);
		JLabel lblDescription = new JLabel("Description:", LABEL_ALIGNMENT);
		JLabel lblStatus = new JLabel("Status:", LABEL_ALIGNMENT);
		JLabel lblPriority = new JLabel("Priority:", LABEL_ALIGNMENT);
		JLabel lblEstimate = new JLabel("Estimate Date", LABEL_ALIGNMENT);
		lblCreatedDate = new JLabel("", LABEL_ALIGNMENT);
		lblModifiedDate = new JLabel("", LABEL_ALIGNMENT);
		JLabel lblCreator = new JLabel("Creator:", LABEL_ALIGNMENT);
		JLabel lblAssignee = new JLabel("Assignee:", LABEL_ALIGNMENT);

		int labelWidth = lblDescription.getPreferredSize().width;

		// Setup all of the spring constraints
		layout.putConstraint(SpringLayout.NORTH, lblTitle, VERTICAL_PADDING, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, lblTitle, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, lblTitle, labelWidth, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, txtTitle, 0, SpringLayout.VERTICAL_CENTER, lblTitle);
		layout.putConstraint(SpringLayout.WEST, txtTitle, HORIZONTAL_PADDING, SpringLayout.EAST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, txtTitle, txtTitle.getPreferredSize().width, SpringLayout.WEST, txtTitle);
		
		layout.putConstraint(SpringLayout.NORTH, lblReleaseNumber, VERTICAL_PADDING, SpringLayout.NORTH, txtTitle);
		layout.putConstraint(SpringLayout.WEST, lblReleaseNumber, 15, SpringLayout.WEST, txtReleaseNumber);
		layout.putConstraint(SpringLayout.EAST, lblReleaseNumber, labelWidth, SpringLayout.WEST, lblReleaseNumber);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, txtReleaseNumber, 0, SpringLayout.VERTICAL_CENTER, txtReleaseNumber);
		layout.putConstraint(SpringLayout.WEST, txtReleaseNumber, HORIZONTAL_PADDING, SpringLayout.EAST, lblReleaseNumber);
		layout.putConstraint(SpringLayout.EAST, txtReleaseNumber, txtTitle.getPreferredSize().width, SpringLayout.WEST, txtReleaseNumber);

		layout.putConstraint(SpringLayout.NORTH, txtDescription, VERTICAL_PADDING, SpringLayout.SOUTH, txtReleaseNumber);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblDescription, 0, SpringLayout.VERTICAL_CENTER, txtDescription);
		layout.putConstraint(SpringLayout.WEST, lblDescription, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, lblDescription, labelWidth, SpringLayout.WEST, lblDescription);
		layout.putConstraint(SpringLayout.WEST, txtDescription, HORIZONTAL_PADDING, SpringLayout.EAST, lblDescription);
		layout.putConstraint(SpringLayout.EAST, txtDescription, 0, SpringLayout.EAST, txtReleaseNumber);
		layout.putConstraint(SpringLayout.SOUTH, txtDescription, txtReleaseNumber.getPreferredSize().height * 4, SpringLayout.NORTH, txtDescription);

		layout.putConstraint(SpringLayout.NORTH, cmbStatus, VERTICAL_PADDING, SpringLayout.SOUTH, txtDescription);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblStatus, 0, SpringLayout.VERTICAL_CENTER, cmbStatus);
		layout.putConstraint(SpringLayout.WEST, lblStatus, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, lblStatus, labelWidth, SpringLayout.WEST, lblStatus);
		layout.putConstraint(SpringLayout.WEST, cmbStatus, HORIZONTAL_PADDING, SpringLayout.EAST, lblStatus);
		
		layout.putConstraint(SpringLayout.NORTH, cmbPriority, VERTICAL_PADDING, SpringLayout.SOUTH, cmbStatus);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblPriority, 0, SpringLayout.VERTICAL_CENTER, cmbPriority);
		layout.putConstraint(SpringLayout.WEST, lblPriority, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, lblPriority, labelWidth, SpringLayout.WEST, lblPriority);
		layout.putConstraint(SpringLayout.WEST, cmbPriority, HORIZONTAL_PADDING, SpringLayout.EAST, lblPriority);
		
//		layout.putConstraint(SpringLayout.NORTH, txtEstimate, VERTICAL_PADDING, SpringLayout.SOUTH, cmbStatus);
//		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblEstimate, 0, SpringLayout.VERTICAL_CENTER, txtCreatedDate);
//		layout.putConstraint(SpringLayout.WEST, lblEstimate, 0, SpringLayout.WEST, lblTitle);
//		layout.putConstraint(SpringLayout.EAST, lblEsrimate, labelWidth, SpringLayout.WEST, lblCreatedDate);
//		layout.putConstraint(SpringLayout.WEST, txtEstimate, HORIZONTAL_PADDING, SpringLayout.EAST, lblCreatedDate);
		
		layout.putConstraint(SpringLayout.NORTH, txtCreatedDate, VERTICAL_PADDING, SpringLayout.SOUTH, cmbStatus);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblCreatedDate, 0, SpringLayout.VERTICAL_CENTER, txtCreatedDate);
		layout.putConstraint(SpringLayout.WEST, lblCreatedDate, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, lblCreatedDate, labelWidth, SpringLayout.WEST, lblCreatedDate);
		layout.putConstraint(SpringLayout.WEST, txtCreatedDate, HORIZONTAL_PADDING, SpringLayout.EAST, lblCreatedDate);
		
		layout.putConstraint(SpringLayout.NORTH, txtModifiedDate, VERTICAL_PADDING, SpringLayout.SOUTH, txtCreatedDate);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblModifiedDate, 0, SpringLayout.VERTICAL_CENTER, txtModifiedDate);
		layout.putConstraint(SpringLayout.WEST, lblModifiedDate, 0, SpringLayout.WEST, lblTitle);
		layout.putConstraint(SpringLayout.EAST, lblModifiedDate, labelWidth, SpringLayout.WEST, lblModifiedDate);
		layout.putConstraint(SpringLayout.WEST, txtModifiedDate, HORIZONTAL_PADDING, SpringLayout.EAST, lblModifiedDate);
		
//		layout.putConstraint(SpringLayout.NORTH, txtCreator, VERTICAL_PADDING, SpringLayout.SOUTH, txtModifiedDate);
//		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblCreator, 0, SpringLayout.VERTICAL_CENTER, txtCreator);
//		layout.putConstraint(SpringLayout.WEST, lblCreator, 0, SpringLayout.WEST, lblTitle);
//		layout.putConstraint(SpringLayout.EAST, lblCreator, labelWidth, SpringLayout.WEST, lblCreator);
//		layout.putConstraint(SpringLayout.WEST, txtCreator, HORIZONTAL_PADDING, SpringLayout.EAST, lblCreator);
//
//		layout.putConstraint(SpringLayout.NORTH, txtAssignee, VERTICAL_PADDING, SpringLayout.SOUTH, txtCreator);
//		layout.putConstraint(SpringLayout.VERTICAL_CENTER, lblAssignee, 0, SpringLayout.VERTICAL_CENTER, txtAssignee);
//		layout.putConstraint(SpringLayout.WEST, lblAssignee, 0, SpringLayout.WEST, lblTitle);
//		layout.putConstraint(SpringLayout.EAST, lblAssignee, labelWidth, SpringLayout.WEST, lblAssignee);
//		layout.putConstraint(SpringLayout.WEST, txtAssignee, HORIZONTAL_PADDING, SpringLayout.EAST, lblAssignee);

		// Add all of the components to this panel
		add(lblTitle);
		add(txtTitle);
		add(lblReleaseNumber);
		add(txtReleaseNumber);
		add(lblDescription);
		add(txtDescription);
		add(lblStatus);
		add(cmbStatus);
		add(lblPriority);
		add(cmbPriority);
		add(lblEstimate);
		add(txtEstimate);
		add(lblCreatedDate);
		add(txtCreatedDate);
		add(lblModifiedDate);
		add(txtModifiedDate);
//		add(lblCreator);
//		add(txtCreator);
//		add(lblAssignee);
//		add(txtAssignee);
	}

	/**
	 * Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled	Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;

		txtTitle.setEnabled(enabled);
		txtReleaseNumber.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
		cmbStatus.setEnabled(enabled);
		cmbPriority.setEnabled(enabled);
		txtEstimate.setEnabled(enabled);
		txtAssignee.setEnabled(enabled);
	}
	
	/**
	 * Updates the RequirementPanel's model to contain the values of the given Requirement and sets the 
	 * RequirementPanel's editMode to {@link Mode#EDIT}.
	 * 
	 * @param defect	The Requirement which contains the new values for the model.
	 */
	protected void updateModel(Requirement requirement) {
		updateModel(requirement, Mode.EDIT);
	}
	
	/**
	 * Updates the RequirementPanel's model to contain the values of the given Requirement.
	 * 
	 * @param	requirement	The Requirement which contains the new values for the model.
	 * @param	mode	The new editMode.
	 */
	protected void updateModel(Requirement requirement, Mode mode) {
	
		model.setId(requirement.getId());
		model.setTitle(requirement.getTitle());
		model.setReleaseNumber(requirement.getReleaseNumber());
		model.setDescription(requirement.getDescription());
		model.setEstimate(requirement.getEstimate());
		model.setCreationDate(requirement.getCreationDate());
		model.setLastModifiedDate(requirement.getLastModifiedDate());
		model.setStatus(requirement.getStatus());
		model.setPriority(requirement.getPriority());
		
		updateFields();
		//defectEventListModel.update(requirement);
		this.revalidate();
		layout.invalidateLayout(this);
		layout.layoutContainer(this);
		this.repaint();
		//parent.refreshScrollPane();
	}
	
	/**
	 * Updates the DefectPanel's fields to match those in the current model.
	 */
	private void updateFields() {
		txtTitle.setText(model.getTitle());
		//txtReleaseNumber.setText(model.getReleaseNumber());
		txtDescription.setText(model.getDescription());
		for (int i = 0; i < cmbStatus.getItemCount(); i++) {
			if (model.getStatus() == RequirementStatus.valueOf((String) cmbStatus.getItemAt(i))) {
				cmbStatus.setSelectedIndex(i);
			}
		}
		for (int i = 0; i < cmbPriority.getItemCount(); i++) {
			if (model.getPriority() == RequirementPriority.valueOf((String) cmbPriority.getItemAt(i))) {
				cmbPriority.setSelectedIndex(i);
			}
		}
		//txtEstimate.setText(model.getEstimate());
//		if (editMode == Mode.EDIT) {
//			lblCreatedDate.setText("Created:");
//			txtCreatedDate.setText(model.getCreationDate().toString());
//
//			lblModifiedDate.setText("Modified:");
//			txtModifiedDate.setText(model.getLastModifiedDate().toString());
//		}
//		if (model.getCreator() != null) {
//			txtCreator.setText(model.getCreator().getUsername());
//		}
//		if (model.getAssignee() != null) {
//			txtAssignee.setText(model.getAssignee().getUsername());
//		}
		
	}

	/**
	 * Returns a boolean representing whether or not input is enabled for the RequirementsPanel and its children.
	 * 
	 * @return	A boolean representing whether or not input is enabled for the RequirementsPanel and its children.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/**
	 * Gets the RequirementsPanel's internal model.
	 * @return
	 */
	public Requirement getModel() {
		return model;
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
	 * Returns the edit {@link Mode} for this DefectPanel.
	 * 
	 * @return	The edit {@link Mode} for this DefectPanel.
	 */
	public Mode getEditMode() {
		return editMode;
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
		//requirement.setReleaseNumber(txtReleaseNumber.getText());
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

	/**
	 * Returns the creator text field
	 * @return the creator text field
	 */
	public JTextField getCreatorField() {
		return txtCreator;
	}
	
}
	
	

