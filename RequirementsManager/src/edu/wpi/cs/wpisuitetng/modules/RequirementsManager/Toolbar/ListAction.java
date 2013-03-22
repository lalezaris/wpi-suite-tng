/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;

/**
 * @author lty
 *
 */
public class ListAction extends AbstractAction {

	private final MainTabController controller;
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		controller.addListTab();
		
	}

	
	public ListAction(MainTabController controller) {
		super("List");
		//TODO: Remove this debug message
		System.out.println("Entered ListAction");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	}
	
}
