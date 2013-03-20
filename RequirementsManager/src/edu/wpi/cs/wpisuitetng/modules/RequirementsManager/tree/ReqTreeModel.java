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

/**
 * Insert Description Here
 *
 * @author Sam Lalezari
 *
 * @version Mar 19, 2013
 *
 */
public class ReqTreeModel extends DefaultTreeModel {

	/**
	 * Enter Description here.
	 * 
	 * @param root
	 * @param tree
	 */
	public ReqTreeModel(TreeNode root, JTree tree) {
		super(root);
		tree.setModel(this);
	}

}
