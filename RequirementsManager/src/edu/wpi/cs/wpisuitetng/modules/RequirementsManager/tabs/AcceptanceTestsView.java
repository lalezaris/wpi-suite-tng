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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveRequirementController;

/**
 * Insert Description Here
 *
 * @author Chris Dunkers
 *
 * @version Mar 26, 2013
 *
 */
public class AcceptanceTestsView extends JPanel {
	public enum AcceptanceStatus{
		INCOMPLETE,
		COMPLETE
	}
	
	protected GridBagLayout layout;
	
	protected JButton btnSave;
	protected JButton btnApplyChanges;
	protected JTextArea txtDescription;
	protected JTable testsTable;
	
	public AcceptanceTestsView() {
		
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		// Add all components to this panel
		addComponents();
		
	}
	
	protected void addComponents(){
		JLabel lblDescription = new JLabel("Acceptance Test Description:", JLabel.TRAILING);
		GridBagConstraints c = new GridBagConstraints();
		btnSave = new JButton("Save New Test");
//		save.setAction(new SaveChangesAction());
		btnApplyChanges = new JButton("Apply Changes");
//		btnApplyChanges.setAction(new SaveChangesAction());
		txtDescription = new JTextArea(10,35);
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		txtDescription.setBorder(btnSave.getBorder());
		
		c.anchor = GridBagConstraints.LINE_START; 
//		c.fill = GridBagConstraints.BOTH; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(5,5,5,5); //top,left,bottom,right
		this.add(btnSave, c);
		
		c.anchor = GridBagConstraints.LINE_START; 
//		c.fill = GridBagConstraints.BOTH; 
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(5,5,5,5); //top,left,bottom,right
		this.add(btnApplyChanges, c);
		
		c.anchor = GridBagConstraints.LINE_START; 
//		c.fill = GridBagConstraints.BOTH; 
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(5,5,5,5); //top,left,bottom,right
		this.add(lblDescription, c);
		
		JScrollPane scrollPaneDescription = new JScrollPane(txtDescription);
		c.anchor = GridBagConstraints.LINE_START; 
		c.fill = GridBagConstraints.BOTH; 
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridheight = 2;
		c.gridwidth = 2;
		c.insets = new Insets(5,5,5,5); //top,left,bottom,right
		this.add(scrollPaneDescription, c);
		
		
//		JScrollPane scrollPaneTests = new JScrollPane(testsTable);
				
//		this.add(scrollPaneTests, c);
	
	}
}
