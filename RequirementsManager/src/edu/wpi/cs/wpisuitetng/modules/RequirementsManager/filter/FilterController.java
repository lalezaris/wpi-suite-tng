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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.AddAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.DisableAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.EnableAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.RemoveAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.VisibleAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.controller.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.controller.RetrieveAllUsersController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleTargetException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Insert Description Here
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
public class FilterController{

	
	protected FilterPanel panel;
	protected FilterModel model;
	protected RequirementListPanel listPanel;
	protected RetrieveAllIterationsController iterationController;
	protected RetrieveAllUsersController userController;
	/**
	 * create a new controller. Set up the buttons of the panel
	 * 
	 */
	public FilterController(RequirementListPanel listPanel) {
		
		
		this.listPanel = listPanel;
		
		panel = new FilterPanel(this);
		model = new FilterModel();

		panel.getAddButton().setAction( new AddAction(this) );
		panel.getRemoveButton().setAction( new RemoveAction(this));
		panel.getDisableButton().setAction( new DisableAction(this));
		panel.getEnableButton().setAction( new EnableAction(this));
		panel.getShowButton().setAction( new VisibleAction(this));
		
		iterationController = new RetrieveAllIterationsController(this);
		iterationController.retrieve();
		
		userController = new RetrieveAllUsersController(this);
		userController.retrieve();
		
	}

	public FilterPanel getPanel() {
		return this.panel;
	}
	public FilterModel getModel(){
		return this.model;
	}
	
	/**
	 * filter the requirements table. 
	 * 
	 * @return The filtered the requirements
	 */
	public Requirement[] setFilteredInTable(){
		
		Requirement[] all = this.listPanel.getContent();
		Requirement[] filtered = null;
		
		boolean sentIterationsRequest = false;
		for (int i = 0 ; i < panel.getRules().size(); i ++){
			if ( !sentIterationsRequest &&
					((String)panel.getRules().get(i).getField().getSelectedItem()).equals("iteration")){
				sentIterationsRequest = true;
				iterationController.retrieve();
				break;
			}
		}
		
		boolean sentUsersRequest = false;
		for (int i = 0 ; i < panel.getRules().size(); i ++){
			if ( !sentUsersRequest &&
					((String)panel.getRules().get(i).getField().getSelectedItem()).equals("user")){
				sentUsersRequest = true;
				userController.retrieve();
				break;
			}
		}
		
		getModel().setModelFromPanel(panel);
		try {
			filtered = getModel().getFilter().getFilteredObjects(all);
		} catch (RuleTargetException e) {
			System.out.println("har har har, you have enraged the filter God!!!");
			e.printStackTrace();
		}
		this.listPanel.filterRequirements(filtered);
		
		
		return filtered;
	}
	
	public void setIterations(Iteration[] iterations){
		this.model.setIterations(iterations);
		this.panel.setIterations(iterations);
	}
	
	public void setUsers(User[] users){
		this.model.setUsers(users);
		this.panel.setUsers(users);
	}
	
	public RequirementListPanel getListPanel(){
		return this.listPanel;
	}
}