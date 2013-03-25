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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
/**
 *	TreeView class shows requirements with parents and children in a tree.
 *
 * @author Sam Lalezari
 *
 * @version Mar 18, 2013
 *
 */
@SuppressWarnings("serial")
public class TreeView extends JPanel {

	JButton refreshButton;
	JTree tree;
	DefaultMutableTreeNode root;
	ReqTreeModel treeModel;
	/**
	 * Creates the tree view of the requirements
	 * Commented out parts are not needed in iteration 1 but may be needed in the future
	 * 
	 */
	public TreeView(){
		this.setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel("<html><bold>Requirements</bold></html>", JLabel.CENTER);
		this.add(titleLabel, BorderLayout.PAGE_START);
	
		// Creates refresh button located at the bottom of the screen
		// TODO eventually get rid of this so it refreshes automatically
		refreshButton = new JButton("Refresh Tree");
		refreshButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				treeModel.refreshTree();
			}
		});
		this.add(refreshButton, BorderLayout.SOUTH);
		

		
		
		root = new DefaultMutableTreeNode("root");
		treeModel = new ReqTreeModel(root);
		
		tree = new JTree(treeModel);
		
		// TODO setRootVisible(false) hides the root and all of its leaves (requirements), look for a fix
		//tree.setRootVisible(false);
		
		//Updates the tree view when it is first focused
		final TreeView tv = this;
		tv.addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if (HierarchyEvent.SHOWING_CHANGED != 0 && tv.isShowing())
				{
					System.out.println("Dashboard Gained View");
					treeModel.refreshTree();
				}

			}

		});

		this.add(tree, BorderLayout.CENTER);
		
		
		
	}

}
