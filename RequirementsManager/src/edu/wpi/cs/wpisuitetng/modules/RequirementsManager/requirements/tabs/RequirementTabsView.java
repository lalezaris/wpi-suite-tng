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
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;


/**
 * Holds the components responsible for creating the attachments, notes and history tabs
 *
 * @author Chris Dunkers
 *
 * @version Mar 25, 2013
 *
 */
@SuppressWarnings("serial")
public class RequirementTabsView extends JTabbedPane {

	public RequirementTabsView(NotesView n) {

		HistoryView historyView = new HistoryView();
		historyView.setMaximumSize(historyView.getPreferredSize());
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		addTab("Notes", new ImageIcon(), /*new NotesView()*/ n,
		       "Notes");
		addTab("History", new ImageIcon(), historyView,
			       "History");
		//addTab("Attachments", new ImageIcon(), new AttachmentsView(),
			//       "Attachments");
	}
}
