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
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Log;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * Insert Description Here
 *
 * @author Chris Dunkers
 *
 * @version Mar 25, 2013
 *
 */
public class HistoryView extends JPanel {
	
	protected GridBagLayout layout;
	
	protected Requirement model;
	
	protected JTextPane historyView;	
	
	public HistoryView(Requirement model) {
		
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		this.model = model;
		
		// Add all components to this panel
		addComponents();
		
	}
	
	protected void addComponents(){
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.LINE_START; 
		c.fill = GridBagConstraints.BOTH; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(5,5,5,5); //top,left,bottom,right
		
//		List<Log> logs = getHistory(this.model);
		
		historyView = new JTextPane();
		String users = "cmd";
		int date = 10;
		historyView.setText("User: " + users + ", Date: " + date + ", Message: ");
				
		JScrollPane scrollPaneHistory = new JScrollPane(historyView);
				
		this.add(scrollPaneHistory, c);
	}
	
	private List<Log> getHistory(Requirement requirement){
		return requirement.getHistory().getHistory();
	}
	
}		

