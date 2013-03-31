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
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Insert Description Here
 *
 * @author Sam Lalezari
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
	
	private void setUp(){
		JLabel dateLabel = new JLabel(date);
		this.add(dateLabel);
		
		JLabel timeLabel = new JLabel(time);
		this.add(timeLabel);
		
		JLabel changeLabel = new JLabel(changes);
		this.add(changeLabel);
		
		JLabel idLabel = new JLabel(id);
		this.add(idLabel);
		
		JLabel userLabel = new JLabel(user);
		this.add(userLabel);
	}
	
}
