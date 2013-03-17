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
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.DefectView;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.TagPanel;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.TextUpdateListener;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.DefectPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.comments.NewCommentPanel;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.defect.defectevents.model.DefectEventListModel;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.models.Defect;

/**
 * Panel to display and edit the basic fields for a requirement
 *
 * @author CDUNKERS
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
	protected Requirement model; //need to get this from the repository when finished

	/*
	 * Form elements
	 */
	protected JTextField txtTitle;
	protected JTextField txtReleaseNumber;
	protected JComboBox cmbStatus;
	protected JComboBox cmbPriority;
	protected JTextArea txtDescription;	
	protected JTextField txtEstimateDate;
	protected JLabel txtCreatedDate;
	protected JLabel txtModifiedDate;
	protected JTextField txtCreator;
	protected JTextField txtAssignee;
	protected JLabel lblCreatedDate;
	protected JLabel lblModifiedDate;
	
	/** The panel containing GUI components for adding tests to requirements */
	protected TestPanel testPanel;
	
	/** A JList containing the change history for the requirements*/
	protected JList changeSetsList;
	
	/** The data model for the ChangeSet list */
	protected DefectEventListModel defectEventListModel;
	
	/** A panel containing GUI components for adding comments to the defect */
	protected NewCommentPanel commentPanel;
	
	/** The layout manager for this panel */
	protected SpringLayout layout;
	
	/*
	 * Listeners for the form fields
	 */
	protected final TextUpdateListener txtTitleListener;
	protected final TextUpdateListener txtDescriptionListener;
	protected final ComboUpdateListener cmbStatusListener;
	protected final TextUpdateListener txtCreatorListener;
	protected final TextUpdateListener txtAssigneeListener;

	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;
	
	/** An enum indicating if the form is in create mode or edit mode */
	protected Mode editMode;

	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	
}
