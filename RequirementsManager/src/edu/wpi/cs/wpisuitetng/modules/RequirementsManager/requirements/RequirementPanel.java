/**************************************************
 * This file was developed for CS3733: Software Engineering The course was taken
 * at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Chris Dunkers Joe Spicola Evan Polekoff Ned Shelton Sam
 * Lalezari Tushar Narayan Chris Hanna Lauren Kahn
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AcceptanceTestsView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AssigneeView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.HistoryView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.NotesView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.ParentAndChildrenView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.RequirementTabsView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;

/**
 * Panel to display and edit the basic fields for a requirement. Adapted from
 * DefectPanel in project DefectTracker.
 * 
 * @author Chris Dunkers
 * @author Joe Spicola
 * @author Ned Shelton
 * @author Michael Perrone
 * @author Evan Polekoff
 * @author Chris Hanna (refactored on 04/13)
 * 
 * @version Mar 17, 2013
 * 
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class RequirementPanel extends JPanel implements FocusListener {

	/**
	 * The Enum Mode.
	 */
	public enum Mode {
		CREATE, EDIT, CHILD;
	}

	/** The parent view **/
	protected RequirementView parent;
	protected RequirementPanel.Mode mode;

	/*
	 * Form elements
	 */
	protected JPlaceholderTextField txtTitle; // JPlaceholderTextField
	protected JTextField txtReleaseNumber;
	protected JComboBox cmbIteration;
	protected Iteration[] knownIterations;
	protected JComboBox cmbType;
	protected JComboBox cmbStatus;
	protected JComboBox cmbPriority;
	protected JTextArea txtDescription;
	protected IntegerField txtEstimate;
	protected IntegerField txtTotalEstimate;
	protected IntegerField txtActual;
	protected JLabel txtCreatedDate;
	protected JLabel txtModifiedDate;
	protected JTextField txtCreator;
	protected JButton saveRequirementButton;
	protected JButton cancelRequirementButton;
	protected JButton deleteRequirementButton;
	protected RequirementTabsView RTabsView;
	protected JButton createChildRequirementButton;
	protected JSplitPane splitPane;
	protected JSplitPane splitPaneLeft;

	private JLabel lblType;
	private JLabel lblReleaseNumber;
	private JLabel lblIteration;
	private JLabel lblDescription;
	private JLabel lblStatus;
	private JLabel lblPriority;
	private JLabel lblEstimate;
	private JLabel lblTotalEstimate;
	private JLabel lblActual;

	/** NotesView for updating notes **/
	private NotesView notesView;

	/** HistoryView for updating history **/
	private HistoryView historyView;

	/** AcceptanceTestsView for viewing and updating Acceptance Tests **/
	private AcceptanceTestsView acceptanceTestsView;

	/** TasksView for updating tasks **/
	private TasksView tasksView;

	// TODO finish implementing assigneeView
	private AssigneeView assigneeView;

	/** Parent and Requirement View for viewing parent and child requirements **/
	private ParentAndChildrenView parentChildrenView;

	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;

	/** Error labels */
	JLabel lblTitleError = new JLabel("ERROR: Must have a title",
			LABEL_ALIGNMENT);
	JLabel lblDescriptionError = new JLabel("ERROR: Must have a description",
			LABEL_ALIGNMENT);
	JLabel lblEstimateError = new JLabel("ERROR: Estimate is invalid",
			LABEL_ALIGNMENT);
	JLabel lblActualError = new JLabel("ERROR: Actual is invalid",
			LABEL_ALIGNMENT);

	/** The layout manager for this panel */
	protected BorderLayout layout;

	/** The other panels */
	protected JPanel panelOverall;
	protected JPanel panelOne;
	protected JPanel panelTwo;
	protected JPanel panelThree;
	protected JPanel panelFour;
	protected JPanel panelButtons;
	protected JPanel panelTabs;

	/**
	 * The layout managers for other panels
	 */
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
	 * Constructs a RequirementPanel for creating or editing a given
	 * Requirement.
	 * 
	 * @param parent
	 *            The parent of the requirement
	 * @param mode
	 *            the mode
	 */
	public RequirementPanel(RequirementView parent, Mode mode) {

		this.parent = parent;

		this.mode = mode;

		// get the list of notes from the given requirement
		this.notesView = new NotesView(parent);

		// get the list of history from the given requirement
		this.historyView = new HistoryView(parent);

		// Instantiate the acceptance tests
		this.acceptanceTestsView = new AcceptanceTestsView(parent);

		// get the list of history from the given requirement
		this.assigneeView = new AssigneeView(parent);

		// get the list of tasks from the given requirement
		this.tasksView = new TasksView(parent);

		// get the parent and the list of children from the given requirement
		this.parentChildrenView = new ParentAndChildrenView(parent);

		// Indicate that input is enabled
		this.inputEnabled = true;

		// Use a grid bag layout manager
		this.layout = new BorderLayout();
		this.setLayout(layout);

		// Set it so the focus will not go to the tile field
		this.setFocusCycleRoot(true);
		this.setFocusTraversalPolicy(new LayoutFocusTraversalPolicy() {
			public Component getDefaultComponent(Container cont) {
				return lblIteration;
			}
		});

		// Add all components to this panel
		addComponents();
	}

	/**
	 * Adds the components to the panel and places constraints on them for the
	 * GridBagLayout manager.
	 */
	@SuppressWarnings({ "unchecked" })
	protected void addComponents() {
		// create a new constrain variable
		GridBagConstraints cOverall = new GridBagConstraints();
		GridBagConstraints cOne = new GridBagConstraints();
		GridBagConstraints cTwo = new GridBagConstraints();
		GridBagConstraints cThree = new GridBagConstraints();
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
		txtTitle.addFocusListener(this);

		txtReleaseNumber = new JTextField(2);
		txtReleaseNumber.addFocusListener(this);

		cmbIteration = new JComboBox();
		cmbIteration.addFocusListener(this);

		txtDescription = new JTextArea(10, 35);
		txtDescription.addFocusListener(this);

		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);

		String[] requirementStatusValues = new String[RequirementStatus
				.values().length];
		for (int i = 0; i < requirementStatusValues.length; i++) {
			requirementStatusValues[i] = RequirementStatus.values()[i]
					.toString();
		}
		cmbStatus = new JComboBox(requirementStatusValues);
		cmbStatus.addFocusListener(this);

		String[] requirementPriorityValues = new String[RequirementPriority
				.values().length];
		for (int i = 0; i < RequirementPriority.values().length; i++) {
			requirementPriorityValues[i] = RequirementPriority.values()[i]
					.toString();
		}
		cmbPriority = new JComboBox(requirementPriorityValues);
		cmbPriority.addFocusListener(this);

		String[] requirementTypeValues = new String[RequirementType.values().length];
		for (int i = 0; i < RequirementType.values().length; i++) {
			requirementTypeValues[i] = RequirementType.values()[i].toString();
		}
		cmbType = new JComboBox(requirementTypeValues);

		txtEstimate = new IntegerField(4);
		txtEstimate.addFocusListener(this);

		txtTotalEstimate = new IntegerField(4);
		txtTotalEstimate.setEnabled(false);
		txtTotalEstimate.setEnabled(false);

		txtActual = new IntegerField(4);
		txtActual.addFocusListener(this);

		txtCreatedDate = new JLabel();
		txtModifiedDate = new JLabel("");
		txtCreator = new JTextField(12);

		RTabsView = new RequirementTabsView(notesView, historyView,
				acceptanceTestsView, assigneeView, parentChildrenView,
				tasksView);

		/** Save Button */
		saveRequirementButton = new JButton("Save");
		/** Delete Button */
		deleteRequirementButton = new JButton("Delete");
		/** Cancel Button */
		cancelRequirementButton = new JButton("Cancel");

		createChildRequirementButton = new JButton("Add Child Requirement");

		// make sit so that all combo boxes will have black text when disabled
		// for easier readability
		UIManager.put("ComboBox.disabledForeground", Color.BLACK);

		/** Iteration Listener */
		cmbIteration.addActionListener(new IterationListener());

		/** Estimate Listener */
		txtEstimate.addKeyListener(new EstimateListener());

		/** Title and Description Listener */
		txtTitle.addKeyListener(new SaveListener());
		txtDescription.addKeyListener(new SaveListener());

		/** Status Listener */
		// cmbStatus.addActionListener(new StatusListener());

		// set maximum widths of components so they are not stretched
		txtTitle.setMaximumSize(txtTitle.getPreferredSize());
		cmbStatus.setMaximumSize(cmbStatus.getPreferredSize());
		cmbStatus.setMaximumSize(cmbPriority.getPreferredSize());

		// Construct labels for the form fields
		lblType = new JLabel("Type:", LABEL_ALIGNMENT);
		lblReleaseNumber = new JLabel("Release Number:", LABEL_ALIGNMENT);
		lblIteration = new JLabel("Iteration:", LABEL_ALIGNMENT);
		lblDescription = new JLabel("Description: *", LABEL_ALIGNMENT);
		lblStatus = new JLabel("Status:", LABEL_ALIGNMENT);
		lblPriority = new JLabel("Priority:", LABEL_ALIGNMENT);
		lblEstimate = new JLabel("Estimate:", LABEL_ALIGNMENT);
		lblTotalEstimate = new JLabel("Total Estimate:", LABEL_ALIGNMENT);
		lblActual = new JLabel("Actual:", LABEL_ALIGNMENT);

		setUpToolTips();

		// Panel One - panel at the top
		// --------------------------------------------------------------------------------------------------------------
		// Use a grid bag layout manager
		layoutOne = new GridBagLayout();
		panelOne.setLayout(layoutOne);

		layoutOne.columnWeights = new double[] { .2, .8 };
		cOne.anchor = GridBagConstraints.LINE_START;
		cOne.gridx = 0;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 2;
		cOne.insets = new Insets(10, 10, 5, 0); // top,left,bottom,right
		panelOne.add(txtTitle, cOne);

		cOne.insets = new Insets(5, 0, 0, 0);
		cOne.gridx = 2;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0;
		cOne.gridwidth = 3;
		lblTitleError.setVisible(false);
		lblTitleError.setForeground(Color.RED);
		panelOne.add(lblTitleError, cOne);

		cOne.insets = new Insets(5, 10, 10, 0); // top,left,bottom,right
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

		// Panel Two - panel below panel one
		// ------------------------------------------------------------------------------------------------------------
		// Use a grid bag layout manager
		layoutTwo = new GridBagLayout();
		panelTwo.setLayout(layoutTwo);

		cTwo.insets = new Insets(10, 10, 5, 0);
		cTwo.anchor = GridBagConstraints.FIRST_LINE_START;
		cTwo.gridx = 0;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		panelTwo.add(lblDescription, cTwo);

		cTwo.insets = new Insets(10, 10, 5, 0);
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
		cTwo.insets = new Insets(0, 10, 10, 0);
		cTwo.gridx = 0;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 2;
		panelTwo.add(scrollPaneDescription, cTwo);

		// Panel Three - panel below panel one
		// -------------------------------------------------------------------------------------
		// Use a grid bag layout manager
		layoutThree = new GridBagLayout();
		panelThree.setLayout(layoutThree);

		cThree.insets = new Insets(10, 10, 10, 0);
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

		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 0;
		cThree.gridy = 3;
		cThree.anchor = GridBagConstraints.LINE_START;
		panelThree.add(lblTotalEstimate, cThree);
		lblTotalEstimate.setVisible(parent.getReqModel().getRequirement()
				.getChildRequirementIds().size() > 0);

		// cThree.fill = GridBagConstraints.HORIZONTAL;
		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 1;
		cThree.anchor = GridBagConstraints.LINE_START;
		panelThree.add(txtEstimate, cThree);

		cThree.weightx = 0.5;
		cThree.weighty = 0.5;
		cThree.gridx = 1;
		cThree.gridy = 3;
		cThree.anchor = GridBagConstraints.LINE_START;
		panelThree.add(txtTotalEstimate, cThree);
		txtTotalEstimate.setVisible((parent.getReqModel().getRequirement()
				.getChildRequirementIds().size() > 0));

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

		// Panel Buttons - panel holding all other panels
		// --------------------------------------------------------------------------
		// Use a grid bag layout manager
		layoutButtons = new GridBagLayout();
		panelButtons.setLayout(layoutButtons);

		cButtons.weightx = 0.5;
		cButtons.weighty = 0.5;
		cButtons.gridx = 0;
		cButtons.gridy = 6;
		cButtons.gridwidth = 1;
		panelButtons.add(saveRequirementButton, cButtons);

		cButtons.weightx = 0.5;
		cButtons.weighty = 0.5;
		cButtons.gridx = 2;
		cButtons.gridy = 6;
		deleteRequirementButton.setVisible(false);
		panelButtons.add(deleteRequirementButton, cButtons);

		cButtons.weightx = 0.5;
		cButtons.weighty = 0.5;
		cButtons.gridx = 1;
		cButtons.gridy = 6;
		panelButtons.add(cancelRequirementButton, cButtons);

		// Panel Tabs - panel holding all other panels
		// --------------------------------------------------------------------------
		// Use a grid bag layout manager
		layoutTabs = new GridBagLayout();
		panelTabs.setLayout(layoutTabs);

		cOverall.fill = GridBagConstraints.BOTH;
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.anchor = GridBagConstraints.LINE_START;
		panelTabs.add(RTabsView, cOverall);

		// Panel Overall - panel holding all other panels
		// --------------------------------------------------------------------------
		// Use a grid bag layout manager
		layoutOverall = new GridBagLayout();
		panelOverall.setLayout(layoutOverall);
		// Overall Panel
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

		cOverall.fill = GridBagConstraints.NONE;
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 3;
		cOverall.gridwidth = 4;
		cOverall.anchor = GridBagConstraints.CENTER;
		panelOverall.add(createChildRequirementButton, cOverall);

		// add to this Panel
		// -----------------------------------------------------------------------------------------------------------------
		JPanel leftPaneltop = new JPanel();
		leftPaneltop.setLayout(new GridBagLayout());
		GridBagConstraints cPaneTop = new GridBagConstraints();

		cPaneTop.anchor = GridBagConstraints.FIRST_LINE_START;
		cPaneTop.weightx = 0.1;
		cPaneTop.weighty = 0.1;
		cPaneTop.gridx = 0;
		cPaneTop.gridy = 0;
		leftPaneltop.add(panelOverall, cPaneTop);

		JScrollPane scrollPaneLeft = new JScrollPane(leftPaneltop);
		// JScrollPane scrollPaneTabs = new JScrollPane(panelTabs);
		splitPaneLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				scrollPaneLeft, panelButtons);
		splitPaneLeft.setEnabled(true);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPaneLeft,
				panelTabs);
		this.add(splitPane, BorderLayout.CENTER);
	}

	/**
	 * @return the txtTotalEstimate
	 */
	public IntegerField getTxtTotalEstimate() {
		return txtTotalEstimate;
	}

	/**
	 * @param txtTotalEstimate
	 *            the txtTotalEstimate to set
	 */
	public void setTxtTotalEstimate(int totalEstimateEffort) {
		this.txtTotalEstimate.setText(totalEstimateEffort + "");

	}

	/**
	 * Sets the up tool tips.
	 */
	public void setUpToolTips() {
		txtTitle.setToolTipText("Required: A title less than 100 characters.");
		lblReleaseNumber
				.setToolTipText("The release number for this requirement.");
		txtReleaseNumber
				.setToolTipText("The release number for this requirement.");
		lblDescription
				.setToolTipText("Required: A description for this requirement.");
		txtDescription
				.setToolTipText("Required: A description for this requirement.");
		lblEstimate
				.setToolTipText("An estimate for the effort of this requirement. \r\n"
						+ "This field must be greater than 0 to assign to an iteration.");
		txtEstimate
				.setToolTipText("An estimate for the effort of this requirement. \r\n"
						+ "This field must be greater than 0 to assign to an iteration.");
		lblTotalEstimate
				.setToolTipText("A total estimate for the total effort of this requirement and all its children. \r\n");
		txtTotalEstimate
				.setToolTipText("A total estimate for the total effort of this requirement and all its children. \r\n");
		lblActual.setToolTipText("The actual effort for this requirement.");
		txtActual.setToolTipText("The actual effort for this requirement.");
		lblIteration
				.setToolTipText("The iteration this requirement is assigned to \r\n"
						+ "This field cannot be filled in until a estimate is entered.");
		cmbIteration
				.setToolTipText("The iteration this requirement is assigned to \r\n"
						+ "This field cannot be filled in until a estimate is entered.");
		lblPriority.setToolTipText("The priority of this requirement.");
		cmbPriority.setToolTipText("The priority of this requirement.");
		lblStatus.setToolTipText("The status of the requirement.");
		cmbStatus.setToolTipText("The status of the requirement.");
		lblType.setToolTipText("Set the type of requirement this requirement is.");
		cmbType.setToolTipText("Set the type of requirement this requirement is.");
	}

	/**
	 * Sets the delete button enabled.
	 * 
	 * @param enabled
	 *            a boolean indicating whether or not to Enabled
	 */
	public void setDeleteEnabled(boolean enabled) {
		deleteRequirementButton.setEnabled(enabled);
	}

	/**
	 * Disable stuff.
	 * 
	 * @param components
	 *            the components to be disabled
	 */
	protected void disableFields(JComponent[] components) {
		for (JComponent com : components) {
			if (com != null)
				com.setEnabled(false);
		}
	}

	/**
	 * Enables components for editing purposes.
	 * 
	 * @param components
	 *            to be enabled
	 */
	protected void enableFields(JComponent[] components) {
		for (JComponent com : components) {
			if (com != null)
				com.setEnabled(true);
		}
	}

	/**
	 * Change background.
	 * 
	 * @param components
	 *            the components
	 */
	protected void changeBackground(JTextComponent[] components) {
		for (JComponent com : components) {
			if (com != null)
				com.setBackground(this.getBackground());
		}
	}

	/**
	 * Make text black.
	 * 
	 * @param components
	 *            the components
	 */
	protected void makeTextBlack(JTextComponent[] components) {
		for (JTextComponent com : components) {
			if (com != null)
				com.setDisabledTextColor(Color.BLACK);
		}
	}

	/**
	 * Make stuff not visible.
	 * 
	 * @param components
	 *            the components
	 */
	protected void makeStuffNotVisible(JComponent[] components) {
		for (JComponent com : components) {
			if (com != null)
				com.setVisible(false);
		}
	}

	/**
	 * Gets the parent RequirementsView.
	 * 
	 * @return the parent RequirementsView.
	 */
	public RequirementView getParent() {
		return parent;
	}

	/**
	 * Sets if the input is enabled.
	 * 
	 * @param enabled
	 *            shows if input is enabled
	 */
	protected void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;

		txtTitle.setEnabled(enabled);
		txtReleaseNumber.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
		cmbStatus.setEnabled(enabled);
		cmbPriority.setEnabled(enabled);

		// if
		// (this.parent.getReqModel().getRequirement().getChildRequirementIds().isEmpty())
		// {
		// txtEstimate.setEnabled(enabled);
		// }
	}

	/**
	 * Sets the up panel.
	 */
	public void setUpPanel() {
		this.revalidate();
		layout.invalidateLayout(this);
		layout.layoutContainer(this);
		this.repaint();
		parent.refreshScrollPane();
	}

	/**
	 * Checks to see if it is an empty string. Returns -1 if the string is less
	 * than 0 or blank; returns the integer value otherwise.
	 * 
	 * @param intf
	 *            the IntergerField in question
	 * @return the integer that is either -1 or the integer value of the string
	 */
	protected int getValue(IntegerField intf) {
		if (intf.getText().equals(null) || intf.getText().equals("")) {
			return 0;
		} else {
			int newValue = 0;
			try {
				newValue = Integer.parseInt(intf.getText().trim());
			} catch (NumberFormatException e) {
				newValue = 0;
			}
			return newValue;
		}
	}

	/**
	 * Returns the model object represented by this view's fields.
	 * 
	 * TODO: Do some basic input verification
	 * 
	 * @return the model represented by this view
	 */
	public Requirement getEditedModel() {
		Requirement requirement = new Requirement();
		requirement.setId(parent.getReqModel().getRequirement().getId());
		requirement.setType(RequirementType.valueFromString((String) cmbType
				.getSelectedItem()));
		requirement.setTitle(txtTitle.getText().trim());
		requirement.setReleaseNumber(txtReleaseNumber.getText());
		requirement.setIteration((Iteration) cmbIteration.getSelectedItem());
		requirement.setDescription(txtDescription.getText().trim());
		requirement.setStatus(RequirementStatus.valueOf((String) cmbStatus
				.getSelectedItem()));
		requirement.setPriority(RequirementPriority
				.valueFromString((String) cmbPriority.getSelectedItem()));
		requirement.setEstimateEffort(getValue(txtEstimate)); // return -1 if
																// the field was
																// left blank
		requirement.setTotalEstimateEffort(getValue(txtTotalEstimate));
		requirement.setActualEffort(getValue(txtActual)); // return -1 if the
															// field was left
															// blank
		requirement.setCreationDate(parent.getReqModel().getRequirement()
				.getCreationDate());

		requirement.updateNotes(notesView.getNotesList());
		requirement.updateHistory(historyView.getHistoryList());
		requirement.updateAcceptanceTests(acceptanceTestsView.getList());
		requirement.setAssignee(assigneeView.getAssignedUserAL());
		requirement.setSubRequirements(parentChildrenView
				.getChildrenRequirementsList());
		requirement.setParentRequirementId(parent.getReqModel()
				.getRequirement().getParentRequirementId());
		requirement.setSubRequirements(parent.getReqModel().getRequirement()
				.getChildRequirementIds());
		requirement.updateTasks(tasksView.getTasks());

		if (!(txtCreator.getText().equals(""))) {
			requirement.setCreator(txtCreator.getText());
		}
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

	// TODO: class exists in action package, refactor
	/**
	 * The listener interface for receiving iteration events. The class that is
	 * interested in processing a iteration event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addIterationListener<code> method. When
	 * the iteration event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 */
	public class IterationListener implements ActionListener {
		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */

		@Override
		public void actionPerformed(ActionEvent iterations) {
			JComboBox cb = (JComboBox) iterations.getSource();

			changeStatus(cb);
		}

		/**
		 * Change the status of a requirement.
		 * 
		 * @param cb
		 *            the JComboBox which contains the list of status
		 */
		@SuppressWarnings("unchecked")
		public void changeStatus(JComboBox cb) {
			int i; // For loop counter, also used directly below the for loop.
			Boolean enabled = true;
			Boolean runThatForLoop = false;
			Boolean listHasStatus = false;
			RequirementStatus setTo = RequirementStatus.OPEN;
			// Change the status back to whatever it was when the backlog is
			// reselected (They changed their mind).
			if ((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.OPEN || parent
					.getReqModel().getRequirement().getStatus() == RequirementStatus.NEW)
					&& cb.getSelectedItem() == Iteration.getBacklog()) {
				setTo = parent.getReqModel().getRequirement().getStatus();
				enabled = false;
				runThatForLoop = true;
			}
			// Change the status to In Progress automatically when the req is
			// assigned to an iteration.
			else if ((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.OPEN || parent
					.getReqModel().getRequirement().getStatus() == RequirementStatus.NEW)
					&& cb.getSelectedItem() != Iteration.getBacklog()) {
				setTo = RequirementStatus.INPROGRESS;
				enabled = false;
				runThatForLoop = true;
			}
			// Change the status to Open automatically when the backlog is
			// selected.
			else if ((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.INPROGRESS)
					&& cb.getSelectedItem() == Iteration.getBacklog()) {
				setTo = RequirementStatus.OPEN;
				enabled = false;
				runThatForLoop = true;
			}
			// Set the status back to In Progress when they reassigned it to an
			// iteration (but let them change the status).
			else if ((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.INPROGRESS)
					&& cb.getSelectedItem() != Iteration.getBacklog()) {
				setTo = RequirementStatus.INPROGRESS;
				enabled = true;
				runThatForLoop = true;
			} else if ((parent.getReqModel().getRequirement().getStatus() == RequirementStatus.COMPLETE)
					&& cb.getSelectedItem() == Iteration.getBacklog()) {
				setTo = RequirementStatus.OPEN;
				enabled = false;
				runThatForLoop = true;
			}

			// Add statuses that are necessary to the dropdown list.
			if (runThatForLoop) {
				for (i = 0; i < cmbStatus.getItemCount(); i++) {
					if (setTo == RequirementStatus.valueOf((String) cmbStatus
							.getItemAt(i))) {
						cmbStatus.setSelectedIndex(i);
						listHasStatus = true;
					}
				}

				if (!listHasStatus) {
					cmbStatus.addItem(setTo.toString());
					cmbStatus.setSelectedIndex(i);// The element is added to the
													// end of the cmbStatus, so
													// its spot is i.
				}
			}
			runThatForLoop = false;

			RMPermissionsLevel pLevel = CurrentUserPermissions
					.getCurrentUserPermission();
			if (pLevel == RMPermissionsLevel.ADMIN) {
				cmbStatus.setBackground(Color.WHITE);
				cmbStatus.setEnabled(enabled);
			}
		}

	}

	// A Key Listener on the Estimate to grey out the Iteration drop down until
	// there is a value in the estimate.
	/**
	 * The listener interface for receiving estimate events. The class that is
	 * interested in processing a estimate event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addEstimateListener<code> method. When
	 * the estimate event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 */
	public class EstimateListener implements KeyListener {

		/**
		 * Action performed.
		 * 
		 * @param estimate
		 *            the estimate
		 */
		public void actionPerformed(ActionEvent estimate) {
		}

		/**
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {
		}

		/**
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {
		}

		/**
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			Boolean enabled = false;
			try {
				if (txtEstimate.getText().equals("")
						|| txtEstimate.getText() == null) {
					enabled = false;
				} else if (Integer.parseInt(txtEstimate.getText()) > 0) {
					enabled = true;
				} else {
					enabled = false;
				}
			} catch (NumberFormatException exception) {
				enabled = false;
			}

			cmbIteration.setEnabled(enabled);
			cmbIteration.setBackground(Color.WHITE);
		}
	}

	// A Key Listener on the Title and Description to grey out the save button
	// until there is text in them.
	/**
	 * The listener interface for receiving save events. The class that is
	 * interested in processing a save event implements this interface, and the
	 * object created with that class is registered with a component using the
	 * component's addSaveListener method. When the save event occurs, that
	 * object's appropriate method is invoked.
	 * 
	 */
	public class SaveListener implements KeyListener {

		/**
		 * Action performed.
		 * 
		 * @param estimate
		 *            the estimate
		 */
		public void actionPerformed(ActionEvent estimate) {
		}

		/**
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {
		}

		/**
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {
		}

		/**
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			Boolean enabled = false;

			try {
				if ((txtTitle.getText().equals("") || /**
				 * txtEstimate.getText() ==
				 * null) ||
				 */
				(txtDescription.getText().equals("") || txtDescription
						.getText() == null))) {
					enabled = false;
				} else {
					enabled = true;
				}
			} catch (NumberFormatException exception) {
				enabled = false;
			}
			saveRequirementButton.setEnabled(enabled);
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
	public AssigneeView getAssigneeView() {
		return assigneeView;
	}

	/**
	 * Gets dependencies view
	 * 
	 * @return dependencies view
	 */
	public ParentAndChildrenView getDependenciesView() {
		return parentChildrenView;
	}

	/**
	 * Get the TasksView.
	 * 
	 * @return the TasksView
	 */
	public TasksView getTasksView() {
		return tasksView;
	}

	/**
	 * Get all elements in the model.
	 * 
	 * @param model
	 *            The model to get elements from
	 * @return A list of elements
	 */
	@SuppressWarnings("unused")
	private List<String> getAllElementsInModel(DefaultListModel model) {
		List<String> modelElements = new ArrayList<String>();
		for (int i = 0; i < model.getSize(); i++) {
			modelElements.add((String) model.getElementAt(i));
		}
		return modelElements;
	}

	/**
	 * Gets the save requirement bottom
	 * 
	 * @return the saveRequirementButton
	 */
	public JButton getSaveRequirementBottom() {
		return saveRequirementButton;
	}

	/**
	 * Gets the cancel requirement button on the bottom
	 * 
	 * @return the cancelRequirementButton
	 */
	public JButton getCancelRequirementBottom() {
		return cancelRequirementButton;
	}

	/**
	 * Gets the delete requirement button on the bottom
	 * 
	 * @return the deleteRequirementButton
	 */
	public JButton getDeleteRequirementBottom() {
		return deleteRequirementButton;
	}

	/**
	 * Gets the create child requirement button
	 * 
	 * @return the createChildRequirementButton
	 */
	public JButton getCreateChildRequirement() {
		return createChildRequirementButton;
	}

	/**
	 * Gets the cmb iteration
	 * 
	 * @return the cmbIteration
	 */
	public JComboBox getCmbIteration() {
		return cmbIteration;
	}

	/**
	 * Gets the cmb type
	 * 
	 * @return the cmbType
	 */
	public JComboBox getCmbType() {
		return cmbType;
	}

	/**
	 * Gets the cmb status
	 * 
	 * @return the cmbStatus
	 */
	public JComboBox getCmbStatus() {
		return cmbStatus;
	}

	/**
	 * Gets the cmb priority
	 * 
	 * @return the cmbPriority
	 */
	public JComboBox getCmbPriority() {
		return cmbPriority;
	}

	/**
	 * Gets the txt title
	 * 
	 * @return the txtTitle
	 */
	public JPlaceholderTextField getTxtTitle() {
		return txtTitle;
	}

	/**
	 * Gets the txt estimate
	 * 
	 * @return the txtEstimate
	 */
	public IntegerField getTxtEstimate() {
		return txtEstimate;
	}

	/**
	 * Sets the txt estimate
	 * 
	 * @param estimateEffort
	 *            the estimate effort to set the txt estimate to
	 */
	public void setTxtEstimate(int estimateEffort) {
		this.txtEstimate.setText(estimateEffort + "");

	}

	/**
	 * Gets the txt modified date
	 * 
	 * @return the txtModifiedDate
	 */
	public JLabel getTxtModifiedDate() {
		return txtModifiedDate;
	}

	/**
	 * Gets the lbl title error
	 * 
	 * @return the lblTitleError
	 */
	public JLabel getLblTitleError() {
		return lblTitleError;
	}

	/**
	 * Gets the lbl description error
	 * 
	 * @return the lblDescriptionError
	 */
	public JLabel getLblDescriptionError() {
		return lblDescriptionError;
	}

	/**
	 * Gets the txt description
	 * 
	 * @return the txtDescription
	 */
	public JTextArea getTxtDescription() {
		return txtDescription;
	}

	/**
	 * Gets the txt release number
	 * 
	 * @return the txtReleaseNumber
	 */
	public JTextField getTxtReleaseNumber() {
		return txtReleaseNumber;
	}

	/**
	 * Gets the txt created date
	 * 
	 * @return the txtCreatedDate
	 */
	public JLabel getTxtCreatedDate() {
		return txtCreatedDate;
	}

	/**
	 * Gets the txt creator
	 * 
	 * @return the txtCreator
	 */
	public JTextField getTxtCreator() {
		return txtCreator;
	}

	/**
	 * Gets known iterations
	 * 
	 * @return the knownIterations
	 */
	public Iteration[] getKnownIterations() {
		return knownIterations;
	}

	/**
	 * Gets the history view
	 * 
	 * @return the historyView
	 */
	public HistoryView getHistoryView() {
		return historyView;
	}

	/**
	 * Gets the acceptance tests view
	 * 
	 * @return the acceptanceTestsView
	 */
	public AcceptanceTestsView getAcceptanceTestsView() {
		return acceptanceTestsView;
	}

	/**
	 * Sets the acceptance tests view
	 * 
	 * @param acceptanceTestsView
	 *            the acceptanceTestsView to set
	 */
	public void setAcceptanceTestsView(AcceptanceTestsView acceptanceTestsView) {
		this.acceptanceTestsView = acceptanceTestsView;
	}

	/**
	 * Gets the txt Actual
	 * 
	 * @return the txtActual
	 */
	public IntegerField getTxtActual() {
		return txtActual;
	}

	/**
	 * Gets the lbl estimate error
	 * 
	 * @return the lblEstimateError
	 */
	public JLabel getLblEstimateError() {
		return lblEstimateError;
	}

	/**
	 * Gets the lblTotalEstimate
	 * 
	 * @return the lblTotalEstimate
	 */
	public JLabel getLblTotalEstimate() {
		return lblTotalEstimate;
	}

	/**
	 * Gets the lbl actual error
	 * 
	 * @return the lblActualError
	 */
	public JLabel getLblActualError() {
		return lblActualError;
	}

	/**
	 * Gets the split pane
	 * 
	 * @return the splitPane
	 */
	public JSplitPane getSplitPane() {
		return splitPane;
	}

	/**
	 * Gets the split pane left
	 * 
	 * @return the splitPaneLeft
	 */
	public JSplitPane getSplitPaneLeft() {
		return splitPaneLeft;
	}

	/**
	 * Sets the iterations
	 * 
	 * @param iterations
	 *            the iterations to set
	 */
	@SuppressWarnings("unchecked")
	public void setIterations(Iteration[] iterations) {
		this.knownIterations = iterations;
		this.cmbIteration.removeAllItems();
		for (int i = 0; i < iterations.length; i++)
			this.cmbIteration.addItem(iterations[i]);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		this.getParent().getReqModel().updateBackgrounds();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		this.getParent().getReqModel().updateBackgrounds();

	}
}
