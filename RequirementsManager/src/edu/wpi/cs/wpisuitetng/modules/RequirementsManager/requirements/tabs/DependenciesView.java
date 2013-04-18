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
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
public class DependenciesView extends JPanel{

	/** The layout manager for this panel */
	protected GridBagLayout layout;

	protected JTextArea txtDependencies;

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

	protected RequirementView parent;

	/**
	 * Instantiates a new dependencies view.
	 *
	 * @param parent the parent RequirementView
	 */
	public DependenciesView(RequirementView parent){
		this.childRequirementIDs = new ArrayList<Integer>();
		this.parentID = 0;
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		layout.columnWeights = new double[]{.2, .8};
		this.setLayout(layout);
		this.parent = parent;
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

		//TODO: Set borders if we decide to do this across sub-tabs

		/* begin panel styling */
		txtDependencies = new JTextArea(4, 40);
		txtDependencies.setLineWrap(true);
		JScrollPane scrollPaneDependencies = new JScrollPane(txtDependencies);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		txtDependencies.setText(dependenciesListToString());
		txtDependencies.setEnabled(false);
		txtDependencies.setDisabledTextColor(Color.BLACK);
		this.add(scrollPaneDependencies, c);
		/* end panel styling */

	}

	/**
	 * Set the children textbox with the children list.
	 */
	public void setTxtDependenciesSaved() {
		txtDependencies.setText(dependenciesListToString());
	}

	/**
	 * Initialize the dependencies textarea.
	 */
	public void setTxtDependencies(){
		txtDependencies.setText("");
	}

	/**
	 * Gets the dependencies string.
	 *
	 * @return txtDependencies in string format
	 */
	public String getDependenciesString(){
		return txtDependencies.getText().trim();
	}

	/**
	 * Returns the ArrayList of ids of children dependencies in the current view.
	 *
	 * @return the ArrayList of ids of children
	 */
	public ArrayList<Integer> getChildrenRequirementsList(){
		return childRequirementIDs;
	}
	
	/**
	 * Returns the id of parent dependency in the current view.
	 *
	 * @return the id of parent
	 */
	public int getParentRequirement(){
		return parentID;
	}

	/**
	 * Sets the dependencies list.
	 *
	 * @param ald arraylist of dependencies
	 */
	public void setDependenciesList(){
		if (txtDependencies!=null)
			txtDependencies.setText(dependenciesListToString());
	}

	/**
	 * Iterates through the notes in the ArrayList and makes
	 * it into a printable string.
	 *
	 * @return notes in the form of a String
	 */
	public String dependenciesListToString(){
		String list = "";
		if(this.parent != null){
			this.childRequirementIDs = parent.getReqModel().getRequirement().getChildRequirementIds();
			this.parentID = parent.getReqModel().getRequirement().getParentRequirementId();
		}
		if(parentID == -1) list += "No upstream dependency (parent requirement).\n\n";
		else list += "Upstream dependency (parent requirement): Requirement #" + parentID + "\n\n";
		if(childRequirementIDs.size() > 0){
			list += "Direct downstream dependencies (child requirements):\n";
			for (int i = 0; i < childRequirementIDs.size(); i++){
				list += "> Requirement #" + childRequirementIDs.get(i).toString() + "\n\n";
			}
		}
		else
			list += "No downstream dependencies (children requirements).\n\n";
		return list;
	}

}
