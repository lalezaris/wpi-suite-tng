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

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Insert Description Here
 *
 * @author Sam Lalezari
 *
 * @version Apr 7, 2013
 *
 */
public class AssigneeView extends JPanel{
	
	private ArrayList<String> allUserAL;
	private ArrayList<String> assignedUserAL;
	private JList<String> allUserList;
	private JList<String> assignedUserList;
	private JButton btnAdd;
	private JButton btnRemove;
	
	public AssigneeView(){
		this.setLayout(new BorderLayout());
		
		this.add(allUserList, BorderLayout.WEST);
		this.add(assignedUserList, BorderLayout.EAST);
	}

}
