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
 *  Sam Lalezari
 *  Arica Liu
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * Hold a history entry.
 *
 * @author Sam Lalezari
 * @author Arica Liu
 *
 * @version Mar 31, 2013
 *
 */
public class HistoryObject extends JPanel {

	String date, time, changes, id, user;

	public HistoryObject(){
		this.date = "31/03/2013";
		this.time = "13:56:00";
		this.changes = "Status from OPEN to COMPLETE";
		this.id = "001";
		this.user = "demo";

		this.setUp();
	}
 
	@Override
	public String toString(){
		String eol = System.getProperty("line.separator"); 	
		return "#"+this.id+" - "+date+" at "+time+" by "+user+": "+changes;
	}
	private void setUp(){
		// Set the border variable
		Border loweredetched;
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

		// Create a new constraint variable
		GridBagConstraints c = new GridBagConstraints();

		// Construct thte panel
		JPanel panel = new JPanel();

		//Use a GridGagLayout manager
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		
		// The group of components to be displayed
		JLabel dateLabel = new JLabel(date);
		JLabel timeLabel = new JLabel(time);
		JLabel changeLabel = new JLabel(changes);
		JLabel idLabel = new JLabel(id);
		JLabel userLabel = new JLabel(user);
		JLabel lblOther2 = new JLabel("at");
		
		// Add components
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,0,10); //top,left,bottom,right
		idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD));
		panel.add(idLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.insets = new Insets(10,0,0,10);
		userLabel.setFont(idLabel.getFont().deriveFont(Font.ITALIC));
		panel.add(userLabel, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		panel.add(lblOther2, c);
		
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		panel.add(dateLabel, c);
		
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		panel.add(timeLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.insets = new Insets(0,10,10,10);
		panel.add(changeLabel, c);
		
		// Set botder
		panel.setBorder(loweredetched);
		
		// Add the panel
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(panel, c);	
	}

}
