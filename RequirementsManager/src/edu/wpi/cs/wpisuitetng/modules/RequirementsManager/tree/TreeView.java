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

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 *	TreeView class shows requirements with parents and children in a tree.
 *
 * @author Sam Lalezari
 *
 * @version Mar 18, 2013
 *
 */
public class TreeView extends JPanel {

	public TreeView(){
		this.setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel("<html><bold>Requirements</bold></html>", JLabel.CENTER);
		this.add(titleLabel, BorderLayout.PAGE_START);
		
		MutableTreeNode root = new DefaultMutableTreeNode("root");
		//DefaultTreeModel treeModel = new DefaultTreeModel(root);
		
		JTree tree = new JTree();
		tree.setRootVisible(false);
		
		ReqTreeModel treeModel = new ReqTreeModel(root, tree);
		/*
		DefaultMutableTreeNode req1 = addRequirement("Req1", treeModel, root);
		DefaultMutableTreeNode req2 = addRequirement("Req2", treeModel, req1);
		DefaultMutableTreeNode req3 = addRequirement("Req3", treeModel, root);
		*/
		this.add(tree, BorderLayout.CENTER);
		
		
	}

	/**
	 * Adds the requirement name to the given treeModel.
	 * 
	 * @param name
	 * @param model 
	 * @param parent
	 * @return 
	 */
	/*
	public DefaultMutableTreeNode addRequirement(String name, TreeModel model, MutableTreeNode parent) {
		DefaultMutableTreeNode temp = new DefaultMutableTreeNode(name);
		
		 ((DefaultTreeModel) model).insertNodeInto(temp, parent, model.getChildCount(parent));
		
		return temp;
		
	}
	*/
}
