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
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;

/**
 * Model for the Iteration to be held
 *
 * @author Chris Dunkers
 *
 * @version Apr 13, 2013
 *
 */
public class IterationModel {

	protected final Iteration uneditedModel;
	protected Iteration editedModel;
	protected boolean gotIteration;
	protected boolean gotAllIterations;
	IterationView iterationView;
	
	public IterationModel(Iteration iteration, IterationView iterationView){
		this.editedModel = iteration;
		this.uneditedModel = iteration;
		this.iterationView = iterationView;
		this.gotIteration = false;
		this.gotAllIterations = false;		
	}
	
	/**
	 * Updates the IterationPanel's model to contain the values of the given
	 * Iteration and sets the IterationPanel's editMode to {@link Mode#EDIT}.
	 * 
	 * @param iteration	The Iteration which contains the new values for the model.
	 */
	public void updateModel(Iteration iteration) {
		updateModel(iteration, Mode.EDIT);
	}

	/**
	 * Updates the RequirementPanel's model to contain the values of
	 * the given Requirement.
	 *
	 * @param iteration the iteration
	 * @param mode The new editMode.
	 */
	protected void updateModel(Iteration iteration, Mode mode) {
		((IterationPanel) this.iterationView.getIterationPanel()).setEditMode(mode);

		editedModel.setIterationName(iteration.getIterationName());
		editedModel.setStartDate(iteration.getStartDate());
		editedModel.setEndDate(iteration.getEndDate());
		editedModel.setRequirements(iteration.getRequirements());
		editedModel.setStatus(iteration.getStatus());

		updateFields();
		((IterationPanel) this.iterationView.getIterationPanel()).revalidate();
		((IterationPanel) this.iterationView.getIterationPanel()).getPanelLayout().invalidateLayout(((IterationPanel) this.iterationView.getIterationPanel()));
		((IterationPanel) this.iterationView.getIterationPanel()).getPanelLayout().layoutContainer(((IterationPanel) this.iterationView.getIterationPanel()));
		((IterationPanel) this.iterationView.getIterationPanel()).repaint();
		this.iterationView.refreshScrollPane();
	}

	/**
	 * Update fields in the iteration Panel
	 */
	protected void updateFields() {
//		if((!(uneditedModel.getIterationName().equals(null)) && (!(uneditedModel.getIterationName().equals("")))))
//			((IterationPanel) this.iterationView.getIterationPanel()).getTxtIterationName().setText(uneditedModel.getIterationName());
//
//		((IterationPanel) this.iterationView.getIterationPanel()).getTxtStartDate().setText(uneditedModel.getStartDate().toString());
//		((IterationPanel) this.iterationView.getIterationPanel()).getTxtEndDate().setText(uneditedModel.getEndDate().toString());
		
		if (((IterationPanel) this.iterationView.getIterationPanel()).getEditMode() == Mode.EDIT) {
			((IterationPanel) this.iterationView.getIterationPanel()).getTxtIterationName().setText(editedModel.getIterationName().toString());
			((IterationPanel) this.iterationView.getIterationPanel()).getTxtStartDate().setText(DateToString(editedModel.getStartDate()));
			((IterationPanel) this.iterationView.getIterationPanel()).getTxtEndDate().setText(DateToString(editedModel.getEndDate()));
		}
	}
	
	/**
	 * Return the model object represented by this view's fields.
	 * 
	 * @return the model represented by this view
	 */
	public Iteration getEditedModel() {
		Iteration iteration;
		if(Mode.EDIT == ((IterationPanel) this.iterationView.getIterationPanel()).getEditMode()){
			iteration = editedModel;
		}
		else{
			iteration = new Iteration("", null, null);
		}
		iteration.setIterationName(((IterationPanel) this.iterationView.getIterationPanel()).getTxtIterationName().getText()); 
		iteration.setStartDate(StringToDate(((IterationPanel) this.iterationView.getIterationPanel()).getTxtStartDate().getText()));
		iteration.setEndDate(StringToDate(((IterationPanel) this.iterationView.getIterationPanel()).getTxtEndDate().getText()));

		return iteration;
	}
	
	/**
	 * Convert a Date to a formatted String. 
	 * 
	 * @param aDate The Date to be converted.
	 * @return The resulting String.
	 */
	private String DateToString(Date aDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String convertedDate = null;
		convertedDate = dateFormat.format(aDate);

		return convertedDate;
	}
	
	/**
	 * Convert a String to Date. 
	 * 
	 * @param aDate The string to be converted.
	 * @return The resulting Date.
	 */
	private Date StringToDate(String aDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(aDate);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return convertedDate;
	}
	
	/**
	 * Return the unedited Model (an Iteration).
	 * 
	 * @return the uneditedModel
	 */
	public Iteration getUneditedModel() {
		return uneditedModel;
	}

	/**
	 * @return the gotIteration
	 */
	public boolean isGotIteration() {
		return gotIteration;
	}

	/**
	 * @param gotIteration: the gotIteration to set
	 */
	public void setGotIteration(boolean gotIteration) {
		this.gotIteration = gotIteration;
	}

	/**
	 * @return the gotAllIterations
	 */
	public boolean isGotAllIterations() {
		return gotAllIterations;
	}

	/**
	 * @param gotAllIterations: the gotAllIterations to set
	 */
	public void setGotAllIterations(boolean gotAllIterations) {
		this.gotAllIterations = gotAllIterations;
	}
	
	
	
}
