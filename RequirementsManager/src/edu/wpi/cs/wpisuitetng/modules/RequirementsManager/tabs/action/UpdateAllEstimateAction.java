package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.BatchRequirementEditController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.UpdateAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.UpdateAllEstimateController;

/**
 * Action for update all modified estimate in list view
 *
 * @author Tianyu Li
 *
 * @version Apr 10, 2013
 *
 */
public class UpdateAllEstimateAction extends AbstractAction {

private final UpdateAllRequirementsController controller;
	
	/**
	 * Create a SaveChangesAction
	 * @param controller When the action is performed, controller.save will be called
	 */
	public UpdateAllEstimateAction(UpdateAllRequirementsController controller) {
		super("Update");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	}
	
	/**
	 * Calls the controller to save the action.
	 * 
	 * @param arg0 The action event to perform
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
			controller.update();
	}

}
