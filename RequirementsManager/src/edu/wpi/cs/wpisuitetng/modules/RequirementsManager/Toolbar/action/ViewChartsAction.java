package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Action to edit user permissions
 * 
 * @author Ned Shelton
 *
 * @version Apr 7, 2013
 *
 */

// 				I just copied this from EditUserPermissionsAction and ListAction, but unsurprisingly, that didn't work
public class ViewChartsAction extends AbstractAction {
	private final MainTabController controller;
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.addListRequirementTab();
	}

	/**
	 * Default constructor
	 * 
	 * @param controller
	 */
	public ListAction(MainTabController controller) {
		super("SHOW ME THE CHARTS");
		
		System.out.println("Entered ListAction");
		this.controller = controller;
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	}
	
}