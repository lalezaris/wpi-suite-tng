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
 *  Michael Perrone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.AllRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.IntegerField;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;

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
public class IterationPanel extends JPanel implements FocusListener {

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
	protected JPlaceholderTextField txtIterationName;
	protected JLabel txtStartDate;
	protected JButton selectStartDate = new JButton("Select Start Date");
	protected JLabel txtEndDate;
	protected JButton selectEndDate = new JButton(" Select End Date ");
	protected JButton saveIterationTop;
	protected JButton btnSaveIteration;
	protected JButton btnCancelIteration;
	protected IntegerField txtEstimate;
	protected JFrame f = new JFrame();

	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;

	/**Error labels*/
	JLabel lblIterationNameError = new JLabel("ERROR: Must have a iteration name", LABEL_ALIGNMENT);
	JLabel lblStartDateError = new JLabel("ERROR: Must have a start date", LABEL_ALIGNMENT);
	JLabel lblEndDateError = new JLabel("ERROR: Must have a end date", LABEL_ALIGNMENT);
	JLabel lblDateError = new JLabel("<html>ERROR: The start date must be <p>before the end date</p></html>", LABEL_ALIGNMENT);
	JLabel lblIterationNameExistsError = new JLabel("ERROR: The iteration name already exists", LABEL_ALIGNMENT);
	JLabel lblDateOverlapError = new JLabel("<html>ERROR: The iteration is overlapping with <p>already existing Iteration(s)</p></html>", LABEL_ALIGNMENT);

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


	protected JTable table;
	protected RequirementListPanel reqListPanel;
	protected RequirementTableModel requirementTableModel;
	
	protected JLabel lblStartDate;
	protected JLabel lblEndDate;

	/**
	 * Construct a IterationPanel for creating or editing a given Iteration.
	 *
	 * @param parent the iteration view for the iteration panel
	 */
	public IterationPanel(IterationView parent) {
		this.parent = parent;

		// Indicate that input is enabled
		inputEnabled = true;

		//Use a grid bag layout manager
		layout = new GridBagLayout();

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

		txtIterationName = new JPlaceholderTextField("Enter iteration name here", 20);
		txtIterationName.addFocusListener(this);
		txtStartDate = new JLabel("");
		txtStartDate.addFocusListener(this);
		txtEndDate = new JLabel("");
		txtEndDate.addFocusListener(this);
		txtEstimate = new IntegerField(4);
		
		txtEstimate.setEnabled(false);

		// Buttons for "Save" and "Cancel"
		btnSaveIteration = new JButton("Save");
		btnCancelIteration = new JButton("Cancel");

		// Construct labels for the form fields
		JLabel lblIterationNumber = new JLabel("", LABEL_ALIGNMENT);
		lblStartDate = new JLabel("Start Date:", LABEL_ALIGNMENT);
		lblEndDate = new JLabel("End Date:", LABEL_ALIGNMENT);
		JLabel lblEstimate = new JLabel("Estimate:", LABEL_ALIGNMENT);

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


		//Panel Two - panel below panel one -------------------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutTwo = new GridBagLayout();
		panelTwo.setLayout(layoutTwo);

		cTwo.anchor = GridBagConstraints.LINE_START;
		cTwo.insets = new Insets(10,10,10,0);
		cTwo.gridx = 0;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		panelTwo.add(lblStartDate, cTwo);

		cTwo.anchor = GridBagConstraints.LINE_START;
		cTwo.gridx = 2;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		txtStartDate.setEnabled(true);
		panelTwo.add(txtStartDate, cTwo);

		cTwo.anchor = GridBagConstraints.LINE_START;
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

		cTwo.anchor = GridBagConstraints.LINE_START;
		cTwo.gridx = 0;
		cTwo.gridy = 5;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 7;
		lblStartDateError.setVisible(false);
		lblStartDateError.setForeground(Color.RED);
		panelTwo.add(lblStartDateError, cTwo);

		cTwo.anchor = GridBagConstraints.LINE_START;
		cTwo.gridx = 0;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		panelTwo.add(lblEndDate, cTwo);

		cTwo.anchor = GridBagConstraints.LINE_START;
		cTwo.gridx = 2;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		txtEndDate.setEnabled(true);
		panelTwo.add(txtEndDate, cTwo);

		cTwo.gridx = 4;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		panelTwo.add(selectEndDate, cTwo);
		
		cTwo.gridx = 0;
		cTwo.gridy = 2;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		panelTwo.add(lblEstimate, cTwo);
		
		cTwo.gridx = 2;
		cTwo.gridy = 2;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		panelTwo.add(txtEstimate, cTwo);

		selectEndDate.addActionListener(new ActionListener()
		{
			/**
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent ae)
			{
				DatePicker dp = new DatePicker(f);
				Point bP = selectEndDate.getLocationOnScreen();
				dp.d.setLocation(bP.x, bP.y + selectEndDate.getHeight()); 
				dp.d.setVisible(true);
				txtEndDate.setText(dp.setPickedDate());
			}
		});

		cTwo.gridx = 0;
		cTwo.gridy = 6;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 7;
		lblEndDateError.setForeground(Color.RED);
		lblEndDateError.setVisible(false);
		panelTwo.add(lblEndDateError, cTwo);

		cTwo.gridx = 0;
		cTwo.gridy = 3;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		panelTwo.add(btnSaveIteration, cTwo);

		cTwo.gridx = 2;
		cTwo.gridy = 3;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		panelTwo.add(btnCancelIteration, cTwo);

		cTwo.gridx = 0;
		cTwo.gridy = 7;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 7;
		lblDateError.setVisible(false);
		lblDateError.setForeground(Color.RED);
		panelTwo.add(lblDateError, cTwo);

		lblDateOverlapError.setVisible(false);
		lblDateOverlapError.setForeground(Color.RED);
		panelTwo.add(lblDateOverlapError, cTwo);

		cTwo.gridx = 0;
		cTwo.gridy = 4;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 7;
		lblIterationNameError.setForeground(Color.RED);
		lblIterationNameError.setVisible(false);
		panelTwo.add(lblIterationNameError, cTwo);

		lblIterationNameExistsError.setForeground(Color.RED);
		lblIterationNameExistsError.setVisible(false);
		panelTwo.add(lblIterationNameExistsError, cTwo);

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
		c.fill = GridBagConstraints.BOTH;
		JPanel left = new JPanel();
		left.setLayout(new GridBagLayout());
		GridBagConstraints cLeft = new GridBagConstraints();
		cLeft.anchor = GridBagConstraints.FIRST_LINE_START;
		cLeft.gridx = 0;
		cLeft.gridy = 0;
		cLeft.weightx = 0.1;
		cLeft.weighty = 0.1;
		left.add(panelOverall,cLeft);
		JScrollPane leftScroll = new JScrollPane(left);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(leftScroll);


		JPanel right = new JPanel();
		if(this.getParent().getMode() == Mode.EDIT){
			right.setLayout(new BorderLayout());
			reqListPanel = new RequirementListPanel(MainTabController.getController());
			String[] removeFields = {"iteration"};
			reqListPanel.getFilterController().getPanel().removeFields(removeFields);
			right.add(reqListPanel,BorderLayout.CENTER);
			JPanel rightSub = new JPanel();
			rightSub.setLayout(new FlowLayout());
			rightSub.add(new JLabel("Requirements in this iteration:"),1.0);
			right.add(rightSub,BorderLayout.NORTH);
		}
		JScrollPane rightScroll = new JScrollPane(right);
		
		splitPane.setRightComponent(rightScroll);

		this.setLayout(new BorderLayout());
		new AllRequirementController(this).retrieve();
		this.add(splitPane,BorderLayout.CENTER);
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

		if(this.getParent().getMode() == Mode.CREATE){	
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
			if(parent.getIterationModel().getUneditedModel().compareTo(Iteration.getBacklog()) != 0){
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
			if(((RequirementTableModel)reqListPanel.getTable().getModel()).getIsChange()){
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the save iteration button
	 * 
	 * @return the btnSaveIteration
	 */
	public JButton getBtnSaveIteration() {
		return btnSaveIteration;
	}

	/**
	 * Gets the cancel iteration button
	 * 
	 * @return the btnCancelIteration
	 */
	public JButton getBtnCancelIteration() {
		return btnCancelIteration;
	}

	/**
	 * Gets the text iteration name
	 * 
	 * @return the txtIterationName
	 */
	public JTextField getTxtIterationName() {
		return txtIterationName;
	}

	/**
	 * Gets the text start date
	 * 
	 * @return the txtStartDate
	 */
	public JLabel getTxtStartDate() {
		return txtStartDate;
	}

	/**
	 * Gets the text end date
	 * 
	 * @return the txtEndDate
	 */
	public JLabel getTxtEndDate() {
		return txtEndDate;
	}

	/**
	 * Gets the label of the iteration name error
	 * 
	 * @return the lblIterationNameError
	 */
	public JLabel getLblIterationNameError() {
		return lblIterationNameError;
	}

	/**
	 * Gets the label of the start date error
	 * 
	 * @return the lblStartDateError
	 */
	public JLabel getLblStartDateError() {
		return lblStartDateError;
	}

	/**
	 * Gets the label of the end date error
	 * 
	 * @return the lblEndDateError
	 */
	public JLabel getLblEndDateError() {
		return lblEndDateError;
	}

	/**
	 * Gets the label of the date error
	 * 
	 * @return the lblDateError
	 */
	public JLabel getLblDateError() {
		return lblDateError;
	}

	/**
	 * Gets the label of the iteration name exists error
	 * 
	 * @return the lblIterationNameExistsError
	 */
	public JLabel getLblIterationNameExistsError() {
		return lblIterationNameExistsError;
	}

	/**
	 * Gets the label of the date overlap error
	 * 
	 * @return the lblDateOverlapError
	 */
	public JLabel getLblDateOverlapError() {
		return lblDateOverlapError;
	}

	/**
	 * Sets the edit mode
	 * 
	 * @param editMode the editMode to set
	 */
	public void setEditMode(Mode editMode) {
		this.editMode = editMode;
	}

	/**
	 * Gets the panel layout
	 * 
	 * @return the layout
	 */
	public GridBagLayout getPanelLayout() {
		return layout;
	}

	/**
	 * Gets estimate
	 * 
	 * @return estimate
	 */
	public IntegerField getTxtEstimate() {
		return txtEstimate;
	}

	/**
	 * Sets estimate
	 * 
	 * @param txtEstimate what to set the estimate to
	 */
	public void setTxtEstimate(IntegerField txtEstimate) {
		this.txtEstimate = txtEstimate;
	}
	
	/**
	 * Calculates the estimate of the parent requirements in an iteration
	 * 
	 * @param requirements the requirements to get the estimate of
	 * @return the estimate 
	 */
	public int calculateTxtEstimate(Requirement[] requirements){
		int totalEstimate = 0;
		int counter = 0;
		Iteration i = getParent().getIterationModel().getUneditedModel();
		Requirement[] parents = requirements;
		for(Requirement parent: requirements){
			if(parent.isTopLevelRequirement() && parent.getIterationId() == i.getId()){
				parents[counter] = parent;
				counter++;
				totalEstimate += parent.getEstimateEffort();
			}
		}
		return totalEstimate; 
	}

	/**
	 * Receives the requirements from the server and adds the correct ones to the requirement panel
	 * 
	 * @param reqs the requirements the server received
	 */
	public void receiveServerRequirements(Requirement[] reqs) {
		Iteration i = getParent().getIterationModel().getUneditedModel();
		ArrayList<Requirement> list = new ArrayList<Requirement>();
		for(Requirement r:reqs){
			if(r.getIterationId() == i.getId()){
				list.add(r);
			}
		}
		reqListPanel.addRequirements(list.toArray(new Requirement[]{}));
		reqListPanel.repaint();
		reqListPanel.revalidate();
	}

	/**
	 * Sends filter server request
	 */
	public void sendFilterServerRequest(){
		//reqListPanel.getFilterController().sendServerRequests();
	}

	/**
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		updateBackgrounds();
	}

	/**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		updateBackgrounds();
	}
	

	/**
	 * Updates the backgrounds to yellow or white depending on if there have been changes
	 * 
	 * @return boolean for if backgrounds have been updated
	 */
	public boolean updateBackgrounds(){

		if(this.getParent().getMode() == Mode.CREATE){	
			if (!(this.txtIterationName.getText().trim().equals("") || txtIterationName.getText().trim().equals(null))){//if old and new are not the same
				txtIterationName.setBackground(Color.YELLOW);
			} else 
				txtIterationName.setBackground(Color.WHITE);

			if(!(txtStartDate.getText().trim().equals("") || txtStartDate.getText().trim().equals(null))){
				txtStartDate.setBackground(Color.YELLOW);
			} else 
				txtStartDate.setBackground(Color.WHITE);

			if(!(txtEndDate.getText().trim().equals("") || txtEndDate.getText().trim().equals(null))){
				txtEndDate.setBackground(Color.YELLOW);
			} else 
				txtEndDate.setBackground(Color.WHITE); 
		} else {
			Iteration oldI = getParent().getIterationModel().getUneditedModel();

			if (oldI.getName().compareTo(txtIterationName.getText()) != 0){//if old and new are not the same
				txtIterationName.setBackground(Color.YELLOW);
			} else 
				txtIterationName.setBackground(Color.WHITE);

			if (oldI.getStartDate().compareTo(StringToDate(txtStartDate.getText())) != 0){//if old and new are not the same
				txtStartDate.setBackground(Color.YELLOW);
			} else 
				txtStartDate.setBackground(Color.WHITE);

			if (oldI.getEndDate().compareTo(StringToDate(txtEndDate.getText())) != 0){//if old and new are not the same
				txtEndDate.setBackground(Color.YELLOW);
			} else 
				txtEndDate.setBackground(Color.WHITE);

			}
		return false;
	}

	/**
	 * @return the selectStartDate
	 */
	public JButton getSelectStartDate() {
		return selectStartDate;
	}

	/**
	 * @return the selectEndDate
	 */
	public JButton getSelectEndDate() {
		return selectEndDate;
	}

	/**
	 * @return the lblStartDate
	 */
	public JLabel getLblStartDate() {
		return lblStartDate;
	}

	/**
	 * @return the lblEndDate
	 */
	public JLabel getLblEndDate() {
		return lblEndDate;
	}
	
	
	
	
}