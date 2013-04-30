/**************************************************
 * This file was developed for CS3733: Software Engineering The course was taken
 * at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Sam Lalezari
 * 				 Tushar Narayan
 * 				 Lauren Kahn
 * 				 Tyler Stone
 * 				 Chris Hanna
 *               Arica Liu
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.RetrieveAllIterationsControllerTree;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * The Model for the Requirement Tree.
 * 
 * @author Sam Lalezari
 * @version Mar 28, 2013
 * 
 */
@SuppressWarnings({ "serial" })
public class ReqTreeModel extends DefaultTreeModel {

	DefaultMutableTreeNode root;
	RetrieveAllRequirementsController controller;
	RetrieveAllIterationsControllerTree itController;
	LinkedList<ReqTreeNode> nodes = new LinkedList<ReqTreeNode>();
	int count;
	int id;
	Iteration[] iterations;
	Requirement[] requirements;
	Requirement[] parentRequirements;
	Requirement[] childRequirements;
	int parentCount = 0;
	int childCount = 0;

	/**
	 * Class constructor for ReqTreeModel.
	 * 
	 * @param root
	 *            the root of the requirement tree
	 */
	public ReqTreeModel(MutableTreeNode root) {
		super(root);
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		controller = new RetrieveAllRequirementsController(RefresherMode.TREE);
		itController = new RetrieveAllIterationsControllerTree(this);

		this.root = (DefaultMutableTreeNode) root;
		controller.refreshData();

		this.root.add(new DefaultMutableTreeNode(Iteration.getBacklog()));
		this.root.add(new DefaultMutableTreeNode("Deleted"));
	}

	/**
	 * Fills the tree with requirements given in the array. Clears the existing
	 * tree first, so that there are no duplicates.
	 * 
	 * @param reqs A list of requirements
	 */
	public void fillTree(Requirement[] reqs) {
		if (reqs == null){
			if (requirements == null)
				requirements = new Requirement[0];
		} else {
			requirements = reqs;
		}

		count = 0;
		id = 0;

		iterations = Refresher.getInstance().getInstantIterations();
		root.removeAllChildren();

		this.reload();

		LinkedList<DefaultMutableTreeNode> iterationNodes = new LinkedList<DefaultMutableTreeNode>();
		LinkedList<DefaultMutableTreeNode> nodes = new LinkedList<DefaultMutableTreeNode>();

		for (int r = 0; r < iterations.length; r++) {
			// initialize all new iteration nodes
			Arrays.sort(iterations);
			iterationNodes.add(new DefaultMutableTreeNode(iterations[r]));
		}

		for (int r = 0; r < requirements.length; r++) {
			// initialize all new tree nodes
			if (requirements[r].getStatus() != RequirementStatus.DELETED) {
				nodes.add(new DefaultMutableTreeNode(requirements[r]));
			}
		}
		
		List<Integer> added = new ArrayList<Integer>();
		for (int r = 0; r < nodes.size(); r++) { // iterate through every requirement
			Requirement leafReq = (Requirement) nodes.get(r).getUserObject();

			if (!(leafReq.getParentRequirementId() == -1)) { // check if it isn't top level
				int parentID = leafReq.getParentRequirementId(); // get parent id
				for (int z = 0; z < nodes.size(); z++) { // iterate through all reqs
					Requirement potentialParent = (Requirement) nodes.get(z).getUserObject();
					if (potentialParent.getId() == parentID) {					
						if (potentialParent.getIterationId() == leafReq.getIterationId()) {
							// If they are in the same iteration, do hierarchy
							nodes.get(z).add(nodes.get(r));
						} else {
							// If not, fake a node to be colored
							DefaultMutableTreeNode fakeNode = new DefaultMutableTreeNode(new Requirement(leafReq));
							for (int x = 0; x < requirements.length; x++) {
								if (leafReq.getChildRequirementIds().contains(requirements[x].getId())) {
									int pID = leafReq.getParentRequirementId();
									Requirement grandParent = leafReq;
									while (pID != -1) {
										grandParent = lookUpRequirement(pID);
										pID = grandParent.getParentRequirementId();
									}
									if (requirements[x].getIterationId() == leafReq.getIterationId()) {
									} else if (grandParent.getIterationId() == leafReq.getIterationId()) {
										fakeNode.add(new DefaultMutableTreeNode(requirements[x]));
										added.add(requirements[x].getId());
									}
								}
							}
							nodes.get(z).add(fakeNode);
							// Find the iteration node and add it
							int reqIterationID = leafReq.getIterationId();
							for (int x = 0; x < iterations.length; x++) {
								Iteration potentialIteration = (Iteration) iterationNodes.get(x).getUserObject();

								if (potentialIteration.getId() == reqIterationID) {
									if (!(added.contains(leafReq.getId())))
										iterationNodes.get(x).add(nodes.get(r));
								}
							}
						}
					}
				}
			} else {
				int reqIterationID = leafReq.getIterationId();
				for (int z = 0; z < iterations.length; z++) {
					Iteration potentialIteration = (Iteration) iterationNodes.get(z).getUserObject();

					if (potentialIteration.getId() == reqIterationID) {
						iterationNodes.get(z).add(nodes.get(r));
					}
				}
			}
		}
		DefaultMutableTreeNode backlog = null;
		for (int r = 0; r < iterations.length; r++) {
			if(!(iterations[r].toString().contains("Backlog"))){
				root.add(iterationNodes.get(r));
			}
			else{
				backlog = iterationNodes.get(r);
			}
		}
		DefaultMutableTreeNode deleted = new DefaultMutableTreeNode("Deleted");
		for (int r = 0 ; r < requirements.length ; r ++){
			if (requirements[r].getStatus() == RequirementStatus.DELETED)
				deleted.add(new DefaultMutableTreeNode(requirements[r]));
		}
		if(backlog != null){
			root.add(backlog);
		}
		root.add(deleted);
		if (reqs != null){
			TreeView.expandAll();
		}
	}

	/**
	 * Refresh the tree.
	 */
	public void refreshTree() {
		itController.refreshData();
		controller.refreshData();
	}

	/**
	 * Add iterations to the tree
	 * 
	 * @param iterations The iterations to be added
	 */
	public void addIterations(Iteration[] iterations) {
		this.iterations = iterations;
		if(requirements == null){
			fillTree2();
		}
	}
	
	public void fillTree2(){
		this.iterations = Refresher.getInstance().getInstantIterations();
		root.removeAllChildren();
		
		this.reload();
		
		LinkedList<DefaultMutableTreeNode> iterationNodes = new LinkedList<DefaultMutableTreeNode>();
		
		for(int r = 0; r < iterations.length; r++){
			//initialize all new iteration nodes
			Arrays.sort(iterations);
			iterationNodes.add(new DefaultMutableTreeNode(iterations[r]));
		}
		
		DefaultMutableTreeNode backlog = null;
		for(int r = 0; r < iterations.length; r++){
			if(!(iterations[r].toString().contains("Backlog"))){
				root.add(iterationNodes.get(r));
			}
			else{
				backlog = iterationNodes.get(r);
			}
		}
		if(backlog != null){
			root.add(backlog);
		}
		
		DefaultMutableTreeNode deleted = new DefaultMutableTreeNode("Deleted");
		root.add(deleted);
		TreeView.expandAll();
	}

	/**
	 * @return the requirements
	 */
	public Requirement[] getRequirements() {
		return requirements;
	}
	
	/**
	 * Look up requirement by id.
	 *
	 * @param id the id to search for.
	 * @return the requirement
	 */
	public Requirement lookUpRequirement(int id) {
		Requirement[] reqs = requirements; 
		for (int i = 0; i < reqs.length; i++) {
			if (!(reqs[i].checkFake())) {
				if (reqs[i].getId() == id) {
					return reqs[i];
				}
			}
		}
		return null;
	}
}