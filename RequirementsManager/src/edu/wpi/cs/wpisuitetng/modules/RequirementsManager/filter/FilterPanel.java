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
 *  Chris
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Insert Description Here
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
public class FilterPanel extends JPanel{

	protected FilterController view;
	
	private final JScrollPane scrollPane;
	private final JButton applyButton, addButton,enableButton, disableButton, removeButton,showButton;
	private final ArrayList<RulePanel> rules;
	private final JPanel ruleHolderPanel, mainPanel, alwaysOnPanel;
	private String[] removeFields;
	private final JLabel filterDesc;
	/**
	 * create a filter panel and add all the components
	 * 
	 */
	public FilterPanel(final FilterController view) {
		BoxLayout layoutBox = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		//this.setLayout(layout);
		this.rules = new ArrayList<RulePanel>();
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		this.view = view;
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridwidth = 1;
		
		applyButton = new JButton("Apply");
		applyButton.setPreferredSize(new Dimension(60,35));
		applyButton.setText("Apply");
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		ruleHolderPanel = new JPanel();
		GridBagLayout pLayout = new GridBagLayout();
		ruleHolderPanel.setLayout(pLayout);

		filterDesc = new JLabel("No filters enabled");

		JPanel buttonPanel = new JPanel();
		GridBagLayout bLayout = new GridBagLayout();
		buttonPanel.setLayout(bLayout);
		addButton = new JButton("New Filter");
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		buttonPanel.add(addButton, c);
		enableButton = new JButton("Enable Filter");

		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		buttonPanel.add(enableButton, c);
		disableButton = new JButton("Disable Filter");

		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		buttonPanel.add(disableButton, c);
		removeButton = new JButton("Remove Filter");

		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		buttonPanel.add(removeButton, c);
		
		
		alwaysOnPanel = new JPanel();

		
		showButton = new JButton("Show Filter");
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridheight =1;
		c.insets = new Insets(10, 10, 0, 0);
		this.add(alwaysOnPanel, c);
		
		alwaysOnPanel.add(showButton);
		alwaysOnPanel.add(filterDesc);
		
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		c.insets = new Insets(10,10,0,0);
		mainPanel.add(buttonPanel,c);
	
		
		ruleHolderPanel.setVisible(true);
		ruleHolderPanel.validate();
		
		//TODO figure out how to correctly get the width of the window
		System.out.println("WIDTH:" + this.getSize().getWidth());
		ruleHolderPanel.setPreferredSize(new Dimension(900,120));
		
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(ruleHolderPanel);
		scrollPane.getVerticalScrollBar().setVisible(true);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.setPreferredSize(new Dimension(
				(int)scrollPane.getPreferredSize().getWidth()+40, 
				(int)scrollPane.getPreferredSize().getHeight()));
		
		
		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				scrollPane.repaint();
			}
		}); 
		

		Border b = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		scrollPane.setViewportBorder(b);
		
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		c.insets = new Insets(10, 10, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(scrollPane,c);
		
		
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		c.insets = new Insets(10, 10, 0, 0);
		this.add(mainPanel,c);
		
		this.mainPanel.setVisible(false);
		
		ruleHolderPanel.validate();
		ruleHolderPanel.repaint();
		
		scrollPane.validate();
		scrollPane.repaint();

		
		final JPanel p = this;
		p.addHierarchyListener(new HierarchyListener() {

			/* Shows changes to hierarchy
			 * @param e HierarchyEvent to respond to
			 * @see java.awt.event.HierarchyListener#hierarchyChanged(java.awt.event.HierarchyEvent)
			 */
			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ( (HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0
						&& p.isShowing())
				{
					triggerTableUpdate();
					//scrollPane.setPreferredSize(new Dimension((int)view.listPanel.getPreferredSize().getWidth(), 150));
					//scrollPane.revalidate();
				}

			}

		});
		
	}

	public void setWidth(int width){
		//mainPanel.setPreferredSize(new Dimension(width, 150));
		//ruleHolderPanel.setPreferredSize(new Dimension(width, (int)ruleHolderPanel.getPreferredSize().getHeight()));
		scrollPane.setPreferredSize(new Dimension(width+40, 150));
	}
	
	public void removeFields(String[] remove){
		this.removeFields = remove;
	}
	
	private JLabel dingus = new JLabel("DINGUS ALERT");
	/**
	 * Add a blank rule to the panel.
	 * 
	 */
	public void addRule(){
		GridBagConstraints c = new GridBagConstraints();
		RulePanel rule = new RulePanel(this);
		rule.setMinimumSize(new Dimension(600,40));

		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = view.getModel().getFilter().getRules().size();
		c.weightx = 1;
		c.weighty = 0;
//		if (rules.size() == 0)
//			c.weighty = 1;
//		
		
		//c.fill = GridBagConstraints.VERTICAL;

		c.insets = new Insets(5, 5, 0, 0);
		view.getModel().getFilter().addRule(rule.extractRule());
		ruleHolderPanel.add(rule,c);
		
		c.gridy += 1;
		c.weighty = 1;
		for (int i = 0 ; i < ruleHolderPanel.getComponents().length; i ++)
			if (ruleHolderPanel.getComponent(i).equals(dingus))
				ruleHolderPanel.remove(dingus);
		ruleHolderPanel.add(dingus,c);
		ruleHolderPanel.setPreferredSize(new Dimension(
				(int) ruleHolderPanel.getMinimumSize().getWidth(),
				(int) (view.getModel().getFilter().getRules().size() * rule.getMinimumSize().getHeight())));
	
		rules.add(rule);
		rule.addListeners();
		
			//ruleHolderPanel.revalidate();
		
//		scrollPane.revalidate();
//		scrollPane.repaint();
		this.revalidate();
		this.repaint();
	}

	/**
	 * update the table with the rules
	 * 
	 */
	protected void triggerTableUpdate(){
		setFilterDescText();
		this.view.setFilteredInTable();
	}
	
	/**
	 * update the label to tell the user how many filters are enabled
	 * 
	 */
	private void setFilterDescText(){
		int ruleCount = 0;
		for (int r = 0 ; r < rules.size() ; r ++)
			if (rules.get(r).getIsEnabled())
				if (!((String)rules.get(r).getField().getSelectedItem()).equals(" "))
					ruleCount++;
		if (ruleCount == 0)
			this.filterDesc.setText("No filters enabled");
		else if (ruleCount == 1)
			this.filterDesc.setText("1 filter enabled");
		if (ruleCount >1)
			this.filterDesc.setText(ruleCount + " filters enabled");
	}

	/**
	 * change the visibility of the filterpanel. The "Show Filters" button will remain, regardless
	 * 
	 */
	public void toggleVisibility(){
		setFilterDescText();
		if (mainPanel.isVisible()){
			showButton.setText("Show Filters");
			mainPanel.setVisible(false);
		} else{
			showButton.setText("Hide Filters");
			mainPanel.setVisible(true);
		}
	}
	
	/**
	 * Of all the rule panels in the filter panel, delete the ones that are selected
	 * 
	 */
	public void deleteSelected(){
		
		ArrayList<RulePanel> delete = new ArrayList<RulePanel>();
		for (int i = 0 ; i < rules.size(); i ++)
			if (rules.get(i).getEnabledBox().isSelected()){
				delete.add(rules.get(i));
			}
		
		for (int i = 0 ; i < delete.size(); i ++){

			ruleHolderPanel.remove(delete.get(i));
			rules.remove(delete.get(i));
		}
		
//		GridBagConstraints c = new GridBagConstraints();
//		c.anchor = GridBagConstraints.FIRST_LINE_START; 
//		c.gridx = 0;
//		c.gridy = view.getModel().getFilter().getRules().size();
//		c.weightx = 1;
//		c.weighty = 0;
//		c.insets = new Insets(5, 5, 0, 0);
//		c.gridy += 10;
//		c.weighty = 1;
//		for (int i = 0 ; i < ruleHolderPanel.getComponents().length; i ++)
//			if (ruleHolderPanel.getComponent(i).equals(dingus))
//				ruleHolderPanel.remove(dingus);
//		ruleHolderPanel.add(dingus,c);
		
		
//		scrollPane.revalidate();
//		scrollPane.repaint();
		this.view.getModel().setModelFromPanel(this);
		ruleHolderPanel.setPreferredSize(new Dimension(
				(int)ruleHolderPanel.getMinimumSize().getWidth(),
				(int) (view.getModel().getFilter().getRules().size() * 40)));
		setFilterDescText();
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Of all the rule panels in the filter panel, enable the ones that are selected
	 * 
	 */
	public void enableSelected(){
		for (int i = 0 ; i < rules.size(); i ++)
			if (rules.get(i).getEnabledBox().isSelected()){
				rules.get(i).setRuleEnabled(true);
				rules.get(i).getEnabledBox().setSelected(false);
			}
		setFilterDescText();
	}
	
	
	/**
	 * Gets the removeFields
	 * @return the removeFields
	 */
	public String[] getRemoveFields() {
		return removeFields;
	}

	/**
	 * Of all the rule panels in the filter panel, disable the ones that are selected
	 * 
	 */
	public void disableSelected(){
		for (int i = 0 ; i < rules.size() ; i ++)
			if (rules.get(i).getEnabledBox().isSelected()){
				rules.get(i).setRuleEnabled(false);
				rules.get(i).getEnabledBox().setSelected(false);
			}
		setFilterDescText();
	}
	
	public void setIterations(Iteration[] iterations){
		for (int i = 0 ; i < rules.size(); i ++){
			rules.get(i).setIterationValues(iterations);
		}
	}
	
	public void setUsers(User[] users){
		for (int i = 0 ; i < rules.size(); i ++){
			rules.get(i).setUserValues(users);
		}
	}
	
	/**
	 * Gets the applyButton
	 * @return the applyButton
	 */
	public JButton getApplyButton() {
		return applyButton;
	}

	

	/**
	 * Gets the removeButton
	 * @return the removeButton
	 */
	public JButton getRemoveButton() {
		return removeButton;
	}

	/**
	 * Gets the addButton
	 * @return the addButton
	 */
	public JButton getAddButton() {
		return addButton;
	}

	/**
	 * Gets the showButton
	 * @return the showButton
	 */
	public JButton getShowButton() {
		return showButton;
	}

	/**
	 * Gets the enableButton
	 * @return the enableButton
	 */
	public JButton getEnableButton() {
		return enableButton;
	}

	/**
	 * Gets the disableButton
	 * @return the disableButton
	 */
	public JButton getDisableButton() {
		return disableButton;
	}

	/**
	 * Gets the rule
	 * @return the rule
	 */
	public ArrayList<RulePanel> getRules() {
		return rules;
	}

	/**
	 * Gets the view
	 * @return the view
	 */
	public FilterController getView() {
		return view;
	}
	
	
}
