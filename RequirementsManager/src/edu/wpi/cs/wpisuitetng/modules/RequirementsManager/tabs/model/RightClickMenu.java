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
 * Tianyu Li
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.DeleteRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.DeleteRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.EditRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * The right click menu for the table¡£
 * 
 * @author Tianyu Li
 *
 */
public class RightClickMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	JMenuItem editItem, saveItem, deleteItem;
	Requirement requirement;

	/**
	 * The constructor
	 * 
	 * @param requirement the requirement in the table that been clicked
	 */
	public RightClickMenu(Requirement requirement) {
		this.requirement = requirement;
		RequirementView view = new RequirementView(requirement, Mode.EDIT, null);
		
		editItem = new JMenuItem("Edit");
		saveItem = new JMenuItem("Save");
		deleteItem = new JMenuItem("Delete");
		
		MainTabController controller = new MainTabController(new MainTabView());
		editItem.setAction(new EditRequirementAction(controller, requirement));
		saveItem.setAction(new SaveChangesAction(new SaveRequirementController(view)));
		deleteItem.setAction(new DeleteRequirementAction(new DeleteRequirementController(view)));
		
		add(editItem);
		add(saveItem);
		add(deleteItem);
	}
}
