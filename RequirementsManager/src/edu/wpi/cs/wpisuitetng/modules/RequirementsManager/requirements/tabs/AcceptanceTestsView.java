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
 *  Michael French
 *  Joe Spicola
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.JPlaceholderTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CancelAcceptanceTestController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveAcceptanceTestController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
/**
 * The Class AcceptanceTestsView creates a panel for viewing acceptance tests.
 * 
 * @author Michael French
 */
@SuppressWarnings({"rawtypes", "serial"})
public class AcceptanceTestsView extends RequirementTab implements FocusListener {
	protected GridBagLayout layout;

	protected JPlaceholderTextField txtTitle;
	protected JTextArea txtBody;
	protected JButton saveTest;
	protected JButton cancelTest;
	protected JComboBox cmbStatus;
	protected RMPermissionsLevel pLevel;

	//flag to see if title is enabled or not
	protected boolean txtTitleFlag;
	//the error labels
	protected JLabel lblTitleError;
	protected JLabel lblBodyError;

	protected JList<AcceptanceTest> listDisplay;
	protected DefaultListModel<AcceptanceTest> listModel;

	protected RequirementView parent;

	//the arraylist that actually holds the Tests
	ArrayList<AcceptanceTest> list;

	/**
	 * Instantiates a new acceptance tests view.
	 *
	 * @param parent the parent requirement view 
	 */
	@SuppressWarnings("unchecked")
	public AcceptanceTestsView(RequirementView parent){
		list = new ArrayList();
		pLevel = CurrentUserPermissions.getCurrentUserPermission();
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		layout.columnWeights = new double[]{.2, .8};
		this.setLayout(layout);
		
		this.setFocusCycleRoot(true);
        this.setFocusTraversalPolicy(new LayoutFocusTraversalPolicy() {
                public Component getDefaultComponent(Container cont) {
                    return lblTitleError;
                }
            });

		this.parent = parent;

		// Add all components to this panel
		addComponents();
	}


	/**
	 * Adds the components.
	 */
	@SuppressWarnings("unchecked")
	protected void addComponents() {
		//create Panels
		JPanel Ptop = new JPanel();
		Ptop.setLayout(new GridBagLayout());
		JPanel Pbot = new JPanel();
		Pbot.setLayout(new GridBagLayout());

		JPanel panelOverall = new JPanel();
		panelOverall.setLayout(new GridBagLayout());

		//create constraint variables
		GridBagConstraints all = new GridBagConstraints();
		GridBagConstraints overall = new GridBagConstraints();
		GridBagConstraints top = new GridBagConstraints();
		GridBagConstraints bot = new GridBagConstraints();

		lblTitleError = new JLabel("ERROR: Must have a title", JLabel.TRAILING);
		lblBodyError = new JLabel("ERROR: Must have a body", JLabel.TRAILING);

		//TODO: Set borders

		/* begin panel styling */
		if(pLevel != RMPermissionsLevel.NONE){
			txtTitle = new JPlaceholderTextField("Enter Title Here", 20);
		} else
			txtTitle = new JPlaceholderTextField("", 20);

		txtTitle.addFocusListener(this);
		txtBody = new JTextArea(4, 40);
		txtBody.addFocusListener(this);
		JLabel lblBody = new JLabel("Test Descriptions: ", JLabel.TRAILING);

		saveTest = new JButton("Save");
		saveTest.addActionListener(new SaveAcceptanceTestController(this));
		saveTest.addFocusListener(this);

		cancelTest = new JButton("Cancel");
		cancelTest.addActionListener(new CancelAcceptanceTestController(this));
		cancelTest.addFocusListener(this);

		//initiate the combobox for status
		final String[] atStatuses = {"", "Passed", "Failed"};
		cmbStatus = new JComboBox(atStatuses);
		cmbStatus.addFocusListener(this);
		cmbStatus.setMaximumSize(cmbStatus.getPreferredSize());
		JLabel lblStatus = new JLabel("Status: ", JLabel.TRAILING);

		JLabel lblTests = new JLabel("Existing Tests:", JLabel.TRAILING);

		//initiate the JList stuff
		listModel = new DefaultListModel<AcceptanceTest>();

		listDisplay = new JList(listModel);
		listDisplay.setBackground(new Color(223,223,223));
		listDisplay.setLayoutOrientation(JList.VERTICAL);

		//add the Title text field
		top.anchor = GridBagConstraints.LINE_START;
		top.insets = new Insets(10,10,5,0); //top,left,bottom,right
		top.weightx = 0.5;
		top.weighty = 0.5;
		top.gridx = 0;
		top.gridy = 0;
		top.gridwidth = 3;
		Ptop.add(txtTitle, top);

		top.anchor = GridBagConstraints.LINE_START;
		top.insets = new Insets(10,10,5,0); //top,left,bottom,right
		top.weightx = 0.5;
		top.weighty = 0.5;
		top.gridx = 2;
		top.gridy = 0;
		top.gridwidth = 3;
		lblTitleError.setVisible(false);
		lblTitleError.setForeground(Color.RED);
		Ptop.add(lblTitleError, top);

		//add the "Status: " label
		top.anchor = GridBagConstraints.LINE_START;
		top.insets = new Insets(5,10,5,0); //top,left,bottom,right
		top.gridx = 0;
		top.gridy = 1;
		top.weightx = 0.5;
		top.weighty = 0.5;
		top.gridwidth = 1;
		Ptop.add(lblStatus, top);

		//add the Status menu
		top.anchor = GridBagConstraints.LINE_START;
		top.insets = new Insets(10,10,5,0); //top,left,bottom,right
		top.gridx = 1;
		top.gridy = 1;
		top.weightx = 0.5;
		top.weighty = 0.5;
		cmbStatus.setBackground(Color.WHITE);
		Ptop.add(cmbStatus, top);		

		//add the "Test Descriptions: " label
		bot.anchor = GridBagConstraints.LINE_START;
		bot.insets = new Insets(5,10,5,0); //top,left,bottom,right
		bot.fill = GridBagConstraints.NONE;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 0;
		Pbot.add(lblBody, bot);

		bot.anchor = GridBagConstraints.LINE_START;
		bot.insets = new Insets(5,10,5,0); //top,left,bottom,right
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 1;
		bot.gridy = 0;
		bot.gridwidth = 2;
		lblBodyError.setVisible(false);
		lblBodyError.setForeground(Color.RED);
		Pbot.add(lblBodyError, bot);

		//add the Body text area
		JScrollPane scrollPaneBody = new JScrollPane(txtBody);
		bot.anchor = GridBagConstraints.LINE_START;
		bot.insets = new Insets(0,10,5,0); //top,left,bottom,right
		bot.fill = GridBagConstraints.BOTH;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 1;
		bot.gridwidth = 4;
		Pbot.add(scrollPaneBody, bot);

		//add the "Save Test" button
		bot.anchor = GridBagConstraints.LINE_START;
		bot.insets = new Insets(5,10,5,0); //top,left,bottom,right
		bot.fill = GridBagConstraints.NONE;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 2;
		bot.gridwidth = 1;

		Pbot.add(saveTest, bot);

		//add the "Cancel" button
		bot.anchor = GridBagConstraints.LINE_START;
		bot.insets = new Insets(5,10,5,0); //top,left,bottom,right
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 1;
		bot.gridy = 2;
		bot.gridwidth = 2;
		Pbot.add(cancelTest, bot);

		//Add the list of AcceptanceTests gui element
		if(pLevel != RMPermissionsLevel.NONE){
			listDisplay.setCellRenderer(new HistoryViewCellRenderer(350));
		}

		//add the "Existing Tests: " label
		bot.anchor = GridBagConstraints.LINE_START;
		bot.insets = new Insets(5,10,5,0); //top,left,bottom,right
		bot.fill = GridBagConstraints.NONE;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 3;
		bot.gridwidth = 1;
		Pbot.add(lblTests, bot);


		JScrollPane scrollPaneList = new JScrollPane(listDisplay);
		bot.anchor = GridBagConstraints.LINE_START;
		bot.insets = new Insets(0,10,5,0); //top,left,bottom,right
		bot.fill = GridBagConstraints.BOTH;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 4;
		bot.gridwidth = 4;
		Pbot.add(scrollPaneList, bot);

		//compile all the panels into overall
		overall.anchor = GridBagConstraints.LINE_START;
		overall.weightx = 0.5;
		overall.weighty = 0.5;
		overall.gridx = 0;
		overall.gridy = 0;
		overall.gridwidth = 1;
		panelOverall.add(Ptop, overall);
		
		overall.gridy = 1;
		panelOverall.add(Pbot, overall);

		//DO IT!
		all.anchor = GridBagConstraints.FIRST_LINE_START;
		all.weightx = 0.5;
		all.weighty = 0.5;
		all.gridx = 0;
		all.gridy = 0;
		
		panelOverall.setMinimumSize(getPreferredSize());
		this.add(panelOverall, all);



		/* end panel styling */

		/**
		 * the following is based off of code from:
		 * http://docs.oracle.com/javase/6/docs/api/javax/swing/JList.html
		 * it makes it so when the user clicks on a list item, the body, title, and
		 * status fields are populated with that item's characteristics
		 */
		MouseListener mouseListener = new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if(list.size() > 0){
					int index = listDisplay.locationToIndex(e.getPoint());
					txtTitle.setText(list.get(index).getTitle());
					txtTitle.setEnabled(false);
					txtTitleFlag = false;
					txtBody.setText(list.get(index).getBody());
					saveTest.setEnabled(true);

					if (list.get(index).getStatus().compareTo("Passed") == 0){
						cmbStatus.setSelectedIndex(1);
					}else if (list.get(index).getStatus().compareTo("Failed") == 0){
						cmbStatus.setSelectedIndex(2);
					}else{
						cmbStatus.setSelectedIndex(0);
					}
				}
			}
		};

		if(pLevel != RMPermissionsLevel.NONE){
			listDisplay.addMouseListener(mouseListener);
		}
		txtTitle.addKeyListener(new ButtonsListener());

		//setup permission features
		RMPermissionsLevel pLevel = CurrentUserPermissions.getCurrentUserPermission();
		switch (pLevel){
		case NONE:
			disableAll();
			break;
		case UPDATE: 
			//full access
			break;		
		case ADMIN:
			//full access
			break;
		}


	}

	/**
	 * Disables the components of the AcceptanceTest view.
	 */
	public void disableAll(){
		saveTest.setEnabled(false);
		txtTitle.setEnabled(false);
		txtTitleFlag = false;
		txtTitle.setDisabledTextColor(Color.BLACK);
		txtTitle.setBackground(this.getBackground());
		txtBody.setEnabled(false);
		txtBody.setDisabledTextColor(Color.BLACK);
		txtBody.setBackground(this.getBackground());
		cmbStatus.setEnabled(false);
	}


	/**
	 * Returns weather or not both the title field and body field are filled in.
	 *
	 * @return false, if both title and body fields are filled in. true otherwise.
	 */
	public boolean notReady(){
		String t = txtTitle.getText().trim();
		String b = txtBody.getText().trim();
		if((t == null || t.equals("")) &&  (b == null || b.equals(""))){
			lblBodyError.setVisible(true);
			lblTitleError.setVisible(true);
			return true;
		}
		if(b == null || b.equals("")){
			lblBodyError.setVisible(true);
			lblTitleError.setVisible(false);
			return true;
		}
		if(t == null || t.equals("")){
			lblTitleError.setVisible(true);
			lblBodyError.setVisible(false);
			return true;
		}
		return false;
	}

	/**
	 * Gets the text area.
	 *
	 * @return the text area
	 */
	public JTextArea getTextArea(){
		return txtBody;
	}

	/**
	 * check to see if test exists with same name.
	 * return -1 if it does not exist
	 * else return index
	 * 
	 * @param testName
	 * @return i
	 */
	public int doesTestExist(String testName) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTitle().compareTo(testName) == 0){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Adds the test to list.
	 *
	 * @param a the acceptance test to add to the listModel
	 */
	public void addTestToList(AcceptanceTest a){
		boolean hasTest = false;
		int testLocation = doesTestExist(a.getTitle());
		if(testLocation != -1)
			hasTest = true;
		if (!hasTest){
			list.add(a);
		}
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(i, list.get(i));}
		}
	}
	/**
	 * Replace test.
	 *
	 * @param a the acceptance test to replace
	 */
	public void replaceTest(AcceptanceTest a){
		boolean hasTest = false;
		int testLocation = doesTestExist(a.getTitle());
		if(testLocation != -1)
			hasTest = true;
		if (hasTest){
			list.get(testLocation).setBody(a.getBody());
			list.get(testLocation).setStatus(a.getStatus());
		}
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(i, list.get(i));}
		}
	}

	/**
	 * Clear body txt.
	 */
	public void clearBodyTxt(){
		txtBody.setText("");
		lblBodyError.setVisible(false);
	}

	/**
	 * Clear title txt.
	 */
	public void clearTitleTxt(){
		txtTitle.setText(null);
		lblTitleError.setVisible(false);
	}

	/**
	 * Clear status cmb.
	 */
	public void clearStatusCmb(){
		cmbStatus.setSelectedIndex(0);
	}

	/**
	 * Gets the title txt.
	 *
	 * @return the title txt
	 */
	public String getTitleTxt(){
		return txtTitle.getText();
	}

	/**
	 * Gets the body txt.
	 *
	 * @return the body txt
	 */
	public String getBodyTxt(){
		return txtBody.getText();
	}

	/**
	 * Gets the status txt.
	 *
	 * @return the status txt
	 */
	public String getStatusTxt(){
		return cmbStatus.getSelectedItem().toString();
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<AcceptanceTest> getList(){
		return list;
	}

	/**
	 * Gets the list size.
	 *
	 * @return the list size
	 */
	public int getListSize(){
		return list.size();
	}

	/**
	 * Update list.
	 */
	public void updateList(){
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(0, list.get(i));}
		}
	}

	/**
	 *
	 * @param list the new list
	 */
	public void setList(ArrayList<AcceptanceTest> list) {
		this.list = list;
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(i, list.get(i));}
		}

		this.repaint();
		this.revalidate();
	}

	/**
	 * Checks if the given title is in the list already
	 *
	 * @param s the s
	 * @return true, if successful
	 */
	public boolean hasTitle(String s){
		boolean result = false;
		for (int i = 0; i < list.size(); i++){
			if (list.get(i).getTitle().compareTo(s) == 0){
				result = true;
			}
		}
		return result;
	}

	/**
	 * Gets the title field
	 * 
	 * @return the title text field
	 */
	public JTextField getTitleField(){
		return txtTitle;
	}

	/**
	 * Gets body field
	 * 
	 * @return the Body text area
	 */
	public JTextArea getBodyField(){
		return txtBody;
	}

	/**
	 * Gets the status field
	 * 
	 * @return the Status combo box
	 */
	public JComboBox getStatusField(){
		return cmbStatus;
	}

	/**
	 * Gets the add button
	 * 
	 * @return the add button
	 */
	public JButton getAddButton(){
		return saveTest;
	}
	/**
	 * A Key Listener on the Title Field to enable/disable the addTest and editTest buttons when applicable
	 * If the title written is already in the list, disable the addTest button and enable the
	 * editTest button. Otherwise, do the opposite.
	 * 
	 * @author Michael French
	 */
	public class ButtonsListener implements KeyListener {

		/**
		 * Action performed.
		 *
		 * @param estimate the estimate
		 */
		public void actionPerformed(ActionEvent estimate) {}

		/**
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {}

		/**
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(KeyEvent e) {}

		/**
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			if (hasTitle(txtTitle.getText())){
				saveTest.setEnabled(true);
				//editTest.setEnabled(true);
			}else{
				saveTest.setEnabled(true);
				//editTest.setEnabled(false);
			}
		}
	}

	/**
	 * @return the txtTitle
	 */
	public JTextField getTxtTitle() {
		return txtTitle;
	}


	/**
	 * @return the txtBody
	 */
	public JTextArea getTxtBody() {
		return txtBody;
	}


	/**
	 * @return the cmbStatus
	 */
	public JComboBox getCmbStatus() {
		return cmbStatus;
	}


	/**
	 * Gets list display
	 * 
	 * @return list of acceptance tests
	 */
	public JList<AcceptanceTest> getListDisplay(){
		return listDisplay;
	}

	/**
	 * Sets list display background color
	 * 
	 * @param c the color
	 */
	public void setListDisplayBackground(Color c) {
		listDisplay.setBackground(c);
	}

	/**
	 * Sets text title background color
	 * 
	 * @param c the color
	 */
	public void setTxtTitleBackground(Color c) {
		txtTitle.setBackground(c);
	}

	/**
	 * Sets text body background color
	 * 
	 * @param c the color
	 */
	public void setTxtBodyBackground(Color c) {
		txtBody.setBackground(c);
	}
	
	/**
	 * gets the cancelButton
	 */
	public JButton getCancelButton(){
		return cancelTest;
	}

	/**
	 * Sets cmb status background color
	 * 
	 * @param c the color
	 */
	public void setCmbStatusBackground(Color c) {
		cmbStatus.setBackground(c);
	}

	/**
	 * Refreshes the background
	 */
	public void refreshBackgrounds() {
		parent.getReqModel().updateBackgrounds();
	}
	/**
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		this.refreshBackgrounds();
	}


	/**
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		this.refreshBackgrounds();
	}
	
	/**
	 * toggle enable of title field.
	 *
	 * @param b determines if the title is enabled.
	 */
	public void toggleTitleEnabled(boolean b) {
		txtTitle.setEnabled(b);
		txtTitleFlag = b;
	}

	/**
	 * check to see if title is enabled or not
	 *
	 * @return true, if is title enabled. If false, then in edit mode.
	 */
	public boolean isTitleEnabled() {
		return txtTitleFlag;
	}


	@Override
	public String getTabTitle() {
		return "Acceptance Tests";
	}

	@Override
	public ImageIcon getImageIcon() {
		return new ImageIcon();
	}

	@Override
	public String getTooltipText() {
		return "Add and modify acceptance tests";
	}
}
