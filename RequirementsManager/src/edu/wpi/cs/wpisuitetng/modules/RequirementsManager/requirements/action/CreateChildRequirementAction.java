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
 *  Lauren Kahn
 *  Tushar Narayan
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CreateChildRequirementController;

/**
 * Creates child requirement action.
 * 
 * @author Tushar Narayan
 *
 * @version Mar 31, 2013
 *
 */
@SuppressWarnings("serial")
public class CreateChildRequirementAction extends AbstractAction{

	private final CreateChildRequirementController controller;

	/**
	 * Default constructor for CreateChildRequirementController.
	 * 
	 * @param controller controller to perform the action
	 */
	public CreateChildRequirementAction(CreateChildRequirementController controller){
		super("Create a child Requirement");
		this.controller = controller;
	}

	/**
	 * Action performed.
	 *
	 * @param arg0 the arg0
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		controller.viewChild();
	}
}
