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
 *  M. French Fried
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddAcceptanceTestController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.EditAcceptanceTestController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
//import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class AcceptanceTestsView creates a panel for viewing acceptance tests.
 * 
 * @author Michael Frenched
 */
@SuppressWarnings({"rawtypes", "serial"})
public class AcceptanceTestsView extends JPanel{
	
	protected GridBagLayout layout;
	
	protected JPlaceholderTextField txtTitle;
	protected JTextArea txtBody;
	//the status dropdown menu goes here
	protected JButton addTest;
	protected JButton editTest;
	protected JComboBox cmbStatus;
	protected RMPermissionsLevel pLevel;
	
	//the error labels
	protected JLabel lblTitleError;
	protected JLabel lblBodyError;
	
	protected JList<AcceptanceTest> listDisplay;
	protected DefaultListModel<AcceptanceTest> listModel;
	
	//the arraylist that actually holds the Tests
	ArrayList<AcceptanceTest> list;
	
	/**
	 * Instantiates a new acceptance tests view.
	 *
	 * @param rView the parent requirement view 
	 */
	@SuppressWarnings("unchecked")
	public AcceptanceTestsView(RequirementView rView){
		list = new ArrayList();
//		list = req.getAcceptanceTests();
		this.pLevel = CurrentUserPermissions.getCurrentUserPermission();
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		layout.columnWeights = new double[]{.2, .8};
		this.setLayout(layout);

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
		JLabel lblTitle = new JLabel("Title: ", JLabel.TRAILING);
		txtBody = new JTextArea(4, 40);
		JLabel lblBody = new JLabel("Body: ", JLabel.TRAILING);
		
		addTest = new JButton("Add Test");
		addTest.addActionListener(new AddAcceptanceTestController(this));
		
		editTest = new JButton("Edit Test");
		editTest.addActionListener(new EditAcceptanceTestController(this));
		
		//initiate the combobox for status
		final String[] atStatuses = {"Blank", "Passed", "Failed"};
		cmbStatus = new JComboBox(atStatuses);
		cmbStatus.setMaximumSize(cmbStatus.getPreferredSize());
		JLabel lblStatus = new JLabel("Status: ", JLabel.TRAILING);
		
		//initiate the JList stuff
		listModel = new DefaultListModel<AcceptanceTest>();

		listDisplay = new JList(listModel);
		listDisplay.setLayoutOrientation(JList.VERTICAL);
		
		//Add ALL the things with style!
		//add the "Title: " label
//		top.anchor = GridBagConstraints.LINE_START;
//		top.weightx = 0.5;
//		top.weighty = 0.5;
//		top.gridx = 0;
//		top.gridy = 0;
//		Ptop.add(lblTitle, top);

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
		top.gridx = 1;
		top.gridy = 1;
		top.weightx = 0.5;
		top.weighty = 0.5;
		cmbStatus.setBackground(Color.WHITE);
		Ptop.add(cmbStatus, top);		

		//add the "Body: " label
		bot.anchor = GridBagConstraints.LINE_START;
		bot.insets = new Insets(5,10,5,0); //top,left,bottom,right
		bot.fill = GridBagConstraints.NONE;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 0;
		Pbot.add(lblBody, bot);
		
		bot.anchor = GridBagConstraints.LINE_START;
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
		bot.fill = GridBagConstraints.BOTH;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 1;
		bot.gridwidth = 4;
		Pbot.add(scrollPaneBody, bot);
		
		//add the "Add Test" button
		bot.anchor = GridBagConstraints.LINE_START;
		bot.fill = GridBagConstraints.NONE;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 2;
		bot.gridwidth = 1;
		Pbot.add(addTest, bot);
		
		//add the "Edit Test" button
		bot.anchor = GridBagConstraints.LINE_START;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 1;
		bot.gridy = 2;
		bot.gridwidth = 2;
		Pbot.add(editTest, bot);
		
		//Add the list of AcceptanceTests gui element
		if(pLevel != RMPermissionsLevel.NONE){
			listDisplay.setCellRenderer(new HistoryViewCellRenderer(350));
		}
		JScrollPane scrollPaneList = new JScrollPane(listDisplay);
		bot.anchor = GridBagConstraints.LINE_START;
		bot.fill = GridBagConstraints.BOTH;
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 3;
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
		    	 int index = listDisplay.locationToIndex(e.getPoint());
	             System.out.println("clicked on Item " + index);
	             txtTitle.setText(list.get(index).getTitle());
	             txtBody.setText(list.get(index).getBody());
	             if (hasTitle(txtTitle.getText())){
					addTest.setEnabled(false);
					editTest.setEnabled(true);
				}else{
					addTest.setEnabled(true);
					editTest.setEnabled(false);
				}
	             
	             if (list.get(index).getStatus().compareTo("Passed") == 0){
		             	cmbStatus.setSelectedItem(atStatuses[1]);
		         }else
	             if (list.get(index).getStatus().compareTo("Failed") == 0){
		             	cmbStatus.setSelectedItem(atStatuses[2]);
		         }else{
			            cmbStatus.setSelectedItem(atStatuses[0]);
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
	 * disables the components of the AcceptanceTest view.
	 */
	public void disableAll(){
		addTest.setEnabled(false);
		editTest.setEnabled(false);
		txtTitle.setEnabled(false);
		txtTitle.setDisabledTextColor(Color.BLACK);
		txtTitle.setBackground(this.getBackground());
		txtBody.setEnabled(false);
		txtBody.setDisabledTextColor(Color.BLACK);
		txtBody.setBackground(this.getBackground());
		cmbStatus.setEnabled(false);
	}
	

	/**
	 * returns weather or not both the title field and body field are filled in.
	 *
	 * @return true, if both title and body fields are filled in. False otherwise.
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
		return this.txtBody;
	}
	
	/**
	 * Update mouse listener.
	 */
	public void updateMouseListener(){
		MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		    	 int index = listDisplay.locationToIndex(e.getPoint());
	             System.out.println("clicked on Item " + index);
	             txtTitle.setText(list.get(index).getTitle());
	             txtBody.setText(list.get(index).getBody());
		     }
		 };
		 listDisplay.addMouseListener(mouseListener);
	}
	
	/**
	 * Adds the test to list.
	 *
	 * @param a the acceptance test to add to the listModel
	 */
	public void addTestToList(AcceptanceTest a){
		boolean hasTest = false;
		int testLocation = 0;
		for (int i = 0; i < list.size(); i++){
			System.out.println("looking at: " + list.get(i).getTitle());
			System.out.println(" body: " + list.get(i).getBody());
			System.out.println(" status: " + list.get(i).getStatus() + "\n");
			if (list.get(i).getTitle().compareTo(a.getTitle()) == 0){
				hasTest = true;
				testLocation = i;
				i = list.size() + 1;
			}
		}
		if (!hasTest){
			list.add(a);
		}else{
			System.out.println("ERROR: test " + list.get(testLocation).getTitle() + " already exists");
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
		int testLocation = 0;
		for (int i = 0; i < list.size(); i++){
			System.out.println("looking at: " + list.get(i).getTitle());
			System.out.println(" body: " + list.get(i).getBody());
			System.out.println(" status: " + list.get(i).getStatus() + "\n");
			if (list.get(i).getTitle().compareTo(a.getTitle()) == 0){
				hasTest = true;
				testLocation = i;
				i = list.size() + 1;
			}
		}
		if (hasTest){
			list.get(testLocation).setBody(a.getBody());
			list.get(testLocation).setStatus(a.getStatus());
			System.out.println(list.get(testLocation).getTitle() + "has been edited");
			System.out.println("new body: " + list.get(testLocation).getBody());
			System.out.println("new status: " + list.get(testLocation).getStatus());
		}else{
			System.out.println("ERROR: could not find Test " + a.getTitle() + "\n");
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
		System.out.println("Status: " + cmbStatus.getSelectedItem().toString());
		return cmbStatus.getSelectedItem().toString();
	}
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<AcceptanceTest> getList(){
		return this.list;
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
	 * checks if the given title is in the list already. returns true if so.
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
	 * returns the Title text field
	 */
	public JTextField getTitleField(){
		return txtTitle;
	}
	
	/**
	 * returns the Body text area
	 */
	public JTextArea getBodyField(){
		return txtBody;
	}
	
	/**
	 * returns the Status combo box
	 */
	public JComboBox getStatusField(){
		return cmbStatus;
	}
	
	/**
	 * returns the add button
	 */
	public JButton getAddButton(){
		return addTest;
	}
	
	/**
	 * returns the edit button
	 */
	public JButton getEditButton(){
		return editTest;
	}
	
	//A Key Listener on the Title Field to enable/disable the addTest and editTest buttons when applicable
		/**
	 * If the title written is already in the list, disable the addTest button and enable the
	 * editTest button. Otherwise, do the opposite.
	 *
	 * @see ButtonsEvent
	 */
		public class ButtonsListener implements KeyListener {

			/**
			 * Action performed.
			 *
			 * @param estimate the estimate
			 */
			public void actionPerformed(ActionEvent estimate) {}
			
			/* (non-Javadoc)
			 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyTyped(KeyEvent e) {}
			
			/* (non-Javadoc)
			 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {}

			/* (non-Javadoc)
			 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				if (hasTitle(txtTitle.getText())){
					addTest.setEnabled(false);
					editTest.setEnabled(true);
				}else{
					addTest.setEnabled(true);
					editTest.setEnabled(false);
				}
			}
		}

		/**
		 * @return the listDisplay
		 */
		public JList<AcceptanceTest> getListDisplay() {
			return listDisplay;
		}
		
}
