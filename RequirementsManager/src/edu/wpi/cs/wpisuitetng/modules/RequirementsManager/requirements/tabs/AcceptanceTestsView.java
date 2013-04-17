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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddAcceptanceTestController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.EditAcceptanceTestController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
//import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveEvent;


public class AcceptanceTestsView extends JPanel{
	
	protected GridBagLayout layout;
	
	protected JTextField txtTitle;
	protected JTextArea txtBody;
	//the status dropdown menu goes here
	protected JButton addTest;
	protected JButton editTest;
	protected JComboBox cmbStatus;
	
	protected JList<AcceptanceTest> listDisplay;
	protected DefaultListModel<AcceptanceTest> listModel;
	
	//the arraylist that actually holds the Tests
	ArrayList<AcceptanceTest> list;
	
	public AcceptanceTestsView(RequirementView rView){
		list = new ArrayList();
//		list = req.getAcceptanceTests();
		
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		layout.columnWeights = new double[]{.2, .8};
		this.setLayout(layout);

		// Add all components to this panel
		addComponents();
	}
	
	
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
		top.anchor = GridBagConstraints.LINE_START;
		top.weightx = 0.5;
		top.weighty = 0.5;
		top.gridx = 0;
		top.gridy = 0;
		Ptop.add(lblTitle, top);

		//add the Title text field
		top.anchor = GridBagConstraints.LINE_START;
		top.weightx = 0.5;
		top.weighty = 0.5;
		top.gridx = 1;
		top.gridy = 0;
		Ptop.add(txtTitle, top);
		
		//add the "Status: " label
		top.anchor = GridBagConstraints.LINE_START;
		top.gridx = 0;
		top.gridy = 1;
		top.weightx = 0.5;
		top.weighty = 0.5;
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
		bot.fill = GridBagConstraints.NONE;
		bot.insets = new Insets(5,0,5,0);
		bot.weightx = 0.5;
		bot.weighty = 0.5;
		bot.gridx = 0;
		bot.gridy = 0;
		Pbot.add(lblBody, bot);
		
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
		bot.weightx = 0;
		bot.weighty = 0;
		bot.gridx = 0;
		bot.gridy = 2;
		bot.gridwidth = 1;
		Pbot.add(addTest, bot);
		
		//add the "Edit Test" button
		bot.anchor = GridBagConstraints.LINE_START;
		bot.weightx = 0;
		bot.weighty = 0;
		bot.gridx = 1;
		bot.gridy = 2;
		Pbot.add(editTest, bot);
		
		//Add the list of AcceptanceTests gui element
		listDisplay.setCellRenderer(new HistoryViewCellRenderer(350));
		JScrollPane scrollPaneList = new JScrollPane(listDisplay);
		bot.anchor = GridBagConstraints.LINE_START;
//		bot.fill = GridBagConstraints.BOTH;
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
		 listDisplay.addMouseListener(mouseListener);
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
	
	//disables the components of the AcceptanceTest view
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
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(i, list.get(i));}
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
	
	public String getStatusTxt(){
		System.out.println("Status: " + cmbStatus.getSelectedItem().toString());
		return cmbStatus.getSelectedItem().toString();
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

	/**
	 * @param list: the list to set
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
	
	//A Key Listener on the Title Field to enable/disable the addTest and editTest buttons when applicable
		/**
		 * If the title written is already in the list, disable the addTest button and enable the
		 * editTest button. Otherwise, do the opposite.
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
	
}
