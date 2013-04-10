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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.SaveIterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * Allow users to view a iteration.
 * Adapted from DefectView in project DefectTracker.
 * 
 * @author Arica Liu
 * @author Tushar Narayan
 *
 * @version Mar 24, 2013
 *
 */
public class IterationView extends JPanel {
	private static Iteration[] allIterations;
	public static void setAllIterations(Iteration[] allIterations){
		IterationView.allIterations = allIterations;
	}
	public static Iteration[] getAllIterations(){
		return IterationView.allIterations;
	}

	private IterationPanel mainPanel;
	private SaveIterationController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;

	/**
	 * Construct a new IterationView where the user can view (and edit) a iteration.
	 * 
	 * @param iteration	The iteration to show.
	 * @param tab		The Tab holding this IterationView (can be null)
	 */
	public IterationView(Iteration iteration, Mode edit, Tab tab) {
		containingTab = tab;
		Mode mode = edit;

		if(containingTab == null) {
			containingTab = new DummyTab();
		}

		inputEnabled = true;

		containingTab.setIcon(new ImageIcon());
		if (mode == Mode.CREATE) {
			containingTab.setTitle("Create Iteration");
			containingTab.setToolTipText("Create a new iteration");
		} else if (mode == Mode.EDIT) {
			setEditModeDescriptors(iteration);
		}

		// Instantiate the main create iteration panel
		mainPanel = new IterationPanel(this, iteration, mode);
		this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(mainPanel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);

		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				mainPanelScrollPane.repaint();
			}
		});

		this.add(mainPanelScrollPane, BorderLayout.CENTER);
		controller = new SaveIterationController(this);
	}


	/**
	 * Return the main panel with the data fields.
	 * 
	 * @return The main panel with the data fields
	 */
	public JPanel getIterationPanel() {
		return mainPanel;
	}


	/**
	 * Set whether the input is enabled.
	 * 
	 * @param enabled A boolean value indicating whether the input needs to be enabled or not
	 */
	public void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;
		mainPanel.setInputEnabled(enabled);
	}

	/**
	 * Return containgTab.
	 * 
	 * @return containingTab.
	 */
	public Tab getTab() {
		return containingTab;
	}

	/**
	 * Revalidates and repaints the scroll pane containing the DefectPanel
	 */
	public void refreshScrollPane() {
		mainPanelScrollPane.revalidate();
		mainPanelScrollPane.repaint();
	}

	/*
	 * This function will be used in future iterations.
	 * 
	 * @param iterations Iterations to be added.
	 */
	public void addIterations(Iteration[] iterations){
		//TODO: so far just a dummy class, but this will be where you get the array of iterations and put do with it what you will
	}
	
	/**
	 * Set the tab title, tooltip, and group name according to this Iteration.
	 * 
	 * @param iteration The input iteration
	 */
	public void setEditModeDescriptors(Iteration iteration) {
		containingTab.setTitle("Iteration #" + iteration.getId() + " - " + iteration.getIterationName());
		containingTab.setToolTipText("View iteration #" + iteration.getId() + " - " + iteration.getIterationName());
	}
}
