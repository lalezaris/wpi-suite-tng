/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsController;

/**
 * Insert Description Here
 *
 * @author lty
 *
 * @version Mar 21, 2013
 *
 */
public class RefreshAction extends AbstractAction {

	private final RetrieveAllRequirementsController controller;
	
	public RefreshAction(RetrieveAllRequirementsController controller){
		super("Refresh");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		controller.refreshData();
	}

}
