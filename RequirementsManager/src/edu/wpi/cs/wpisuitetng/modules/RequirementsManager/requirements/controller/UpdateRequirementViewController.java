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
 *  Chris Hanna
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.UpdateRequirementViewObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * live updates the views of requirements after a 'save' button has been clicked within another requirement view
 *
 * @author Chris Hanna
 *
 * @version Apr 26, 2013
 *
 */
public class UpdateRequirementViewController {

	
	public static void updateAllOpenRequirementViews(){

		System.out.print("Printing View Req Names");
		for (int i = 0 ; i < MainTabController.getController().getReqViewList().size() ;i ++){
			System.out.println("VIEW REQ NAME:" + MainTabController.getController().getReqViewList().get(i).getReqModel().getRequirement().getTitle());
			UpdateRequirementViewController controller = new UpdateRequirementViewController(MainTabController.getController().getReqViewList().get(i));
			controller.retrieve();
		}
		
	}

	RequirementView view;
	Mode mode;
	/**
	 * Make an updateReqViewController
	 * 
	 * @param view
	 */
	public UpdateRequirementViewController(RequirementView view){
		this.view = view;
		this.mode = view.getMode();
	}
	
	
	/**
	 * Send out update request
	 * 
	 */
	private void retrieve() {
		UpdateRequirementViewObserver observer = new UpdateRequirementViewObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement/" + this.view.getReqModel().getRequirement().getId(), HttpMethod.GET);
		request.addObserver(observer);
		request.send();
	}
	
	public void receive(Requirement req){
		this.view.getReqModel().update(req, this.mode);
	}
	
}
