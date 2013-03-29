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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

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
public class IterationButtonController implements ActionListener {
	
	/** The view object containing the request fields */
	protected IterationView view;

	/**
	 * Construct a new handler for the given view
	 * @param view the view containing the request fields
	 */
	public IterationButtonController(IterationView view) {
		this.view = view;
	}

	/**
	 * Save the view's Iteration model to the server (asynchronous).
	 */
	public void actionPerformed(ActionEvent ae) {
		JButton sourceButton = (JButton) ae.getSource();
		if(sourceButton.getText().equals("Save") || sourceButton.getText().equals("Delete")){
			final IterationPanel panel = (IterationPanel) view.getIterationPanel();
			final RequestObserver requestObserver = /* (panel.getEditMode() == Mode.CREATE) ? new CreateIterationRequestObserver(view) : */ new SaveIterationRequestObserver(view);
			Request request;
			//panel.getParent().setInputEnabled(false);
			request = Network.getInstance().makeRequest("iterationsmanager/iteration", /* (panel.getEditMode() == Mode.CREATE) ? */ HttpMethod.PUT /* HttpMethod.POST */);
			if(panel.checkRequiredFields() > 0){
			} 
			else {
				request.setBody(panel.getEditedModel().toJSON());
				request.addObserver(requestObserver);
				request.send();
				//close tab
				this.view.getTab().getView().removeTabAt(this.view.getTab().getThisIndex());
				
			}
		} else {
			this.view.getTab().getView().removeTabAt(this.view.getTab().getThisIndex());
		}
	} 

}

