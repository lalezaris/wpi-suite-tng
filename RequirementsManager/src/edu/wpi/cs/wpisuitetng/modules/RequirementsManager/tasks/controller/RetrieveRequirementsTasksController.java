/**
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Sam Abradi
 * Evan Polekoff
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.observers.RetrieveRequirementTasksObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Take a response to a request, and pass it to the controller
 * that it was instantiated with.
 *
 * @author Sam Abradi
 * @author Evan Polkoff
 *
 * @version Apr 18, 2013
 *
 */
public class RetrieveRequirementsTasksController {
	TasksView view;

	public RetrieveRequirementsTasksController(TasksView view){
		this.view = view;
	}
	/**
	 * Retrieve iterations from view
	 */
	public void retrieve(int requirementID){
		final RequestObserver requestObserver = new RetrieveRequirementTasksObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("tasksmanager/task/" + requirementID, HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * Changes iteration combo box for view.
	 *
	 * @param iterations the iterations to added to the combo box.
	 */
	public void received(Task[] tasks){
		view.setTaskArray(tasks);
	}

	/**
	 * Print an error message.
	 * @param message The error message.
	 */
	public void errorReceivingData(String message){
		System.out.println(message);
	}

}
