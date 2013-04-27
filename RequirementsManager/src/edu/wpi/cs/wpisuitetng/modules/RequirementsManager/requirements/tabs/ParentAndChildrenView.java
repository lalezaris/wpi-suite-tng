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
 * Tushar Narayan
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * The Class to hold DependenciesView.
 *
 * @author Tushar Narayan
 *
 * @version Apr 16, 2013
 *
 */
@SuppressWarnings("serial")
public class ParentAndChildrenView extends JPanel{

	/** The layout manager for this panel */
	protected GridBagLayout layout;

	protected JLabel lblParent;
	protected JLabel lblChildren;
	protected JList txtParent;
	protected JList txtChildren;
	protected DefaultListModel mdlChildren;
	protected DefaultListModel mdlParent;

	/** The ArrayList of Child Requirement IDs (aka downstream dependencies)**/
	protected ArrayList<Integer> childRequirementIDs;

	/** The Parent Requirement ID (aka upstream dependency)**/
	protected int parentID;

	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;

	protected RequirementView parentRequirementView;
	
	/**
	 * Instantiates a new dependencies view.
	 *
	 * @param parent the parent RequirementView
	 */
	public ParentAndChildrenView(RequirementView parent){
		this.childRequirementIDs = new ArrayList<Integer>();
		this.parentID = 0;
		//Use a grid bag layout manager
		layout = new GridBagLayout();
//		layout.columnWeights = new double[]{.2, .8};
		this.setLayout(layout);
		this.parentRequirementView = parent;
		// Add all components to this panel
		this.addComponents();
	}

	/**
	 * Adds the components to the panel and places constraints on them
	 * for the GridBagLayout manager.
	 *
	 */
	protected void addComponents() {
		//create a new constrain variable
		GridBagConstraints c = new GridBagConstraints();
		
		lblParent = new JLabel("Parent: ", LABEL_ALIGNMENT);
		lblParent.setForeground(Color.BLACK);
		lblChildren = new JLabel("Children: ", LABEL_ALIGNMENT);
		lblChildren.setForeground(Color.BLACK);
		mdlParent = new DefaultListModel();
		mdlParent.addElement("Requirement " + parentRequirementView.getReqModel().getUneditedRequirement().getParentRequirementId());
		txtParent = new JList(mdlParent);
		mdlChildren = new DefaultListModel();
		childRequirementIDs = parentRequirementView.getReqModel().getUneditedRequirement().getChildRequirementIds();
		String req = "";
		for(int i = 0; i < childRequirementIDs.size(); i++){
			req = "Requirement " + childRequirementIDs.get(i);
			mdlChildren.addElement(req);
			req = "";
		}
		txtChildren = new JList(mdlChildren);
		
		JPanel panelOne = new JPanel();
		panelOne.setLayout(new GridBagLayout());
		GridBagConstraints cOne = new GridBagConstraints();
		JPanel panelTwo = new JPanel();
		panelTwo.setLayout(new GridBagLayout());
		GridBagConstraints cTwo = new GridBagConstraints();
		//TODO: Set borders if we decide to do this across sub-tabs

		/* begin panel styling */
		cTwo.anchor = GridBagConstraints.FIRST_LINE_START;
		cTwo.insets = new Insets(10,10,5,0); //top,left,bottom,right
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridx = 0;
		cTwo.gridy = 0;
		cTwo.gridwidth = 1;
		panelTwo.add(lblParent,cTwo);
		
		cTwo.anchor = GridBagConstraints.FIRST_LINE_START;
		cTwo.insets = new Insets(10,10,5,0); //top,left,bottom,right
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridx = 1;
		cTwo.gridy = 0;
		cTwo.gridwidth = 1;
		txtParent.setForeground(Color.BLACK);
		txtParent.enableInputMethods(false);
		panelTwo.add(txtParent,cTwo);
		
		cOne.anchor = GridBagConstraints.FIRST_LINE_START;
		cOne.insets = new Insets(20,10,5,0); //top,left,bottom,right
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridx = 0;
		cOne.gridy = 1;
		cOne.gridwidth = 1;
		panelOne.add(lblChildren,cOne);
		
		cOne.anchor = GridBagConstraints.FIRST_LINE_START;
		cOne.insets = new Insets(5,10,5,0); //top,left,bottom,right
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridx = 0;
		cOne.gridy = 2;
		cOne.gridwidth = 1;
		txtChildren.setForeground(txtParent.getForeground());
//		txtChildren.setBackground(this.getBackground());
//		txtChildren.setEnabled(false);
		txtChildren.enableInputMethods(false);
		panelOne.add(txtChildren,cOne);
		
		JPanel panelOverall = new JPanel();
		panelOverall.setLayout(new GridBagLayout());
		GridBagConstraints cOverall = new GridBagConstraints();
		
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START;
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.gridwidth = 1;
		panelOverall.add(panelTwo,cOverall);
		
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START;
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 1;
		cOverall.gridwidth = 1;
		panelOverall.add(panelOne,cOverall);
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		this.add(panelOverall,c);
		
		/* end panel styling */

	}

	/**
	 * @return the txtParent
	 */
	public JList getTxtParent() {
		return txtParent;
	}

	/**
	 * @param txtParent: the txtParent to set
	 */
	public void setTxtParent(JList txtParent) {
		this.txtParent = txtParent;
	}

	/**
	 * Gets the ArrayList of ids of children dependencies in the current view.
	 *
	 * @return the ArrayList of ids of children
	 */
	public ArrayList<Integer> getChildrenRequirementsList(){
		return childRequirementIDs;
	}

	/**
	 * Gets the id of parent dependency in the current view.
	 *
	 * @return the id of parent
	 */
	public int getParentRequirement(){
		return parentID;
	}
	
	/**
	 * @param childRequirementIDs: the childRequirementIDs to set
	 */
	public void setChildRequirementIDs(ArrayList<Integer> childRequirementIDs) {
		this.childRequirementIDs = childRequirementIDs;
	}

	/**
	 * Iterates through the notes in the ArrayList and makes
	 * it into a printable string.
	 *
	 * @return notes in the form of a String
	 */
	public String dependenciesListToString(){
		String list = "";
		if(this.parentRequirementView != null){
			this.childRequirementIDs = parentRequirementView.getReqModel().getRequirement().getChildRequirementIds();
			this.parentID = parentRequirementView.getReqModel().getRequirement().getParentRequirementId();
		}
		if(parentID == -1) list += "No parent requirement.\n\n";
		else list += "Parent requirement: Requirement #" + parentID + "\n\n";
		if(childRequirementIDs.size() > 0){
			list += "Child requirements):\n\n";
			for (int i = 0; i < childRequirementIDs.size(); i++){
				list += "> Requirement #" + childRequirementIDs.get(i).toString() + "\n";
			}
		}
		else
			list += "No child requirements.\n\n";
		return list;
	}

}
