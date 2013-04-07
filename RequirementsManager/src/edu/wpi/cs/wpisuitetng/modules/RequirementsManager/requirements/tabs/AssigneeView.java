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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
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
	private DefaultListModel<String> allUserLM;
	private DefaultListModel<String> assignedUserLM;
	private JList<String> assignedUserList;
	private JButton btnAdd;
	private JButton btnRemove;
	private JPanel buttonPanel;
	
	public AssigneeView(){
		this.setLayout(new FlowLayout());

		allUserLM = new DefaultListModel<String>();
		assignedUserLM = new DefaultListModel<String>();
		
		allUserLM.addElement("USER"); 	//TODO pull list from database
		assignedUserLM.addElement("USER");
		
		allUserList = new JList<String>(allUserLM);
		assignedUserList = new JList<String>(assignedUserLM);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1,0,5));
		
		btnAdd = new    JButton("ADD");
		btnRemove = new JButton("REMOVE");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnRemove);
		
		this.add(allUserList);
		this.add(buttonPanel);
		this.add(assignedUserList);
	}

}
