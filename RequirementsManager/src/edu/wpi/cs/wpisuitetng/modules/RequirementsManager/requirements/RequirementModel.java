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
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import java.awt.Color;
import java.util.ArrayList;

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
		ArrayList<Note> notesList = new ArrayList<Note>();
		for(int i = 0; i < requirement.getNotes().size(); i++){
			notesList.add(requirement.getNotes().get(i));
		}
		panel.getNotesView().setNotesList(notesList);
//		panel.getHistoryView().setHistoryList(requirement.getHistory());
//		panel.getAssigneeView().setAssigneeList(requirement.getAssignee());
		
		
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
		
		if (oldR.getNotes().size() != newR.getNotes().size()){//if old and new are not the same
			return true;
		}
		
		if (this.view.getRequirementPanel().getAssigneeView().isButtonPressed()){//if old and new are not the same
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
		
		if(!this.view.getRequirementPanel().getAcceptanceTestsView().getBodyField().getText().trim().equals("") && !this.view.getRequirementPanel().getAcceptanceTestsView().getBodyField().getText().trim().equals(null)){
			System.out.println("Title IN ACCEPTANCE TEST");
			return true;
		}
		
		if(!this.view.getRequirementPanel().getAcceptanceTestsView().getTitleField().getText().equals("") && !this.view.getRequirementPanel().getAcceptanceTestsView().getTitleField().getText().equals(null)){
			System.out.println("Body IN ACCEPTANCE TEST");
			return true;
		}
		
//		if(this.view.getRequirementPanel().getAcceptanceTestsView().getTitleField().getText().equals("") || this.view.getRequirementPanel().getAcceptanceTestsView().getTitleField().getText().equals(null)){
//			System.out.println("Body IN ACCEPTANCE TEST");
//			return true;
//		}
		
		if(oldR.getAcceptanceTests().size() != this.view.getRequirementPanel().getAcceptanceTestsView().getList().size()){
			System.out.println("Added IN ACCEPTANCE TEST");
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
		Requirement oldR = this.uneditedRequirement;
		Requirement newR = view.getRequirementPanel().getEditedModel();
		boolean flag = false; //gets set to true when something has changed; in order to iterate through everything
		int notesDifference = (newR.getNotes().size() - oldR.getNotes().size());
		int acceptDifference = (newR.getAcceptanceTests().size() - oldR.getAcceptanceTests().size());
		
		//compare titles
		if (oldR.getTitle().compareTo(newR.getTitle()) != 0){//if old and new are not the same
			//change to yellow background
			view.getRequirementPanel().txtTitle.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtTitle.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().txtTitle.setBackground(Color.WHITE);//change to white background in case of reset
		
		//compare Release Numbers
		if (!oldR.getReleaseNumber().equals(newR.getReleaseNumber())){//if old and new are not the same
			view.getRequirementPanel().txtReleaseNumber.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtReleaseNumber.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().txtReleaseNumber.setBackground(Color.WHITE);//change to white background in case of reset
		
		//compare type
		if (oldR.getType().compareTo(newR.getType()) != 0){//if old and new are not the same
			view.getRequirementPanel().cmbType.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().cmbType.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().cmbType.setBackground(Color.WHITE);//change to white background in case of reset
		
		//compare Iterations
		if (oldR.getIterationId()!=(newR.getIterationId())){//if old and new are not the same
			view.getRequirementPanel().cmbIteration.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().cmbIteration.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().cmbIteration.setBackground(Color.WHITE);//change to white background in case of reset
				
		//compare Descriptions
		if (oldR.getDescription().compareTo(newR.getDescription()) != 0){//if old and new are not the same
			view.getRequirementPanel().txtDescription.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtDescription.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().txtDescription.setBackground(Color.WHITE);//change to white background in case of reset
		
		//compare Statuses
		if (oldR.getStatus() != newR.getStatus()){//if old and new are not the same
			view.getRequirementPanel().cmbStatus.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().cmbStatus.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().cmbStatus.setBackground(Color.WHITE);//change to white background in case of reset

		//compare Priorities
		if (oldR.getPriority() != newR.getPriority()){//if old and new are not the same
			view.getRequirementPanel().cmbPriority.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().cmbPriority.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().cmbPriority.setBackground(Color.WHITE);//change to white background in case of reset

		//compare estimate efforts
		if (oldR.getEstimateEffort() != newR.getEstimateEffort()){//if old and new are not the same
			view.getRequirementPanel().txtEstimate.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtEstimate.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().txtEstimate.setBackground(Color.WHITE);//change to white background in case of reset

		//compare actual efforts
		if (oldR.getActualEffort() != newR.getActualEffort()){//if old and new are not the same
			view.getRequirementPanel().txtActual.setBackground(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().txtActual.getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().txtActual.setBackground(Color.WHITE);//change to white background in case of reset
		
		//set notes field to yellow
		if (!this.view.getRequirementPanel().getNotesView().getNoteString().equals("") && !this.view.getRequirementPanel().getNotesView().getNoteString().equals(null) ){//if old and new are not the same
			view.getRequirementPanel().getNotesView().setTxtNotesBackgroundColor(Color.YELLOW);
			flag = true;
		}
		else
			if(this.view.getRequirementPanel().getNotesView().getTextArea().getBackground().equals(Color.YELLOW))
					view.getRequirementPanel().getNotesView().setTxtNotesBackgroundColor(Color.WHITE);
		
		
		//set Assigneeview to yellow
		if (this.view.getRequirementPanel().getAssigneeView().isButtonPressed()){//if old and new are not the same
			view.getRequirementPanel().getAssigneeView().setBackgroundColors(Color.YELLOW);
			flag = true;
		}
		
		/*if (!oldR.getAssignee().equals(newR.getAssignee())){//if old and new are not the same
			view.getRequirementPanel().getAv().setBackgroundColors(Color.YELLOW);
			flag = true;
		}*/
		else
			if(this.view.getRequirementPanel().getAssigneeView().getBackgroundColor().equals(Color.YELLOW))
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
			view.getRequirementPanel().getNotesView().setTxtNotesSavedBackgroundColor(Color.YELLOW);
			flag = true;
		}
		else //no change
			if(view.getRequirementPanel().getNotesView().getSavedTextArea().getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().getNotesView().setTxtNotesSavedBackgroundColor(Color.WHITE);//change to white background in case of reset
		
		//AcceptanceTestFields
		if(!view.getRequirementPanel().getAcceptanceTestsView().getTitleTxt().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getTitleTxt().equals(null)) {
			view.getRequirementPanel().getAcceptanceTestsView().setTxtTitleBackground(Color.YELLOW);
			flag = true;
		}
		else
			if(view.getRequirementPanel().getAcceptanceTestsView().getTxtTitle().getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().getAcceptanceTestsView().setTxtTitleBackground(Color.WHITE);
			
		if(!view.getRequirementPanel().getAcceptanceTestsView().getBodyTxt().equals("") && !view.getRequirementPanel().getAcceptanceTestsView().getBodyTxt().equals(null)) {
			view.getRequirementPanel().getAcceptanceTestsView().setTxtBodyBackground(Color.YELLOW);
			flag = true;
		}
		else
			if(view.getRequirementPanel().getAcceptanceTestsView().getTxtBody().getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().getAcceptanceTestsView().setTxtBodyBackground(Color.WHITE);
			
		if(!view.getRequirementPanel().getAcceptanceTestsView().getStatusTxt().equals("Blank") && !view.getRequirementPanel().getAcceptanceTestsView().getStatusTxt().equals(null)) {
			view.getRequirementPanel().getAcceptanceTestsView().setCmbStatusBackground(Color.YELLOW);
			flag = true;
		}
		else
			if(view.getRequirementPanel().getAcceptanceTestsView().getCmbStatus().getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().getAcceptanceTestsView().setCmbStatusBackground(Color.WHITE);
			
		if(acceptDifference != 0) {
			view.getRequirementPanel().getAcceptanceTestsView().setListDisplayBackground(Color.YELLOW);
			flag = true;
		}
		else
			if(view.getRequirementPanel().getAcceptanceTestsView().getListDisplay().getBackground().equals(Color.YELLOW))
				view.getRequirementPanel().getAcceptanceTestsView().setListDisplayBackground(Color.YELLOW);
		
		return flag;
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
