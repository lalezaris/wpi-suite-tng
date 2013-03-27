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

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * The Model for the Requirement Tree
 *
 * @author Sam Lalezari
 *
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
		Requirement[] requirements = reqs;
//		Iteration[] iterations = Refresher.getInstance().getInstantIterations();
		ReqTreeNode temp = null;
		count = 0;
		id = 0;
		
		this.root.removeAllChildren();
		this.reload();
		// Loop through all the iterations
		for (int j = 0; j < iterations.length; j++) {
			DefaultMutableTreeNode tempIt = new DefaultMutableTreeNode(
					"Iteration" + j);
			this.insertNodeInto(tempIt, root, j);

			System.out.println("Added iteration");
			// Loop through List of Requirment ID
			for (int k = 0; k < iterations[j].getRequirements().size(); k++) {
				id = iterations[j].getRequirements().get(k);
				// Loop through all the requirements
				for (int i = 0; i < requirements.length; i++) {
					// If requirements status is DELETED, do nothing, otherwise
					// add to tree
					if (requirements[i].getStatus() == RequirementStatus.DELETED || requirements[i].getId() != id)
						System.out.println("Requirement has Deleted Status or is not contained in Iteration " + j);
					else {
						temp = new ReqTreeNode(requirements[i]);
						this.insertNodeInto(temp, tempIt, count);
						nodes.add(temp);
						System.out.println("Added node");
						count++;
					}
				}
			}
		}
		TreeView.expandAll();
	}
	
	public void refreshTree(){
		controller.refreshData();
		itController.refreshData();
	}

	/**
	 * Enter description here.
	 * 
	 * @param iterations
	 */
	public void addIterations(Iteration[] iterations) {
		// TODO Auto-generated method stub
		this.iterations = iterations;
		
	}
	
}
