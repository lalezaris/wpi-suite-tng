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
 *  Sam Lalezari
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.RetrieveAllIterationsControllerTree;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * The Model for the Requirement Tree
 *
 * @author Sam Lalezari
 * CHris Hanna changed this on Mar 27,2013
 * @version Mar 19, 2013
 *
 */
@SuppressWarnings({"serial"})
public class ReqTreeModel extends DefaultTreeModel {
	
	DefaultMutableTreeNode root;
	RetrieveAllRequirementsController controller;
	RetrieveAllIterationsControllerTree itController;
	LinkedList<ReqTreeNode> nodes = new LinkedList<ReqTreeNode>();
	int count;
	int id;
	Iteration[] iterations;
	Requirement[] requirements;
	
	/**
	 * Class constructor
	 * 
	 * @param root the root of the requirement tree
	 * @param tree the tree of requirements
	 */
	public ReqTreeModel(MutableTreeNode root) {
		super(root);
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		controller = new RetrieveAllRequirementsController(RefresherMode.TREE);
		itController = new RetrieveAllIterationsControllerTree(this);
		
		this.root = (DefaultMutableTreeNode) root;
		controller.refreshData();
	}

	/**
	 * Fills the tree with requirements given in the array. 
	 * Clears the existing tree first, so that there are no duplicates
	 * 
	 * @param reqs
	 */
	public void fillTree(Requirement[] reqs){
		
		System.out.println("Filling the tree");
		if (reqs != null){
			requirements = reqs;
		}
		
		ReqTreeNode temp = null;
		count = 0;
		id = 0;
		
		//CHris Hanna added this line
		this.iterations = Refresher.getInstance().getInstantIterations();
		this.root.removeAllChildren();
		
		this.reload();
		// Loop through all the iterations
		for (int j = 0; j < iterations.length; j++) {
			DefaultMutableTreeNode tempIt;
			if(iterations[j].getIterationName().equals("Backlog"))
				tempIt = new DefaultMutableTreeNode("Backlog");
			else
				tempIt = new DefaultMutableTreeNode(
						iterations[j]);
			this.insertNodeInto(tempIt, root, 0);
			count++;
			
			System.out.println("Added iteration with ID = " + iterations[j].getId());
			
			for (int k = 0 ; k < iterations[j].getRequirements().size(); k++){
				
				int reqId = iterations[j].getRequirements().get(k);
				
				DefaultMutableTreeNode node = null;
				
				for (int r = 0 ; r < requirements.length; r ++){
					if (reqId == requirements[r].getId() && requirements[r].getStatus() != RequirementStatus.DELETED){
						System.out.println("Iter" + iterations[j].getId() + " has Req" + requirements[r].getId());
						if (node == null)
							node = new DefaultMutableTreeNode(requirements[r]);
						else node.add(new DefaultMutableTreeNode(requirements[r]));
					}
					
				}
				if (node != null){
					this.insertNodeInto(node, tempIt, 0);
					
					count++;
				}
				
			}
		}
		TreeView.expandAll();
	}
	
	/**
	 * Refresh the tree.
	 */
	public void refreshTree(){
		controller.refreshData();
		itController.refreshData();
	}

	/**
	 * Add iterations to the tree
	 * 
	 * @param iterations
	 */
	public void addIterations(Iteration[] iterations) {
		this.iterations = iterations;
		this.fillTree(null);
	}
	
}
