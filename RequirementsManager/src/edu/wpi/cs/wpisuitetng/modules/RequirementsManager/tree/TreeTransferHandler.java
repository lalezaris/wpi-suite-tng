/**************************************************
 * This file was developed for CS3733: Software Engineering The course was taken
 * at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * 		Arica Liu
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;

/**
 * Handles Drag and Drop for TreeView.
 * Adapted from: https://code.google.com/p/rnp-videoplayer/source/browse/trunk/src/jtree/DnD/TreeDragAndDrop.java?r=15
 * 
 * @author Arica Liu
 * @version April 21, 2013
 * 
 */
class TreeTransferHandler extends TransferHandler {  
	private static final long serialVersionUID = 8302056184464863258L;
	DataFlavor nodesFlavor;  
	DataFlavor[] flavors = new DataFlavor[1];  
	DefaultMutableTreeNode[] nodesToRemove;
	SaveRequirementController controller;

	/**
	 * Instantiates a new TreeTransferHandler.
	 */
	public TreeTransferHandler() {  
		try {  
			String mimeType = DataFlavor.javaJVMLocalObjectMimeType +  
					";class=\"" +  
					javax.swing.tree.DefaultMutableTreeNode[].class.getName() +  
					"\"";  
			nodesFlavor = new DataFlavor(mimeType);  
			flavors[0] = nodesFlavor;  
		} catch(ClassNotFoundException e) {  
			System.out.println("ClassNotFound: " + e.getMessage());  
		}  
	}  

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
	 */
	protected Transferable createTransferable(JComponent c) {   
		JTree tree = (JTree)c;  
		TreePath[] paths = tree.getSelectionPaths();  
		if(paths != null) {  
			// Make up a node array of copies for transfer and  
			// another for/of the nodes that will be removed in  
			// exportDone after a successful drop.  
			List<DefaultMutableTreeNode> copies =  
					new ArrayList<DefaultMutableTreeNode>();  
			List<DefaultMutableTreeNode> toRemove =  
					new ArrayList<DefaultMutableTreeNode>();  
			DefaultMutableTreeNode node =  
					(DefaultMutableTreeNode)paths[0].getLastPathComponent();  
			DefaultMutableTreeNode copy = copy(node);  
			copies.add(copy);  
			toRemove.add(node);  
			for(int i = 1; i < paths.length; i++) {  
				DefaultMutableTreeNode next =  
						(DefaultMutableTreeNode)paths[i].getLastPathComponent();  
				// Do not allow higher level nodes to be added to list.  
				if(next.getLevel() < node.getLevel()) {  
					break;  
				} else if(next.getLevel() > node.getLevel()) {  // child node  
					copy.add(copy(next));  
					// node already contains child  
				} else {                                        // sibling  
					copies.add(copy(next));  
					toRemove.add(next);  
				}  
			}  
			DefaultMutableTreeNode[] nodes =  
					copies.toArray(new DefaultMutableTreeNode[copies.size()]);  
			nodesToRemove =  toRemove.toArray(new DefaultMutableTreeNode[toRemove.size()]);  
			return new NodesTransferable(nodes);  
		}  
		return null;  
	}  

	/**
	 * Defensive copy used in createTransferable.
	 *
	 * @param node the node to be copied
	 * @return a DefaultMutableTreeNode
	 */  
	private DefaultMutableTreeNode copy(DefaultMutableTreeNode node) {  
		return new DefaultMutableTreeNode(node);  
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent, java.awt.datatransfer.Transferable, int)
	 */
	protected void exportDone(JComponent source, Transferable data, int action) {  
		if((action & MOVE) == MOVE) {  
			JTree tree = (JTree)source;  
			DefaultTreeModel model = (DefaultTreeModel)tree.getModel();  
			// Remove nodes saved in nodesToRemove in createTransferable.  
			for(int i = 0; i < nodesToRemove.length; i++) {  
				model.removeNodeFromParent(nodesToRemove[i]);  
			}  
		}  
	}  

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	public int getSourceActions(JComponent c) {  
		return COPY_OR_MOVE;  
	}  

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#importData(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean importData(TransferHandler.TransferSupport support) {  
		if(!canImport(support)) {  
			return false;  
		}  
		// Extract transfer data.  
		DefaultMutableTreeNode[] nodes = null;  
		try {  
			Transferable t = support.getTransferable();  
			nodes = (DefaultMutableTreeNode[])t.getTransferData(nodesFlavor);  
		} catch(UnsupportedFlavorException ufe) {  
			System.out.println("UnsupportedFlavor: " + ufe.getMessage());  
		} catch(java.io.IOException ioe) {  
			System.out.println("I/O error: " + ioe.getMessage());  
		}  
		// Get drop location info.  
		JTree.DropLocation dl =  
				(JTree.DropLocation)support.getDropLocation();  
		TreePath dest = dl.getPath();  
		DefaultMutableTreeNode parent =  
				(DefaultMutableTreeNode)dest.getLastPathComponent();  
		JTree tree = (JTree)support.getComponent();  
		// Send changes to database
		Object destObject = ((DefaultMutableTreeNode)parent).getUserObject();
		List<String> s = new ArrayList<String>();
		for(int i = 0; i < nodes.length; i++) {
			s.add(nodes[i].getUserObject().toString());
		}
		Requirement[] reqs = ((ReqTreeModel)tree.getModel()).getRequirements();
		List<Requirement> r = new ArrayList<Requirement>();
		int n = 0;
		for(int i = 0; i < reqs.length; i++) {
			for(int x = 0; x < nodes.length; x++) {
				if (reqs[i].getTitle() == s.get(x)) {
					r.add(reqs[i]);
					n += 1;
				}
			}
		}
		if (destObject.toString().startsWith("Iteration")) {
			for(int i = 0; i < n; i++) {
				Requirement req = r.get(i);
				// Change the parent
				req.setIteration((Iteration)destObject);
				// Save the changed requirement
				controller = new SaveRequirementController(req);
				controller.save();
			}
		} else if (destObject instanceof Requirement) {
			for(int i = 0; i < n; i++) {
				Requirement req = r.get(i);
				// Change the parent
				req.setParentRequirementId(((Requirement)destObject).getId());
				// Save the changed requirement
				controller = new SaveRequirementController(((Requirement) req));
				controller.save();
			}
		} else if (destObject.toString().contains("Backlog")){
			for(int i = 0; i < n; i++) {
				Requirement req = r.get(i);
				// Change the parent
				req.setIterationId(0);
				// Save the changed requirement
				controller = new SaveRequirementController(req);
				controller.save();
			}
		} else if (destObject.toString().contains("Deleted")){
			MainView.getInstance().showErrorMessage("Cannot drag to Deleted");
		}
		else {
			System.out.print("The drop destination is not recognizable!");
		}

		return true;  
	}  

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean canImport(TransferHandler.TransferSupport support) {  
		if(!support.isDrop()) {  
			return false;  
		}  
		support.setShowDropLocation(true);  
		if(!support.isDataFlavorSupported(nodesFlavor)) {  
			return false;  
		}  
		// Do not allow a drop on the drag source selections.  
		JTree.DropLocation dl =  
				(JTree.DropLocation)support.getDropLocation();  
		JTree tree = (JTree)support.getComponent();  
		int dropRow = tree.getRowForPath(dl.getPath());  
		int[] selRows = tree.getSelectionRows();  
		for(int i = 0; i < selRows.length; i++) {  
			if(selRows[i] == dropRow) {  
				return false;  
			}
		}  
		// Do not allow a drag for just children
		for(int i = 0; i < selRows.length; i++) {  
			TreePath path2 = tree.getPathForRow(selRows[i]);  
			DefaultMutableTreeNode aNode =  
					(DefaultMutableTreeNode)path2.getLastPathComponent();
			if (aNode.getUserObject() instanceof Requirement) {
				// Do not allow a drag from Backlog to an Iteration if the estimated effort is 0
				if ((((Requirement)aNode.getUserObject()).getIterationId() == 0)
						&& (((Requirement)aNode.getUserObject()).getEstimateEffort() == 0)){
					MainView.getInstance().showErrorMessage("The Estimated Effort needs to be filled before you assign Requirement " 
							+ aNode.getUserObject().toString() 
							+ " to an Iteration");
					return false;
				}
				if (((Requirement)aNode.getUserObject()).getParentRequirementId() != -1) {
					if (!(requirementSelected(selRows, tree, ((DefaultMutableTreeNode)aNode.getParent())))) {
						return false;
					}
				}
			}
		}  
		// Do not allow MOVE-action drops if a non-leaf node is  
		// selected unless all of its children are also selected.  
		int action = support.getDropAction();  
		if(action == MOVE) {  
			return haveCompleteNode(tree);  
		}  
		// Do not allow a non-leaf node to be copied to a level  
		// which is less than its source level.  
		TreePath dest = dl.getPath();  
		DefaultMutableTreeNode target =  
				(DefaultMutableTreeNode)dest.getLastPathComponent();  
		TreePath path = tree.getPathForRow(selRows[0]);  
		DefaultMutableTreeNode firstNode =  
				(DefaultMutableTreeNode)path.getLastPathComponent();  
		if(firstNode.getChildCount() > 0 &&  
				target.getLevel() < firstNode.getLevel()) {  
			return false;  
		}  
		return true;  
	}  

	/**
	 * Check if a tree node is selected.
	 *
	 * @param selRows the selected rows
	 * @param tree the tree
	 * @param aNode the node
	 * @return true, if selected
	 */
	private boolean requirementSelected (int[] selRows, JTree tree, DefaultMutableTreeNode aNode) {
		for(int i = 0; i < selRows.length; i++) {  
			TreePath path = tree.getPathForRow(selRows[i]);  
			DefaultMutableTreeNode treeNode =  
					(DefaultMutableTreeNode)path.getLastPathComponent();
			if ((treeNode.getUserObject() instanceof Requirement)
					&& (treeNode.equals(aNode)))
			{
				return true;
			}
		}  
		return false;
	}

	/**
	 * Check whether the user have selected all the nodes under a parent.
	 *
	 * @param tree the tree
	 * @return true, if successful
	 */
	private boolean haveCompleteNode(JTree tree) {  
		int[] selRows = tree.getSelectionRows();  
		TreePath path = tree.getPathForRow(selRows[0]);  
		DefaultMutableTreeNode first =  
				(DefaultMutableTreeNode)path.getLastPathComponent();  
		int childCount = first.getChildCount();  
		// first has children and no children are selected.  
		if(childCount > 0 && selRows.length == 1)  
			return false;  
		// first may have children.  
		for(int i = 1; i < selRows.length; i++) {  
			path = tree.getPathForRow(selRows[i]);  
			DefaultMutableTreeNode next =  
					(DefaultMutableTreeNode)path.getLastPathComponent();  
			if(first.isNodeChild(next)) {  
				// Found a child of first.  
				if(childCount > selRows.length-1) {  
					// Not all children of first are selected.  
					return false;  
				}  
			}  
		}  
		return true;  
	} 

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {  
		return getClass().getName();  
	}  

	/**
	 * The Class NodesTransferable is used to store the data to be transfered.
	 * 
	 */
	public class NodesTransferable implements Transferable {  
		DefaultMutableTreeNode[] nodes;  

		/**
		 * Instantiates a new NodesTransferable.
		 *
		 * @param nodes the nodes
		 */
		public NodesTransferable(DefaultMutableTreeNode[] nodes) {  
			this.nodes = nodes;  
		}  

		/* (non-Javadoc)
		 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
		 */
		public Object getTransferData(DataFlavor flavor)  
				throws UnsupportedFlavorException {  
			if(!isDataFlavorSupported(flavor))  
				throw new UnsupportedFlavorException(flavor);  
			return nodes;  
		}  

		/* (non-Javadoc)
		 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
		 */
		public DataFlavor[] getTransferDataFlavors() {  
			return flavors;  
		}  

		/* (non-Javadoc)
		 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
		 */
		public boolean isDataFlavorSupported(DataFlavor flavor) {  
			return nodesFlavor.equals(flavor);  
		}  
	}  
}  