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

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

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

	/**
	 * Class constructor
	 * 
	 * @param root the root of the requirement tree
	 * @param tree the tree of requirements
	 */
	public ReqTreeModel(TreeNode root, JTree tree) {
		super(root);
		tree.setModel(this);
	}

	public void fillTree(Requirement[] reqs){
		Requirement[] requirements = reqs;
		
		ReqTreeNode temp = null;
		for (int i = 0; i < requirements.length; i++){
			temp = new ReqTreeNode(requirements[i]);
			this.insertNodeInto(temp, null, i);
		}
	}
	
}
