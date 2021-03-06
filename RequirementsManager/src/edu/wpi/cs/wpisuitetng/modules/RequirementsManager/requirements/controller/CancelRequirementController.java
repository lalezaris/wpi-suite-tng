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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * Controller that handles the cancel requirement action.
 *
 * @author CDUNKERS
 *
 * @version Mar 27, 2013
 *
 */
public class CancelRequirementController {

	/** The view object containing the request fields */
	protected RequirementView view;

	/**
	 * Construct a new handler for the given view.
	 * 
	 * @param view the view containing the request fields
	 */
	public CancelRequirementController(RequirementView view) {
		this.view = view;
	}

	/**
	 * Save the view's Iteration model to the server (asynchronous).
	 */
	public void cancel() {
		this.view.getTab().getView().removeTabAt(this.view.getTab().getThisIndex());
	} 
}
