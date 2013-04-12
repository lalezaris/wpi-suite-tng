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
 *  Evan Polekoff
 *  Ned Shelton
 *  Sam Lalezari
 *  Tushar Narayan
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatusLists;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.CancelRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.CreateChildRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.DeleteRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CancelRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CreateChildRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.DeleteRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AssigneeView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.HistoryView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.NotesView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.RequirementTabsView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
/**
 * Panel to display and edit the basic fields for a requirement.
 * Adapted from DefectPanel in project DefectTracker.
 *
 * @author Chris Dunkers 
 * @author Joe Spicola
 * @author Ned Shelton
 * @author Michael Perrone
 * @author Evan Polekoff
 * @version Mar 17, 2013
 *
 */
@SuppressWarnings("serial")
public class RequirementPanel extends JPanel{

	/**
	 * The Enum Mode.
	 */
	public enum Mode {
		CREATE,
		EDIT, 
		CHILD;
	}

	/** The Requirement displayed in this panel */
	protected Requirement model; 
	protected final Requirement uneditedModel;

	/** The parent view **/
	protected RequirementView parent;

	/*
	 * Form elements
	 */
	protected JPlaceholderTextField txtTitle; //JPlaceholderTextField
	protected JTextField txtReleaseNumber;
	protected JComboBox cmbIteration;	
	protected Iteration[] knownIterations;
	protected JComboBox cmbType;
	protected JComboBox cmbStatus;
	protected JComboBox cmbPriority;
	protected JTextArea txtDescription;	
	protected IntegerField txtEstimate;
	protected IntegerField txtActual;
	protected JLabel txtCreatedDate;
	protected JLabel txtModifiedDate;
	protected JTextField txtCreator;
//	protected JTextField txtAssignee; //TODO replace with a tab
	protected JButton saveRequirementBottom;
	protected JButton cancelRequirementBottom;
	protected JButton deleteRequirementBottom;
	protected RequirementTabsView RTabsView;
	protected JButton createChildRequirement;
	protected JSplitPane splitPane;

	/** The ArrayList of Notes**/
	protected ArrayList<Note> notes = new ArrayList<Note>();

	/** the ArrayList of HistoricalChange **/
	protected ArrayList<HistoricalChange> history = new ArrayList<HistoricalChange>();

	/** NotesView for updating notes **/
	private NotesView notesView; //= new NotesView();

	/** HistoryView for updating history **/
	private HistoryView hv;
	
	/** AssigneeView for updating assignees **/
	//TODO finish implementing av
	private AssigneeView av;


	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;

	/**Error labels*/
	JLabel lblTitleError = new JLabel("ERROR: Must have a title", LABEL_ALIGNMENT);
	JLabel lblDescriptionError = new JLabel("ERROR: Must have a description", LABEL_ALIGNMENT);

	/** The layout manager for this panel */
	protected BorderLayout layout;

	/* origin/team1-theHistoryLogBackEnd
	 */	/** The other panels */
	protected JPanel panelOverall;
	protected JPanel panelOne;
	protected JPanel panelTwo;
	protected JPanel panelThree;
	protected JPanel panelFour;
	protected JPanel panelButtons;
	protected JPanel panelTabs;

	/** The layout managers for  
	 *  other panels */
	protected GridBagLayout layoutOverall;
	protected GridBagLayout layoutOne;
	protected GridBagLayout layoutTwo;
	protected GridBagLayout layoutThree;
	protected GridBagLayout layoutFour;
	protected GridBagLayout layoutButtons;
	protected GridBagLayout layoutTabs;

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
	 * @param mode the mode
	 */
	public RequirementPanel(RequirementView parent, Requirement requirement, Mode mode) {
		this.model = requirement;
		System.out.println("NEW PANEL WITH " + model.getTitle());
		System.out.println("\n\nChildren: " + model.getChildRequirementIds().toString());
		this.uneditedModel = requirement;
		this.parent = parent;
		System.out.println("INITIALIZED REQUIREMENTPANEL WITH MODEL: " + model.getIterationId() + " AND " + uneditedModel.getIterationId());
		editMode = mode;

		//get the list of notes from the given requirement
		notesView = new NotesView(model);

		//get the list of history from the given requirement
		hv = new HistoryView(model);

		//get the list of history from the given requirement
		av = new AssigneeView(model);
				
		// Indicate that input is enabled
		inputEnabled = true;

		//Use a grid bag layout manager
		layout = new BorderLayout();
		this.setLayout(layout);

		// Add all components to this panel
		addComponents();
		updateFields();
	}

	/**
	 * Adds the components to the panel and places constraints on them
	 * for the GridBagLayout manager.
	 */
	protected void addComponents() {
		//create a new constrain variable
		GridBagConstraints cOverall = new GridBagConstraints();
		GridBagConstraints cOne = new GridBagConstraints();
		GridBagConstraints cTwo = new GridBagConstraints();
		GridBagConstraints cThree = new GridBagConstraints();
		GridBagConstraints cFour = new GridBagConstraints();
		GridBagConstraints cButtons = new GridBagConstraints();
		GridBagConstraints c = new GridBagConstraints();

		// Construct all of the components for the form
		panelOverall = new JPanel();
		panelOne = new JPanel();
		panelTwo = new JPanel();
		panelThree = new JPanel();
		panelFour = new JPanel();
		panelButtons = new JPanel();
		panelTabs = new JPanel();
		
		txtTitle = new JPlaceholderTextField("Enter Title Here", 20);
		txtReleaseNumber = new JTextField(6);
		
		//filter out the expired iterations
		ArrayList<Iteration> knownIts = new ArrayList<Iteration>();
		knownIterations = Refresher.getInstance().getInstantIterations();
		for (int i = 0; i < knownIterations.length ;i++){
			if (knownIterations[i].getEndDate().compareTo(new Date()) >= 0 || knownIterations[i] == Iteration.getBacklog()){
				knownIts.add(knownIterations[i]);
			} else if (knownIterations[i].getId() == model.getIteration().getId()){
				knownIts.add(knownIterations[i]);
			}
		}
		knownIterations = new Iteration[knownIts.size()];
		for (int i = 0; i < knownIterations.length; i++){
			knownIterations[i] = knownIts.get(i);
		}
		
		cmbIteration = new JComboBox(knownIterations);
		txtDescription = new JTextArea(10,35);
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		String[] requirementStatusValues = RequirementStatusLists.getList(model);
		for (int i = 0; i < requirementStatusValues.length; i++) {
			requirementStatusValues[i] = RequirementStatusLists.getList(model)[i];
		}
		cmbStatus = new JComboBox(requirementStatusValues);
		String[] requirementPriorityValues = new String[RequirementPriority.values().length];
		for (int i = 0; i < RequirementPriority.values().length; i++) {
			requirementPriorityValues[i] = RequirementPriority.values()[i].toString();
		}
		cmbPriority = new JComboBox(requirementPriorityValues);
		String[] requirementTypeValues = new String[RequirementType.values().length];
		for (int i = 0; i < RequirementType.values().length; i++) {
			requirementTypeValues[i] = RequirementType.values()[i].toString();
		}
		cmbType = new JComboBox(requirementTypeValues);
		txtEstimate = new IntegerField(4);
		txtActual = new IntegerField(4);
		txtCreatedDate = new JLabel();
		txtModifiedDate = new JLabel("");
		txtCreator = new JTextField(12);
//		txtAssignee = new JTextField(12);

		notesView.setNotesList(this.getNotesArrayList());
		hv.setHistoryList(this.getHistoryList());
		av.setAssigneeList(model.getAssignee());
		RTabsView = new RequirementTabsView(notesView, hv, av);

		/**Save Button*/
		saveRequirementBottom = new JButton("Save");
		saveRequirementBottom.setAction(new SaveChangesAction(new SaveRequirementController(this.getParent())));
		deleteRequirementBottom = new JButton("Delete");
		deleteRequirementBottom.setAction(new DeleteRequirementAction(new DeleteRequirementController(this.getParent())));
		cancelRequirementBottom = new JButton("Cancel");
		cancelRequirementBottom.setAction(new CancelRequirementAction(new CancelRequirementController(this.getParent())));

		if (editMode == Mode.EDIT) {
			if(model.getStatus() == RequirementStatus.NEW ||
					model.getStatus() == RequirementStatus.OPEN ||
					model.getStatus() == RequirementStatus.INPROGRESS){
				createChildRequirement = new JButton("Add Child Requirement");
				createChildRequirement.setAction(new CreateChildRequirementAction(new CreateChildRequirementController(this.getParent())));
			}
		}
		
		//make sit so that all combo boxes will have black text when disabled for easier readability
		UIManager.put( "ComboBox.disabledForeground", Color.BLACK );

		/**Iteration Listener*/
		cmbIteration.addActionListener(new IterationListener());

		/**Estimate Listener*/
		txtEstimate.addKeyListener(new EstimateListener());

		/**Title and Description Listener*/
		txtTitle.addKeyListener(new SaveListener());
		txtDescription.addKeyListener(new SaveListener());

		/**Status Listener*/
		cmbStatus.addActionListener(new StatusListener());
		
		// set maximum widths of components so they are not stretched
		txtTitle.setMaximumSize(txtTitle.getPreferredSize());
		cmbStatus.setMaximumSize(cmbStatus.getPreferredSize());
		cmbStatus.setMaximumSize(cmbPriority.getPreferredSize());

		// Construct labels for the form fields
		JLabel lblType = new JLabel("Type:", LABEL_ALIGNMENT);
		JLabel lblReleaseNumber = new JLabel("Release Number:", LABEL_ALIGNMENT);
		JLabel lblIteration = new JLabel("Iteration:", LABEL_ALIGNMENT);
		JLabel lblDescription = new JLabel("Description: *", LABEL_ALIGNMENT);
		JLabel lblStatus = new JLabel("Status:", LABEL_ALIGNMENT);
		JLabel lblPriority = new JLabel("Priority:", LABEL_ALIGNMENT);
		JLabel lblEstimate = new JLabel("Estimate:", LABEL_ALIGNMENT);
		JLabel lblActual = new JLabel("Actual:", LABEL_ALIGNMENT);
		JLabel lblCreatedDate = new JLabel("Date Created:", LABEL_ALIGNMENT);
		JLabel lblModifiedDate = new JLabel("Date Modified:", LABEL_ALIGNMENT);
		JLabel lblCreator = new JLabel("Creator:", LABEL_ALIGNMENT);
		JLabel lblAssignee = new JLabel("Assignee:", LABEL_ALIGNMENT);

		int labelWidth = lblDescription.getPreferredSize().width;

		//Panel One - panel at the top --------------------------------------------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutOne = new GridBagLayout();
		panelOne.setLayout(layoutOne);	

		layoutOne.columnWeights = new double[]{.2, .8};
		cOne.anchor = GridBagConstraints.LINE_START; 
		cOne.gridx = 0;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 2;
		cOne.insets = new Insets(10,10,5,0); //top,left,bottom,right
		panelOne.add(txtTitle, cOne);
		
		cOne.insets = new Insets(5,0,0,0);
		cOne.gridx = 2;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		 cOne.weighty = 0;
		 cOne.gridwidth = 3;
		 lblTitleError.setVisible(false);
		 lblTitleError.setForeground(Color.RED);
		 panelOne.add(lblTitleError, cOne);
		 
		 cOne.insets = new Insets(5,10,10,0); //top,left,bottom,right
		 cOne.gridx = 0;
		 cOne.gridy = 1;
		 cOne.weightx = 0.5;
		 cOne.weighty = 0.5;
		 cOne.gridwidth = 1;
		 panelOne.add(lblIteration, cOne);

		 cOne.gridx = 1;
		 cOne.gridy = 1;
		 cOne.weightx = 0.5;
		 cOne.weighty = 0.5;
		 cOne.gridwidth = 1;
		 panelOne.add(cmbIteration, cOne);

		 cOne.gridx = 2;
		 cOne.gridy = 1;
		 cOne.weightx = 0.5;
		 cOne.weighty = 0.5;
		 cOne.gridwidth = 1;
		 panelOne.add(lblReleaseNumber, cOne);

		 cOne.gridx = 3;
		 cOne.gridy = 1;
		 cOne.weightx = 0.5;
		 cOne.weighty = 0.5;
		 cOne.gridwidth = 1;
		 panelOne.add(txtReleaseNumber, cOne);

		 //Default the Iteration Box based on the values of the estimate (Don't let you choose it if the estimate is blank).
		 if(model.getEstimateEffort() > 0) {
			 cmbIteration.setEnabled(true);
			 cmbIteration.setBackground(Color.WHITE);
		 }
		 else
			 cmbIteration.setEnabled(false);

		 //Default the save button depending on what is filled in (Title and Description).
		 if(!model.getTitle().equals("") && !model.getDescription().equals("") && model.getTitle() != null && model.getDescription() != null){
			 saveRequirementBottom.setEnabled(true);
		 }
		 else{
			 saveRequirementBottom.setEnabled(false);
		 }

		 //Move the requirement to the backlog if it is set to OPEN.
		 if(model.getStatus() == RequirementStatus.OPEN){
			 model.setIteration(Iteration.getBacklog());
			 cmbIteration.setEnabled(true);
			 cmbStatus.setEnabled(true);
		 }

//		 else if(model.getStatus() == RequirementStatus.INPROGRESS)
//			 deleteRequirementBottom.setEnabled(false);
//		 else
//			 deleteRequirementBottom.setEnabled(true);
		 
		 cOne.gridx = 0;
		 cOne.gridy = 2;
		 cOne.weightx = 0.5;
		 cOne.weighty = 0.5;
		 cOne.gridwidth = 1;
		 panelOne.add(lblType, cOne);
		 
		 cOne.gridx = 1;
		 cOne.gridy = 2;
		 cOne.weightx = 0.5;
		 cOne.weighty = 0.5;
		 cOne.gridwidth = 1;
		 cmbType.setBackground(Color.WHITE);
		 panelOne.add(cmbType, cOne);
		 
//		 cOne.weightx = 0.5;
//		 cOne.weighty = 0.5;
//		 cOne.gridx = 2;
//		 cOne.gridy = 2;
//		 cOne.anchor = GridBagConstraints.LINE_START;
//		 panelOne.add(lblCreator, cOne);
//
//		 cOne.weightx = 0.5;
//		 cOne.weighty = 0.5;
//		 cOne.gridx = 3;
//		 cOne.gridy = 2;
//		 txtCreator.setEnabled(false);
//		 txtCreator.setText(model.getCreator());
//		 txtCreator.setDisabledTextColor(Color.BLACK);
//		 cOne.anchor = GridBagConstraints.LINE_START;
//		 panelOne.add(txtCreator, cOne);		 

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
		 lblDescriptionError.setForeground(Color.RED);
		 panelTwo.add(lblDescriptionError, cTwo);

		 JScrollPane scrollPaneDescription = new JScrollPane(txtDescription);
		 cTwo.anchor = GridBagConstraints.LAST_LINE_START; 
		 cTwo.insets = new Insets(0,10,10,0);
		 cTwo.gridx = 0;
		 cTwo.gridy = 1;
		 cTwo.weightx = 0.5;
		 cTwo.weighty = 0.5;
		 cTwo.gridwidth = 2;
		 panelTwo.add(scrollPaneDescription, cTwo);

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
		 cThree.anchor = GridBagConstraints.LINE_START;
		 cmbStatus.setSelectedItem(requirementStatusValues[0]);
		 panelThree.add(cmbStatus, cThree);

		 cThree.weightx = 0.5;
		 cThree.weighty = 0.5;
		 cThree.gridx = 2;
		 cThree.gridy = 0;
		 cThree.anchor = GridBagConstraints.LINE_START;
		 panelThree.add(lblPriority, cThree);

		 cThree.weightx = 0.5;
		 cThree.weighty = 0.5;
		 cThree.gridx = 3;
		 cThree.gridy = 0;
		 cThree.anchor = GridBagConstraints.LINE_START;
		 cmbPriority.setSelectedItem(requirementPriorityValues[1]);
		 cmbPriority.setBackground(Color.WHITE);
		 panelThree.add(cmbPriority, cThree);

		 cThree.weightx = 0.5;
		 cThree.weighty = 0.5;
		 cThree.gridx = 0;
		 cThree.gridy = 1;
		 cThree.anchor = GridBagConstraints.LINE_START;
		 panelThree.add(lblEstimate, cThree);

		 //cThree.fill = GridBagConstraints.HORIZONTAL;
		 cThree.weightx = 0.5;
		 cThree.weighty = 0.5;
		 cThree.gridx = 1;
		 cThree.gridy = 1;
		 cThree.anchor = GridBagConstraints.LINE_START;
		 panelThree.add(txtEstimate, cThree);

		 cThree.weightx = 0.5;
		 cThree.weighty = 0.5;
		 cThree.gridx = 2;
		 cThree.gridy = 1;
		 cThree.anchor = GridBagConstraints.LINE_START;
		 panelThree.add(lblActual, cThree);

		 cThree.weightx = 0.5;
		 cThree.weighty = 0.5;
		 cThree.gridx = 3;
		 cThree.gridy = 1;
		 panelThree.add(txtActual, cThree);

		 //Panel Four - panel below panel three -------------------------------------------------------------------------------------
		 //Use a grid bag layout manager

		 layoutFour = new GridBagLayout();
		 panelFour.setLayout(layoutFour);

		 cFour.insets = new Insets(10,10,10,0);

//		 cFour.weightx = 0.5;
//		 cFour.weighty = 0.5;
//		 cFour.gridx = 0;
//		 cFour.gridy = 0;
//		 cFour.anchor = GridBagConstraints.LINE_START;
//		 panelFour.add(lblCreatedDate, cFour);
//
//		 cFour.weightx = 0.5;
//		 cFour.weighty = 0.5;
//		 cFour.gridx = 1;
//		 cFour.gridy = 0;
//		 txtCreatedDate.setText(model.getCreationDate().toString());
//		 cFour.anchor = GridBagConstraints.LINE_START;
//		 panelFour.add(txtCreatedDate, cFour);

//		 cFour.weightx = 0.5;
//		 cFour.weighty = 0.5;
//		 cFour.gridx = 0;
//		 cFour.gridy = 1;
//		 panelFour.add(lblModifiedDate, cFour);
//
//		 cFour.weightx = 0.5;
//		 cFour.weighty = 0.5;
//		 cFour.gridx = 1;
//		 cFour.gridy = 1;
//		 cFour.anchor = GridBagConstraints.LINE_START;
//		 panelFour.add(txtModifiedDate, cFour);

//		 cFour.weightx = 0.5;
//		 cFour.weighty = 0.5;
//		 cFour.gridx = 0;
//		 cFour.gridy = 2;
//		 cFour.anchor = GridBagConstraints.LINE_START;
//		 panelFour.add(lblAssignee, cFour);
//
//		 cFour.weightx = 0.5;
//		 cFour.weighty = 0.5;
//		 cFour.gridx = 1;
//		 cFour.gridy = 2;
//		 cFour.anchor = GridBagConstraints.LINE_START;
////		 panelFour.add(txtAssignee, cFour);


		 //Panel Buttons - panel holding all other panels --------------------------------------------------------------------------
		 //Use a grid bag layout manager
		 layoutButtons = new GridBagLayout();
		 panelButtons.setLayout(layoutButtons);

		 cButtons.insets = new Insets(10,10,10,10);
		 if (editMode == Mode.EDIT) { 
			 if(model.getStatus() == RequirementStatus.NEW ||
					 model.getStatus() == RequirementStatus.OPEN ||
					 model.getStatus() == RequirementStatus.INPROGRESS){
				 cButtons.weightx = 0.5;
				 cButtons.weighty = 0.5;
				 cButtons.gridx = 0;
				 cButtons.gridy = 0;
				 cButtons.gridwidth = 3;
				 panelButtons.add(createChildRequirement, cButtons);
			 }
		 }

		 cButtons.weightx = 0.5;
		 cButtons.weighty = 0.5;
		 cButtons.gridx = 0;
		 cButtons.gridy = 6;
		 cButtons.gridwidth = 1;
		 panelButtons.add(saveRequirementBottom, cButtons);

		 cButtons.weightx = 0.5;
		 cButtons.weighty = 0.5;
		 cButtons.gridx = 2;
		 cButtons.gridy = 6;
		 deleteRequirementBottom.setVisible(false);
		 panelButtons.add(deleteRequirementBottom, cButtons);

		 cButtons.weightx = 0.5;
		 cButtons.weighty = 0.5;
		 cButtons.gridx = 1;
		 cButtons.gridy = 6;
		 panelButtons.add(cancelRequirementBottom, cButtons);

		 //Panel Tabs - panel holding all other panels --------------------------------------------------------------------------
		 //Use a grid bag layout manager
		 layoutTabs = new GridBagLayout();
		 panelTabs.setLayout(layoutTabs);

		 cOverall.fill = GridBagConstraints.BOTH;
		 cOverall.weightx = 0.5;
		 cOverall.weighty = 0.5;
		 cOverall.gridx = 0;
		 cOverall.gridy = 0;
		 cOverall.anchor = GridBagConstraints.LINE_START;
		 panelTabs.add(RTabsView, cOverall);


		 //Panel Overall - panel holding all other panels --------------------------------------------------------------------------
		 //Use a grid bag layout manager
		 layoutOverall = new GridBagLayout();
		 panelOverall.setLayout(layoutOverall);
		 //Overall Panel
		 cOverall.weightx = 0.5;
		 cOverall.weighty = 0.5;
		 cOverall.gridx = 0;
		 cOverall.gridy = 0;
		 cOverall.anchor = GridBagConstraints.LINE_START;
		 panelOverall.add(panelOne, cOverall);

		 cOverall.weightx = 0.5;
		 cOverall.weighty = 0.5;
		 cOverall.gridx = 0;
		 cOverall.gridy = 1;
		 cOverall.anchor = GridBagConstraints.LINE_START;
		 panelOverall.add(panelTwo, cOverall);

		 cOverall.weightx = 0.5;
		 cOverall.weighty = 0.5;
		 cOverall.gridx = 0;
		 cOverall.gridy = 2;
		 cOverall.anchor = GridBagConstraints.LINE_START;
		 panelOverall.add(panelThree, cOverall);

		 cOverall.weightx = 0.5;
		 cOverall.weighty = 0.5;
		 cOverall.gridx = 0;
		 cOverall.gridy = 3;
		 cOverall.anchor = GridBagConstraints.LINE_START;
		 panelOverall.add(panelFour, cOverall);
		 
		 
		 cOverall.weightx = 0.5;
		 cOverall.weighty = 0.5;
		 cOverall.gridx = 0;
		 cOverall.gridy = 4;
		 cOverall.anchor = GridBagConstraints.LINE_START;
		 panelOverall.add(panelButtons, cOverall);

		 // add to this Panel -----------------------------------------------------------------------------------------------------------------

		 JPanel leftPanel = new JPanel();
		 leftPanel.setLayout(new GridBagLayout());
		 GridBagConstraints cPane = new GridBagConstraints();

		 cPane.anchor = GridBagConstraints.FIRST_LINE_START;
		 cPane.weightx = 0.1;
		 cPane.weighty = 0.1;
		 cPane.gridx = 0;
		 cPane.gridy = 0;
		 leftPanel.add(panelOverall,cPane);
		 
		 JScrollPane scrollPaneLeft = new JScrollPane(leftPanel);
		 JScrollPane scrollPaneTabs = new JScrollPane(panelTabs);

		 splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneLeft, scrollPaneTabs);
		 splitPane.setDividerLocation(0.5);
		 splitPane.resetToPreferredSizes();
		 this.add(splitPane, BorderLayout.CENTER);

		 //depending on the mode, disable certain components
		 if (editMode == Mode.CREATE || editMode == Mode.CHILD) {
			 cmbStatus.setEnabled(false);
			 txtActual.setEnabled(false);
		 }

		 if (editMode == Mode.CHILD) {
			 cmbIteration.setEnabled(false);
			 txtReleaseNumber.setEnabled(false);
		 }

		 if(editMode == Mode.EDIT && !model.isTopLevelRequirement()){
			 cmbStatus.setEnabled(false);
			 cmbIteration.setEnabled(false);
			 txtReleaseNumber.setEnabled(false);
			 txtActual.setEnabled(false);

		 }
		 
		 // depending on the status and sub-requirements, disable certain components

		 if (model.getStatus() == RequirementStatus.INPROGRESS
				 || model.getStatus() == RequirementStatus.COMPLETE){
			 //TODO: uncomment the next line once busy waiting issue is fixed
			 //|| childList.retrieveChildrenByID(model.getId()).size() != 0) {
			 txtEstimate.setEnabled(false);
		 }
		 
//		 if (model.getChildRequirementIds().isEmpty()) {
//			 setDeleteEnabled(false);
//		 }


		 //depending on the user's permission, disable certain components
		 RMPermissionsLevel pLevel = CurrentUserPermissions.getCurrentUserPermission();
		 if(!model.getAssignee().contains(ConfigManager.getConfig().getUserName()) && pLevel == RMPermissionsLevel.UPDATE){
			 pLevel = RMPermissionsLevel.NONE;
		 }
		 switch (pLevel){
		 case NONE:
			 disableStuff(new JComponent[]{cmbStatus,cmbPriority,cmbType,txtDescription,txtEstimate,txtActual,txtCreator,/*txtAssignee,*/
					 txtTitle,txtReleaseNumber,cmbIteration,notesView.getSaveButton(),notesView.getTextArea(),saveRequirementBottom, 
					 deleteRequirementBottom, cancelRequirementBottom, createChildRequirement, av.getBtnAdd(), av.getBtnRemove()});
			 changeBackground(new JTextComponent[]{txtDescription,txtEstimate,txtActual,txtCreator,/*txtAssignee,*/
					 txtTitle,txtReleaseNumber,notesView.getTextArea()});
			 makeTextBlack(new JTextComponent[]{txtDescription,txtEstimate,txtActual,txtCreator,/*txtAssignee,*/
					 txtTitle,txtReleaseNumber});
			 makeStuffNotVisible(new JComponent[]{panelButtons});
			 break;
		 case UPDATE: 
			 disableStuff(new JComponent[]{cmbStatus,cmbPriority,cmbType,txtDescription,txtEstimate,
					 txtCreator,/*txtAssignee,*/txtTitle,txtReleaseNumber,cmbIteration, deleteRequirementBottom, createChildRequirement, av.getBtnAdd(), av.getBtnRemove()});
			 changeBackground(new JTextComponent[]{txtDescription,txtEstimate,txtCreator,/*txtAssignee,*/txtTitle,txtReleaseNumber,});
			 makeTextBlack(new JTextComponent[]{txtDescription,txtEstimate,txtCreator,/*txtAssignee,*/txtTitle,txtReleaseNumber});
			 makeStuffNotVisible(new JComponent[]{deleteRequirementBottom, createChildRequirement});
			 break;		
		 case ADMIN: break;
		 }
		 
		 
		 // loops through assignees in a requirement to enable actual estimate field
		 
		 if(model.getAssignee().contains(ConfigManager.getConfig().getUserName()) && pLevel != RMPermissionsLevel.NONE){
			 enableStuff(new JComponent[]{txtActual, cancelRequirementBottom, saveRequirementBottom});
		 }
		 
		 if (model.getStatus() == RequirementStatus.DELETED)
			 disableStuff(new JComponent[]{cmbPriority,txtDescription,txtEstimate,txtActual,txtCreator,/*txtAssignee,*/
					 txtTitle,txtReleaseNumber,cmbIteration,notesView.getSaveButton(),notesView.getTextArea(), 
					 deleteRequirementBottom, createChildRequirement});
		 
		 System.out.println("HELLO!!!! " + model.getChildRequirementIds().toString());
		 if (!getEditedModel().getChildRequirementIds().isEmpty()) {
			 disableStuff(new JComponent[]{deleteRequirementBottom});
		 }
	}

	/**
	 * Sets the delete button enabled.
	 *
	 * @param enabled a boolean indicating whether or not to Enabled
	 */
	public void setDeleteEnabled(boolean enabled) {
		deleteRequirementBottom.setEnabled(enabled);
	}
	
	/**
	 * Disable stuff.
	 *
	 * @param components the components to be disabled
	 */
	private void disableStuff(JComponent[] components){
		for(JComponent com:components){
			if (com!=null)
				com.setEnabled(false);
		}
	}
	

	/**
	 * Enables components for editing purposes.
	 * 
	 * @param components to be enabled
	 */
	private void enableStuff(JComponent[] components){
		for(JComponent com:components){
			if (com!=null)
				com.setEnabled(true);
		}
	}
	
	/**
	 * Change background.
	 *
	 * @param components the components
	 */
	private void changeBackground(JTextComponent[] components){
		for(JComponent com:components){
			if (com!=null)
				com.setBackground(this.getBackground());
		}
	}
	
	/**
	 * Make text black.
	 *
	 * @param components the components
	 */
	private void makeTextBlack(JTextComponent[] components){
		for(JTextComponent com:components){
			if (com!=null)
				com.setDisabledTextColor(Color.BLACK);
			}
	}
	
	/**
	 * Make stuff not visible.
	 *
	 * @param components the components
	 */
	private void makeStuffNotVisible(JComponent[] components){
		for(JComponent com:components){
			if (com!=null)
				com.setVisible(false);
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
	 * Sets if the input is enabled.
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
	 * @param requirement The Requirement which contains the new values for the model
	 */
	public void updateModel(Requirement requirement) {
		updateModel(requirement, Mode.EDIT);
	}

	/**
	 * Updates the RequirementPanel's model to contain the values of the given Requirement.
	 * 
	 * @param	requirement	The requirement which contains the new values for the model
	 * @param	mode	The new editMode
	 */
	protected void updateModel(Requirement requirement, Mode mode) {
		editMode = mode;

		model.setId(requirement.getId());
		model.setTitle(requirement.getTitle());
		model.setType(requirement.getType());
		model.setReleaseNumber(requirement.getReleaseNumber());
		model.setIteration(requirement.getIteration());
		model.setDescription(requirement.getDescription());
		model.setAssignee(requirement.getAssignee());
		model.setCreator(requirement.getCreator());
		model.setCreationDate(requirement.getCreationDate());
		model.setLastModifiedDate(requirement.getLastModifiedDate());
		model.setStatus(requirement.getStatus());
		model.setPriority(requirement.getPriority());
		model.setEstimateEffort(requirement.getEstimateEffort());
		model.setActualEffort(requirement.getActualEffort());
		requirement.updateNotes(this.getNotesArrayList());
		model.updateNotes(requirement.getNotes());
		requirement.updateHistory(this.getHistoryList());
		model.updateHistory(requirement.getHistory());

		model.setParentRequirementId(requirement.getParentRequirementId());

		updateFields();
		this.revalidate();
		layout.invalidateLayout(this);
		layout.layoutContainer(this);
		this.repaint();
		parent.refreshScrollPane();
	}

	/**
	 * Checks to see if it is an empty string.
	 * Returns -1 if the string is less than 0 or blank;
	 * returns the integer value otherwise.
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

	/**
	 * Returns the model object represented by this view's fields.
	 * 
	 * TODO: Do some basic input verification
	 * @return the model represented by this view
	 */
	public Requirement getEditedModel() {
		Requirement requirement = new Requirement();
		requirement.setId(model.getId());
		requirement.setType(RequirementType.valueFromString((String) cmbType.getSelectedItem()));
		requirement.setTitle(txtTitle.getText().trim());
		requirement.setReleaseNumber(txtReleaseNumber.getText());
		requirement.setIteration((Iteration) cmbIteration.getSelectedItem());
		requirement.setDescription(txtDescription.getText().trim());
		requirement.setStatus(RequirementStatus.valueOf((String) cmbStatus.getSelectedItem()));
		requirement.setPriority(RequirementPriority.valueFromString((String) cmbPriority.getSelectedItem()));
		requirement.setEstimateEffort(getValue(txtEstimate)); // return -1 if the field was left blank
		requirement.setActualEffort(getValue(txtActual)); // return -1 if the field was left blank
		requirement.setCreationDate(model.getCreationDate());

		requirement.updateNotes(notesView.getNotesList());
		requirement.updateHistory(hv.getHistoryList());
		requirement.setAssignee(av.getAssignedUserAL());
		requirement.setParentRequirementId(model.getParentRequirementId());
		requirement.setSubRequirements(model.getChildRequirementIds());

		/*
		if (!(txtAssignee.getText().equals(""))) {
			requirement.getAssignee().add(txtAssignee.getText());
			requirement.setAssignee(requirement.getAssignee());
		}
		*/

		if (!(txtCreator.getText().equals(""))) {
			requirement.setCreator(txtCreator.getText());
		}

		System.out.println(requirement.toJSON());
		return requirement;
	}

	/**
	 * Checks to make sure the title and description are filled in.
	 *
	 * @return 3 if both title and description not filled in, 2 if only title, 1 if only description, 0 otherwise
	 */
	public int checkRequiredFields(){
		String tempTitle = txtTitle.getText().trim();
		String tempDesc = txtDescription.getText().trim();
		if((tempTitle.equals(null) || tempTitle.equals("")) && 
				(tempDesc.equals(null) || tempDesc.equals(""))){
			lblTitleError.setVisible(true);
			lblDescriptionError.setVisible(true);
			return 3;
		} else if(tempTitle.equals(null) || tempTitle.equals("")){
			lblTitleError.setVisible(true);
			lblDescriptionError.setVisible(false);
			return 2; 
		} else if(tempDesc.equals(null) || tempDesc.equals("")){
			lblDescriptionError.setVisible(true);
			lblTitleError.setVisible(false);
			return 1;
		} else 
			return 0;
	}

	/**
	 * Update fields.
	 */
	private void updateFields() {
		if(!(model.getTitle().equals(null) || model.getTitle().equals("")))
			txtTitle.setText(model.getTitle());
		txtDescription.setText(model.getDescription());
		txtReleaseNumber.setText(model.getReleaseNumber());
		txtEstimate.setText( String.valueOf(model.getEstimateEffort()) );
		txtActual.setText( String.valueOf(model.getActualEffort()) );

		for (int i = 0; i < cmbType.getItemCount(); i++) {
			if (model.getType().toString().equals(cmbType.getItemAt(i).toString())) {
				cmbType.setSelectedIndex(i);
			}
		}
		
		for (int i = 0; i < cmbStatus.getItemCount(); i++) {
			if (model.getStatus() == RequirementStatus.valueOf((String) cmbStatus.getItemAt(i))) {
				cmbStatus.setSelectedIndex(i);
			}
		}

		for (int i = 0; i < cmbIteration.getItemCount(); i++) {
			if (model.getIteration().toString().equals(knownIterations[i].toString()) ){
				cmbIteration.setSelectedIndex(i);

			}
		}

		for (int i = 0; i < cmbPriority.getItemCount(); i++) {
			if (model.getPriority().toString().equals(cmbPriority.getItemAt(i).toString())) {
				cmbPriority.setSelectedIndex(i);
			}
		}

		if (editMode == Mode.EDIT) {
			txtCreatedDate.setText(model.getCreationDate().toString());
			txtModifiedDate.setText(model.getLastModifiedDate().toString());
			deleteRequirementBottom.setVisible(true);
		}
		if (model.getCreator() != null) {
			txtCreator.setText(model.getCreator());
		}

		/*
		if (model.getAssignee() != null) {
			txtAssignee.setText(model.getAssignee().toString().equals("[]")? "" : model.getAssignee().toString().replaceAll("\\[", "").replaceAll("\\]", ""));	
			//if (!(txtAssignee.getText().equals("")))
			//(!(txtAssignee.getText().equals(""))) {
			//requirement.setAssignee(new User("", txtAssignee.getText(), "", -1));
		}
		*/
		notesView.setNotesList(model.getNotes());
		hv.setHistoryList(model.getHistory());
		av.setAssigneeList(model.getAssignee());
	}

	/**
	 * Gets the edits the mode.
	 *
	 * @return the edits the mode
	 */
	public Mode getEditMode() {
		return editMode;
	}

	//TODO: Getter and Setter for Notes
	/**
	 * Gets the notes array list.
	 *
	 * @return the notes array list
	 */
	public ArrayList<Note> getNotesArrayList() {
		return notes;
	}

	/**
	 * Gets the history list.
	 *
	 * @return the history list
	 */
	public ArrayList<HistoricalChange> getHistoryList(){
		return history;
	}

	/**
	 * Sets the notes array list.
	 *
	 * @param aln the new notes array list
	 */
	public void setNotesArrayList(ArrayList<Note> aln) {
		notes = aln;
	}

	/**
	 * Gets the unedited model.
	 *
	 * @return the unedited requirement model
	 */
	public Requirement getUneditedModel() {
		return uneditedModel;
	}
	
	/**
	 * Sets the history array list.
	 *
	 * @param alh the new history array list
	 */
	public void setHistoryArrayList(ArrayList<HistoricalChange> alh){
		history = alh;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public Requirement getModel() {
		return model;
	}
	
	/**
	 * The listener interface for receiving status events.
	 * The class that is interested in processing a status
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addStatusListener<code> method. When
	 * the status event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see StatusEvent
	 */
	public class StatusListener implements ActionListener{
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent status) {
			JComboBox cb = (JComboBox)status.getSource();
			System.out.println(cb.getSelectedItem());

			changeIteration(cb);
		}
		
		/**
		 * Change the iteration to which a requirement belongs.
		 *
		 * @param cb the JComboBox which contains all the iterations.
		 */
		public void changeIteration(JComboBox cb){
			if(RequirementStatus.valueOf((String) cb.getSelectedItem()) == RequirementStatus.OPEN && model.getIterationId() != Iteration.getBacklog().getId() ){
				cmbIteration.setSelectedIndex(cmbIteration.getItemCount()-1);
				cmbIteration.setEnabled(false);
			} else if((model.getStatus() == RequirementStatus.COMPLETE || model.getStatus() == RequirementStatus.DELETED)  && model.getIterationId() != Iteration.getBacklog().getId()){ 
				for (int i = 0; i < cmbIteration.getItemCount(); i++) {
					if (model.getIteration().toString().equals(knownIterations[i].toString()) ){
						cmbIteration.setSelectedIndex(i);
						if(model.getStatus() == RequirementStatus.COMPLETE && model.getParentRequirementId() == -1){
							cmbIteration.setEnabled(true);
						}
					}
				}
			}
		}
	}

	//TODO: class exists in action package, refactor
	/**
	 * The listener interface for receiving iteration events.
	 * The class that is interested in processing a iteration
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addIterationListener<code> method. When
	 * the iteration event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see IterationEvent
	 */
	public class IterationListener implements ActionListener {
		/* (non-Javadoc)
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */

		@Override
		public void actionPerformed(ActionEvent iterations) {
			JComboBox cb = (JComboBox)iterations.getSource();
			System.out.println(cb.getSelectedItem());

			changeStatus(cb);
		}

		/**
		 * Change the status of a requriement.
		 *
		 * @param cb the JComboBox which contains the list of status
		 */
		public void changeStatus(JComboBox cb){
			int i; //For loop counter, also used directly below the for loop.
			Boolean enabled = true;
			Boolean runThatForLoop = false;
			Boolean listHasStatus = false;
			RequirementStatus setTo = RequirementStatus.OPEN;
//			if (model.getStatus() != RequirementStatus.DELETED){
				//Change the status back to whatever it was when the backlog is reselected (They changed their mind).
				if((model.getStatus() == RequirementStatus.OPEN || model.getStatus() == RequirementStatus.NEW) && cb.getSelectedItem() == Iteration.getBacklog()){
					setTo = model.getStatus();
					enabled = false;
					runThatForLoop = true;
				}
				//Change the status to In Progress automatically when the req is assigned to an iteration.
				else if((model.getStatus() == RequirementStatus.OPEN || model.getStatus() == RequirementStatus.NEW) && cb.getSelectedItem() != Iteration.getBacklog()){
					setTo = RequirementStatus.INPROGRESS;
					enabled = false;
					runThatForLoop = true;
				}
				//Change the status to Open automatically when the backlog is selected.
				else if((model.getStatus() == RequirementStatus.INPROGRESS) && cb.getSelectedItem() == Iteration.getBacklog()){
					setTo = RequirementStatus.OPEN;
					enabled = false;
					runThatForLoop = true;
				}
				//Set the status back to In Progress when they reassigned it to an iteration (but let them change the status).
				else if((model.getStatus() == RequirementStatus.INPROGRESS) && cb.getSelectedItem() != Iteration.getBacklog()){
					setTo = RequirementStatus.INPROGRESS;
					enabled = true;
					runThatForLoop = true;
				}
//			} else
//			{
//				setTo = RequirementStatus.DELETED;
//				enabled = true;
//				runThatForLoop = true;
//			}

			//Add statuses that are necessary to the dropdown list.
			if(runThatForLoop){
				for (i = 0; i < cmbStatus.getItemCount(); i++) {
					System.out.println("For Loop Iteration: " + i);
					if (setTo == RequirementStatus.valueOf((String) cmbStatus.getItemAt(i))) {
						cmbStatus.setSelectedIndex(i);
						listHasStatus = true;
						System.out.println("Found Index!");
					}
				}
				if(!listHasStatus){
					cmbStatus.addItem(setTo.toString());
					cmbStatus.setSelectedIndex(i);//The element is added to the end of the cmbStatus, so its spot is i.
					System.out.println("Did not find index. Added " + setTo + " to the end, at spot " + i);
				}
			}
			runThatForLoop = false;

			//child status should not be editable on creation
			RMPermissionsLevel pLevel = CurrentUserPermissions.getCurrentUserPermission();
			if (pLevel == RMPermissionsLevel.ADMIN && editMode != Mode.CHILD){
				cmbStatus.setEnabled(enabled);
				cmbStatus.setBackground(Color.WHITE);
			}
		}

	}

	//A Key Listener on the Estimate to grey out the Iteration drop down until there is a value in the estimate.
	/**
	 * The listener interface for receiving estimate events.
	 * The class that is interested in processing a estimate
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addEstimateListener<code> method. When
	 * the estimate event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see EstimateEvent
	 */
	public class EstimateListener implements KeyListener {

		/**
		 * Action performed.
		 *
		 * @param estimate the estimate
		 */
		public void actionPerformed(ActionEvent estimate) {}
		
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {}
		
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			Boolean enabled = false;
			try{
				if(txtEstimate.getText() == "" || txtEstimate.getText() == null){
					enabled = false;
				}
				else if(Integer.parseInt(txtEstimate.getText()) > 0){
						enabled = true;
				}
				else{
					enabled = false;
				}
			}
			catch(NumberFormatException exception){
				enabled = false;
			}
			if(editMode != Mode.CHILD){
				cmbIteration.setEnabled(enabled);
				cmbIteration.setBackground(Color.WHITE);
			}
		}
	}

	//A Key Listener on the Title and Description to grey out the save button until there is text in them.
	/**
	 * The listener interface for receiving save events.
	 * The class that is interested in processing a save
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addSaveListener<code> method. When
	 * the save event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see SaveEvent
	 */
	public class SaveListener implements KeyListener {

		/**
		 * Action performed.
		 *
		 * @param estimate the estimate
		 */
		public void actionPerformed(ActionEvent estimate) {}
		
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {}
		
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {}

		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			Boolean enabled = false;

			try{
				if((txtTitle.getText().equals("") || txtEstimate.getText() == null) || 
						(txtDescription.getText().equals("") || txtDescription.getText() == null)){
					enabled = false;
				}
				else{
					enabled = true;
				}
			}
			catch(NumberFormatException exception){
				enabled = false;
			}
			saveRequirementBottom.setEnabled(enabled);
		}
	}

	/**
	 * Gets the notes view.
	 *
	 * @return the notesView
	 */
	public NotesView getNotesView() {
		return notesView;
	}
	
	/**
	 * Checks to see if any changes have been made.
	 *
	 * @return true if changes has been made otherwise false
	 */
	public boolean isThereChanges(){
		Requirement oldR = getUneditedModel();
		Requirement newR = getEditedModel();
		
		int notesDifference = (newR.getNotes().size() - oldR.getNotes().size());
		
		//compare titles
		if (oldR.getTitle().compareTo(newR.getTitle()) != 0){//if old and new are not the same
			return true;
		}
		
		//compare Release Numbers
		if (!oldR.getReleaseNumber().equals(newR.getReleaseNumber())){//if old and new are not the same
			return true;
		}
		
		//compare type
		if (oldR.getType().compareTo(newR.getType()) != 0){//if old and new are not the same
			return true;
		}
		
		//compare Iterations
		if (oldR.getIterationId()!=(newR.getIterationId())){//if old and new are not the same
			return true;
		}
				
		//compare Descriptions
		if (oldR.getDescription().compareTo(newR.getDescription()) != 0){//if old and new are not the same
			return true;
		}
		
		//compare Statuses
		if (oldR.getStatus() != newR.getStatus()){//if old and new are not the same
			return true;
		}
		
		//compare Priorities
		if (oldR.getPriority() != newR.getPriority()){//if old and new are not the same
			return true;
		}
		
		//compare estimate efforts
		if (oldR.getEstimateEffort() != newR.getEstimateEffort()){//if old and new are not the same
			return true;
		}
		
		//compare actual efforts
		if (oldR.getActualEffort() != newR.getActualEffort()){//if old and new are not the same
			return true;
		}
		
		if (!this.getNotesView().getNoteString().equals("") && !this.getNotesView().getNoteString().equals(null) ){//if old and new are not the same
			return true;
		}
		
		if (this.getAv().isButtonPressed()){//if old and new are not the same
			return true;
		}
		
		//TODO: come back to this
		//compare sub-requirements 
		for (int i = 0; i < oldR.getChildRequirementIds().size(); i++){
			if (!newR.getChildRequirementIds().contains(oldR.getChildRequirementIds().get(i))){
				return true;
			}
		}
		for (int i = 0; i < newR.getChildRequirementIds().size(); i++){
			if (!oldR.getChildRequirementIds().contains(newR.getChildRequirementIds().get(i))){
				return true;
			}
		}
	
		if (!oldR.getAssignee().equals(newR.getAssignee())){//if old and new are not the same
			return true;
		}
		
		//compare notes lists
		if (notesDifference != 0){//if old and new are not the same
			return true;
		}
		return false;
	}

	/**
	 * Get the AssigneeView.
	 * 
	 * @return the AssigneeView
	 */
	public AssigneeView getAv() {
		return av;
	}
	
	/**
	 * Get all elements in the model.
	 * 
	 * @param model The model to get elements from
	 * @return A list of elements
	 */
	private List<String> getAllElementsInModel(DefaultListModel model){
		List<String> modelElements = new ArrayList<String>();
		for(int i = 0; i < model.getSize(); i++){
			modelElements.add((String)model.getElementAt(i));
		}
		return modelElements;
	}
}