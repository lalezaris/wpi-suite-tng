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
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.SaveIterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.Tab;

/**
 * Allows users to view a iteration
 * Adapted from DefectView in project DefectTracker
 * 
 * @author Arica Liu
 * @author Tushar Narayan
 *
 * @version Mar 24, 2013
 *
 */
public class IterationView extends JPanel {
	
	
	private JButton saveButton;
	private IterationPanel mainPanel;
	private SaveIterationController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	
	/**
	 * Constructs a new IterationView where the user can view (and edit) a iteration.
	 * 
	 * @param iteration	The iteration to show.
	 * @param tab		The Tab holding this IterationView (can be null)
	 */
	public IterationView(Iteration iteration, Tab tab) {
		containingTab = tab;
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		
		inputEnabled = true;
		
		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("Create Iteration");
		
		// Instantiate the main create iteration panel
		mainPanel = new IterationPanel(this, iteration);
		this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(mainPanel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				//SwingUtilities.invokeLater(new Runnable(){
				//	public void run(){
						mainPanelScrollPane.repaint();
				//	}
				//});
			}
		});
		
		this.add(mainPanelScrollPane, BorderLayout.CENTER);
		controller = new SaveIterationController(this);
	//	controller.save();
	}


	/**
	 * Returns the main panel with the data fields
	 * 
	 * @return the main panel with the data fields
	 */
	public JPanel getIterationPanel() {
		return mainPanel;
	}
	

	/**
	 * Sets whether the input is enabled
	 * 
	 * @param enabled
	 */
	public void setInputEnabled(boolean enabled) {
	    inputEnabled = enabled;
	
	    saveButton.setEnabled(enabled);
	    mainPanel.setInputEnabled(enabled);
	}
	
	public Tab getTab() {
		return containingTab;
	}
	
    public void addIterations(Iteration[] iterations){
	//so far just a dummy class, but this will be where you get the array of iterations and put do with it what you will
    }
}
