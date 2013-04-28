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
import java.util.Calendar;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.SaveIterationController;
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
	SaveIterationController itcontroller;
	Requirement[] reqs;

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

	/**
	 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
	 */
	protected Transferable createTransferable(JComponent c) {   
		JTree tree = (JTree)c;  
		reqs = ((ReqTreeModel)tree.getModel()).getRequirements(); // Get a list of all requirements
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

	/**
	 * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent, java.awt.datatransfer.Transferable, int)
	 */
	protected void exportDone(JComponent source, Transferable data, int action) {  
		//		if((action & MOVE) == MOVE) {  
		//			JTree tree = (JTree)source;  
		//			DefaultTreeModel model = (DefaultTreeModel)tree.getModel();  
		//			// Remove nodes saved in nodesToRemove in createTransferable.  
		//			for(int i = 0; i < nodesToRemove.length; i++) {  
		//				model.removeNodeFromParent(nodesToRemove[i]);  
		//			}  
		//		}  
		TreeView.getInstance().clearStatus();
	}  

	/**
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	public int getSourceActions(JComponent c) {  
		return COPY_OR_MOVE;  
	}  

	/**
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
		// Send changes to database
		Object destObject = ((DefaultMutableTreeNode)parent).getUserObject();
		List<Requirement> r = new ArrayList<Requirement>(); // List of requirements to be saved
		for(int i = 0; i < nodes.length; i++) {
			r.add((Requirement)((DefaultMutableTreeNode)(nodes[i].getUserObject())).getUserObject());
		}
		if (destObject.toString().startsWith("Iteration")) {
			for(int i = 0; i < nodes.length; i++) {
				System.out.println("in the for loop");
				Requirement req = checkFake(r.get(i));
				System.out.println(req.getTitle());
				// Change the requirement
				if (req.getStatus() == RequirementStatus.NEW) {
					req.setStatus(RequirementStatus.INPROGRESS);
				}
				req.setIteration((Iteration)destObject);
				// Save the changed requirement
				controller = new SaveRequirementController(req);
				controller.save();
				System.out.println("Saved req " + req.getTitle());
				// Change the iteration
				Iteration it = (Iteration)destObject;
				if (!(it.getRequirements().contains(req))) {
					it.addRequirement(req.getId());
				}
				// Save the changed iteration
				itcontroller = new SaveIterationController(it);
				itcontroller.save();
			}
		} else if (destObject instanceof Requirement) {
			for(int i = 0; i < nodes.length; i++) {
				Requirement req = checkFake(r.get(i));
				// Remove the child from its original parent
				if (TreeView.getInstance().lookUpRequirement(req.getParentRequirementId()) != null) {
					Requirement oldParent = TreeView.getInstance().lookUpRequirement(req.getParentRequirementId());
					oldParent.removeChildRequirement(req.getId());
					oldParent.setTotalEstimateEffort(oldParent.getTotalEstimateEffort() - req.getTotalEstimateEffort());
					controller = new SaveRequirementController(oldParent);
					controller.save();
				}
				// Change the requirement
				req.setParentRequirementId(((Requirement)destObject).getId());
				// Save the changed requirement
				controller = new SaveRequirementController(req);
				controller.save();
				// Save the new parent
				Requirement req2 = TreeView.getInstance().lookUpRequirement(((Requirement)destObject).getId());
				req2.setTotalEstimateEffort(req2.getTotalEstimateEffort() + req.getTotalEstimateEffort());
				req2.addChildRequirement(req.getId());
				controller = new SaveRequirementController(req2);
				controller.save();
			}
		} else if (destObject.toString().contains("Backlog")){
			for(int i = 0; i < nodes.length; i++) {
				Requirement req = backFromDel(checkFake(r.get(i)));
				// Change the requirement
				req.setIterationId(0);
				// Save the changed requirement
				controller = new SaveRequirementController(req);
				controller.save();
				// Change the old iteration
				//				if (lookUpIteration(req.getIterationId()) != null) {
				//					Iteration it = lookUpIteration(req.getIterationId());
				//					it.removeRequirement(req.getId());
				//					// Save the changed iteration
				//					itcontroller = new SaveIterationController(it);
				//					itcontroller.save();
				//				}
			}
		} else if (destObject.toString().contains("Deleted")){
			// Show a dialog
			int n = JOptionPane.showConfirmDialog(
					MainView.getInstance(), "Are you sure you want to delete it?",
					"Deletion Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (n == JOptionPane.YES_OPTION) {
				for(int i = 0; i < nodes.length; i++) {
					Requirement req = checkFake(r.get(i));
					// Save the changed parent
					if (req.getParentRequirementId() != -1){
						Requirement req2 = TreeView.getInstance().lookUpRequirement(req.getParentRequirementId());
						req2.setTotalEstimateEffort(req2.getTotalEstimateEffort() - req.getTotalEstimateEffort());
						req2.removeChildRequirement(req.getId());
						controller = new SaveRequirementController(req2);
						controller.save();
					}
					// Change the requirement
					req.setParentRequirementId(-1);
					req.setStatus(RequirementStatus.DELETED);
					req.setIterationId(0);
					// Save the changed requirement
					controller = new SaveRequirementController(req);
					controller.save();
					// Change the old iteration
					//					if (lookUpIteration(req.getIterationId()) != null) {
					//						Iteration it = lookUpIteration(req.getIterationId());
					//						it.removeRequirement(req.getId());
					//						// Save the changed iteration
					//						itcontroller = new SaveIterationController(it);
					//						itcontroller.save();
					//					}
				}
			} else if (n == JOptionPane.NO_OPTION) {
				TreeView.getInstance().setStatus("Cancelled deletion.");
			} else {
				TreeView.getInstance().setStatus("Closed dialog.");
			}
		}
		else {
			TreeView.getInstance().setStatus("The drop destination is not recognizable!");
		}

		return true;  
	}  

	/**
	 * Check if a Requirement is "fake" and return the real one.
	 *
	 * @param r the requirement
	 * @return the real requirement
	 */
	private Requirement checkFake(Requirement r) {
		if (r.checkFake()) {
			Requirement requirement = new Requirement();
			// Find the right requirement
			for(int i = 0; i < reqs.length; i++) {
				if (r.getId() == reqs[i].getId()) {
					requirement = reqs[i];
				}
			}
			return requirement;
		}
		return r;
	}

	/**
	 * Change the status of a requirement if drag it from Deleted.
	 *
	 * @param r the r
	 * @return the requirement
	 */
	private Requirement backFromDel(Requirement r) {
		if (r.getStatus() == RequirementStatus.DELETED) {
			r.setStatus(RequirementStatus.OPEN);
		}
		return r;
	}

	/**
	 * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean canImport(TransferHandler.TransferSupport support) {
		if (MainTabController.getController().getCurrentComponent() != null) {
			if (MainTabController.getController().getCurrentComponent().getClass() == RequirementListPanel.class) {
				if (((RequirementListPanel)(MainTabController.getController()
						.getCurrentComponent())).getModel().getIsChange()) {
					TreeView.getInstance().setStatus("Disabled drag&drop! Please save changes first.");
					return false;
				}
			}
		}
		if(!support.isDrop()) {
			TreeView.getInstance().setStatus("Not a drop operation!");
			return false;  
		}  
		support.setShowDropLocation(true);  
		if(!support.isDataFlavorSupported(nodesFlavor)) {  
			TreeView.getInstance().setStatus("nodesFlavor not supported!");
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
				TreeView.getInstance().setStatus("Can't drop on the drag source selections!");
				return false;  
			}
		} 
		// Do not allow a drop on a past Iteration
		TreePath ddest = dl.getPath();
		DefaultMutableTreeNode ttarget = null;
		if (ddest.getLastPathComponent() != null) {
			ttarget =  
					(DefaultMutableTreeNode)ddest.getLastPathComponent();
		}
		else {
			TreeView.getInstance().setStatus("The target is null!");
			return false;
		}
		if (ttarget.getUserObject().toString().contains("Iteration")) {
			Calendar cEnd = Calendar.getInstance();
			cEnd.setTime(((Iteration)ttarget.getUserObject()).getEndDate());
			cEnd.add(Calendar.DATE, 1);
			if (cEnd.compareTo(Calendar.getInstance()) < 0) {
				TreeView.getInstance().setStatus("Can't drop on a past iteration!");
				return false;
			}
		}
		// Do not allow a drop on the root
		if (ttarget.isRoot()) {
			TreeView.getInstance().setStatus("Can't drop on the root!");
			return false;
		}
		for(int i = 0; i < selRows.length; i++) {  
			TreePath path2 = tree.getPathForRow(selRows[i]);  
			DefaultMutableTreeNode aNode =  
					(DefaultMutableTreeNode)path2.getLastPathComponent();
			// Do not allow a drop on itself
			//			if (aNode == ttarget) {
			//				TreeView.getInstance().setStatus("Can't drop on itself!");
			//				return false;
			//			}
			if (aNode.getUserObject() instanceof Requirement) {
				Requirement requirement = checkFake((Requirement)aNode.getUserObject());
				// Do not allow a drag from Backlog to an Iteration if the estimated effort is 0
				if ((requirement.getIterationId() == 0)
						&& (requirement.getEstimateEffort() == 0)){
					MainView.getInstance().showErrorMessage("The Estimated Effort needs to be filled before you assign Requirement " 
							+ aNode.getUserObject().toString() 
							+ " to an Iteration/Requirement");
					return false;
				}
				// Do not allow a drop on the same parent
				TreeNode parentNode = aNode;
				while (parentNode.getParent() != null) {
					if (parentNode.getParent().equals(ttarget)) {
						TreeView.getInstance().setStatus("Can't drop on the its parent!");
						return false;
					} else {
						parentNode = parentNode.getParent();
					}
				}
				// Do not allow a drag for just children
				//				if (((Requirement)aNode.getUserObject()).getParentRequirementId() != -1) {
				//					if (!(requirementSelected(selRows, tree, ((DefaultMutableTreeNode)aNode.getParent())))) {
				//						return false;
				//					}
				//				}


				if (ttarget.getUserObject() instanceof Requirement) {
					if (requirement.getStatus() == RequirementStatus.DELETED) {
						TreeView.getInstance().setStatus("Cannot assign it to a requirement while it is in the Deleted folder!");
						return false;
					}
					// Do not allow a drop on its children
					Requirement req = checkFake((Requirement)ttarget.getUserObject());
					if (requirement.getChildRequirementIds().contains(req.getId())) {
						TreeView.getInstance().setStatus("Requirement " + requirement.getTitle() + " "
								+ ": Can't drop on its own children!");
						return false;
					}
					// Do not allow a drop on its parent
					if (req.getChildRequirementIds().contains(requirement.getId())) {
						TreeView.getInstance().setStatus("Requirement " + requirement.getTitle() + " "
								+ ": Already a child of the target!");
						return false;
					}
					// Do not allow a drop on itself
					if (req.getId() == requirement.getId()) {
						TreeView.getInstance().setStatus("Requirement " + requirement.getTitle() + " "
								+ ": Cannot drop on itself!");
						return false;
					}
				}
				if (ttarget.getUserObject().toString().contains("Iteration")) {
					if (requirement.getStatus() == RequirementStatus.DELETED) {
						TreeView.getInstance().setStatus("Cannot drag from Deleted to an Iteration!");
						return false;
					}
					if (requirement.getParentRequirementId() != -1) {
						if (TreeView.getInstance().lookUpRequirement(requirement.getParentRequirementId()) != null) {
							Requirement parent = TreeView.getInstance().lookUpRequirement(requirement.getParentRequirementId());
							if (parent.getIterationId() != 0) {
								if (((Iteration)ttarget.getUserObject()).getEndDate().
										after(parent.getIteration().getEndDate())) {
									TreeView.getInstance().setStatus("Requirement " + requirement.getTitle() + " "
											+ ": Can't not be dragged to an Iteration whose end date is after its parent requirement's iteration end date!");
									return false;
								}
							}
						}
					}
				}
			}
			// Do not allow a drag for an Iteration
			else if (aNode.getUserObject() instanceof Iteration) {
				TreeView.getInstance().setStatus("Can't drag an iteration!");
				return false;
			}
			// Do not allow a drag for an Deleted folder
			else if (aNode.getUserObject().toString().contains("Deleted")) {
				TreeView.getInstance().setStatus("Can't drag the Deleted folder!");
				return false;
			}
			// Do not allow a drag for the root
			else if (aNode.isRoot()) {
				TreeView.getInstance().setStatus("Can't drag the root!");
				return false;
			}
		}

		// Do not allow MOVE-action drops if a non-leaf node is  
		// selected unless all of its children are also selected.  
		//		int action = support.getDropAction();  
		//		if(action == MOVE) {  
		//			return haveCompleteNode(tree);  
		//		}  
		// Do not allow a non-leaf node to be copied to a level  
		// which is less than its source level.  
		//		TreePath dest = dl.getPath();  
		//		DefaultMutableTreeNode target =  
		//				(DefaultMutableTreeNode)dest.getLastPathComponent();  
		//		TreePath path = tree.getPathForRow(selRows[0]);  
		//		DefaultMutableTreeNode firstNode =  
		//				(DefaultMutableTreeNode)path.getLastPathComponent();  
		//		if(firstNode.getChildCount() > 0 &&  
		//				target.getLevel() < firstNode.getLevel()) {  
		//			TreeView.getInstance().setStatus("Can't drop to a level less than its source level!");
		//			return false;  
		//		}

		TreeView.getInstance().clearStatus();
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

	/**
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

		/**
		 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
		 */
		public Object getTransferData(DataFlavor flavor)  
				throws UnsupportedFlavorException {  
			if(!isDataFlavorSupported(flavor))  
				throw new UnsupportedFlavorException(flavor);  
			return nodes;  
		}  

		/**
		 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
		 */
		public DataFlavor[] getTransferDataFlavors() {  
			return flavors;  
		}  

		/**
		 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
		 */
		public boolean isDataFlavorSupported(DataFlavor flavor) {  
			return nodesFlavor.equals(flavor);  
		}  
	}  
}  