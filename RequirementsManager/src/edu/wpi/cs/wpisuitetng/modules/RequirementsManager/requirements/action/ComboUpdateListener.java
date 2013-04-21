/*******************************************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 *    Arica Liu
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;

/**
 * Adapted from JPage and the ComboUpdateListener in DefectTracker
 * 
 * Checks for whether or not the text in a given JComboBox differs from the current model (a Requirement).
 * 
 * Whenever a key is released in the ItemListener's component, checkIfUpdated() is called. This method 
 * gets the component's name and looks up the value of the relevant field in panel's Requirement model. It then 
 * compares this value to the component's selection to see if the text differs from the model. If the selection 
 * differs, the style of the component is changed to show that it differs from the relevant field in the model.
 * Otherwise, the component's style is changed to be normal.
 *
 * @author Arica Liu
 *
 * @version Mar 20, 2013
 *
 */
public class ComboUpdateListener implements ItemListener {
	protected final RequirementPanel panel;
	@SuppressWarnings("rawtypes")
	protected final JComboBox component;
	protected final Border defaultBorder;

	/**
	 * Constructs a ComboUpdateListener.
	 * 
	 * @param panel			The RequirementPanel which contains the JTextComponent.
	 * @param component		The JComboBox which will have its selection compared to the model. The name 
	 * 						of the JComboBox must match the name of a getter in Requirement after the 
	 * 						"get". For instance: for the method "getStatus", the name of the 
	 * 						JComboBox must be "Status".
	 */
	@SuppressWarnings("rawtypes")
	public ComboUpdateListener(RequirementPanel panel, JComboBox component) {
		this.panel = panel;
		this.component = component;
		this.defaultBorder = component.getBorder();
	}

	/** 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		checkIfUpdated();
	}

	/**
	 * Commented out part not needed for iteration 1 but may be needed in the future
	 * Checks if the field differs from the RequirementPanel's model and changes the style of the field accordingly.
	 */
	public void checkIfUpdated() {
		String base = ""; // the String value of the field in the panel's Requirement model that corresponds to the component

		// Compare base to the component's text to determine whether or not to highlight the field.
		if (base.equals((String) component.getSelectedItem())) {
			component.setBackground(Color.WHITE);
			component.setBorder(defaultBorder);
		}
		else {
			component.setBackground(new Color(243, 243, 209));
			component.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	}
}
