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
 *  Joe Spicola
 *  Tushar Narayan
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * Holds the components responsible for creating the attachments,
 * notes and history tabs.
 *
 * @author Chris Dunkers
 *
 * @version Mar 25, 2013
 *
 */
@SuppressWarnings("serial")
public class RequirementTabsView extends JTabbedPane {

	/**
	 * Instantiates a new requirement tabs view.
	 *
	 * @param n the NotesView
	 * @param hv the HistoryView
	 * @param av the AssigneeView
	 */
	public RequirementTabsView(RequirementTab[] tabs){
		//TODO get history objects from database
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		for(RequirementTab t: tabs){
			addTab(t.getTabTitle(), t.getImageIcon(), t, t.getTooltipText());
		}
		/*addTab("Notes", new ImageIcon(), nv, "Add and modify notes");
		addTab("History", new ImageIcon(), hv, "View history of changes");
		JScrollPane scrollPaneAssigneeView = new JScrollPane(av);
		addTab("Assigned To", new ImageIcon(), scrollPaneAssigneeView, "Add and modify assignees");
		JScrollPane scrollPaneAcceptanceTestView = new JScrollPane(atv);
		addTab("Acceptance Tests", new ImageIcon(), scrollPaneAcceptanceTestView, "Add and modify acceptance tests");
		addTab("Dependencies", new ImageIcon(), dv, "View upstream and downstream dependencies");
		addTab("Tasks", new ImageIcon(), tv, "Edit and view tasks");*/
	}
}
