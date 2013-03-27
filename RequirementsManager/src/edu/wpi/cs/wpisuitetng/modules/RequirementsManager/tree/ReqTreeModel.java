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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.RetrieveAllRequirementsController;
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
	LinkedList<ReqTreeNode> nodes = new LinkedList<ReqTreeNode>();

	/**
	 * Class constructor
	 * 
	 * @param root the root of the requirement tree
	 * @param tree the tree of requirements
	 */
	public ReqTreeModel(MutableTreeNode root) {
		super(root);
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		controller = new RetrieveAllRequirementsController(this);
		
		//tree.setModel(this);
		this.root = (DefaultMutableTreeNode) root;
		controller.refreshData();
	}

	public void fillTree(Requirement[] reqs){
		
		System.out.println("Filling the tree");
		Requirement[] requirements = reqs;
//		int[] indices = new int[reqs.length];
		ReqTreeNode temp = null;
		this.root.removeAllChildren();
		this.reload();
		
		for (int i = 0; i < requirements.length; i++){
			temp = new ReqTreeNode(requirements[i]);
			
					this.insertNodeInto(temp, root, i);
					nodes.add(temp);
				
			
			
//			indices[i]=i;
			System.out.println("Added node");
		}
		//this.fireTreeNodesChanged(this, null, indices, reqs);
	}
	
	public void refreshTree(){
		controller.refreshData();
	}
	
}
