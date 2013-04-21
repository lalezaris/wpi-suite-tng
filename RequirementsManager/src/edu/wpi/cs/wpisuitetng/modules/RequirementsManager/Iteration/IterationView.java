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
 *  Lauren Kahn
 *  Chris Dunkers
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.action.CancelIterationAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.action.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.CancelIterationController;
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
@SuppressWarnings("serial")
public class IterationView extends JPanel {
	private static Iteration[] allIterations;
	public static void setAllIterations(Iteration[] allIterations){
		IterationView.allIterations = allIterations;
	}
	public static Iteration[] getAllIterations(){
		return IterationView.allIterations;
	}

	private IterationPanel mainPanel;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	protected IterationModel iterationModel;
	IterationPanel.Mode mode;

	/**
	 * Construct a new IterationView where the user can view (and edit) a iteration.
	 * 
	 * @param iteration	The iteration to show.
	 * @param tab		The Tab holding this IterationView (can be null)
	 */
	public IterationView(Iteration iteration, Mode edit, Tab tab) {

		this.mode = edit;
		iterationModel = new IterationModel(iteration, this); // have to get the iteration we want, or create a new iteration if in the Create Mode

		containingTab = tab;

		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		containingTab.setIcon(new ImageIcon());
		if (mode == Mode.CREATE) {
			containingTab.setTitle("Create Iteration");
			containingTab.setToolTipText("Create a new iteration");
		} else if (mode == Mode.EDIT) {
			setEditModeDescriptors(iterationModel.getUneditedModel());
		}

		// Instantiate the main create iteration panel
		mainPanel = new IterationPanel(this/*, mode*/);

		mainPanel.getBtnSaveIteration().setAction(new SaveChangesAction(new SaveIterationController(this)));
		mainPanel.getBtnCancelIteration().setAction(new CancelIterationAction(new CancelIterationController(this)));

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
		iterationModel.updateModel(iteration, edit);
	}

	/**
	 * Set the tab title, tooltip, and group name according to this Iteration.
	 * 
	 * @param iteration The input iteration
	 */
	public void setEditModeDescriptors(Iteration iteration) {
		containingTab.setTitle("Iteration #" + iterationModel.getUneditedModel().getId() + " - " + iterationModel.getUneditedModel().getIterationName());
		containingTab.setToolTipText("View iteration #" + iterationModel.getUneditedModel().getId() + " - " + iterationModel.getUneditedModel().getIterationName());
	}

	/**
	 * Check to make sure that all the fields are correctly filled in. If multiple errors are present, it returns
	 * the error that is lowest. So if startDate is after endDate and the iteration name is missing, this will
	 * return 1.
	 * 
	 * @return	1 if field(s) are missing,
	 * 			2 if startDate >= endDate,
	 * 			3 if iteration name already exists,
	 * 			4 if dates overlap,
	 * 			0 otherwise, so no input errors
	 */
	public int checkRequiredFields(Iteration[] iterations){
		mainPanel.setMultipleVisibilities(new JComponent[]{mainPanel.getLblIterationNameError(),mainPanel.getLblStartDateError(),mainPanel.getLblEndDateError(),
				mainPanel.getLblDateError(),mainPanel.getLblIterationNameExistsError(),mainPanel.getLblDateOverlapError()} , false);

		if(mainPanel.getTxtIterationName().getText().equals("") || mainPanel.getTxtIterationName().getText() == null){//no iteration name entered
			mainPanel.lblIterationNameError.setVisible(true);
		}
		if(mainPanel.getTxtStartDate().getText().equals(null) || mainPanel.getTxtStartDate().getText().equals("")){//no start date entered
			mainPanel.lblStartDateError.setVisible(true);
		}
		if(mainPanel.getTxtEndDate().getText() == null || mainPanel.getTxtEndDate().getText().equals("")){//no end date entered
			mainPanel.lblEndDateError.setVisible(true);
		}
		if(mainPanel.getLblIterationNameError().isVisible() || mainPanel.getLblStartDateError().isVisible() || mainPanel.getLblEndDateError().isVisible()){
			//if any fields were missing
			return 1;
		}

		Date startDate = mainPanel.StringToDate(mainPanel.getTxtStartDate().getText());
		Date endDate = mainPanel.StringToDate(mainPanel.getTxtEndDate().getText());
		if (startDate.compareTo(endDate) > 0) {//start date is after the end date
			mainPanel.getLblDateError().setVisible(true);
			return 2;
		}

		Iteration[] array = iterations;
		String idName = mainPanel.getTxtIterationName().getText();
		for (int i = 0; i < array.length; i++) {
			if (this.iterationModel.getUneditedModel().getId() != array[i].getId()) {
				if(idName.equals(array[i].getIterationName())) {//duplicate iteration name found
					mainPanel.getLblIterationNameExistsError().setVisible(true);
					return 3;
				}
				else if (! (endDate.compareTo(array[i].getStartDate()) <= 0
						|| startDate.compareTo(array[i].getEndDate()) >= 0)){
					mainPanel.getLblDateOverlapError().setVisible(true);
					return 4;
				}
			}
		}

		//no errors
		return  0;
	}

	/**
	 * Shows message dialog if there is an error retrieving iterations
	 * 
	 * @param string the error
	 */
	public void errorReceivingIterations(String string) {
		JOptionPane.showMessageDialog(this,string);
	}

	/**
	 * Revalidates and repaints the scroll pane containing the DefectPanel
	 */
	public void refreshScrollPane() {
		mainPanelScrollPane.revalidate();
		mainPanelScrollPane.repaint();
	}

	/**
	 * This function will be used in future iterations.
	 * 
	 * @param iterations Iterations to be added.
	 */
	public void addIterations(Iteration[] iterations){
		//TODO: so far just a dummy class, but this will be where you get the array of iterations and put do with it what you will
	}

	/**
	 * @return the iterationModel
	 */
	public IterationModel getIterationModel() {
		return iterationModel;
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
	 * Gets the mode
	 * 
	 * @return the mode of the iteration panel
	 */
	public IterationPanel.Mode getMode(){
		return this.mode;
	}

}