package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddAcceptanceTestController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddNoteController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.EditAcceptanceTestController;


public class AcceptanceTestsView extends JPanel{
	
	protected GridBagLayout layout;
	
	protected JTextField txtTitle;
	protected JTextArea txtBody;
	//the status dropdown menu goes here
	protected JButton addTest;
	protected JButton editTest;
	
	protected JList<AcceptanceTest> listDisplay;
	protected DefaultListModel<AcceptanceTest> listModel;
	
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
		
		addTest = new JButton("Add Test");
		addTest.addActionListener(new AddAcceptanceTestController(this));
		
		editTest = new JButton("Edit Test");
		editTest.addActionListener(new EditAcceptanceTestController(this));
		
		//initiate the JList stuff
		listModel = new DefaultListModel<AcceptanceTest>();

		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(i, list.get(i));}
		}
		
		listDisplay = new JList<AcceptanceTest>(listModel);
		listDisplay.setLayoutOrientation(JList.VERTICAL);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(lblTitle, c);

		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
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
		
		listDisplay.setCellRenderer(new HistoryViewCellRenderer(350));
		JScrollPane scrollPaneList = new JScrollPane(listDisplay);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		this.add(scrollPaneList, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 2;
		c.gridy = 0;
		this.add(addTest, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		this.add(editTest, c);
		
		/* end panel styling */
		
		/**
		 * the following courtesy of:
		 * http://docs.oracle.com/javase/6/docs/api/javax/swing/JList.html		
		 */
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
	
	public void updateList(){
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(0, list.get(i));}
		}
	}
}
