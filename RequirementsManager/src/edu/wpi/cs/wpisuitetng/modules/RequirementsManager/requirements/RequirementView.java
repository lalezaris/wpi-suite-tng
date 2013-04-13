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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.CancelRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.CreateChildRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.DeleteRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddNoteController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddAssigneeController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CancelRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CreateChildRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.DeleteRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RemoveAssigneeController;
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
 *
 * @version Mar 17, 2013
 *
 */
public class RequirementView extends JPanel implements IToolbarGroupProvider {
	
	private RequirementModel reqModel;
	
	
	private ToolbarGroupView buttonGroup;
	private JButton saveButton;
	private RequirementPanel mainPanel;
	private SaveRequirementController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	private RequirementView parentView; //add to default constructor
//	private RequirementPanel reqPanel;
	
	/**
	 * Constructs a new RequirementView where the user can view (and edit) a requirement.
	 * 
	 * @param requirement	The requirement to show.
	 * @param editMode	The editMode for editing the Requirement
	 * @param tab		The Tab holding this RequirementView (can be null)
	 */
	public RequirementView(Requirement requirement, Mode editMode, Tab tab) {		
		
		this.reqModel = new RequirementModel(requirement, this);
		
		containingTab = tab;
		this.parentView = null;
		
		if(containingTab == null) {
			containingTab = new DummyTab();
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
		
		mainPanel = new RequirementPanel(this, editMode);
		
		//set create child button action
		mainPanel.getCreateChildRequirement().setAction(new CreateChildRequirementAction(new CreateChildRequirementController(this)));
		//set the cancel button action
		mainPanel.getCancelRequirementBottom().setAction(new CancelRequirementAction(new CancelRequirementController(this)));
		//set the delete button action
		mainPanel.getDeleteRequirementBottom().setAction(new DeleteRequirementAction(new DeleteRequirementController(this)));
		//set the save button action
		mainPanel.getSaveRequirementBottom().setAction(new SaveChangesAction(new SaveRequirementController(this)));
		
		
		mainPanel.getNotesView().getSaveButton().addActionListener(new AddNoteController(mainPanel.getNotesView()));
		mainPanel.getAv().getBtnAdd().addActionListener(new AddAssigneeController(mainPanel.getAv()));
		mainPanel.getAv().getBtnRemove().addActionListener(new RemoveAssigneeController(mainPanel.getAv()));
		
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
		
		setUp(requirement, editMode);
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
		try{ estimate = Integer.parseInt(mainPanel.getTxtEstimate().getText()); }
		catch (NumberFormatException e){
			System.out.println("The estimate is too big to save. Error.");
			estimate = -1; //TODO add JLabel in RequirementPanel to warn user of this error
		}
		try{ actual = Integer.parseInt(mainPanel.getTxtActual().getText()); }
		catch (NumberFormatException e){
			System.out.println("The actual was just too dayum big!. Error");
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

	public void setUp(Requirement requirement, Mode editMode) {
		reqModel.update(requirement, editMode);
		setUpPermissions();
		//mainPanel.setUpPanel();
	}
	
	private void setUpPermissions(){
		//depending on the user's permission, disable certain components
		 RMPermissionsLevel pLevel = CurrentUserPermissions.getCurrentUserPermission();
		 if(!this.getReqModel().getRequirement().getAssignee().contains(ConfigManager.getConfig().getUserName()) && 
				 pLevel == RMPermissionsLevel.UPDATE){
			 pLevel = RMPermissionsLevel.NONE;
		 }
		 switch (pLevel){
		 case NONE:
			 mainPanel.disableStuff(new JComponent[]{mainPanel.getCmbStatus(),mainPanel.getCmbPriority(),mainPanel.getCmbType(),mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtActual(),mainPanel.getTxtCreator(),/*txtAssignee,*/
					 mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),mainPanel.getCmbIteration(),mainPanel.getNotesView().getSaveButton(),mainPanel.getNotesView().getTextArea(),mainPanel.getSaveRequirementBottom(), 
					 mainPanel.getDeleteRequirementBottom(), mainPanel.getCancelRequirementBottom(), mainPanel.getCreateChildRequirement(), mainPanel.getAv().getBtnAdd(), mainPanel.getAv().getBtnRemove()});
			 mainPanel.changeBackground(new JTextComponent[]{mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtActual(),mainPanel.getTxtCreator(),/*txtAssignee,*/
					 mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),mainPanel.getNotesView().getTextArea()});
			 mainPanel.makeTextBlack(new JTextComponent[]{mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtActual(),mainPanel.getTxtCreator(),/*txtAssignee,*/
					 mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber()});
			 mainPanel.makeStuffNotVisible(new JComponent[]{mainPanel.getPanelButtons()});
			 break;
		 case UPDATE: 
			 
			 mainPanel.disableStuff(new JComponent[]{mainPanel.getCmbStatus(),mainPanel.getCmbPriority(),mainPanel.getCmbType(),mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),
					 mainPanel.getTxtCreator(),/*txtAssignee,*/mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),mainPanel.getCmbIteration(), mainPanel.getDeleteRequirementBottom(), mainPanel.getCreateChildRequirement(), mainPanel.getAv().getBtnAdd(), mainPanel.getAv().getBtnRemove()});
			 mainPanel.changeBackground(new JTextComponent[]{mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtCreator(),/*txtAssignee,*/mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),});
			 mainPanel.makeTextBlack(new JTextComponent[]{mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtCreator(),/*txtAssignee,*/mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber()});
			 mainPanel.makeStuffNotVisible(new JComponent[]{mainPanel.getDeleteRequirementBottom(), mainPanel.getCreateChildRequirement()});
			 break;		
		 case ADMIN: break;
		 }
		 
		 
		 // loops through assignees in a requirement to enable actual estimate field
		 
		 if(this.getReqModel().getRequirement().getAssignee().contains(ConfigManager.getConfig().getUserName()) && 
				 pLevel != RMPermissionsLevel.NONE){
			 mainPanel.enableStuff(new JComponent[]{mainPanel.getTxtActual(), mainPanel.getCancelRequirementBottom(), mainPanel.getSaveRequirementBottom()});
		 }
		 
		 if (this.getReqModel().getRequirement().getStatus() == RequirementStatus.DELETED)
			 mainPanel.disableStuff(new JComponent[]{mainPanel.getCmbPriority(),mainPanel.getTxtDescription(),mainPanel.getTxtEstimate(),mainPanel.getTxtActual(),mainPanel.getTxtCreator(),/*txtAssignee,*/
					 mainPanel.getTxtTitle(),mainPanel.getTxtReleaseNumber(),mainPanel.getCmbIteration(),mainPanel.getNotesView().getSaveButton(),mainPanel.getNotesView().getTextArea(), 
					 mainPanel.getDeleteRequirementBottom(), mainPanel.getCreateChildRequirement()});
		 
		 System.out.println("HELLO!!!! " + this.getReqModel().getRequirement().getChildRequirementIds().toString());
		 if (!mainPanel.getEditedModel().getChildRequirementIds().isEmpty()) {
			 mainPanel.disableStuff(new JComponent[]{mainPanel.getDeleteRequirementBottom()});
		 }
	}
	
	
}