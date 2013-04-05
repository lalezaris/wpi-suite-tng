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

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.CurrentUserPermissionsObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.ReqTreeModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.RetrieveRequirementControllerTree;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer.RetrieveRequirementObserverTree;
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
	
	public static Refresher getInstance(){
		System.out.println("Fetching Refresher: " + (instance != null));
		return instance;
	}
	
	private ReqTreeModel tree;
	private RequirementListPanel table;
	
	private RetrieveAllRequirementsController reqController;
	private RetrieveAllIterationsController iterationController;
	
	private Iteration[] lastKnownIterations;
	private boolean iterationsSet;
	
	public Refresher(ReqTreeModel tree, RequirementListPanel table)
	{
		instance = this;
		this.tree = tree;
		this.table = table;
		this.iterationsSet = false;
		CurrentUserPermissions.updateCurrentUserPermissions();
	}
	
	
	private User[] lastKnownUsers = null;
	
	public void getObjects(RequestObserver observer, String path, String id){
		Request request;
		request = Network.getInstance().makeRequest(path + id, HttpMethod.GET);
		request.addObserver(observer);
		request.send();
	}

	
	public User[] getUsers(RequestObserver observer){
		System.out.println("GETTING USERS");
		User[] all = null;
		lastKnownUsers = null;
		RetrieveRequirementControllerTree<User> c = new RetrieveRequirementControllerTree<User>(
				observer, "core/user/", new IRetrieveRequirementController<User>(){

					@Override
					public void runWhenRecieved(String content) {
						//System.out.println("GETTING USERS RESPONSE");
						GsonBuilder builder = new GsonBuilder();
						//System.out.println(content);
						
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
	 * refresh all requirements depending on the given
	 * @param mode
	 */
	public void refreshRequirementsFromServer(RefresherMode mode)
	{
		this.reqController = new RetrieveAllRequirementsController(mode);
		this.reqController.refreshData();
	}
	
	/**
	 * refresh all iterations depending on the given
	 * @param view
	 */
	public void refreshIterationsFromServer(IterationView view)
	{
		this.iterationController = new RetrieveAllIterationsController(view);
		this.iterationController.refreshData();
	}
	
	/**
	 * @return iteration list
	 */
	public Iteration[] getInstantIterations() 
	{
		if (this.lastKnownIterations == null){
			System.out.println("Server failed to retrieve iterations in time");
			Iteration[] failedResponse = new Iteration[1];
			failedResponse[0] = Iteration.getBacklog();
			return failedResponse;
		}
		else
			return this.lastKnownIterations;
	}
	
	/**
	 * Refresh requirements depending on the given mode
	 * 
	 * @param reqArray
	 * @param mode
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
	 * Refresh iterations depending on the given mode
	 * 
	 * @param iterations
	 * @param view
	 */
	public void refreshIterations(Iteration[] iterations, IterationView view) {
		if (view != null)
		{
			//do nothing
		}
		for (int i = 0 ; i < iterations.length; i ++)
		{
			System.out.println("server Iteration: " + iterations[i].getIterationName());
		}
		setLastKnownIterations(iterations);
		this.iterationsSet = true;
		tree.refreshTree();
	}

	/**
	 * Set last known iterations
	 * 
	 * @param iterations
	 */
	public void setLastKnownIterations(Iteration[] iterations) {
		System.out.println("Setting Iterations");
		
		if (iterations == null)
		{
			System.out.println("No server side iterations found");
		}
		else {
			ArrayList<Iteration> knownIterations = new ArrayList<Iteration>();
			knownIterations.add(Iteration.getBacklog());
			for (int i = 0; i < iterations.length; i ++){
				System.out.println("Found:" + iterations[i] + " with id = " + iterations[i].getId());
				knownIterations.add(iterations[i]);
			}
			
			this.lastKnownIterations = new Iteration[knownIterations.size()];
			for (int i = 0 ; i < knownIterations.size() ; i ++)
			{
				System.out.println("Added:" + knownIterations.get(i));
				this.lastKnownIterations[i] = knownIterations.get(i);
			}
		}
	}
}
