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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


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
	JTable table;
	JScrollPane scrollPane;
	public RequirementListPanel(RequirementListView parent){
		
		super(new GridLayout(1,1));
		

		TableModel model = new RequirementTable();
		
		table = new JTable(model);
		
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		add(scrollPane);
	}
	
	
	public void addRequirement(Requirement req){
		((RequirementTable)table.getModel()).addRow(req);
		table.updateUI();
	}


	public void clearList() {
		((RequirementTable)table.getModel()).clear();
	}
}
