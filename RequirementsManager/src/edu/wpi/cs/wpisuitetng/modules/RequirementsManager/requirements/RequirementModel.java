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
 * Christina Hannah
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;

/**
 * The Class RequirementModel is the model that holds requirements and their views.
 *
 * @author Chris Hanna
 */
public class RequirementModel {

	private Requirement requirement;
	private final Requirement uneditedRequirement;
	private RequirementView view;
	
	public RequirementModel(Requirement requirement, RequirementView view){
		this.requirement = requirement;
		this.uneditedRequirement = requirement;
		this.view = view;
	}
	
	
	/**
	 * Updates the panel.
	 * @param editMode
	 */
	protected void setUpPanel(Mode editMode){
		RequirementPanel panel = view.getRequirementPanel();
		
		if(!(requirement.getTitle().equals(null) || 
				requirement.getTitle().equals("")))
			panel.getTxtTitle().setText(requirement.getTitle());
		
		
		panel.getTxtDescription().setText(requirement.getDescription());
		panel.getTxtReleaseNumber().setText(requirement.getReleaseNumber());
		panel.getTxtEstimate().setText( String.valueOf(requirement.getEstimateEffort()) );
		panel.getTxtActual().setText( String.valueOf(requirement.getActualEffort()) );

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
			
			if (requirement.getIteration().toString().equals(panel.getKnownIterations()[i].toString()) ){
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

		/*
		if (model.getAssignee() != null) {
			txtAssignee.setText(model.getAssignee().toString().equals("[]")? "" : model.getAssignee().toString().replaceAll("\\[", "").replaceAll("\\]", ""));	
			//if (!(txtAssignee.getText().equals("")))
			//(!(txtAssignee.getText().equals(""))) {
			//requirement.setAssignee(new User("", txtAssignee.getText(), "", -1));
		}
		*/
		panel.getNotesView().setNotesList(requirement.getNotes());
		panel.getHv().setHistoryList(requirement.getHistory());
		panel.getAv().setAssigneeList(requirement.getAssignee());
		
		
		panel.setUpPanel();
	}
	
	/**
	 * Updates the model and the fields in the panel
	 * @param req
	 * @param editMode
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
		requirement.setEstimateEffort(req.getEstimateEffort());
		requirement.setActualEffort(req.getActualEffort());
		//req.updateNotes(this.getNotes());
		requirement.updateNotes(req.getNotes());
		//req.updateHistory(this.getHistory());
		requirement.updateHistory(req.getHistory());

		requirement.setParentRequirementId(requirement.getParentRequirementId());

		
		setUpPanel(editMode);
		
	}
	
	
	/**
	 * Checks to see if any changes have been made.
	 *
	 * @return true if changes has been made otherwise false
	 */
	public boolean isThereChanges(){
		Requirement oldR = this.uneditedRequirement;
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
		
		if (!this.view.getRequirementPanel().getNotesView().getNoteString().equals("") && !this.view.getRequirementPanel().getNotesView().getNoteString().equals(null) ){//if old and new are not the same
			return true;
		}
		
		if (this.view.getRequirementPanel().getAv().isButtonPressed()){//if old and new are not the same
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
	 * @return the requirement
	 */
	public Requirement getRequirement() {
		return requirement;
	}
	/**
	 * @return the uneditedRequirement
	 */
	public Requirement getUneditedRequirement() {
		return uneditedRequirement;
	}

	
}
