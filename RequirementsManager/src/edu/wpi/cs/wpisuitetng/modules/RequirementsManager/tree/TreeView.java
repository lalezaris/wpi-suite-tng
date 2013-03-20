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
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		
		DefaultMutableTreeNode req1 = addRequirement("Req1", treeModel, root);
		DefaultMutableTreeNode req2 = addRequirement("Req2", treeModel, req1);
		DefaultMutableTreeNode req3 = addRequirement("Req3", treeModel, root);

		JTree tree = new JTree(treeModel);
		tree.setRootVisible(false);
		this.add(tree, BorderLayout.CENTER);
		
		
	}

	/**
	 * Adds the requirement name to the given tree.
	 * 
	 * @param name
	 * @param model 
	 * @param parent
	 * @return 
	 */
	public DefaultMutableTreeNode addRequirement(String name, DefaultTreeModel model, DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode temp = new DefaultMutableTreeNode(name);
		
		model.insertNodeInto(temp, parent, model.getChildCount(parent));
		
		return temp;
		
	}
}
