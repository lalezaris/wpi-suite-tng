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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AcceptanceTestsView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AssigneeView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.HistoryView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.NotesView;

/**
 * @author Tushar Narayan
 * 
 * @version April 16, 2013
 *
 */
@SuppressWarnings("serial")
public class IterationTabsView extends JTabbedPane{
	/**
	 * Instantiates a new iterations tabs view.
	 *
	 * @param r the requirementsView
	 */
	public IterationTabsView(RequirementsView r) {
		
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		addTab("Requirements View", new ImageIcon(), r, "Requirements");
	}
}
