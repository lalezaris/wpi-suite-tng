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
 *  Tyler Stone
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.GridLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * The basic dashboard for the Requirements Manager Tab.
 * Adapted from Defect Tracker
 *
 * @author Tyler Stone 
 * @modified by Chris H on Mar 24
 * @version Mar 17, 2013
 *
 */
public class DashboardTab extends JPanel{
	
	RequirementListPanel requirementListPanel;
	/**
	 * Class Constructor
	 * 
	 */
	public DashboardTab() {
		super (new GridLayout(1,1));
		//JLabel testLabel = new JLabel("This is the dashboard panel.");
		//this.add(testLabel);
		requirementListPanel = new RequirementListPanel();
		if (staticReqListPanel == null){
			staticReqListPanel = requirementListPanel;
		}
		
		final JPanel p = this;
		p.addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				// TODO Auto-generated method stub
				//if ( (HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0
				//		&& p.isShowing())
				if (HierarchyEvent.SHOWING_CHANGED != 0 && p.isShowing())
				{
					System.out.println("Dashboard Gained View");
					requirementListPanel.refreshList();
				}
					
			}
			
		});
		
		this.add(requirementListPanel);
	}
	
	
	
	/**
	 * Jenky as all get out. The first instance of a DashboardTab will set the class'
	 * staticReqListPanel to be its own. Then when DashboardTab.refreshRequiremnts()
	 * is called, the staticReqListPanel will be updated. This is NOT A GOOD PRACTICE
	 * but it works for now.... The consequence is that we cannot have more than one dashboardTab
	 * at once. 
	 */
	static RequirementListPanel staticReqListPanel;
	/**
	 * Jenky method that should probably not exist. This method will force the requirement table to refresh
	 */
	public static void refreshRequirements(){
		staticReqListPanel.refreshList();
	}
	
	/**
	 * This allows us to reference the selected requirement and return it for use with 
	 * the toolbar.
	 * 
	 * @return the static list panel
	 */
	public static RequirementListPanel getDashboardListPanel() {
		return staticReqListPanel;
	}
}
