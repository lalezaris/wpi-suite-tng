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
 *  Tushar Narayan
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatusLists;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.CancelRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.CreateChildRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.DeleteRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddAssigneeController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddNoteController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CancelRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CreateChildRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.DeleteRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RemoveAssigneeController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveParentRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * Allows users to view Requirements.
 * Adapted from DefectView in project DefectTracker.
 * 
 * @author Chris Dunkers 
 * @author Joe Spicola
 * @author Michael French
 *
 * @version April 17, 2013
 *
 */
@SuppressWarnings({"serial", "unused"})
public class RequirementView extends JPanel implements IToolbarGroupProvider {

	private RequirementModel reqModel;
	private Requirement parentRequirement;
	Iteration[] availableIterations;
	protected RequirementPanel.Mode mode;

	private ToolbarGroupView buttonGroup;
	private JButton saveButton;
	private RequirementPanel mainPanel;
	private SaveRequirementController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	private RequirementView parentView; //add to default constructor
	private RMPermissionsLevel pLevel;
	//	private RequirementPanel reqPanel;
	/**
	 * Constructs a new RequirementView where the user can view (and edit) a requirement.
	 * 
	 * @param requirement	The requirement to show.
	 * @param editMode	The editMode for editing the Requirement
	 * @param tab		The Tab holding this RequirementView (can be null)
	 */
	public RequirementView(Requirement requirement, Mode editMode, Tab tab) {	
		this.pLevel = CurrentUserPermissions.getCurrentUserPermission();
		this.mode = editMode;
		this.reqModel = new RequirementModel(requirement, this);
		boolean test = false;
		containingTab = tab;
		this.parentView = null;
		this.availableIterations = new Iteration[1];
		availableIterations[0] = Iteration.getBacklog();

		if(containingTab == null) {
			containingTab = new DummyTab();
			test = true;
		}

		inputEnabled = true;

		containingTab.setIcon(new ImageIcon());
		if(editMode == Mode.CREATE) {
			containingTab.setTitle("Create Requirement"); //Math.random()
			containingTab.setToolTipText("Create a new requirement");
		} else if (editMode == Mode.CHILD){
			setChildModeDescriptors(requirement);
		} else {
			setEditModeDescriptors(requirement);
		}

		// If this is a new requirement, set the creator
		if (editMode == Mode.CREATE || editMode == Mode.CHILD) {
			requirement.setCreator(ConfigManager.getConfig().getUserName());
		}

		mainPanel = new RequirementPanel(this,mode);

		//set create child button action
		mainPanel.getCreateChildRequirement().setAction(new CreateChildRequirementAction(new CreateChildRequirementController(this)));
		//set the cancel button action
		mainPanel.getCancelRequirementBottom().setAction(new CancelRequirementAction(new CancelRequirementController(this)));
		//set the delete button action
		mainPanel.getDeleteRequirementBottom().setAction(new DeleteRequirementAction(new DeleteRequirementController(this)));
		//set the save button action
		mainPanel.getSaveRequirementBottom().setAction(new SaveChangesAction(new SaveRequirementController(this)));


		mainPanel.getNotesView().getSaveButton().addActionListener(new AddNoteController(mainPanel.getNotesView()));
		mainPanel.getAssigneeView().getBtnAdd().addActionListener(new AddAssigneeController(mainPanel.getAssigneeView()));
		mainPanel.getAssigneeView().getBtnRemove().addActionListener(new RemoveAssigneeController(mainPanel.getAssigneeView()));
		
		if (reqModel.getRequirement().getParentRequirementId() != -1) {
			RetrieveParentRequirementController recieveParentController = new RetrieveParentRequirementController(this);
			recieveParentController.retrieveParent();
		}
		
		RetrieveAllIterationsController iterationsController = new RetrieveAllIterationsController(this);
		iterationsController.retrieve();
		
		this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(mainPanel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);

		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				mainPanelScrollPane.repaint();
			}
		});

		//this.add(mainPanelScrollPane, BorderLayout.CENTER);
		this.add(mainPanel, BorderLayout.CENTER);
		controller = new SaveRequirementController(this);
	}


	/**
	 * Checks to make sure the title and description are filled in.
	 *
	 * @return 6 if actual is too big, 5 is estimate is too big,4 if both, 3 if both title and description not filled in, 2 if only title, 1 if only description, 0 otherwise
	 */
	public int checkRequiredFields(){
		String tempTitle = mainPanel.getTxtTitle().getText().trim();
		String tempDesc = mainPanel.getTxtDescription().getText().trim();


		int estimate = 0, actual = 0;
		try{
			estimate = Integer.parseInt(mainPanel.getTxtEstimate().getText());
		}
		catch (NumberFormatException e){
			System.out.println("The estimate is too big to save. Error!");
			mainPanel.getLblEstimateError().setVisible(true);  
			estimate = -1; //TODO add JLabel in RequirementPanel to warn user of this error
		}
		try{
			actual = Integer.parseInt(mainPanel.getTxtActual().getText());
		}
		catch (NumberFormatException e){
			System.out.println("The actual is too big to save. Error!");
			mainPanel.getLblActualError().setVisible(true);
			actual = -1; //TODO add JLabel in RequirementPanel to warn user of this error
		}
		if (estimate == -1 && actual == -1){
			return 4;
		} else if (estimate == -1)
			return 5;
		else if (actual == -1)
			return 6;


		if((tempTitle.equals(null) || tempTitle.equals("")) && 
				(tempDesc.equals(null) || tempDesc.equals(""))){
			mainPanel.getLblTitleError().setVisible(true);
			mainPanel.getLblDescriptionError().setVisible(true);
			return 3;
		} else if(tempTitle.equals(null) || tempTitle.equals("")){
			mainPanel.getLblTitleError().setVisible(true);
			mainPanel.getLblDescriptionError().setVisible(false);
			return 2; 
		} else if(tempDesc.equals(null) || tempDesc.equals("")){
			mainPanel.getLblDescriptionError().setVisible(true);
			mainPanel.getLblTitleError().setVisible(false);
			return 1;
		} else 
			return 0;
	}

	/**
	 * Constructor that allows user to send a parent view for reference.
	 * 
	 * @param requirement The requirement to be displayed
	 * @param editMode The mode the view should be opened with
	 * @param tab The tab
	 * @param parentView
	 */
	public RequirementView(Requirement requirement, Mode editMode, Tab tab, RequirementView parentView) {
		this(requirement, editMode, tab);
		this.parentView = parentView;
	}

	/**
	 * Tooltip info for new child requirement.
	 * 
	 * @param requirement The requirement to be created with a child requirement
	 */
	public void setChildModeDescriptors(Requirement requirement) {
		containingTab.setTitle("New Child Requirement");
		containingTab.setToolTipText("Create a new child requirement");
	}


	/**
	 * Returns the main panel with the data fields.
	 * 
	 * @return the main panel with the data fields
	 */
	public RequirementPanel getRequirementPanel() {
		return mainPanel;
	}

	/* 
	 * @see edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider#getGroup()
	 */
	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}

	/**
	 * Set the tab title, tooltip, and group name according to this Requirement.
	 * 
	 * @param requirement The input Requirement
	 */
	public void setEditModeDescriptors(Requirement requirement) {
		containingTab.setTitle("#" + requirement.getId() + ": " + requirement.getTitle().substring(0, Math.min(10, requirement.getTitle().length())));
		containingTab.setToolTipText("#" + requirement.getId() + ": " + requirement.getTitle());
	}

	/**
	 * Sets whether the input is enabled.
	 * 
	 * @param enabled A boolean indicating whether or not to be Enabled.
	 */
	public void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;

		mainPanel.setInputEnabled(enabled);
	}

	/**
	 * Return containingTab.
	 * 
	 * @return the containing tab
	 */
	public Tab getTab() {
		return containingTab;
	}

	/**
	 * Revalidates and repaints the scroll pane containing the RequirementPanel.
	 */
	public void refreshScrollPane() {
		mainPanelScrollPane.revalidate();
		mainPanelScrollPane.repaint();
	}

	/**
	 * Set the parentview of this requirementview.
	 * 
	 * @param parentView the parent requirement view
	 */
	public void setParentView(RequirementView parentView) {
		this.parentView = parentView;
	}

	/**
	 * Returns the parent's requirementview.
	 * 
	 * @return the parent's requirementview
	 */
	public RequirementView getParentView() {
		return parentView;
	}

	/**
	 * @return the reqModel
	 */
	public RequirementModel getReqModel() {
		return reqModel;
	}

	@SuppressWarnings("unchecked")
	public void setUp(Requirement requirement, Mode editMode, RMPermissionsLevel pLevel) {

		String[] requirementStatusValues = RequirementStatusLists.getList(this.getReqModel().getRequirement());
		mainPanel.getCmbStatus().removeAllItems();
		for (int i = 0; i < requirementStatusValues.length; i++) {
			requirementStatusValues[i] = RequirementStatusLists.getList(this.getReqModel().getRequirement())[i];
			mainPanel.getCmbStatus().addItem(requirementStatusValues[i]);
		}

		reqModel.update(requirement, editMode);

		mainPanel.getHistoryView().setHistoryList(this.getReqModel().getRequirement().getHistory());
		mainPanel.getAssigneeView().setAssigneeList(this.getReqModel().getRequirement().getAssignee());
		mainPanel.getAssigneeView().setLists();

		ArrayList<AcceptanceTest> acceptanceTestList = new ArrayList<AcceptanceTest>();
		for(int i = 0; i < this.getReqModel().getRequirement().getAcceptanceTests().size(); i++){
			acceptanceTestList.add(this.getReqModel().getRequirement().getAcceptanceTests().get(i));
		}
		mainPanel.getAcceptanceTestsView().setList(acceptanceTestList);

		//Default the Iteration Box based on the values of the estimate (Don't let you choose it if the estimate is blank).
		if(this.getReqModel().getRequirement().getEstimateEffort() > 0) {
			mainPanel.getCmbIteration().setEnabled(true);
			mainPanel.getCmbIteration().setBackground(Color.WHITE);
		}
		else
			mainPanel.getCmbIteration().setEnabled(false);

		//Default the save button depending on what is filled in (Title and Description).
		if(!this.getReqModel().getRequirement().getTitle().equals("") && 
				!this.getReqModel().getRequirement().getDescription().equals("") && 
				this.getReqModel().getRequirement().getTitle() != null && 
				this.getReqModel().getRequirement().getDescription() != null){
			mainPanel.getSaveRequirementBottom().setEnabled(true);
		}
		else{
			mainPanel.getSaveRequirementBottom().setEnabled(false);
		}

		//Move the requirement to the backlog if it is set to OPEN.
		if(this.getReqModel().getRequirement().getStatus() == RequirementStatus.OPEN){
			this.getReqModel().getRequirement().setIteration(Iteration.getBacklog());
			mainPanel.getCmbIteration().setEnabled(true);
			mainPanel.getCmbStatus().setEnabled(true);
		}


		//depending on the mode, disable certain components
		if (this.getMode() == Mode.CREATE || this.getMode() == Mode.CHILD) {
			mainPanel.getCmbStatus().setEnabled(false);
			mainPanel.getTxtActual().setEnabled(false);
		}

		if (this.getMode() == Mode.CHILD) {
			mainPanel.getTxtReleaseNumber().setEnabled(false);
		}

		if(!(this.getReqModel().getRequirement().getParentRequirementId() == -1)){
			mainPanel.getTxtReleaseNumber().setEnabled(false);
		}
		
		if(!this.getReqModel().getRequirement().getChildRequirementIds().isEmpty()) {
			mainPanel.getTxtEstimate().setEnabled(false);
		}

		// depending on the status and sub-requirements, disable certain components

		if (this.reqModel.getRequirement().getStatus() == RequirementStatus.INPROGRESS
				|| this.getReqModel().getRequirement().getStatus() == RequirementStatus.COMPLETE){
			//TODO: uncomment the next line once busy waiting issue is fixed
			//|| childList.retrieveChildrenByID(model.getId()).size() != 0) {
			mainPanel.getTxtEstimate().setEnabled(false);
		}

		setUpPermissions(pLevel);
		//mainPanel.setUpPanel();
	}

	private void setUpPermissions(RMPermissionsLevel pLevel){
		//depending on the user's permission, disable certain components

		if(!this.reqModel.getRequirement().getAssignee().contains(ConfigManager.getConfig().getUserName()) && 
				pLevel == RMPermissionsLevel.UPDATE){
			pLevel = RMPermissionsLevel.NONE;
		}
		switch (pLevel){
		case NONE:
			mainPanel.disableFields(new JComponent[]{mainPanel.getCmbStatus(),mainPanel.getCmbPriority(),mainPanel.getCmbType(),mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtActual(),mainPanel.getTxtCreator(),/*txtAssignee,*/
					mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),mainPanel.getCmbIteration(),mainPanel.getNotesView().getSaveButton(),mainPanel.getNotesView().getTextArea(),mainPanel.getSaveRequirementBottom(), 
					mainPanel.getDeleteRequirementBottom(), mainPanel.getCancelRequirementBottom(), mainPanel.getCreateChildRequirement(), mainPanel.getAssigneeView().getBtnAdd(), mainPanel.getAssigneeView().getBtnRemove(),mainPanel.getAcceptanceTestsView().getListDisplay()});
			mainPanel.changeBackground(new JTextComponent[]{mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtActual(),mainPanel.getTxtCreator(),/*txtAssignee,*/
					mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),mainPanel.getNotesView().getTextArea()});
			mainPanel.makeTextBlack(new JTextComponent[]{mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtActual(),mainPanel.getTxtCreator(),/*txtAssignee,*/
					mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber()});
			mainPanel.makeStuffNotVisible(new JComponent[]{mainPanel.getPanelButtons()});
			break;
		case UPDATE: 

			mainPanel.disableFields(new JComponent[]{mainPanel.getCmbStatus(),mainPanel.getCmbPriority(),mainPanel.getCmbType(),mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),
					mainPanel.getTxtCreator(),/*txtAssignee,*/mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),mainPanel.getCmbIteration(), mainPanel.getDeleteRequirementBottom(), mainPanel.getCreateChildRequirement(), mainPanel.getAssigneeView().getBtnAdd(), mainPanel.getAssigneeView().getBtnRemove()});
			mainPanel.changeBackground(new JTextComponent[]{mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtCreator(),/*txtAssignee,*/mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),});
			mainPanel.makeTextBlack(new JTextComponent[]{mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtCreator(),/*txtAssignee,*/mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber()});
			mainPanel.makeStuffNotVisible(new JComponent[]{mainPanel.getDeleteRequirementBottom(), mainPanel.getCreateChildRequirement()});
			break;		
		case ADMIN: break;
		}


		// loops through assignees in a requirement to enable actual estimate field

		if(this.getReqModel().getRequirement().getAssignee().contains(ConfigManager.getConfig().getUserName()) && 
				pLevel != RMPermissionsLevel.NONE){
			mainPanel.enableFields(new JComponent[]{mainPanel.getTxtActual(), mainPanel.getCancelRequirementBottom(), mainPanel.getSaveRequirementBottom()});
		}

		if (this.getReqModel().getRequirement().getStatus() == RequirementStatus.DELETED)
			mainPanel.disableFields(new JComponent[]{mainPanel.getCmbType(), mainPanel.getCmbPriority(),mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtActual(),mainPanel.getTxtCreator(),/*txtAssignee,*/
					mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),mainPanel.getCmbIteration(),mainPanel.getNotesView().getSaveButton(),mainPanel.getNotesView().getTextArea(), 
					mainPanel.getDeleteRequirementBottom(), mainPanel.getCreateChildRequirement()});

		if (!reqModel.getUneditedRequirement().getChildRequirementIds().isEmpty()) {
			mainPanel.disableFields(new JComponent[]{mainPanel.getDeleteRequirementBottom()});
			mainPanel.getDeleteRequirementBottom().setToolTipText("Cannot delete this requirement as it has children.");
		} 
	}

	/**
	 * @return the mode
	 */
	public RequirementPanel.Mode getMode() {
		return mode;
	}
	
	public void setIterationComboBox(){
		Iteration[] knownIterations = availableIterations;
		ArrayList<Iteration> knownIts = new ArrayList<Iteration>();
		
		for (int i = 0; i < knownIterations.length ;i++){
			if (parentRequirement != null) {
				System.out.println("Parent: " + parentRequirement.getTitle());
				if (parentRequirement.getIterationId() == knownIterations[i].getId() || knownIterations[i] == Iteration.getBacklog()) {
					knownIts.add(knownIterations[i]);
				}
			} else {
				System.out.println("No parent...");
				if (knownIterations[i].getEndDate().compareTo(new Date()) >= 0 || knownIterations[i] == Iteration.getBacklog() || knownIterations[i].getId() == getReqModel().getRequirement().getIteration().getId()){
					knownIts.add(knownIterations[i]);
				}
			}
		}

		if (!(knownIts.contains(Iteration.getBacklog()))) {
			knownIts.add(Iteration.getBacklog());
		}
			
		knownIterations = new Iteration[knownIts.size()];
		for (int i = 0; i < knownIterations.length; i++){
			knownIterations[i] = knownIts.get(i);
		}

		mainPanel.setIterations(knownIterations);

		setUp(this.reqModel.getRequirement(), mode, CurrentUserPermissions.getCurrentUserPermission());

		mainPanel.getCmbStatus().addActionListener(new StatusListener(this));

		//if either a parent or a child requirement is being created, do not allow the user to create (further) children
		if(mode == RequirementPanel.Mode.CREATE || mode == RequirementPanel.Mode.CHILD){
			mainPanel.getCreateChildRequirement().setEnabled(false);
			mainPanel.getCreateChildRequirement().setVisible(false);
		}

		//if the requirement is completed disable the child requirement 
		if(getReqModel().getUneditedRequirement().getStatus().equals(RequirementStatus.COMPLETE)){
			mainPanel.getCreateChildRequirement().setEnabled(false);				
		}

		mainPanel.getSplitPaneLeft().setDividerLocation(0.95);
//		mainPanel.getSplitPane().setDividerLocation(0.5);
		mainPanel.getSplitPane().setDividerLocation(mainPanel.getTxtDescription().getWidth()+50);

	}

	/**
	 * @param parent the parent requirement to add
	 */
	public void setParentRequirement(Requirement parent) {
		parentRequirement = parent;
	}
	
	/**
	 * @return the parent requirement
	 */
	public Requirement getParentRequirement() {
		return parentRequirement;
	}
	
	/**
	 * @param availableIterations the iterations available to set the requirement to
	 */
	public void setAvailableIterations(Iteration[] availableIterations) {
		this.availableIterations = availableIterations;
	}
	
	/**
	 * @return the available iterations to set the requirement to
	 */
	public Iteration[] getAvailableIterations() {
		return availableIterations;
	}

	/**
	 * @return the pLevel
	 */
	public RMPermissionsLevel getpLevel() {
		return pLevel;
	}
	
}