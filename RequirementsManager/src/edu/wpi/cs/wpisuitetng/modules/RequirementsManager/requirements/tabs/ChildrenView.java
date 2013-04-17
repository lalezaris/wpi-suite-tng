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
 * The Class to hold ChildrenView.
 *
 * @author Tushar Narayan
 *
 * @version Apr 16, 2013
 *
 */
@SuppressWarnings("serial")
public class ChildrenView extends JPanel{

	/** The layout manager for this panel */
	protected GridBagLayout layout;

	protected JTextArea txtChildren;

	/** The ArrayList of Integers **/
	protected ArrayList<Integer> childRequirementIDs;

	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;

	protected RequirementView parent;

	/**
	 * Instantiates a new child requirement view.
	 *
	 * @param parent the parent RequirementView
	 */
	public ChildrenView(RequirementView parent){
		this.childRequirementIDs = new ArrayList<Integer>();
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
		txtChildren = new JTextArea(4, 40);
		txtChildren.setLineWrap(true);
		JScrollPane scrollPaneChildren = new JScrollPane(txtChildren);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		txtChildren.setText(childrenListToString());
		txtChildren.setEnabled(false);
		txtChildren.setDisabledTextColor(Color.BLACK);
		this.add(scrollPaneChildren, c);
		/* end panel styling */

	}

	/**
	 * Set the children textbox with the children list.
	 */
	public void setTxtChindrenSaved() {
		txtChildren.setText(childrenListToString());
	}

	/**
	 * Initialize the notes textarea.
	 */
	public void setTxtNotes(){
		txtChildren.setText("");
	}

	/**
	 * Gets the note string.
	 *
	 * @return txtChildren in string format
	 */
	public String getNoteString(){
		return txtChildren.getText().trim();
	}

	/**
	 * Returns the ArrayList of children in the current view.
	 *
	 * @return the ArrayList of children
	 */
	public ArrayList<Integer> getChildrenRequirementsList(){
		return childRequirementIDs;
	}

	/**
	 * Sets the children list.
	 *
	 * @param aln arraylist of notes
	 */
	public void setChildrenRequirementsList(ArrayList<Integer> aln){
		this.childRequirementIDs = aln;
		if (txtChildren!=null)
			txtChildren.setText(childrenListToString());
	}

	/**
	 * Iterates through the notes in the ArrayList and makes
	 * it into a printable string.
	 *
	 * @return notes in the form of a String
	 */
	public String childrenListToString(){
		String list = "";
		if(this.parent != null)
			this.childRequirementIDs = parent.getReqModel().getRequirement().getChildRequirementIds();
		for (int i = 0; i < childRequirementIDs.size(); i++){
			list += "> Requirement " + childRequirementIDs.get(i).toString() + "\n\n";
		}
		return list;
	}

}
