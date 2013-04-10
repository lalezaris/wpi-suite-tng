package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddAcceptanceTestController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddNoteController;


public class AcceptanceTestsView extends JPanel{
	
	protected GridBagLayout layout;
	
	protected JTextField txtTitle;
	protected JTextArea txtBody;
	//the status dropdown menu goes here
	protected JButton addTest;
	
	//the arraylist that actually holds the Tests
	ArrayList<AcceptanceTest> list;
	
	public AcceptanceTestsView(Requirement req){
		//list = new ArrayList<AcceptanceTest>();
		list = req.getAcceptanceTests();
		System.out.println("AccTest list size: " + this.getListSize());
		
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		layout.columnWeights = new double[]{.2, .8};
		this.setLayout(layout);

		// Add all components to this panel
		addComponents();
	}
	
	
	protected void addComponents() {
		//create a new constrain variable
		GridBagConstraints c = new GridBagConstraints();


		//TODO: Set borders

		/* begin panel styling */
		txtTitle = new JTextField(12);
		JLabel lblTitle = new JLabel("Title: ", JLabel.TRAILING);
		txtBody = new JTextArea(4, 40);
		JLabel lblBody = new JLabel("Body: ", JLabel.TRAILING);
		
		addTest = new JButton("Add Note");
		addTest.addActionListener(new AddAcceptanceTestController(this));
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(lblTitle, c);

		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		this.add(txtTitle, c);		

		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5,0,5,0);
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		this.add(lblBody, c);
		
		JScrollPane scrollPaneBody = new JScrollPane(txtBody);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		this.add(scrollPaneBody, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 2;
		c.gridy = 0;
		this.add(addTest, c);
		
		/* end panel styling */

	}
	
	//returns weather or not both the title field
	//and body field are filled in
	public boolean notReady(){
		String t = txtTitle.getText().trim();
		System.out.println(t);
		String b = txtBody.getText().trim();
		System.out.println(b);
		return (b == null && b == "" && t == null && t == "");
	}
	
	public JTextArea getTextArea(){
		return this.txtBody;
	}
	
	public void addTestToList(AcceptanceTest a){
		list.add(a);
	}
	
	public void clearBodyTxt(){
		txtBody.setText("");
	}
	
	public void clearTitleTxt(){
		txtTitle.setText("");
	}
	
	public String getTitleTxt(){
		return txtTitle.getText();
	}
	
	public String getBodyTxt(){
		return txtBody.getText();
	}
	
	public ArrayList<AcceptanceTest> getList(){
		return this.list;
	}
	
	public int getListSize(){
		return list.size();
	}
}
