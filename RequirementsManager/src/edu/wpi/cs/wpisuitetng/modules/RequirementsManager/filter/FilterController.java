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
 *  Chris
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.AddAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.DisableAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.EnableAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.RemoveAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button.VisibleAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleTargetException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;

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
	/**
	 * create a new controller. Set up the buttons of the panel
	 * 
	 */
	public FilterController(RequirementListPanel listPanel) {
		
		
		this.listPanel = listPanel;
		
		this.panel = new FilterPanel(this);
		this.model = new FilterModel();

		this.panel.getAddButton().setAction( new AddAction(this) );
		this.panel.getRemoveButton().setAction( new RemoveAction(this));
		this.panel.getDisableButton().setAction( new DisableAction(this));
		this.panel.getEnableButton().setAction( new EnableAction(this));
		this.panel.getShowButton().setAction( new VisibleAction(this));
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
	
	public RequirementListPanel getListPanel(){
		return this.listPanel;
	}
}
