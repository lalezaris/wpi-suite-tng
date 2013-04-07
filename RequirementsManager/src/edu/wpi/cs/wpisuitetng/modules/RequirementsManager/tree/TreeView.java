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
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.RetrieveRequirementControllerTree;

/**
 * TreeView class shows requirements with parents and children in a tree.
 * 
 * @author Sam Lalezari
 * 
 * @version Mar 18, 2013
 * 
 */
@SuppressWarnings("serial")
public class TreeView extends JPanel {

	JButton refreshButton;
	static JTree tree;
	DefaultMutableTreeNode root;
	ReqTreeModel treeModel;

	/**
	 * Creates the tree view of the requirements Commented out parts are not
	 * needed in iteration 1 but may be needed in the future
	 * 
	 */
	public TreeView() {
		this.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(
				"<html><bold>Requirements</bold></html>", JLabel.CENTER);
		this.add(titleLabel, BorderLayout.PAGE_START);

		// Creates refresh button located at the bottom of the screen
		// TODO eventually get rid of this so it refreshes automatically
		refreshButton = new JButton("Refresh Tree");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				treeModel.refreshTree();
			}
		});

		this.add(refreshButton, BorderLayout.SOUTH);

		root = new DefaultMutableTreeNode(ConfigManager.getConfig()
				.getProjectName());
		treeModel = new ReqTreeModel(root);

		tree = new JTree(treeModel);

		
		ImageIcon icon = createImageIcon("icon.png");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(icon);
		tree.setCellRenderer(renderer);
		
		// Updates the tree view when it is first focused
		final TreeView tv = this;
		tv.addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if (HierarchyEvent.SHOWING_CHANGED != 0 && tv.isShowing()) {
					treeModel.refreshTree();
				}
			}
		});

		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				
			
				
				
				if (selRow != -1 && e.getClickCount() == 2) {
					RetrieveRequirementControllerTree<Requirement> controller = new RetrieveRequirementControllerTree<Requirement>(
							null,"requirementsmanager/requirement/", new IRetrieveRequirementController<Requirement>() {
								boolean isRequirement = true;

								@Override
								public void runWhenRecieved(String s){
								//public void runWhenRecieved(Requirement r) {
									
									
									Requirement r = Requirement.fromJSONArray(s)[0];
									if (this.isRequirement) {
										r.setIteration(Iteration
												.getIterationById(r
														.getIterationId()));
										MainTabController.getController()
												.addEditRequirementTab(r);
									}
								}

								@Override
								public String getID() {
									TreePath path = tree.getSelectionPath();
									DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path
											.getLastPathComponent();
									Object selectedObject = selectedNode
											.getUserObject();
									if (selectedObject instanceof Requirement) {
										tree.expandPath(path);
										return ""+((Requirement) selectedObject).getId();
									} else {
										this.isRequirement = false;
										return "-1";
									}
								}

							});
					controller.retrieve();
				}
			}
		};
		
		tree.addMouseListener(ml);

		JScrollPane scrollPane = new JScrollPane(tree);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * expand the entire tree
	 */
	public static void expandAll() {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}
	
	/**
	 * Gets tree model
	 * 
	 * @return the tree model
	 */
	public ReqTreeModel getTreeModel() {
		return treeModel;
	}

	/**
	 * Gets tree
	 * 
	 * @return the JTree
	 */
	public JTree getTree() {
		return tree;
	}
	
	
	/** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path) {
    	
    	path = "..\\RequirementsManager\\src\\media\\req_no_priority.png";

            return new ImageIcon(path);
    }
}
