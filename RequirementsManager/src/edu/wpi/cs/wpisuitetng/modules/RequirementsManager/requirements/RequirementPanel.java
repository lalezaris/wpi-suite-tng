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
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatusLists;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.CancelRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.DeleteRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CancelRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.DeleteRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.NotesView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.RequirementTabsView;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
/**
 * Panel to display and edit the basic fields for a requirement
 * Adapted from DefectPanel in project DefectTracker
 *
 * @author Chris Dunkers 
 * @author Joe Spicola
 * @author Ned Shelton
 * @author Michael Perrone
 * @author Evan Polekoff
 * @version Mar 17, 2013
 *
 */
public class RequirementPanel extends JPanel{
	

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
	protected JPlaceholderTextField txtTitle; //JPlaceholderTextField
	protected JTextField txtReleaseNumber;
	protected JComboBox cmbIteration;	
	protected Iteration[] knownIterations;
	protected JComboBox cmbStatus;
	protected JComboBox cmbPriority;
	protected JTextArea txtDescription;	
	protected IntegerField txtEstimate;
	protected IntegerField txtActual;
	protected JLabel txtCreatedDate;
	protected JLabel txtModifiedDate;
	protected JTextField txtCreator;
	protected JTextField txtAssignee;
	protected JButton saveRequirementBottom;
	protected JButton cancelRequirementBottom;
	protected JButton deleteRequirementBottom;
	protected RequirementTabsView RTabsView;
	protected JSplitPane splitPane;
	
	/** The ArrayList of Notes**/
	protected ArrayList<Note> notes = new ArrayList<Note>();
	
	
	/** NotesView for updating notes **/
	private NotesView n; //= new NotesView();
	
	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;
	
	/**Error labels*/
	JLabel lblTitleError = new JLabel("ERROR: Must have a title", LABEL_ALIGNMENT);
	JLabel lblDescriptionError = new JLabel("ERROR: Must have a description", LABEL_ALIGNMENT);
	
	/** The layout manager for this panel */
//	protected GridBagLayout layout;
	protected BorderLayout layout;
	
	/** The other panels */
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
	 */
	public RequirementPanel(RequirementView parent, Requirement requirement, Mode mode) {
		this.model = requirement;
		this.parent = parent;
		editMode = mode;
		
		//get the list of notes from the given requirement
		n = new NotesView(model);
		// a = new AttachmentsView(model);
		//h = new HistoryView(model);
		//u = new UsersView(model);
		
		// Indicate that input is enabled
		inputEnabled = true;
		
		//Use a grid bag layout manager
		//layout = new GridBagLayout();
		layout = new BorderLayout();
		this.setLayout(layout);
		
		// Add all components to this panel
		addComponents();
		updateFields();
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
		GridBagConstraints cFour = new GridBagConstraints();
		GridBagConstraints cButtons = new GridBagConstraints();

		// Construct all of the components for the form
		panelOverall = new JPanel();
		panelOne = new JPanel();
		panelTwo = new JPanel();
		panelThree = new JPanel();
		panelFour = new JPanel();
		panelButtons = new JPanel();
		panelTabs = new JPanel();
		txtTitle = new JPlaceholderTextField("Enter Title Here", 20);
		txtReleaseNumber = new JTextField(12);
		knownIterations = Refresher.getInstance().getInstantIterations();
		cmbIteration = new JComboBox(knownIterations);
		txtDescription = new JTextArea(10,35);
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		//txtDescription.setBorder(txtTitle.getBorder());
		String[] requirementStatusValues = RequirementStatusLists.getList(model.getStatus());
		for (int i = 0; i < requirementStatusValues.length; i++) {
			requirementStatusValues[i] = RequirementStatusLists.getList(model.getStatus())[i];
		}
		cmbStatus = new JComboBox(requirementStatusValues);
		String[] requirementPriorityValues = new String[RequirementPriority.values().length];
		for (int i = 0; i < RequirementPriority.values().length; i++) {
			requirementPriorityValues[i] = RequirementPriority.values()[i].toString();
		}
		cmbPriority = new JComboBox(requirementPriorityValues);
		txtEstimate = new IntegerField(4);
		txtActual = new IntegerField(4);
		txtCreatedDate = new JLabel();
		txtModifiedDate = new JLabel("");
		txtCreator = new JTextField(15);
		txtAssignee = new JTextField(15);
		n.setNotesList(this.getNotesArrayList());
		RTabsView = new RequirementTabsView(n);
		
		/**Save Button*/
		saveRequirementBottom = new JButton("Save");
		saveRequirementBottom.setAction(new SaveChangesAction(new SaveRequirementController(this.getParent())));
		deleteRequirementBottom = new JButton("Delete");
		deleteRequirementBottom.setAction(new DeleteRequirementAction(new DeleteRequirementController(this.getParent())));
		cancelRequirementBottom = new JButton("Cancel");
		cancelRequirementBottom.setAction(new CancelRequirementAction(new CancelRequirementController(this.getParent())));
		
		
		/**Iteration Listener*/
		
		cmbIteration.addActionListener(new IterationListener());
		
		/**Estimate Listener*/
		
		txtEstimate.addKeyListener(new EstimateListener());
		
		/**Title and Description Listener*/
		
		txtTitle.addKeyListener(new SaveListener());
		txtDescription.addKeyListener(new SaveListener());
		
		// set maximum widths of components so they are not stretched
		txtTitle.setMaximumSize(txtTitle.getPreferredSize());
		cmbStatus.setMaximumSize(cmbStatus.getPreferredSize());
		cmbStatus.setMaximumSize(cmbPriority.getPreferredSize());
		
		// Construct labels for the form fields
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
		//txtTitle.setFont(txtTitle.getFont().deriveFont(18f));
		panelOne.add(txtTitle, cOne);
		
		cOne.insets = new Insets(5,0,0,0);
		cOne.gridx = 2;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0;
		cOne.gridwidth = 1;
		lblTitleError.setVisible(false);
		lblTitleError.setForeground(Color.RED);
		panelOne.add(lblTitleError, cOne);
		
		cOne.insets = new Insets(5,10,10,0); //top,left,bottom,right
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
		
		cOne.gridx = 0;
		cOne.gridy = 2;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		panelOne.add(lblIteration, cOne);
		
		cOne.gridx = 1;
		cOne.gridy = 2;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		//Default the Iteration Box based on the valus of the estimate (Don't let you choose it if the estimate is blank).
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
		//Allow the iteration of a completed requirement to be changed.
		/*else if(model.getStatus() == RequirementStatus.COMPLETE){
			cmbIteration.setEnabled(true);
			cmbStatus.setSelectedIndex(2);
		}*/
		
		panelOne.add(cmbIteration, cOne);
		
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
		
		//Panel Three - panel below panel two -------------------------------------------------------------------------------------
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
			
		//cThree.fill = GridBagConstraints.HORIZONTAL;
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
		
		cFour.weightx = 0.5;
		cFour.weighty = 0.5;
		cFour.gridx = 0;
		cFour.gridy = 0;
		cFour.anchor = GridBagConstraints.LINE_START;
		panelFour.add(lblCreatedDate, cFour);
				
		cFour.weightx = 0.5;
		cFour.weighty = 0.5;
		cFour.gridx = 1;
		cFour.gridy = 0;
		txtCreatedDate.setEnabled(false);
		txtCreatedDate.setText(model.getCreationDate().toString());
		cFour.anchor = GridBagConstraints.LINE_START;
		panelFour.add(txtCreatedDate, cFour);
				
		cFour.weightx = 0.5;
		cFour.weighty = 0.5;
		cFour.gridx = 0;
		cFour.gridy = 1;
		panelFour.add(lblModifiedDate, cFour);
				
		cFour.weightx = 0.5;
		cFour.weighty = 0.5;
		cFour.gridx = 1;
		cFour.gridy = 1;
		txtModifiedDate.setEnabled(false);
		cFour.anchor = GridBagConstraints.LINE_START;
		panelFour.add(txtModifiedDate, cFour);
				
		cFour.weightx = 0.5;
		cFour.weighty = 0.5;
		cFour.gridx = 0;
		cFour.gridy = 2;
		cFour.anchor = GridBagConstraints.LINE_START;
		panelFour.add(lblCreator, cFour);
				
		cFour.weightx = 0.5;
		cFour.weighty = 0.5;
		cFour.gridx = 1;
		cFour.gridy = 2;
		txtCreator.setEnabled(false);
		txtCreator.setText(model.getCreator().getUsername());
		cFour.anchor = GridBagConstraints.LINE_START;
		panelFour.add(txtCreator, cFour);
				
		cFour.weightx = 0.5;
		cFour.weighty = 0.5;
		cFour.gridx = 0;
		cFour.gridy = 3;
		cFour.anchor = GridBagConstraints.LINE_START;
		panelFour.add(lblAssignee, cFour);
				
		cFour.weightx = 0.5;
		cFour.weighty = 0.5;
		cFour.gridx = 1;
		cFour.gridy = 3;
		cFour.anchor = GridBagConstraints.LINE_START;
		panelFour.add(txtAssignee, cFour);


		//Panel Buttons - panel holding all other panels --------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutButtons = new GridBagLayout();
		panelButtons.setLayout(layoutButtons);
				
		cButtons.weightx = 0.5;
		cButtons.weighty = 0.5;
		cButtons.gridx = 0;
		cButtons.gridy = 0;
		panelButtons.add(saveRequirementBottom, cButtons);
		
		cButtons.weightx = 0.5;
		cButtons.weighty = 0.5;
		cButtons.gridx = 2;
		cButtons.gridy = 0;
		deleteRequirementBottom.setVisible(false);
		panelButtons.add(deleteRequirementBottom, cButtons);
		
		cButtons.weightx = 0.5;
		cButtons.weighty = 0.5;
		cButtons.gridx = 1;
		cButtons.gridy = 0;
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
		
//		cOverall.weightx = 0.5;
//		cOverall.weighty = 0.5;
//		cOverall.gridx = 1;
//		cOverall.gridy = 0;
//		cOverall.anchor = GridBagConstraints.LINE_START;
//		cOverall.fill = GridBagConstraints.BOTH;
//		cOverall.gridheight = 4;
//		cOverall.gridwidth = 4;
//		panelOverall.add(panelTabs, cOverall);
		
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
//		splitPane.setResizeWeight(0.5);
		splitPane.setDividerLocation(0.5);
		this.add(splitPane, BorderLayout.CENTER);
		
		//depending on the mode, disable certain components
		if (editMode == Mode.CREATE) {
			cmbStatus.setEnabled(false);
			txtActual.setEnabled(false);
		}

		// depending on the status and sub-requirements, disable certain components
		if (model.getStatus() == RequirementStatus.INPROGRESS
				|| model.getStatus() == RequirementStatus.COMPLETE
				|| model.getSubRequirements().size() != 0) {
			txtEstimate.setEnabled(false);
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
	public void updateModel(Requirement requirement) {
		updateModel(requirement, Mode.EDIT);
	}
	
	/**
	 * Updates the RequirementPanel's model to contain the values of the given Requirement.
	 * 
	 * @param	requirement	The requirement which contains the new values for the model.
	 * @param	mode	The new editMode.
	 */
	protected void updateModel(Requirement requirement, Mode mode) {
		editMode = mode;
		
		model.setId(requirement.getId());
		model.setTitle(requirement.getTitle());
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
		
		updateFields();
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
		requirement.setTitle(txtTitle.getText().trim());
		requirement.setReleaseNumber(txtReleaseNumber.getText());

		requirement.setIteration((Iteration) cmbIteration.getSelectedItem());
		
		requirement.setDescription(txtDescription.getText().trim());
		requirement.setStatus(RequirementStatus.valueOf((String) cmbStatus.getSelectedItem()));
		requirement.setPriority(RequirementPriority.valueOf((String) cmbPriority.getSelectedItem()));
		requirement.setEstimateEffort(getValue(txtEstimate)); // return -1 if the field was left blank
		requirement.setActualEffort(getValue(txtActual)); // return -1 if the field was left blank
		requirement.setCreationDate(model.getCreationDate());
		requirement.updateNotes(n.getNotesList());
		
		if (!(txtAssignee.getText().equals(""))) {
			requirement.setAssignee(new User("", txtAssignee.getText(), "", -1));
		}
		if (!(txtCreator.getText().equals(""))) {
			requirement.setCreator(new User("", txtCreator.getText(), "", -1));
		}
		
		System.out.println("the result of getEditedModel:");
		System.out.println(requirement.toJSON());
		return requirement;
	}
	
	/**
	 * Checks to make sure the title and description are filled in 
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
	
	private void updateFields() {
		if(!(model.getTitle().equals(null) || model.getTitle().equals("")))
			txtTitle.setText(model.getTitle());
		txtDescription.setText(model.getDescription());
		txtReleaseNumber.setText(model.getReleaseNumber());
		txtEstimate.setText( String.valueOf(model.getEstimateEffort()) );
		txtActual.setText( String.valueOf(model.getActualEffort()) );

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
			if (model.getPriority() == RequirementPriority.valueOf((String) cmbPriority.getItemAt(i))) {
				cmbPriority.setSelectedIndex(i);
			}
		}
		
		if (editMode == Mode.EDIT) {
			txtCreatedDate.setText(model.getCreationDate().toString());
			txtModifiedDate.setText(model.getLastModifiedDate().toString());
			deleteRequirementBottom.setVisible(true);
		}
		if (model.getCreator() != null) {
			txtCreator.setText(model.getCreator().getUsername());
		}
		if (model.getAssignee() != null) {
			txtAssignee.setText(model.getAssignee().getUsername());
		}
		n.setNotesList(model.getNotes());
	}

	public Mode getEditMode() {
		return editMode;
	}
	
	//TODO: Getter and Setter for Notes
	public ArrayList<Note> getNotesArrayList() {
		return notes;
	}
	
	public void setNotesArrayList(ArrayList<Note> aln) {
		notes = aln;
	}
	
	/**
	 * Gets the model
	 * 
	 * @return the model
	 */
	public Requirement getModel() {
		return model;
	}
	
	
	//TODO: class exists in action package, refactor
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

		public void changeStatus(JComboBox cb){
			int i; //For loop counter, also used directly below the for loop.
			Boolean enabled = true;
			Boolean runThatForLoop = false;
			Boolean listHasStatus = false;
			RequirementStatus setTo = RequirementStatus.OPEN;
			
			//Change the status back to whatever it was when the backlog is reselected (They changed their mind).
			if((model.getStatus() == RequirementStatus.OPEN || model.getStatus() == RequirementStatus.NEW) && cb.getSelectedItem() == knownIterations[0]){
				setTo = model.getStatus();
				enabled = false;
				runThatForLoop = true;
			}
			//Change the status to In Progress automatically when the req is assigned to an iteration.
			else if((model.getStatus() == RequirementStatus.OPEN || model.getStatus() == RequirementStatus.NEW) && cb.getSelectedItem() != knownIterations[0]){
				setTo = RequirementStatus.INPROGRESS;
				enabled = false;
				runThatForLoop = true;
			}
			//Change the status to Open automatically when the backlog is selected.
			else if((model.getStatus() == RequirementStatus.INPROGRESS) && cb.getSelectedItem() == knownIterations[0]){
				setTo = RequirementStatus.OPEN;
				enabled = false;
				runThatForLoop = true;
			}
			//Set the status back to In Progress when they reassigned it to an iteration (but let them change the status).
			else if((model.getStatus() == RequirementStatus.INPROGRESS) && cb.getSelectedItem() != knownIterations[0]){
				setTo = RequirementStatus.INPROGRESS;
				enabled = true;
				runThatForLoop = true;
			}
			//If you are changing the iteration of a complete requirement, set it to INPROGRESS
			/*else if((model.getStatus() == RequirementStatus.COMPLETE) && cb.getSelectedItem() != knownIterations[0] && cb.getSelectedItem() != model.getIteration()){
				setTo = RequirementStatus.INPROGRESS;
				enabled = false;
				runThatForLoop = true;
			}
			//If you are changing the iteration of a complete requirement to the backlog, set it to OPEN
			else if((model.getStatus() == RequirementStatus.COMPLETE) && cb.getSelectedItem() == knownIterations[0]){
				setTo = RequirementStatus.OPEN;
				enabled = false;
				runThatForLoop = true;
			}*/
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
			cmbStatus.setEnabled(enabled);
			cmbStatus.setBackground(Color.WHITE);
		}
		
	}
	
	//A Key Listener on the Estimate to grey out the Iteration drop down until there is a value in the estimate.
	public class EstimateListener implements KeyListener {

		public void actionPerformed(ActionEvent estimate) {}
		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {}

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
			cmbIteration.setEnabled(enabled);
			cmbIteration.setBackground(Color.WHITE);
			
		}
		
	}
	
	
	//A Key Listener on the Title and Description to grey out the save button until there is text in them.
	public class SaveListener implements KeyListener {

		public void actionPerformed(ActionEvent estimate) {}
		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {}

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

	
}
	
	

