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
 *  Arica Liu
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Checks for whether or not the text in a given JTextComponent differs from the current model (a Requirement).
 * Adapted from JPage
 * 
 * Whenever a key is released in the TextUpdateListener's component, checkIfUpdated() is called. This method 
 * gets the component's name and looks up the value of the relevant field in panel's Requirement model. It then 
 * compares this value to the component's text to see if the text differs from the model. If the text 
 * differs, the style of the component is changed to show that it differs from the relevant field in the model.
 * Otherwise, the component's style is changed to be normal.
 *
 * @author Arica Liu
 *
 * @version Mar 20, 2013
 *
 */
public class TextUpdateListener implements KeyListener {
	protected final RequirementPanel panel;
	protected final JTextComponent component;
	protected final Border defaultBorder;

	/**
	 * Constructs a TextUpdateListener.
	 * 
	 * @param panel			The RequirementPanel which contains the JTextComponent.
	 * @param component		The JTextComponent which will have its text compared to the model. The name 
	 * 						of the JTextComponent must match the name of a getter in Requirement after the 
	 * 						"get". For instance: for the method "getTitle", the name of the 
	 * 						JTextComponent must be "Title".
	 */
	public TextUpdateListener(RequirementPanel panel, JTextComponent component) {
		this.panel = panel;
		this.component = component;
		this.defaultBorder = component.getBorder();
	}

	/* 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	/* 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		checkIfUpdated();
	}

	/*
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Checks if the field differs from the RequirementPanel's model and changes the style of the field accordingly.
	 */
	public void checkIfUpdated() {
		String base = ""; // the String value of the field in the panel's Requirement model that corresponds to the component

		// TODO: Need getModel() method in RequirementPanel
		
		// Get the base String to compare to the text of the JTextComponent
		try {
			// Get the field from the Requirement model that corresponds with the name of component.
			// For instance, if the component's name is "Title" Requirement#getTitle will be called.
			Object field = panel.getParent().getReqModel().getRequirement().getClass().getDeclaredMethod(
					"get" + component.getName()).invoke(panel.getParent().getReqModel().getRequirement());
			
			// If field is null, set base to an empty String.
			if (field == null) {
				base = "";
			}
			// If field is an instance of String, set base to that String.
			else if (field instanceof String) {
				base = (String) field;
			}
			// If field is an instance of User, set base to its username.
			else if (field instanceof User) {
				base = ((User) field).getUsername();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		// Compare base to the component's text to determine whether or not to highlight the field.
		if (base.equals(component.getText())) {
			component.setBackground(Color.WHITE);
			component.setBorder(defaultBorder);
		}
		else {
			component.setBackground(new Color(243, 243, 209));
			component.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	}
}