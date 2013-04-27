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
 * 		Sam Lalezari
 * 		Arica Liu
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.RetrieveRequirementControllerTree;

/**
 * TreeView class shows requirements with parents and children in a tree.
 * 
 * @author Sam Lalezari
 * @author Arica Liu
 * 
 * @version April 21th, 2013
 * 
 */
@SuppressWarnings("serial")
public class TreeView extends JPanel {

	JButton refreshButton;
	static JTree tree;
	DefaultMutableTreeNode root;
	ReqTreeModel treeModel;
	JTextArea status;

	private static TreeView instance;

	/**
	 * Creates the tree view.
	 * 
	 */
	public TreeView() {
		instance = this;
		this.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(
				"<html><bold>Requirements</bold></html>", JLabel.CENTER);
		this.add(titleLabel, BorderLayout.PAGE_START);

		root = new DefaultMutableTreeNode(ConfigManager.getConfig()
				.getProjectName());
		treeModel = new ReqTreeModel(root);

		tree = new JTree(treeModel) {

			/* (non-Javadoc)
			 * @see javax.swing.JTree#getToolTipText(java.awt.event.MouseEvent)
			 */
			@Override
			public String getToolTipText(MouseEvent evt) {
				if (getRowForLocation(evt.getX(), evt.getY()) == -1)
					return null;
				TreePath curPath = getPathForLocation(evt.getX(), evt.getY());
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)curPath.getLastPathComponent();
				Object obj = node.getUserObject();
				if(obj instanceof Requirement) {
					Requirement req = (Requirement) obj;
					if (req.checkFake()) {
						return "Requirement " + req.getTitle() + " is in Iteration " 
								+ req.getIteration().getIterationName();
					}  else	if (req.getParentRequirementId() != -1) {
						if (lookUpRequirement(req.getParentRequirementId()).getIterationId() != req.getIterationId()) {
							return "Requirement " + req.getTitle() + "'s parent is in Iteration " 
									+ Integer.toString(lookUpRequirement(req.getParentRequirementId()).getIterationId());
						}
					} else
						return "Requirement " + req.getTitle();
				}
				else if(obj instanceof Iteration) {
					return obj.toString();
				}
				return null;
			}
		};
		ToolTipManager.sharedInstance().registerComponent(tree);

		// Enable drag and drop.
		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON);
		tree.setTransferHandler(new TreeTransferHandler()); 
		tree.getSelectionModel().setSelectionMode(  
				TreeSelectionModel.CONTIGUOUS_TREE_SELECTION); 

		ReqTreeCellRenderer renderer = new ReqTreeCellRenderer();
		tree.setCellRenderer(renderer);

		//prevent double clicking from expanding a view.
		tree.setToggleClickCount(0);

		// Updates the tree view when it is first focused
		final TreeView tv = this;
		tv.addHierarchyListener(new HierarchyListener() {
			/**
			 * @see java.awt.event.HierarchyListener#hierarchyChanged(java.awt.event.HierarchyEvent)
			 */
			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if (HierarchyEvent.SHOWING_CHANGED != 0 && tv.isShowing()) {
					treeModel.refreshTree();
				}
			}
		});

		addMouseListeners();

		JScrollPane scrollPane = new JScrollPane(tree);
		this.add(scrollPane, BorderLayout.CENTER);

		setUpBottom();
	}

	/** Sets the text displayed at the bottom of TreeView. */
	void setStatus(String newText) {
		if (!(status.getText() == newText)) {
			status.setText(newText);
		}
	}

	/** Clear the text displayed at the bottom of TreeView. */
	void clearStatus() {
		status.setText("");
	}

	/**
	 * Sets up the bottom of TreeView.
	 */
	void setUpBottom() {
		// Construct all of the components for the form
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// Creates the status label
		status = new JTextArea("");
		Dimension d = status.getPreferredSize();  
		status.setPreferredSize(new Dimension(d.width,d.height+30)); 
		status.setEditable(false);
		status.setLineWrap(true);
		status.setWrapStyleWord(true);

		// Creates refresh button located at the bottom of the screen
		// TODO eventually get rid of this so it refreshes automatically
		refreshButton = new JButton("Refresh Tree");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTree();
			}
		});

		panel.add(status, BorderLayout.CENTER);
		panel.add(refreshButton, BorderLayout.SOUTH);

		this.add(panel, BorderLayout.SOUTH);
	}
	/**
	 * Expand the entire tree.
	 */
	public static void expandAll() {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}

	/**
	 * Gets tree model.
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

	/**
	 * Add the mouse listeners to the tree.
	 */
	private void addMouseListeners() {
		MouseListener requirementml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());

				if (selRow != -1 && e.getClickCount() == 2) {
					RetrieveRequirementControllerTree<Requirement> controller = new RetrieveRequirementControllerTree<Requirement>(
							null,"requirementsmanager/requirement/", new IRetrieveRequirementController<Requirement>() {
								private boolean isRequirement = true;

								/**
								 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController#runWhenRecieved(java.lang.String)
								 */
								@Override
								public void runWhenRecieved(String s){
									Requirement r = Requirement.fromJSONArray(s)[0];
									if (this.isRequirement) {
										r.setIteration(Iteration
												.getIterationById(r
														.getIterationId()));
										MainTabController.getController()
										.addEditRequirementTab(r);
									}
								}

								/**
								 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController#getID()
								 */
								@Override
								public String getID() {
									TreePath path = tree.getSelectionPath();
									DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path
											.getLastPathComponent();
									Object selectedObject = selectedNode
											.getUserObject();
									if (selectedObject instanceof Requirement) {
										//tree.expandPath(path);
										return ""+((Requirement) selectedObject).getId();
									} else {
										this.isRequirement = false;
										return "-1";
									}
								}

							});
					controller.retrieve();
				} else if (selRow != -1 && e.getClickCount() == 1) {
					TreePath path = tree.getSelectionPath();
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path
							.getLastPathComponent();
					Object selectedObject = selectedNode
							.getUserObject();
					if (selectedNode.getUserObject() instanceof Requirement) {
						setStatus(((Requirement) selectedObject).getTitle());
					}
				}
			}
			
			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
			 */
			public void mouseReleased(MouseEvent e) {
				clearStatus();
			}
		};

		MouseListener iterationml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());

				if (selRow != -1 && e.getClickCount() == 2) {
					RetrieveRequirementControllerTree<Iteration> controller = new RetrieveRequirementControllerTree<Iteration>(
							null,"iterationsmanager/iteration/", new IRetrieveRequirementController<Iteration>() {
								private boolean isIteration = true;

								/**
								 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController#runWhenRecieved(java.lang.String)
								 */
								@Override
								public void runWhenRecieved(String s){

									if(s.equals("0")){
										MainTabController.getController().addEditIterationTab(Iteration.getBacklog());
									}else{
										Iteration iteration = Iteration.fromJSONArray(s)[0];
										if (this.isIteration) {
											MainTabController.getController().addEditIterationTab(iteration);
										}
									}
								}

								/**
								 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController#getID()
								 */
								@Override
								public String getID() {
									TreePath path = tree.getSelectionPath();
									DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path
											.getLastPathComponent();
									Object selectedObject = selectedNode
											.getUserObject();

									if (selectedObject instanceof Iteration) {

										if(((Iteration) selectedObject).getIterationName().equals("Backlog")){
											return ""+ ((Iteration) selectedObject).getId();
										}


										tree.expandPath(path);
										return ""+ ((Iteration) selectedObject).getId();
									} else {
										this.isIteration = false;
										return "-1";
									}
								}
							});
					if(!controller.getID().equals("0")){
						controller.retrieve();
					} else 
						controller.runWhenRecieved("0");
				}
			}
		};

		tree.addMouseListener(requirementml);
		tree.addMouseListener(iterationml);
	}

	/**
	 * Gets the single instance of Refresher.
	 *
	 * @return single instance of Refresher
	 */
	public static TreeView getInstance(){
		return instance;
	}

	/**
	 * Refresh the tree.
	 */
	public void refreshTree() {
		treeModel.refreshTree();
		clearStatus();
	}

	public Requirement lookUpRequirement(int id) {
		Requirement[] reqs = treeModel.getRequirements(); 
		for (int i = 0; i < reqs.length; i++) {
			if (!(reqs[i].checkFake())) {
				if (reqs[i].getId() == id) {
					return reqs[i];
				}
			}
		}
		System.out.println("Cannot find the requirement in lookUpRequirement()!");
		return null;
	}
}