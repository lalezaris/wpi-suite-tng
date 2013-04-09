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
 * @author Evan Polekoff
 *
 * @version Apr 9, 2013
 *
 */


public class ViewChartsAction extends AbstractAction {
	private final MainTabController controller;
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.addBarChartTab();
	}

	/**
	 * Default constructor
	 * 
	 * @param controller
	 */
	public ViewChartsAction(MainTabController controller) {
		super("View Bar Chart");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	}
	
}