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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.HistoryView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AcceptanceTestsView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AssigneeView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.ChildrenView;
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
 * @author Chris Hanna <-refactored on april13.
 * @version Mar 17, 2013
 *
 */
@SuppressWarnings({"serial","rawtypes"})
public class RequirementPanel extends JPanel{

	/**
	 * The Enum Mode.
	 */
	public enum Mode {
		CREATE,
		EDIT, 
		CHILD;
	}
	
	/** The parent view **/
	protected RequirementView parent;
	protected RequirementPanel.Mode mode;
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
	protected JButton saveRequirementBottom;
	protected JButton cancelRequirementBottom;
	protected JButton deleteRequirementBottom;
	protected RequirementTabsView RTabsView;
	protected JButton createChildRequirement;
	protected JSplitPane splitPane;

	/** NotesView for updating notes **/
	private NotesView notesView; //= new NotesView();

	/** HistoryView for updating history **/
	private HistoryView hv;

	/** AcceptanceTestsView for viewing and updating Acceptance Tests **/
	private AcceptanceTestsView atv;

	/** AssigneeView for updating assignees **/
	//TODO finish implementing av
	private AssigneeView av;
	
	/** ChildrenView for viewing child requirements **/
	private ChildrenView cv;

	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;

	/**Error labels*/
	JLabel lblTitleError = new JLabel("ERROR: Must have a title", LABEL_ALIGNMENT);
	JLabel lblDescriptionError = new JLabel("ERROR: Must have a description", LABEL_ALIGNMENT);
	JLabel lblEstimateError = new JLabel("ERROR: Estimate is too large", LABEL_ALIGNMENT);
	JLabel lblActualError = new JLabel("ERROR: Actual is too large", LABEL_ALIGNMENT);

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
	public RequirementPanel(RequirementView parent, Mode mode) {
		
		this.parent = parent;

		this.mode = mode;

		//get the list of notes from the given requirement
		this.notesView = new NotesView(parent);

		//get the list of history from the given requirement
		this.hv = new HistoryView(parent);
//		hv = new HistoryView(model);
		
		//Instantiate the acceptance tests
		this.atv = new AcceptanceTestsView(parent);

		//get the list of history from the given requirement
		this.av = new AssigneeView(parent);
		
		//get the list of children from the given requirement
		this.cv = new ChildrenView(parent);
		
		// Indicate that input is enabled
		this.inputEnabled = true;

		//Use a grid bag layout manager
		this.layout = new BorderLayout();
		this.setLayout(layout);

		// Add all components to this panel
		addComponents();
	}

	
	
	
	/**
	 * Adds the components to the panel and places constraints on them
	 * for the GridBagLayout manager.
	 */
	@SuppressWarnings("unchecked")
	protected void addComponents() {
		//create a new constrain variable
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
		txtReleaseNumber = new JTextField(6);
		
		cmbIteration = new JComboBox();

		txtDescription = new JTextArea(10,35);
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);

		String[] requirementStatusValues =new String[RequirementStatus.values().length];
		for (int i = 0; i < requirementStatusValues.length; i++) {
			requirementStatusValues[i] = RequirementStatus.values()[i].toString();
			System.out.println("Status:" + requirementStatusValues[i]);
		}
		cmbStatus = new JComboBox(requirementStatusValues);
		
		System.out.println("Status selected:" + cmbStatus.getSelectedItem());
		
		
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

		RTabsView = new RequirementTabsView(notesView, hv, atv, av, cv);

		/**Save Button*/
		saveRequirementBottom = new JButton("Save");
		//saveRequirementBottom.setAction(new SaveChangesAction(new SaveRequirementController(this.getParent())));
		deleteRequirementBottom = new JButton("Delete");
		//deleteRequirementBottom.setAction(new DeleteRequirementAction(new DeleteRequirementController(this.getParent())));
		cancelRequirementBottom = new JButton("Cancel");
		//cancelRequirementBottom.setAction(new CancelRequirementAction(new CancelRequirementController(this.getParent())));

		createChildRequirement = new JButton("Add Child Requirement");
		
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
		//cmbStatus.addActionListener(new StatusListener());
		
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
		
		setUpToolTips();

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
		 
		 cThree.weightx = 0.5;
		 cThree.weighty = 0.5;
		 cThree.gridx = 2;
		 cThree.gridy = 2;
		 cThree.gridwidth = 2;
		 lblActualError.setVisible(false);
		 lblActualError.setForeground(Color.RED);
		 panelThree.add(lblActualError, cThree);
		 
		 cThree.weightx = 0.5;
		 cThree.weighty = 0.5;
		 cThree.gridx = 0;
		 cThree.gridy = 2;
		 cThree.gridwidth = 2;
		 lblEstimateError.setVisible(false);
		 lblEstimateError.setForeground(Color.RED);
		 panelThree.add(lblEstimateError, cThree);

		 //Panel Four - panel below panel three -------------------------------------------------------------------------------------
		 //Use a grid bag layout manager

		 layoutFour = new GridBagLayout();
		 panelFour.setLayout(layoutFour);

		 cFour.insets = new Insets(10,10,10,0);

		 //Panel Buttons - panel holding all other panels --------------------------------------------------------------------------
		 //Use a grid bag layout manager
		 layoutButtons = new GridBagLayout();
		 panelButtons.setLayout(layoutButtons);

		 cButtons.insets = new Insets(10,10,10,10);
		 cButtons.weightx = 0.5;
		 cButtons.weighty = 0.5;
		 cButtons.gridx = 0;
		 cButtons.gridy = 0;
		 cButtons.gridwidth = 3;
		 panelButtons.add(createChildRequirement, cButtons);
		 
		 
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


		 
//		 if (model.getChildRequirementIds().isEmpty()) {
//			 setDeleteEnabled(false);
//		 }


	}
	
	public void setUpToolTips(){
		this.txtTitle.setToolTipText("Required: A title less than 100 character.");
		this.txtReleaseNumber.setToolTipText("The release number for this requirement.");
		this.txtDescription.setToolTipText("Required: A description for this requirement.");
		this.txtEstimate.setToolTipText("An estimate for the effort of this requirement. \r\n" +
				"This field must be greater than 0 to assign to an iteration.");
		this.txtActual.setToolTipText("The actual effort for this requirement.");
		this.cmbIteration.setToolTipText("The iteration this requirement is assigned to \r\n" + 
				"This field cannot be filled in until a estimate is entered.");
		this.cmbPriority.setToolTipText("The priority of this requirement.");
		this.cmbStatus.setToolTipText("The Status of the requirement.");
		this.cmbType.setToolTipText("Set the type of requirement this requirement is.");		
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
	protected void disableStuff(JComponent[] components){
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
	protected void enableStuff(JComponent[] components){
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
	protected void changeBackground(JTextComponent[] components){
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
	protected void makeTextBlack(JTextComponent[] components){
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
	protected void makeStuffNotVisible(JComponent[] components){
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

	
//	public void setUpPanel(){
//		setUpPanel(Mode.EDIT);
//	}
	public void setUpPanel(){
		
		//updateFields();
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
		requirement.setId(parent.getReqModel().getRequirement().getId());
		requirement.setType(RequirementType.valueFromString((String) cmbType.getSelectedItem()));
		requirement.setTitle(txtTitle.getText().trim());
		requirement.setReleaseNumber(txtReleaseNumber.getText());
		requirement.setIteration((Iteration) cmbIteration.getSelectedItem());
		requirement.setDescription(txtDescription.getText().trim());
		requirement.setStatus(RequirementStatus.valueOf((String) cmbStatus.getSelectedItem()));
		requirement.setPriority(RequirementPriority.valueFromString((String) cmbPriority.getSelectedItem()));
		requirement.setEstimateEffort(getValue(txtEstimate)); // return -1 if the field was left blank
		requirement.setActualEffort(getValue(txtActual)); // return -1 if the field was left blank
		requirement.setCreationDate(parent.getReqModel().getRequirement().getCreationDate());

		requirement.updateNotes(notesView.getNotesList());
		requirement.updateHistory(hv.getHistoryList());
		requirement.updateAcceptanceTests(atv.getList());
		requirement.setAssignee(av.getAssignedUserAL());
		requirement.setSubRequirements(cv.getChildrenRequirementsList());
		requirement.setParentRequirementId(parent.getReqModel().getRequirement().getParentRequirementId());
		requirement.setSubRequirements(parent.getReqModel().getRequirement().getChildRequirementIds());

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
	 * Gets the edits the mode.
	 *
	 * @return the edits the mode
	 */
	public Mode getEditMode() {
		return parent.getMode();
	}



	
	/**
	 * @return the panelButtons
	 */
	public JPanel getPanelButtons() {
		return panelButtons;
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
		@SuppressWarnings("unchecked")
		public void changeStatus(JComboBox cb){
			int i; //For loop counter, also used directly below the for loop.
			Boolean enabled = true;
			Boolean runThatForLoop = false;
			Boolean listHasStatus = false;
			RequirementStatus setTo = RequirementStatus.OPEN;
//			if (model.getStatus() != RequirementStatus.DELETED){
				//Change the status back to whatever it was when the backlog is reselected (They changed their mind).
				if((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.OPEN || parent.getReqModel().getRequirement().getStatus() == RequirementStatus.NEW) && cb.getSelectedItem() == Iteration.getBacklog()){
					setTo = parent.getReqModel().getRequirement().getStatus();
					enabled = false;
					runThatForLoop = true;
				}
				//Change the status to In Progress automatically when the req is assigned to an iteration.
				else if((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.OPEN || parent.getReqModel().getRequirement().getStatus() == RequirementStatus.NEW) && cb.getSelectedItem() != Iteration.getBacklog()){
					setTo = RequirementStatus.INPROGRESS;
					enabled = false;
					runThatForLoop = true;
				}
				//Change the status to Open automatically when the backlog is selected.
				else if((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.INPROGRESS) && cb.getSelectedItem() == Iteration.getBacklog()){
					setTo = RequirementStatus.OPEN;
					enabled = false;
					runThatForLoop = true;
				}
				//Set the status back to In Progress when they reassigned it to an iteration (but let them change the status).
				else if((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.INPROGRESS) && cb.getSelectedItem() != Iteration.getBacklog()){
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
			if (pLevel == RMPermissionsLevel.ADMIN && parent.getMode() != Mode.CHILD){
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
				if(txtEstimate.getText().equals("") || txtEstimate.getText() == null){
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
			if(parent.getMode() != Mode.CHILD){
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
	 * Get the AssigneeView.
	 * 
	 * @return the AssigneeView
	 */
	public AssigneeView getAv() {
		return av;
	}
	
	public ChildrenView getCv(){
		return cv;
	}
	
	/**
	 * Get all elements in the model.
	 * 
	 * @param model The model to get elements from
	 * @return A list of elements
	 */
	@SuppressWarnings("unused")
	private List<String> getAllElementsInModel(DefaultListModel model){
		List<String> modelElements = new ArrayList<String>();
		for(int i = 0; i < model.getSize(); i++){
			modelElements.add((String)model.getElementAt(i));
		}
		return modelElements;
	}

	/**
	 * @return the saveRequirementBottom
	 */
	public JButton getSaveRequirementBottom() {
		return saveRequirementBottom;
	}

	/**
	 * @return the cancelRequirementBottom
	 */
	public JButton getCancelRequirementBottom() {
		return cancelRequirementBottom;
	}

	/**
	 * @return the deleteRequirementBottom
	 */
	public JButton getDeleteRequirementBottom() {
		return deleteRequirementBottom;
	}

	/**
	 * @return the createChildRequirement
	 */
	public JButton getCreateChildRequirement() {
		return createChildRequirement;
	}

	
	/**
	 * @return the cmbIteration
	 */
	public JComboBox getCmbIteration() {
		return cmbIteration;
	}

	/**
	 * @return the cmbType
	 */
	public JComboBox getCmbType() {
		return cmbType;
	}

	/**
	 * @return the cmbStatus
	 */
	public JComboBox getCmbStatus() {
		return cmbStatus;
	}

	/**
	 * @return the cmbPriority
	 */
	public JComboBox getCmbPriority() {
		return cmbPriority;
	}

	/**
	 * @return the txtTitle
	 */
	public JPlaceholderTextField getTxtTitle() {
		return txtTitle;
	}

	/**
	 * @return the txtEstimate
	 */
	public IntegerField getTxtEstimate() {
		return txtEstimate;
	}

	/**
	 * @return the txtModifiedDate
	 */
	public JLabel getTxtModifiedDate() {
		return txtModifiedDate;
	}

	/**
	 * @return the lblTitleError
	 */
	public JLabel getLblTitleError() {
		return lblTitleError;
	}

	/**
	 * @return the lblDescriptionError
	 */
	public JLabel getLblDescriptionError() {
		return lblDescriptionError;
	}

	/**
	 * @return the txtDescription
	 */
	public JTextArea getTxtDescription() {
		return txtDescription;
	}

	/**
	 * @return the txtReleaseNumber
	 */
	public JTextField getTxtReleaseNumber() {
		return txtReleaseNumber;
	}

	/**
	 * @return the txtCreatedDate
	 */
	public JLabel getTxtCreatedDate() {
		return txtCreatedDate;
	}

	/**
	 * @return the txtCreator
	 */
	public JTextField getTxtCreator() {
		return txtCreator;
	}

	/**
	 * @return the knownIterations
	 */
	public Iteration[] getKnownIterations() {
		return knownIterations;
	}
	

	/**
	 * @return the hv
	 */
	public HistoryView getHv() {
		return hv;
	}
	
	/**
	 * @return the atv
	 */
	public AcceptanceTestsView getAtv() {
		return atv;
	}

	/**
	 * @param atv: the atv to set
	 */
	public void setAtv(AcceptanceTestsView atv) {
		this.atv = atv;
	}

	/**
	 * @return the txtActual
	 */
	public IntegerField getTxtActual() {
		return txtActual;
	}
	
	/**
	 * @return the lblEstimateError
	 */
	public JLabel getLblEstimateError() {
		return lblEstimateError;
	}

	/**
	 * @return the lblActualError
	 */
	public JLabel getLblActualError() {
		return lblActualError;
	}




	@SuppressWarnings("unchecked")
	public void setIterations(Iteration[] iterations){
		this.knownIterations = iterations;
		this.cmbIteration.removeAllItems();
		for (int i = 0 ; i < iterations.length; i ++)
			this.cmbIteration.addItem(iterations[i]);
		
	}
}
