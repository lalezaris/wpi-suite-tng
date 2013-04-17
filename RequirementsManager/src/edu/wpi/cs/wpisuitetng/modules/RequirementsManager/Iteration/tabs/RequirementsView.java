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
 *  Tushar Narayan
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.tabs;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * The JPanel for all requirements in iteration.
 *
 * @author Tushar Narayan
 *
 * @version April 16, 2013
 *
 */
@SuppressWarnings("serial")
public class RequirementsView extends JPanel {	
	private JList<Requirement> list;
	private DefaultListModel<Requirement> listModel;

	protected ArrayList<Requirement> requirementAL;

	/**
	 * RequirementsView Constructor.
	 * 
	 * @param parent Iteration to view the requirements of 
	 */
	public RequirementsView(IterationView parent) {
		super(new BorderLayout());
		
		listModel = new DefaultListModel<Requirement>();
		
		//Create the list and put it in a scroll pane.
		list = new JList(listModel);
		list.setLayoutOrientation(JList.VERTICAL);

		JScrollPane listScrollPane = new JScrollPane(list);

		add(listScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Changes the requirement array list to the given array list.
	 * 
	 * @param requirements
	 */
	public void setRequirementList(ArrayList<Requirement> requirements) {
		this.requirementAL = requirements;
		for(int i = 0; i <requirementAL.size(); i++){
			if(!listModel.contains(requirementAL.get(i))){
				listModel.add(0, requirementAL.get(i));}
		}
		redisplay();
		repaint();
		revalidate();
		redisplay();
	}

	protected void redisplay(){

		listModel = new DefaultListModel<Requirement>();

		for(int i = 0; i <this.requirementAL.size(); i++){
			if(!listModel.contains(requirementAL.get(i))){
				listModel.add(0, requirementAL.get(i));}
		}
		
		if (list!=null)
		this.remove(list);
		
		//Create the list and put it in a scroll pane.
		list = new JList<Requirement>(listModel);
		list.setLayoutOrientation(JList.VERTICAL);

		JScrollPane listScrollPane = new JScrollPane(list);

		add(listScrollPane, BorderLayout.CENTER);
	}
	
	
	/**
	 * Gets requirements list.
	 * 
	 * @return list of requirements
	 */
	public ArrayList<Requirement> getRequirementsList() {
		return this.requirementAL;
	}
}