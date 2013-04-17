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
 *  Evan Polekoff
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * Tab panel for viewing and working with tasks.
 *
 * @author Evan Polekof
 * @version Apr 17, 2013
 */
@SuppressWarnings("serial")
public class TasksView extends JPanel{
	protected RequirementView parent;
	
	/** The layout manager for this panel */
	protected GridBagLayout layout;
	
	private JTextField txtName;
	private JTextField txtDescription;
	private JComboBox cmbUser;
	private JTextField txtEstimate;
	private JComboBox cmbStatus;

	/**
	 * Instantiates a new tasks view.
	 *
	 * @param req the requirement holding the tasks
	 */
	public TasksView(RequirementView parent) {
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		layout.columnWeights = new double[]{.2, .8};
		this.setLayout(layout);
		this.parent = parent;
		// Add all components to this panel
		addComponents();
	}
	
	protected void addComponents(){
		//create a new constrain variable
		GridBagConstraints c = new GridBagConstraints();
		
		
	}
}
