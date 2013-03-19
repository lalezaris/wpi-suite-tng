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
 *  Arica Liu
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;

/**
 * Action that calls {@link MainTabController#addNewRequirementTab()}, default mnemonic key is N.
 * 
 * @author Adapted from Defect Tracker
 * @author Arica liu
 * 
 * @version March 18, 2013
 */
@SuppressWarnings("serial")
public class NewRequirementAction extends AbstractAction {

	private final MainTabController controller;
	
	/**
	 * Create a NewRequirementAction
	 * @param controller When the action is performed, controller.addNewRequirementTab() is called
	 */
	public NewRequirementAction(MainTabController controller) {
		super("New");
		//TODO: Remove this debug message
		System.out.println("Entered NewRequirementAction");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO: Remove this debug message
		System.out.println("action performed: should create new requirement tab");
		controller.addCreateRequirementTab();
	}
}