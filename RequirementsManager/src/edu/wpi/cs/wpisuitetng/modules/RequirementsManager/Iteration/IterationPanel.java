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
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.IntegerField;

/**
 * Panel to display and edit the basic fields for a Iteration
 * Adapted from DefectPanel in project DefectTracker
 *
 * @author Tushar Narayan
 * @author Arica Liu
 *
 * @version Mar 26, 2013
 *
 */
public class IterationPanel extends JPanel {
	/** The Iteration displayed in this panel */
	protected Iteration model; 

	/** The parent view **/
	protected IterationView parent;

	/*
	 * Form elements
	 */
	protected IntegerField txtIterationNumber;
	protected JTextField txtStartDate;
	protected JButton selectStartDate = new JButton("Select");
	protected JTextField txtEndDate;
	protected JButton selectEndDate = new JButton("Select");
	protected JButton saveIterationTop;
	protected JButton saveIterationBottom;
	protected JFrame f = new JFrame();

	/** A flag indicating if input is enabled on the form */
	protected boolean inputEnabled;

	/**Error labels*/
	JLabel lblIterationNumberError = new JLabel("ERROR: Must have a iteration number", LABEL_ALIGNMENT);
	JLabel lblStartDateError = new JLabel("ERROR: Must have a start date", LABEL_ALIGNMENT);
	JLabel lblEndDateError = new JLabel("ERROR: Must have a end date", LABEL_ALIGNMENT);
	JLabel lblDateError = new JLabel("ERROR: The start date must be before the end date", LABEL_ALIGNMENT);

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

	/*
	 * Constants used to layout the form
	 */
	protected static final int HORIZONTAL_PADDING = 5;
	protected static final int VERTICAL_PADDING = 15;
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;

	/**
	 * Constructs a IterationPanel for creating or editing a given Iteration.
	 * 
	 * @param parent The parent of the iteration
	 * @param iteration The Iteration to edit
	 */
	public IterationPanel(IterationView parent, Iteration iteration) {
		this.model = iteration;
		this.parent = parent;

		// Indicate that input is enabled
		inputEnabled = true;

		//Use a grid bag layout manager
		layout = new GridBagLayout();
		this.setLayout(layout);

		// Add all components to this panel
		addComponents();
	}

	/**
	 * Adds the components to the panel and places constraints on them
	 * for the GridBagLayout manager.
	 */
	protected void addComponents() {
		//create a new constrain variable
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints cOverall = new GridBagConstraints();
		GridBagConstraints cOne = new GridBagConstraints();
		GridBagConstraints cTwo = new GridBagConstraints();

		// Construct all of the components for the form
		panelOverall = new JPanel();
		panelOne = new JPanel();
		panelTwo = new JPanel();

		txtIterationNumber = new IntegerField(3);
		txtStartDate = new JTextField(15);
		txtEndDate = new JTextField(15);

		//Save Button
		saveIterationTop = new JButton("Save");
		saveIterationTop.setAction(new SaveChangesAction(new SaveIterationController(this.getParent())));
		saveIterationBottom = new JButton("Save");
		saveIterationBottom.setAction(new SaveChangesAction(new SaveIterationController(this.getParent())));

		// set maximum widths of components so they are not stretched
		//		txtIterationNumber.setMaximumSize(txtIterationNumber.getPreferredSize());

		// Construct labels for the form fields
		JLabel lblIterationNumber = new JLabel("Iteration Number:", LABEL_ALIGNMENT);
		JLabel lblStartDate = new JLabel("Start Date:", LABEL_ALIGNMENT);
		JLabel lblEndDate = new JLabel("End Date:", LABEL_ALIGNMENT);

		//Panel One - panel at the top --------------------------------------------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutOne = new GridBagLayout();
		panelOne.setLayout(layoutOne);	

		cOne.anchor = GridBagConstraints.LINE_START; 
		cOne.gridx = 0;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		cOne.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panelOne.add(saveIterationTop, cOne);

		cOne.gridx = 2;
		cOne.gridy = 0;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		lblDateError.setVisible(false);
		lblDateError.setForeground(Color.RED);
		panelOne.add(lblDateError, cOne);

		cOne.anchor = GridBagConstraints.LINE_START; 
		cOne.gridx = 0;
		cOne.gridy = 1;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		cOne.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panelOne.add(lblIterationNumber, cOne);

		cOne.gridx = 2;
		cOne.gridy = 1;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		panelOne.add(txtIterationNumber, cOne);

		cOne.gridx = 4;
		cOne.gridy = 1;
		cOne.weightx = 0.5;
		cOne.weighty = 0.5;
		cOne.gridwidth = 1;
		lblDateError.setForeground(Color.RED);
		lblIterationNumberError.setVisible(false);
		panelOne.add(lblIterationNumberError, cOne);

		//Panel Two - panel below panel one -------------------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutTwo = new GridBagLayout();
		panelTwo.setLayout(layoutTwo);

		cTwo.insets = new Insets(10,10,10,0);
		cTwo.gridx = 0;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.anchor = GridBagConstraints.LINE_START;
		panelTwo.add(lblStartDate, cTwo);

		cTwo.gridx = 2;
		cTwo.gridy = 0;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		cTwo.gridwidth = 1;
		txtStartDate.setEnabled(true);
		//	txtStartDate.setText(model.getStartDate().toString());
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
		lblDateError.setForeground(Color.RED);
		panelTwo.add(lblStartDateError, cTwo);

		cTwo.gridx = 0;
		cTwo.gridy = 1;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		panelTwo.add(lblEndDate, cTwo);

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
		lblDateError.setForeground(Color.RED);
		lblEndDateError.setVisible(false);
		panelTwo.add(lblEndDateError, cTwo);

		cTwo.gridx = 0;
		cTwo.gridy = 5;
		cTwo.weightx = 0.5;
		cTwo.weighty = 0.5;
		panelTwo.add(saveIterationBottom, cTwo);

		//Panel Overall - panel holding all other panels --------------------------------------------------------------------------
		//Use a grid bag layout manager
		layoutOverall = new GridBagLayout();
		panelOverall.setLayout(layoutOverall);
		//Overall Panel
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		//c.gridcolumn something like this
		cOverall.anchor = GridBagConstraints.LINE_START;
		panelOverall.add(panelOne, cOverall);

		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridx = 0;
		cOverall.gridy = 1;
		cOverall.anchor = GridBagConstraints.LINE_START;
		//c.gridcolumn something like this
		panelOverall.add(panelTwo, cOverall);

		// add to this Panel -----------------------------------------------------------------------------------------------------------------
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//c.gridcolumn something like this
		this.add(panelOverall, c);		
	}

	/**
	 * Returns the parent IterationView.
	 * 
	 * @return the parent IterationView.
	 */
	public IterationView getParent() {
		return parent;
	}


	/**
	 * Sets if the input is enabled
	 * 
	 * @param enabled shows if input is enabled
	 */
	protected void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;

		txtIterationNumber.setEnabled(enabled);
		txtStartDate.setEnabled(enabled);
		txtEndDate.setEnabled(enabled);
	}

	/**
	 * checks to see if it is an empty string returns -1 if the string is less than 0 or blank and returns the integer value otherwise
	 * 
	 * @param intf the IntergerField in question
	 * @return the integer that is either -1 or the integer value of the string
	 */
	protected int getValue(IntegerField intf){
		if(intf.getText().equals(null) || intf.getText().equals("")){
			return -1;
		} else {
			return Integer.parseInt(intf.getText());
		}		
	}

	/**Commented out parts are not needed for iteration 1 but may be needed in the future
	 * Returns the model object represented by this view's fields.
	 * 
	 * @return the model represented by this view
	 */
	public Iteration getEditedModel() {
		Iteration iteration = new Iteration();
		iteration.setIterationNumber(getValue(txtIterationNumber)); 
		iteration.setStartDate(StringToDate(txtStartDate.getText()));
		iteration.setEndDate(StringToDate(txtEndDate.getText()));
		//		if (!(txtAssignee.getText().equals(""))) {
		//			iteration.setAssignee(new User("", txtAssignee.getText(), "", -1));
		//		}
		//		if (!(txtCreator.getText().equals(""))) {
		//			iteration.setCreator(new User("", txtCreator.getText(), "", -1));
		//		}
		//		HashSet<Tag> tags = new HashSet<Tag>();
		//		for (int i = 0; i < tagPanel.lmTags.getSize(); i++) {
		//			tags.add(new Tag((String)tagPanel.lmTags.get(i)));
		//		}
		//		iteration.setTags(tags);

		return iteration;
	}

	/**
	 * Checks to make sure that all of the fields are correctly filled in.  
	 * 
	 * @return 2 if required fields are missing, 1 if startDate >= endDate, 0 otherwise
	 */
	public int checkRequiredFields(){
		// TODO: Any non-null string is currently accepted
		// (except in the case of all three fields being not null)
		if((getValue(txtIterationNumber) < 0)
				&&
				(txtStartDate.getText().equals(null) || txtStartDate.getText().equals(""))
				&&
				(txtEndDate.getText().equals(null) || txtEndDate.getText().equals(""))){
			lblIterationNumberError.setVisible(true);
			lblStartDateError.setVisible(true);
			lblEndDateError.setVisible(true);
			lblDateError.setVisible(false);
			return 2;
		} else if((getValue(txtIterationNumber) < 0)
				&&
				(txtStartDate.getText().equals(null) || txtStartDate.getText().equals(""))){
			lblIterationNumberError.setVisible(true);
			lblStartDateError.setVisible(true);
			lblEndDateError.setVisible(false);
			lblDateError.setVisible(false);
			return 2; 
		} else if((getValue(txtIterationNumber) < 0)
				&&
				(txtEndDate.getText().equals(null) || txtEndDate.getText().equals(""))){
			lblIterationNumberError.setVisible(true);
			lblStartDateError.setVisible(false);
			lblEndDateError.setVisible(true);
			lblDateError.setVisible(false);
			return 2;
		} else if ((txtStartDate.getText().equals(null) || txtStartDate.getText().equals(""))
				&&
				(txtEndDate.getText().equals(null) || txtEndDate.getText().equals(""))){
			lblIterationNumberError.setVisible(false);
			lblStartDateError.setVisible(true);
			lblEndDateError.setVisible(true);
			lblDateError.setVisible(false);
			return 2;
		}
		else if((txtEndDate.getText().equals(null) || txtEndDate.getText().equals(""))){
			lblIterationNumberError.setVisible(false);
			lblStartDateError.setVisible(false);
			lblEndDateError.setVisible(true);
			lblDateError.setVisible(false);
			return 2;
		}
		else if ((txtStartDate.getText().equals(null) || txtStartDate.getText().equals(""))){
			lblIterationNumberError.setVisible(false);
			lblStartDateError.setVisible(true);
			lblEndDateError.setVisible(false);
			lblDateError.setVisible(false);
			return 2;
		}
		else if ((getValue(txtIterationNumber) < 0)){
			lblIterationNumberError.setVisible(true);
			lblStartDateError.setVisible(false);
			lblEndDateError.setVisible(false);
			lblDateError.setVisible(false);
			return 2;
		}
		else{
			Date startDate = StringToDate(txtStartDate.getText());
			Date endDate = StringToDate(txtEndDate.getText());
			if (startDate.compareTo(endDate) >= 0) {
				lblIterationNumberError.setVisible(false);
				lblStartDateError.setVisible(false);
				lblEndDateError.setVisible(false);
				lblDateError.setVisible(true);
				return 1;
			}
			else{
				return 0;
			}
		}
	}


	/**
	 * Convert a String to Date.
	 * 
	 * @param aDate the String to be converted.
	 * @return the Date after conversion.
	 */
	private Date StringToDate(String aDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy"); 
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(aDate);
		} catch (ParseException e) {
			System.out.println("Error converting string to date!");
			e.printStackTrace();
		} 
		// TODO: Delete error message
		System.out.println("Converted string to date : " + convertedDate);
		return convertedDate;
	}
}



