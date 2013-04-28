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
 *  Chris Hanna
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.IterationListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.ReqTreeModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.RetrieveRequirementControllerTree;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A Refresher refreshes the tree view.
 * 
 * @author Chris Hanna
 *
 */
public class Refresher {
	private static Refresher instance;

	/**
	 * Gets the single instance of Refresher.
	 *
	 * @return single instance of Refresher
	 */
	public static Refresher getInstance(){
		if (instance==null){
			instance = new Refresher(null,null,null);
		}
		return instance;
	}

	private ReqTreeModel tree;
	private RequirementListPanel table;

	private IterationListPanel iterationTable;


	private RetrieveAllRequirementsController reqController;
	private RetrieveAllIterationsController iterationController;

	private Iteration[] lastKnownIterations;
	/**
	 * Instantiates a new refresher.
	 *
	 * @param tree the tree
	 * @param table the table
	 * @param iterationTable the iteration table
	 */
	public Refresher(ReqTreeModel tree, RequirementListPanel table, IterationListPanel iterationTable)
	{
		instance = this;
		this.tree = tree;
		this.table = table;
		this.iterationTable = iterationTable;
		if (tree!=null)
			CurrentUserPermissions.updateCurrentUserPermissions();
	}


	private User[] lastKnownUsers = null;

	/**
	 * Gets the objects.
	 *
	 * @param observer the observer
	 * @param path the path
	 * @param id the id
	 */
	public void getObjects(RequestObserver observer, String path, String id){
		Request request;
		request = Network.getInstance().makeRequest(path + id, HttpMethod.GET);
		request.addObserver(observer);
		request.send();
	}

	/**
	 * Gets the users.
	 *
	 * @param observer the observer
	 * @return the users
	 */
	public User[] getUsers(RequestObserver observer){
		lastKnownUsers = null;
		RetrieveRequirementControllerTree<User> c = new RetrieveRequirementControllerTree<User>(
				observer, "core/user/", new IRetrieveRequirementController<User>(){

					@Override
					public void runWhenRecieved(String content) {
						GsonBuilder builder = new GsonBuilder();
						User[] users = builder.create().fromJson(content, User[].class);
						lastKnownUsers = users;
					}

					@Override
					public String getID() {
						return ""; //an ID of NOTHING will ask the controller for ALL of the objects
					}

				});
		c.retrieve();
		return lastKnownUsers;
	}


	/**
	 * Refresh all requirements depending on the given.
	 *
	 * @param mode the mode
	 */
	public void refreshRequirementsFromServer(RefresherMode mode)
	{
		reqController = new RetrieveAllRequirementsController(mode);
		reqController.refreshData();
	}

	/**
	 * Refresh all iterations depending on the given.
	 *
	 * @param view the view
	 */
	public void refreshIterationsFromServer(IterationView view)
	{
		iterationController = new RetrieveAllIterationsController(view);
		iterationController.refreshData();
	}

	/**
	 * Gets an array of all iterations.
	 * 
	 * @return an array of all iterations
	 */
	public Iteration[] getInstantIterations() 
	{
		if (lastKnownIterations == null){
			Iteration[] failedResponse = new Iteration[1];
			failedResponse[0] = Iteration.getBacklog();
			return failedResponse;
		}
		else
			return lastKnownIterations;
	}

	/**
	 * Refresh requirements depending on the given mode.
	 *
	 * @param reqArray the requirements array
	 * @param mode the mode
	 */
	public void refreshRequirements(Requirement[] reqArray, RefresherMode mode)
	{
		if (mode == RefresherMode.ALL || mode == RefresherMode.TABLE){

			table.addRequirements(reqArray);

		}
		if (mode == RefresherMode.ALL || mode == RefresherMode.TREE)
			tree.fillTree(reqArray);
	}

	/**
	 * Refresh iterations depending on the given mode.
	 *
	 * @param iterations the iterations
	 * @param view the view
	 */
	public void refreshIterations(Iteration[] iterations, IterationView view) {
		setLastKnownIterations(iterations);
		iterationTable.addIterations(iterations);
		tree.refreshTree();
	}

	/**
	 * Set last known iterations.
	 *
	 * @param iterations the new last known iterations
	 */
	public void setLastKnownIterations(Iteration[] iterations) {
		if (iterations != null)
		{
			ArrayList<Iteration> knownIterations = new ArrayList<Iteration>();
			knownIterations.add(Iteration.getBacklog());
			for (int i = 0; i < iterations.length; i ++){
				knownIterations.add(iterations[i]);
			}

			lastKnownIterations = new Iteration[knownIterations.size()];
			for (int i = 0 ; i < knownIterations.size() ; i ++)
			{
				lastKnownIterations[i] = knownIterations.get(i);
			}
		}
	}
	
	/**
	 * Get the tree model
	 * 
	 * @return
	 */
	public ReqTreeModel getTreeModel(){
		return tree;
	}

	/**
	 * @return the table
	 */
	public RequirementListPanel getTable() {
		return table;
	}
}