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
 *  Chris Hanna
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
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
 * Panel for filters
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
@SuppressWarnings("serial")
public class FilterPanel extends JPanel{

	protected FilterController view;

	private final JScrollPane scrollPane;

	private final JButton applyButton, addButton,enableButton, disableButton, removeButton,showButton, snakeButton;
	private final ArrayList<RulePanel> rules;

	private final JPanel ruleHolderPanel, mainPanel, alwaysOnPanel;
	private String[] removeFields;
	private final JLabel filterDesc;
	private int ruleCount = 0, ruleInc = 0;
	private JLabel dingus = new JLabel(" ");
	/**
	 * Create a filter panel and add all the components
	 * 
	 */

	@SuppressWarnings("unused")
	public FilterPanel(final FilterController view) {
		BoxLayout layoutBox = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		rules = new ArrayList<RulePanel>();
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
		c.weightx = .5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		
		buttonPanel.add(addButton, c);
		
		enableButton = new JButton("Enable Selected Filters");
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = .5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		c.insets = new Insets(0,50,0,0);
		buttonPanel.add(enableButton, c);
		
		disableButton = new JButton("Disable Selected Filters");
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = .5;
		c.weighty = 0.5;
		//c.gridwidth = 1;
		c.gridheight =1;
		c.insets = new Insets(0,5,0,0);
		buttonPanel.add(disableButton, c);
		
		removeButton = new JButton("Remove Selected Filters");
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 5;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		buttonPanel.add(removeButton, c);
		
		snakeButton = new JButton("Munch");
		c.anchor = GridBagConstraints.FIRST_LINE_END; 
		c.gridx = 6;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		snakeButton.setVisible(false);
		buttonPanel.add(snakeButton, c);
		
		
		alwaysOnPanel = new JPanel();
		showButton = new JButton("Show Filter");
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = .5;
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
		ruleHolderPanel.setPreferredSize(new Dimension((int)ruleHolderPanel.getPreferredSize().getWidth(),120));

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(ruleHolderPanel);
		scrollPane.getVerticalScrollBar().setVisible(true);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.setPreferredSize(new Dimension(
				(int)scrollPane.getPreferredSize().getWidth(), 
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
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight =1;
		c.insets = new Insets(10, 10, 0, 0);
		this.add(mainPanel,c);
		
		mainPanel.setVisible(false);

		ruleHolderPanel.validate();
		ruleHolderPanel.repaint();

		scrollPane.validate();
		scrollPane.repaint();

		this.addRule();
		
		this.rules.get(0).getField().setSelectedIndex(1);
		this.rules.get(0).updateCompareBox();
		this.rules.get(0).getCompareMode().setSelectedIndex(1);
		this.rules.get(0).updatePossibleValues();
		this.rules.get(0).getPossibleValues().setSelectedIndex(4); //The index of the DELETED status
		
		this.rules.get(0).addListeners();
		//this.rules.get(0).getPossibleValues().setSelectedIndex(1);
		
		
		this.setAlignmentX(0);

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
		scrollPane.setPreferredSize(new Dimension(width+40, 150));
	}

	public void removeFields(String[] remove){
		removeFields = remove;
	}
	
	/**
	 * Add a blank rule to the panel.
	 * 
	 */
	public void addRule(){
		GridBagConstraints c = new GridBagConstraints();
		RulePanel rule = new RulePanel(this);


		ruleHolderPanel.revalidate();
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 0;
		ruleCount++;
		c.gridy = ruleInc;
		ruleInc++;
		ruleHolderPanel.revalidate();
		c.weightx = 1;
		c.weighty = 0;

		c.insets = new Insets(5, 5, 0, 0);
		//view.getModel().getFilter().addRule(rule.extractRule());
		ruleHolderPanel.add(rule,c);
		ruleHolderPanel.revalidate();
		ruleHolderPanel.revalidate();
		
		c.gridy = ruleInc+1;
		c.weighty = 1;

		for (int i = 0 ; i < ruleHolderPanel.getComponents().length; i ++)
			if (ruleHolderPanel.getComponent(i).equals(dingus)){
				ruleHolderPanel.remove(dingus);
				break;
			}
		ruleHolderPanel.add(dingus,c);
		ruleHolderPanel.setPreferredSize(new Dimension(
				(int) ruleHolderPanel.getPreferredSize().getWidth(),
				(int) (ruleHolderPanel.getComponentCount() * rule.getMinimumSize().getHeight())));
	
		
		rules.add(rule);
		
		ruleHolderPanel.revalidate();
		this.revalidate();
		this.repaint();
		ruleHolderPanel.revalidate();
		
		showSnakeHuh();
	}

	private void showSnakeHuh(){
		boolean show = false;
		if (this.getRules().size() == 1)
			if (this.getRules().get(0).getField().getSelectedItem().equals("title"))
				if (this.getRules().get(0).getPossibleValuesText().getText().equals("snake"))
					show = true;
		snakeButton.setVisible( show);
	}
	
	/**
	 * Update the table with the rules
	 * 
	 */
	public void triggerTableUpdate(){
		setFilterDescText();
		view.setFilteredInTable();
		showSnakeHuh();
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
			filterDesc.setText("No filters enabled");
		else if (ruleCount == 1)
			filterDesc.setText("1 filter enabled");
		if (ruleCount >1)
			filterDesc.setText(ruleCount + " filters enabled");
	}

	/**
	 * Change the visibility of the filterpanel. The "Show Filters" button will remain, regardless
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
		
		List<RulePanel> delete = new ArrayList<RulePanel>();
		int ySize = 40;

		for (int i = 0 ; i < rules.size(); i ++)
			if (rules.get(i).getEnabledBox().isSelected()){
				ySize = (int)rules.get(i).getPreferredSize().getHeight();
				delete.add(rules.get(i));
			}

		for (int i = 0 ; i < delete.size(); i ++){
			ruleCount--;
			ruleHolderPanel.remove(delete.get(i));
			rules.remove(delete.get(i));
		}

		ruleHolderPanel.revalidate();
		view.getModel().setModelFromPanel(this);
		ruleHolderPanel.setPreferredSize(new Dimension(
				(int) ruleHolderPanel.getPreferredSize().getWidth(),
				(int) (ruleHolderPanel.getComponentCount()  * ySize)));
		setFilterDescText();
		ruleHolderPanel.revalidate();
		this.revalidate();
		this.repaint();
		showSnakeHuh();
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

	/**
	 * Sets iterations to filter
	 * 
	 * @param iterations the users to filter
	 */
	public void setIterations(Iteration[] iterations){
		for (int i = 0 ; i < rules.size(); i ++){
			rules.get(i).setIterationValues(iterations);
		}
	}

	/**
	 * Sets users to filter
	 * 
	 * @param users the users to filter
	 */
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
	public List<RulePanel> getRules() {
		return rules;
	}

	/**
	 * Gets the view
	 * @return the view
	 */
	public FilterController getView() {
		return view;
	}

	public AbstractButton getSnakeButton() {
		return snakeButton;
	}
}
