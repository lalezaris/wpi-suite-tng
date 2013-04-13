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
 *  Arica Liu
 *  Lauren Kahn
 *  Chris Dunkers
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.action.CancelIterationAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.action.SaveChangesAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.CancelIterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.SaveIterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.IntegerField;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;

/**
 * Panel to display and edit the basic fields for a Iteration.
 * Adapted from DefectPanel in project DefectTracker.
 *
 * @author Tushar Narayan
 * @author Arica Liu
 *
 * @version Mar 29, 2013
 *
 */
@SuppressWarnings("serial")
public class IterationPanel extends JPanel {

	/**
	 * The Enum Mode.
	 */
	public enum Mode {
		CREATE,
		EDIT, 
	}

	protected Mode editMode;

	/** The Iteration displayed in this panel */
	protected Iteration model; 
	protected Iteration uneditedModel;

	/** The parent view */
	protected IterationView parent;

	/** Form elements */
	protected JTextField txtIterationName;
	protected JLabel txtStartDate;
	protected JButton selectStartDate = new JButton("Select Start Date");
	protected JLabel txtEndDate;
	protected JButton selectEndDate = new JButton(" Select End Date ");
	protected JButton saveIterationTop;
	protected JButton btnSaveIteration;
	protected JButton btnCancelIteration;
	protected JFrame f = new JFrame();

	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;

	/**Error labels*/
	JLabel lblIterationNameError = new JLabel("ERROR: Must have a iteration name", LABEL_ALIGNMENT);
	JLabel lblStartDateError = new JLabel("ERROR: Must have a start date", LABEL_ALIGNMENT);
	JLabel lblEndDateError = new JLabel("ERROR: Must have a end date", LABEL_ALIGNMENT);
	JLabel lblDateError = new JLabel("ERROR: The start date must be before the end date", LABEL_ALIGNMENT);
	JLabel lblIterationNameExistsError = new JLabel("ERROR: The iteration name already exists", LABEL_ALIGNMENT);
	JLabel lblDateOverlapError = new JLabel("ERROR: The iteration is overlapping with already existing Iteration(s)", LABEL_ALIGNMENT);

	/** The layout manager for this panel */
	protected GridBagLayout layout;

	/** The other panels */
	protected JPanel panelOverall;
	protected JPanel panelOne;
	protected JPanel panelTwo;

	/** The layout managers for other panels */
	protected GridBagLayout layoutOverall;
	protected GridBagLayout layoutOne;
	protected GridBagLayout layoutTwo;

	/** Constants used to layout the form */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;

	/**
	 * Construct a IterationPanel for creating or editing a given Iteration.
	 *
	 * @param parent The parent of the iteration
	 * @param iteration The Iteration to edit
	 * @param mode the mode
	 */
	public IterationPanel(IterationView parent, Mode mode) {
		this.parent = parent;
		this.editMode = mode;

		// Indicate that input is enabled
		inputEnabled = true;

		//Use a grid bag layout manager
		layout = new GridBagLayout();
		this.setLayout(layout);

		// Add all components to this panel
		addComponents();
	}

	/**
	 * Add the components to the panel and place constraints on them
	 * using the GridBagLayout manager.
	 *
	 */
	protected void addComponents() {
		// Create new constraint variables
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints cOverall = new GridBagConstraints();
		GridBagConstraints cOne = new GridBagConstraints();
		GridBagConstraints cTwo = new GridBagConstraints();

		// Construct all of the components for the form
		panelOverall = new JPanel();
		panelOne = new JPanel();
		panelTwo = new JPanel();

		txtIterationName = new JTextField("", 20);;
		txtStartDate = new JLabel("");
		txtEndDate = new JLabel("");

		// Buttons for "Save" and "Cancel"
		btnSaveIteration = new JButton("Save");
		btnCancelIteration = new JButton("Cancel");

		// Construct labels for the form fields
		JLabel lblIterationNumber = new JLabel("", LABEL_ALIGNMENT);
		JLabel lblStartDate = new JLabel("Start Date:", LABEL_ALIGNMENT);
		JLabel lblEndDate = new JLabel("End Date:", LABEL_ALIGNMENT);

		//Panel One - panel at the top --------------------------------------------------------------------------------------------------------------
		//Use a GridGagLayout manager
		layoutOne = new GridBagLayout();
		panelOne.setLayout(layoutOne);	

		cOne.anchor = GridBagConstraints.LINE_START; 
		cOne.gridx = 0;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		cOne.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panelOne.add(lblIterationNumber, cOne);

		cOne.gridx = 2;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		panelOne.add(txtIterationName, cOne);

		cOne.gridx = 4;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		lblIterationNameError.setForeground(Color.RED);
		lblIterationNameError.setVisible(false);
		panelOne.add(lblIterationNameError, cOne);

		lblIterationNameExistsError.setForeground(Color.RED);
		lblIterationNameExistsError.setVisible(false);
		panelOne.add(lblIterationNameExistsError, cOne);

		//Panel Two - panel below panel one -------------------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutTwo = new GridBagLayout();
		panelTwo.setLayout(layoutTwo);

		cTwo.anchor = GridBagConstraints.LINE_END;
		cTwo.insets = new Insets(10,10,10,0);
		cTwo.gridx = 0;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.anchor = GridBagConstraints.LINE_START;
		panelTwo.add(lblStartDate, cTwo);

		cTwo.anchor = GridBagConstraints.LINE_START;
		cTwo.gridx = 2;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		txtStartDate.setEnabled(true);
		panelTwo.add(txtStartDate, cTwo);

		cTwo.gridx = 4;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		panelTwo.add(selectStartDate, cTwo);

		selectStartDate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				DatePicker dp = new DatePicker(f);
				Point bP = selectStartDate.getLocationOnScreen();
				dp.d.setLocation(bP.x, bP.y + selectStartDate.getHeight()); 
				dp.d.setVisible(true);
				txtStartDate.setText(dp.setPickedDate());
			}
		});

		cTwo.gridx = 6;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		lblStartDateError.setVisible(false);
		lblStartDateError.setForeground(Color.RED);
		panelTwo.add(lblStartDateError, cTwo);

		cTwo.anchor = GridBagConstraints.LINE_END;
		cTwo.gridx = 0;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		panelTwo.add(lblEndDate, cTwo);

		cTwo.anchor = GridBagConstraints.LINE_START;
		cTwo.gridx = 2;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		txtEndDate.setEnabled(true);
		panelTwo.add(txtEndDate, cTwo);

		cTwo.gridx = 4;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		panelTwo.add(selectEndDate, cTwo);

		selectEndDate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				DatePicker dp = new DatePicker(f);
				Point bP = selectEndDate.getLocationOnScreen();
				dp.d.setLocation(bP.x, bP.y + selectEndDate.getHeight()); 
				dp.d.setVisible(true);
				txtEndDate.setText(dp.setPickedDate());
			}
		});

		cTwo.gridx = 6;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		lblEndDateError.setForeground(Color.RED);
		lblEndDateError.setVisible(false);
		panelTwo.add(lblEndDateError, cTwo);

		cTwo.gridx = 0;
		cTwo.gridy = 2;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		panelTwo.add(btnSaveIteration, cTwo);

		cTwo.gridx = 2;
		cTwo.gridy = 2;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		panelTwo.add(btnCancelIteration, cTwo);

		cTwo.gridx = 3;
		cTwo.gridy = 2;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 7;
		lblDateError.setVisible(false);
		lblDateError.setForeground(Color.RED);
		panelTwo.add(lblDateError, cTwo);

		lblDateOverlapError.setVisible(false);
		lblDateOverlapError.setForeground(Color.RED);
		panelTwo.add(lblDateOverlapError, cTwo);

		//Panel Overall - panel holding all other panels --------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutOverall = new GridBagLayout();
		panelOverall.setLayout(layoutOverall);
		//Overall Panel
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.anchor = GridBagConstraints.LINE_START;
		panelOverall.add(panelOne, cOverall);

		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 1;
		cOverall.anchor = GridBagConstraints.LINE_START;
		panelOverall.add(panelTwo, cOverall);

		// add to this Panel -----------------------------------------------------------------------------------------------------------------
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(panelOverall, c);		
	}

	/**
	 * Return the parent IterationView.
	 * 
	 * @return the parent IterationView.
	 */
	public IterationView getParent() {
		return parent;
	}

	/**
	 * Set certain input fields to Enabled or Not Enabled.
	 * 
	 * @param enabled A boolean indicating if the fields need to be enabled or not.
	 */
	protected void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;

		txtIterationName.setEnabled(enabled);
	}

//	/**
//	 * Check to see if the given IntegerField is empty.  
//	 * 
//	 * @param intf The IntergerField passed in.
//	 * @return -1 if the string is less than 0 or blank;
//	 * 		   the integer value otherwise.
//	 * 
//	 */
//	protected int getValue(IntegerField intf){
//		if(intf.getText().equals(null) || intf.getText().equals("")){
//			return -1;
//		} else {
//			return Integer.parseInt(intf.getText());
//		}		
//	}

	/**
	 * Gets the editMode.
	 *
	 * @return the editMode
	 */
	public Mode getEditMode() {
		return editMode;
	}
	
	/**
	 * 
	 * Sets the visibility of multiple JComponents to the given state.
	 * 
	 * @param components The JComponents to have their visibility set to b
	 * @param b True for visible, false for not visible
	 */
	public void setMultipleVisibilities(JComponent[] components, boolean b){
		for(JComponent j: components){
			j.setVisible(b);
		}
	}

	/**
	 * Convert a String to Date. 
	 * 
	 * @param aDate The string to be converted.
	 * @return The resulting Date.
	 */
	public Date StringToDate(String aDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(aDate);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return convertedDate;
	}

	/**
	 * Checks to see if any changes have been made.
	 * 
	 * @return true if changes has been made otherwise false
	 */
	public boolean isThereChanges(){

		if(editMode == Mode.CREATE){		
			if (!(this.txtIterationName.getText().trim().equals("") || txtIterationName.getText().trim().equals(null))){//if old and new are not the same
				return true;
			}

			if(!(txtStartDate.getText().trim().equals("") || txtStartDate.getText().trim().equals(null))){
				return true;
			} 	

			if(!(txtEndDate.getText().trim().equals("") || txtEndDate.getText().trim().equals(null))){
				return true;
			} 
		} else {
			Iteration oldI = getParent().getIterationModel().getUneditedModel();

			if (oldI.getName().compareTo(txtIterationName.getText()) != 0){//if old and new are not the same
				return true;
			}

			if (oldI.getStartDate().compareTo(StringToDate(txtStartDate.getText())) != 0){//if old and new are not the same
				return true;
			}

			if (oldI.getEndDate().compareTo(StringToDate(txtEndDate.getText())) != 0){//if old and new are not the same
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the btnSaveIteration
	 */
	public JButton getBtnSaveIteration() {
		return btnSaveIteration;
	}

	/**
	 * @return the btnCancelIteration
	 */
	public JButton getBtnCancelIteration() {
		return btnCancelIteration;
	}

	/**
	 * @return the txtIterationName
	 */
	public JTextField getTxtIterationName() {
		return txtIterationName;
	}

	/**
	 * @return the txtStartDate
	 */
	public JLabel getTxtStartDate() {
		return txtStartDate;
	}

	/**
	 * @return the txtEndDate
	 */
	public JLabel getTxtEndDate() {
		return txtEndDate;
	}

	/**
	 * @return the lblIterationNameError
	 */
	public JLabel getLblIterationNameError() {
		return lblIterationNameError;
	}

	/**
	 * @return the lblStartDateError
	 */
	public JLabel getLblStartDateError() {
		return lblStartDateError;
	}

	/**
	 * @return the lblEndDateError
	 */
	public JLabel getLblEndDateError() {
		return lblEndDateError;
	}

	/**
	 * @return the lblDateError
	 */
	public JLabel getLblDateError() {
		return lblDateError;
	}

	/**
	 * @return the lblIterationNameExistsError
	 */
	public JLabel getLblIterationNameExistsError() {
		return lblIterationNameExistsError;
	}

	/**
	 * @return the lblDateOverlapError
	 */
	public JLabel getLblDateOverlapError() {
		return lblDateOverlapError;
	}

	/**
	 * @param editMode: the editMode to set
	 */
	public void setEditMode(Mode editMode) {
		this.editMode = editMode;
	}

	/**
	 * @return the layout
	 */
	public GridBagLayout getPanelLayout() {
		return layout;
	}
	
}