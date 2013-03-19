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
 *  Arica Liu
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.Component;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarController;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;

/**
 * Controller for the Reguirement Manager toolbar.
 * 
 * @author Adapted from Defect Tracker
 * @author Arica liu
 * 
 * @version March 18, 2013
 */

public class ToolbarController extends DefaultToolbarController implements ChangeListener {

	private ToolbarGroupView relevantTabGroup;
	
	/**
	 * Control the given DefaultToolbarView based on the state of the tabs in tabController.
	 * @param toolbarView The toolbar to add/remove groups from
	 * @param tabController The MainTabController to listen to for changes
	 */
	public ToolbarController(DefaultToolbarView toolbarView, MainTabController tabController) {
		super(toolbarView);
		tabController.addChangeListener(this);
	}

	private void setRelevantTabGroup(ToolbarGroupView group) {
		// keep track of only one toolbar group for the active tab
		if(relevantTabGroup != null) {
			setRelevant(relevantTabGroup, false);
		}
		relevantTabGroup = group;
		if(relevantTabGroup != null) {
			setRelevant(relevantTabGroup, true);
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO: there has to be a cleaner way to do this
		if(e.getSource() instanceof MainTabView) {
			MainTabView view = (MainTabView) e.getSource();
			Component selectedComponent = view.getSelectedComponent();
			if(selectedComponent instanceof IToolbarGroupProvider) {
				IToolbarGroupProvider provider = (IToolbarGroupProvider) selectedComponent;
				setRelevantTabGroup(provider.getGroup());
			} else {
				setRelevantTabGroup(null);
			}
		}
	}
}