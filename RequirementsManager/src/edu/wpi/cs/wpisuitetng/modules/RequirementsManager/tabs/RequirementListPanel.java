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
 *  Tianyu Li
 *  Mike Perrone
 *  Chris Hanna
 *  
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * The innermost JPanel for the list of all requirements tab, which displays the requirement's information
 *
 * @author Tianyu Li
 *
 * @version Mar 21, 2013
 *
 */
public class RequirementListPanel extends JPanel {

	JTextArea list;
	
	public RequirementListPanel(RequirementListView parent){
		list = new JTextArea();
		list.setEditable(false);
		
		list.setText("REQ LIST VIEW\n");
		add(list);
	}
	
	
	public void addRequirement(Requirement req){
		list.append(req.getTitle() + "\n");
	}


	public void clearList() {
		list.setText("REQ LIST VIEW" + "\n");
	}
}
