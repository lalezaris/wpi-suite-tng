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
 *  Tyler
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.event.MouseAdapter;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementTable;

/**
 * A mouse listener for the requirement table on the dashboard
 *
 * @author Tyler Stone
 *
 * @version Mar 24, 2013
 *
 */
public class RetrieveRequirementController extends MouseAdapter {
	TreeView treeView;
	private int selectedRequirement;
	
	public RetrieveRequirementController(TreeView treeView) { 
		this.treeView = treeView;
	}
	
	@Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
		TreePath selectedPath = treeView.getTree().getSelectionPath();
		Requirement selectedNode = (Requirement) ((ReqTreeNode)selectedPath.getLastPathComponent()).getRequirement();

		if (evt.getClickCount() == 2) {
				showRequirement(selectedNode);
        }

    }
	
	/**
	 * @param requirement the requirement that was retrieved
	 */
	public void showRequirement(Requirement requirement) {
		// Make a new defect view to display the defect that was received
		treeView.getTabController().addEditRequirementTab(requirement);
	}
}