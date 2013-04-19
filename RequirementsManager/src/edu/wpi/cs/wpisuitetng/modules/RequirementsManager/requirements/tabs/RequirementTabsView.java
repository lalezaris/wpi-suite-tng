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
	public RequirementTabsView(NotesView nv, HistoryView hv, AcceptanceTestsView atv, AssigneeView av, DependenciesView dv) {
		//TODO get history objects from database
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		addTab("Notes", new ImageIcon(), nv, "Add and modify notes");
		addTab("History", new ImageIcon(), hv, "View history of changes");
		addTab("Assigned To", new ImageIcon(), av, "Add and modify assignees");
		addTab("Acceptance Tests", new ImageIcon(), atv, "Add and modify acceptance tests");
		addTab("Dependencies", new ImageIcon(), dv, "View upstream and downstream dependencies");
	}
}
