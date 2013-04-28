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
 * Chris Hanna
 * Lauren Kahn
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;


import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;

import java.awt.Color;

/**
 * The Class RequirementModel is the model that holds requirements and their views.
 *
 * @author Chris Hanna
 */
public class RequirementModel {

	private Requirement requirement;
	private Requirement uneditedRequirement;
	private RequirementView view;

	/**
	 * Instantiates a new requirement model.
	 *
	 * @param requirement the requirement
	 * @param view the view
	 */
	public RequirementModel(Requirement requirement, RequirementView view){
		this.requirement = requirement;
		uneditedRequirement = requirement;
		this.view = view;
	}


	/**
	 * Updates the panel.
	 *
	 * @param editMode The mode to use.
	 */
	protected void setUpPanel(Mode editMode){
		RequirementPanel panel = view.getRequirementPanel();

		if(!(requirement.getTitle().equals(null) || 
				requirement.getTitle().equals("")))
			panel.getTxtTitle().setText(requirement.getTitle());


		panel.getTxtDescription().setText(requirement.getDescription());
		panel.getTxtReleaseNumber().setText(requirement.getReleaseNumber());
		panel.getTxtEstimate().setText( String.valueOf(requirement.getEstimateEffort()) );
		panel.getTxtTotalEstimate().setText(  String.valueOf(requirement.getTotalEstimateEffort()));
		panel.getTxtActual().setText( String.valueOf(requirement.getActualEffort()) );

		panel.getLblTotalEstimate().setVisible(panel.getParent().getReqModel().getRequirement().getChildRequirementIds().size() > 0);	
		panel.getTxtTotalEstimate().setVisible(panel.getParent().getReqModel().getRequirement().getChildRequirementIds().size() > 0);	

		
		
		for (int i = 0; i < panel.getCmbType().getItemCount(); i++) {
			if (requirement.getType().toString().equals(panel.getCmbType().getItemAt(i).toString())) {
				panel.getCmbType().setSelectedIndex(i);
			}
		}

		for (int i = 0; i < panel.getCmbStatus().getItemCount(); i++) {
			if (requirement.getStatus() == RequirementStatus.valueOf((String) panel.getCmbStatus().getItemAt(i))) {
				panel.getCmbStatus().setSelectedIndex(i);
			}
		}

		for (int i = 0; i < panel.getCmbIteration().getItemCount(); i++) {
			if (requirement.getIterationId() == (((Iteration) panel.getCmbIteration().getItemAt(i)).getId()) ){
				panel.getCmbIteration().setSelectedIndex(i);
			}
		}

		for (int i = 0; i < panel.getCmbPriority().getItemCount(); i++) {
			if (requirement.getPriority().toString().equals(panel.getCmbPriority().getItemAt(i).toString())) {
				panel.getCmbPriority().setSelectedIndex(i);
			}
		}

		if (panel.getEditMode() == Mode.EDIT) {
			panel.getTxtCreatedDate().setText(requirement.getCreationDate().toString());
			panel.getTxtModifiedDate().setText(requirement.getLastModifiedDate().toString());
			panel.getDeleteRequirementBottom().setVisible(true);
		}
		if (requirement.getCreator() != null) {
			panel.getTxtCreator().setText(requirement.getCreator());
		}

		ArrayList<Note> notesList = new ArrayList<Note>();
		for(int i = 0; i < requirement.getNotes().size(); i++){
			notesList.add(requirement.getNotes().get(i));
		}
		panel.getNotesView().setNotesList(notesList);		
		panel.setUpPanel();
	}

	/**
	 * Updates the model and the fields in the panel.
	 *
	 * @param req the requirement to update
	 * @param editMode the new mode to use
	 */
	public void update(Requirement req, Mode editMode){
		requirement.setId(req.getId());
		requirement.setTitle(req.getTitle());
		requirement.setType(req.getType());
		requirement.setReleaseNumber(req.getReleaseNumber());
		requirement.setIteration(req.getIteration());
		requirement.setDescription(req.getDescription());
		requirement.setAssignee(req.getAssignee());
		requirement.setCreator(req.getCreator());
		requirement.setCreationDate(req.getCreationDate());
		requirement.setLastModifiedDate(req.getLastModifiedDate());
		requirement.setStatus(req.getStatus());
		requirement.setPriority(req.getPriority());
		
		System.out.println("Setting estimate effort to : " + req.getEstimateEffort());
		System.out.println("Setting total estimate effort to : " + req.getTotalEstimateEffort());
		requirement.setEstimateEffort(req.getEstimateEffort());
		requirement.setTotalEstimateEffort(req.getTotalEstimateEffort());
		requirement.setActualEffort(req.getActualEffort());
		requirement.updateNotes(req.getNotes());
		requirement.updateHistory(req.getHistory());
		requirement.setCompletedChildren(req.getCompletedChildren());
		requirement.setParentRequirementId(requirement.getParentRequirementId());

		view.getRequirementPanel().getTxtTotalEstimate().setText(  String.valueOf(requirement.getTotalEstimateEffort()));
		if(req.getChildRequirementIds().size() > 0){
			view.getRequirementPanel().getLblTotalEstimate().setVisible(true);
			view.getRequirementPanel().getTxtTotalEstimate().setVisible(true);
		}
		view.getRequirementPanel().getDependenciesView().updateChildrenList(requirement.getChildRequirementIds());
//		setUpPanel(editMode);

	}


	/**
	 * Checks to see if any changes have been made.
	 *
	 * @return true if changes has been made otherwise false
	 */
	public boolean isThereChanges(){
		Requirement oldR = uneditedRequirement;
		Requirement newR = view.getRequirementPanel().getEditedModel();

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

		if (!view.getRequirementPanel().getNotesView().getNoteString().equals("") && !view.getRequirementPanel().getNotesView().getNoteString().equals(null) ){//if old and new are not the same
			return true;
		}

		if (oldR.getNotes().size() != newR.getNotes().size()){//if old and new are not the same
			return true;
		}		
		if (view.getRequirementPanel().getAssigneeView().isButtonPressed()){//if old and new are not the same
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

		if(!view.getRequirementPanel().getAcceptanceTestsView().getBodyField().getText().trim().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getBodyField().getText().trim().equals(null)){
			return true;
		}

		if(!view.getRequirementPanel().getAcceptanceTestsView().getTitleField().getText().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getTitleField().getText().equals(null)){
			return true;
		}

		if(oldR.getAcceptanceTests().size() != view.getRequirementPanel().getAcceptanceTestsView().getList().size()){
			return true;
		}
		
		if(view.getRequirementPanel().getTasksView().isChanged()){
			return true;
		}

		return false;
	}

	/**
	 * Checks to see if any changes have been made to fields.
	 * Set background that are changed
	 * TODO: make individual flags for each, to be able to reset when field goes white again
	 * @return true if changes has been made otherwise false
	 */
	public boolean updateBackgrounds(){
		Requirement oldR = uneditedRequirement;
		Requirement newR = view.getRequirementPanel().getEditedModel();
		boolean flag = false; //gets set to true when something has changed; in order to iterate through everything
		int notesDifference = (newR.getNotes().size() - oldR.getNotes().size());
		int acceptDifference = (newR.getAcceptanceTests().size() - oldR.getAcceptanceTests().size());

		//compare titles
		if (oldR.getTitle().compareTo(newR.getTitle()) != 0){//if old and new are not the same
			//change to yellow background
			view.getRequirementPanel().txtTitle.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtTitle.getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().txtTitle.setBackground(Color.WHITE);//change to white background in case of reset

		//compare Release Numbers
		if (!oldR.getReleaseNumber().equals(newR.getReleaseNumber())){//if old and new are not the same
			view.getRequirementPanel().txtReleaseNumber.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtReleaseNumber.getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().txtReleaseNumber.setBackground(Color.WHITE);//change to white background in case of reset

		//compare type
		if (oldR.getType().compareTo(newR.getType()) != 0){//if old and new are not the same
			view.getRequirementPanel().cmbType.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().cmbType.getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().cmbType.setBackground(Color.WHITE);//change to white background in case of reset

		//compare Iterations
		if (oldR.getIterationId()!=(newR.getIterationId())){//if old and new are not the same
			view.getRequirementPanel().cmbIteration.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().cmbIteration.getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().cmbIteration.setBackground(Color.WHITE);//change to white background in case of reset

		//compare Descriptions
		if (oldR.getDescription().compareTo(newR.getDescription()) != 0){//if old and new are not the same
			view.getRequirementPanel().txtDescription.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtDescription.getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().txtDescription.setBackground(Color.WHITE);//change to white background in case of reset

		//compare Statuses
		if (oldR.getStatus() != newR.getStatus() && view.getRequirementPanel().cmbStatus.isEnabled()){//if old and new are not the same
			view.getRequirementPanel().cmbStatus.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().cmbStatus.getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().cmbStatus.setBackground(Color.WHITE);//change to white background in case of reset

		//compare Priorities
		if (oldR.getPriority() != newR.getPriority()){//if old and new are not the same
			view.getRequirementPanel().cmbPriority.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().cmbPriority.getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().cmbPriority.setBackground(Color.WHITE);//change to white background in case of reset

		//compare estimate efforts
		if (oldR.getEstimateEffort() != newR.getEstimateEffort() && view.getRequirementPanel().txtEstimate.isEnabled()){//if old and new are not the same and txtEstimate is enabled
			view.getRequirementPanel().txtEstimate.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtEstimate.getBackground().equals(MainView.getChangedColor()) && view.getRequirementPanel().txtEstimate.isEnabled())
				view.getRequirementPanel().txtEstimate.setBackground(Color.WHITE);//change to white background in case of reset

		//compare actual efforts
		if (oldR.getActualEffort() != newR.getActualEffort()){//if old and new are not the same
			view.getRequirementPanel().txtActual.setBackground(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtActual.getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().txtActual.setBackground(Color.WHITE);//change to white background in case of reset

		//set notes field to yellow
		if (!view.getRequirementPanel().getNotesView().getNoteString().equals("") && !view.getRequirementPanel().getNotesView().getNoteString().equals(null) ){//if old and new are not the same
			view.getRequirementPanel().getNotesView().setTxtNotesBackgroundColor(MainView.getChangedColor());
			flag = true;
		}
		else
			if(view.getRequirementPanel().getNotesView().getTextArea().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getNotesView().setTxtNotesBackgroundColor(Color.WHITE);


		//set Assigneeview to yellow
		if (view.getRequirementPanel().getAssigneeView().isButtonPressed()){//if old and new are not the same
			view.getRequirementPanel().getAssigneeView().setBackgroundColors(MainView.getChangedColor());
			flag = true;
		}

		/*if (!oldR.getAssignee().equals(newR.getAssignee())){//if old and new are not the same
			view.getRequirementPanel().getAv().setBackgroundColors(MainView.getChangedColor());
			flag = true;
		}*/
		else
			if(view.getRequirementPanel().getAssigneeView().getBackgroundColor().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getAssigneeView().setBackgroundColors(Color.WHITE);
		/*
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
		 */

		//compare notes lists
		if (notesDifference != 0){//if old and new are not the same
			view.getRequirementPanel().getNotesView().setTxtNotesSavedBackgroundColor(MainView.getChangedColor());
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().getNotesView().getSavedTextArea().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getNotesView().setTxtNotesSavedBackgroundColor(Color.WHITE);//change to white background in case of reset

		//AcceptanceTestFields
		if(!view.getRequirementPanel().getAcceptanceTestsView().getTitleTxt().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getTitleTxt().equals(null)) {
			int k = view.getRequirementPanel().getAcceptanceTestsView().doesTestExist(view.getRequirementPanel().getAcceptanceTestsView().getTitleTxt());
			if(k != -1) {
				if(!view.getRequirementPanel().getAcceptanceTestsView().getBodyTxt().equals(view.getRequirementPanel().getAcceptanceTestsView().getList().get(k).getBody())) {
					view.getRequirementPanel().getAcceptanceTestsView().setTxtBodyBackground(MainView.getChangedColor());
					flag = true;
				}
				else
					view.getRequirementPanel().getAcceptanceTestsView().setTxtBodyBackground(Color.WHITE);
				if(!view.getRequirementPanel().getAcceptanceTestsView().getStatusTxt().equals(view.getRequirementPanel().getAcceptanceTestsView().getList().get(k).getStatus())) {
					view.getRequirementPanel().getAcceptanceTestsView().setCmbStatusBackground(MainView.getChangedColor());
					flag = true;
				}
				else
					view.getRequirementPanel().getAcceptanceTestsView().setCmbStatusBackground(Color.WHITE);
			}
			else {//title does not exist yet
				view.getRequirementPanel().getAcceptanceTestsView().setTxtTitleBackground(MainView.getChangedColor());
				flag = true;
				if(!view.getRequirementPanel().getAcceptanceTestsView().getStatusTxt().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getStatusTxt().equals(null))
					view.getRequirementPanel().getAcceptanceTestsView().setCmbStatusBackground(MainView.getChangedColor());
				if(!view.getRequirementPanel().getAcceptanceTestsView().getBodyTxt().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getBodyTxt().equals(null))
					view.getRequirementPanel().getAcceptanceTestsView().setTxtBodyBackground(MainView.getChangedColor());	
			}
		}
		else {//title is blank
			if(view.getRequirementPanel().getAcceptanceTestsView().getTxtTitle().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getAcceptanceTestsView().setTxtTitleBackground(Color.WHITE);

			if(!view.getRequirementPanel().getAcceptanceTestsView().getBodyTxt().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getBodyTxt().equals(null)) {
				view.getRequirementPanel().getAcceptanceTestsView().setTxtBodyBackground(MainView.getChangedColor());
				flag = true;
			}
			else
				if(view.getRequirementPanel().getAcceptanceTestsView().getTxtBody().getBackground().equals(MainView.getChangedColor()))
					view.getRequirementPanel().getAcceptanceTestsView().setTxtBodyBackground(Color.WHITE);

			if(!view.getRequirementPanel().getAcceptanceTestsView().getStatusTxt().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getStatusTxt().equals(null)) {
				view.getRequirementPanel().getAcceptanceTestsView().setCmbStatusBackground(MainView.getChangedColor());
				flag = true;
			}
			else
				if(view.getRequirementPanel().getAcceptanceTestsView().getCmbStatus().getBackground().equals(MainView.getChangedColor()))
					view.getRequirementPanel().getAcceptanceTestsView().setCmbStatusBackground(Color.WHITE);
		}
		if(acceptDifference != 0) {
			view.getRequirementPanel().getAcceptanceTestsView().setListDisplayBackground(MainView.getChangedColor());
			flag = true;
		}
		else
			if(view.getRequirementPanel().getAcceptanceTestsView().getListDisplay().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getAcceptanceTestsView().setListDisplayBackground(MainView.getChangedColor());
		
		
		//Tasks
		if(!view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtName().getText().equals("") && !view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtName().getText().equals(null)) {
			view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtName().setBackground(MainView.getChangedColor());
			flag = true;
		}
		else
			if(view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtName().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtName().setBackground(Color.WHITE);
		
		if(!view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtDescription().getText().equals("") && !view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtDescription().equals(null)) {
			view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtDescription().setBackground(MainView.getChangedColor());
			flag = true;
		}
		else
			if(view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtDescription().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtDescription().setBackground(Color.WHITE);
		
		if(!view.getRequirementPanel().getTasksView().getNewTaskPanel().getCmbAssignee().getSelectedItem().equals("")) {
			view.getRequirementPanel().getTasksView().getNewTaskPanel().getCmbAssignee().setBackground(MainView.getChangedColor());
			flag = true;
		}
		else
			if(view.getRequirementPanel().getTasksView().getNewTaskPanel().getCmbAssignee().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getTasksView().getNewTaskPanel().getCmbAssignee().setBackground(Color.WHITE);
		
		if(!view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtEffort().getText().equals("0")) {
			view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtEffort().setBackground(MainView.getChangedColor());
			flag = true;
		}
		else
			if(view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtEffort().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getTasksView().getNewTaskPanel().getTxtEffort().setBackground(Color.WHITE);
		
		if(view.getRequirementPanel().getTasksView().getNewTaskPanel().getCmbStatus().getSelectedIndex() != 0) {
			view.getRequirementPanel().getTasksView().getNewTaskPanel().getCmbStatus().setBackground(MainView.getChangedColor());
			flag = true;
		}
		else
			if(view.getRequirementPanel().getTasksView().getNewTaskPanel().getCmbStatus().getBackground().equals(MainView.getChangedColor()))
				view.getRequirementPanel().getTasksView().getNewTaskPanel().getCmbStatus().setBackground(Color.WHITE);


		return flag;
	}

	/**
	 * Gets the requirement
	 * 
	 * @return the requirement
	 */
	public Requirement getRequirement() {
		return requirement;
	}
	
	/**
	 * Gets unedited requirement
	 * 
	 * @return the uneditedRequirement
	 */
	public Requirement getUneditedRequirement() {
		return uneditedRequirement;
	}
	
	/**
	 * @param uneditedRequirement: the uneditedRequirement to set
	 */
	public void setUneditedRequirement(Requirement uneditedRequirement) {
		this.uneditedRequirement = uneditedRequirement;
	}

	/**
	 * Sets requirement.
	 *
	 * @param req the new requirement
	 */
	public void setRequirement(Requirement req) {
		requirement = req;
	}

	/**
	 * Changes the Unedited Requirement to know the knew txt estimate when adding the child's estimate to the parent's estimate.
	 *
	 * @param estimateEffort the new txt estimate of unedited requirement
	 */
//	public void setTxtEstimateOfUneditedRequirement(int estimateEffort) {
//		this.uneditedRequirement.setEstimateEffort(estimateEffort);
//	}

}
