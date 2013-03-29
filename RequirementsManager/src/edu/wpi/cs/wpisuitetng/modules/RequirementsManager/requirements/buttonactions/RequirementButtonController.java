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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.buttonactions;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus.DELETED;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.buttonactions.ButtonActions.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.CreateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.UpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller for the Requirements buttons (save, cancel, delete)
 *
 * @author Chris Dunkers
 *
 * @version Mar 28, 2013
 *
 */
public class RequirementButtonController implements ActionListener {
	
	/** The view object containing the request fields */
	protected RequirementView view;

	/**
	 * Construct a new handler for the given view
	 * @param view the view containing the request fields
	 */
	public RequirementButtonController(RequirementView view) {
		this.view = view;
	}

	/**
	 * Save the view's Iteration model to the server (asynchronous).
	 */
	public void actionPerformed(ActionEvent ae) {
		JButton sourceButton = (JButton) ae.getSource();
		if(sourceButton.getText().equals("Save") || sourceButton.getText().equals("Delete")){
			final RequirementPanel panel = (RequirementPanel) view.getRequirementPanel();
			final RequestObserver requestObserver = (panel.getEditMode() == Mode.CREATE) ? new CreateRequirementRequestObserver(view) : new UpdateRequirementRequestObserver(view);
			Request request;
			//panel.getParent().setInputEnabled(false);
			request = Network.getInstance().makeRequest("requirementsmanager/requirement", (panel.getEditMode() == Mode.CREATE) ? HttpMethod.PUT : HttpMethod.POST);
			if(panel.checkRequiredFields() > 0){} 
			else {
				Requirement requirement = panel.getEditedModel();
				if(sourceButton.getText().equals("Delete")){
					requirement.setStatus(DELETED);
				}
				request.setBody(requirement.toJSON());
				request.addObserver(requestObserver);
				request.send();
				//close tab
				this.view.getTab().getView().removeTabAt(this.view.getTab().getThisIndex());
				System.out.println("DELETE REQUIREMENT");
			}
		} else {
			this.view.getTab().getView().removeTabAt(this.view.getTab().getThisIndex());
		}
	} 

}
