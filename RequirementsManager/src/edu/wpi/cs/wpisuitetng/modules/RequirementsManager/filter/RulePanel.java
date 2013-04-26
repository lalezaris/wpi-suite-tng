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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.FilterTable;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.Rule;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleComparisonMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleEditableType;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RulesCombinor;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Panel for rules
 *
 * @author Chris Hanna
 *
 * @version Apr 14, 2013
 *
 */
@SuppressWarnings({"rawtypes", "serial"})
public class RulePanel extends JPanel{


	private JLabel title;
	private JComboBox<String> field;
	private JComboBox<RuleComparisonMode> compareMode;
	private JComboBox possibleValues;
	private JComboBox<RulesCombinor> andOrBox;
	private JTextField possibleValuesText;
	private JCheckBox enabledBox;
	private FilterPanel filterPanel;
	private RuleEditableType editType;
	protected boolean test = true;
	private boolean enabled = true;
	private GridBagConstraints constraint;


	private Iteration[] knownIterations;
	private User[] knownUsers;
	/**
	 * Create a blank panel for a rule.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public RulePanel(FilterPanel parent) {
		filterPanel = parent;
		enabledBox = new JCheckBox();
		title = new JLabel("SELECTED:   ");
		field = new JComboBox<String>();
		compareMode = new JComboBox<RuleComparisonMode>();
		possibleValues = new JComboBox();
		possibleValuesText = new JTextField(12);
		andOrBox = new JComboBox<RulesCombinor>();
		andOrBox.addItem(RulesCombinor.MUST);
		andOrBox.addItem(RulesCombinor.ANY);

		this.setLayout(new GridBagLayout());
		constraint = new GridBagConstraints();

		possibleValuesText.setPreferredSize(new Dimension(100, 100));

		possibleValuesText.validate();

		this.validate();

		DefaultListCellRenderer comboBoxRenderer = new DefaultListCellRenderer(){
			/**
			 * @see javax.swing.JComponent#paint(java.awt.Graphics)
			 */
			@Override
			public void paint(Graphics g) {
				setForeground(Color.BLACK);
				super.paint(g);
			}
		};

		field.setRenderer(comboBoxRenderer);
		compareMode.setRenderer(comboBoxRenderer);
		possibleValues.setRenderer(comboBoxRenderer);



		constraint.anchor = GridBagConstraints.FIRST_LINE_START;
		constraint.weightx = 0;

		this.add(enabledBox, constraint);
		//this.add(andOrBox); //TODO Maybe add this back in if we can get everything else working
		this.add(title, constraint);
		this.add(field, constraint);
		this.add(compareMode, constraint);
		this.add(possibleValues, constraint);

		setUp();
		setRuleEnabled(true);
		checkForNullRule();
	}

	/**
	 * Enable or disable the rule. The color of the components will change to let the user know
	 * 
	 * @param enabled
	 */
	protected void setRuleEnabled(boolean enabled){
		this.enabled = enabled;
		Color backColor = new Color(208, 255, 208);
		if (enabled){
			field.setEnabled(true);
			compareMode.setEnabled(true);
			possibleValues.setEnabled(true);
			possibleValuesText.setEnabled(true);
		} else{
			backColor = new Color(228, 208, 208);
			field.setEnabled(false);
			compareMode.setEnabled(false);
			possibleValues.setEnabled(false);
			possibleValuesText.setEnabled(false);
			possibleValuesText.setDisabledTextColor(Color.BLACK);
		}
		
//		this.enabledBox.setEnabled(true);
//		if (this.field.getSelectedItem().equals(" ")){
//			backColor = new Color(208, 208, 208);
//			this.enabledBox.setEnabled(false);
//		}

		for (int i = 0 ; i < this.getComponentCount(); i ++){
			this.getComponent(i).setBackground(backColor);
		}

		this.checkForNullRule();
		
	}

	/**
	 * Assigns the listeners to different components to make sure that auto-refresh is working
	 * 
	 */
	public void addListeners(){

		ItemListener l1 = new ItemListener(){

			/**
			 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
			 */
			@Override
			public void itemStateChanged(ItemEvent e) {

				test = false;
				updateCompareBox();
				updatePossibleValues();
				test = true;
				filterPanel.triggerTableUpdate();

			}	
		};

		ItemListener l2 = new ItemListener(){

			/**
			 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
			 */
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (test){
					filterPanel.triggerTableUpdate();
				}
			}

		};

		KeyListener k = new KeyAdapter(){

			/**
			 * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyTyped(KeyEvent e) {
				if (test){
					filterPanel.triggerTableUpdate();
				}
			}


		};


		field.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				checkForNullRule();
			}
			
		});

		andOrBox.addItemListener(l2);
		field.addItemListener(l1);
		compareMode.addItemListener(l2);
		possibleValues.addItemListener(l2);
		possibleValuesText.addKeyListener(k);
	}

	/**
	 * Set up the rule panel
	 * 
	 */
	public void setUp(){
		String[] fieldNames = getPossibleFields();
		for (int i = 0 ; i < fieldNames.length; i++)
			field.addItem(fieldNames[i]);

		updateCompareBox();
		updatePossibleValues();
	}

	/**
	 * Change the values component so that it reflects correctly what the user can filter
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void updatePossibleValues(){

		int possibleValuesIndex = -1, possibleValuesTextIndex = -1;
		for (int i = 0 ; i < this.getComponentCount(); i ++){
			if (this.getComponent(i).equals(possibleValues)){
				possibleValuesIndex = i;
			}
			if (this.getComponent(i).equals(possibleValuesText)){
				possibleValuesTextIndex = i;
			}
		}

		if (editType == RuleEditableType.ENUM){
			possibleValues.removeAllItems();
			Enum[] all = FilterTable.getInstance().getEnumFromString((String)field.getSelectedItem());
			for (int i = 0 ; i < all.length ; i ++)
				possibleValues.addItem(all[i]);

			if (possibleValuesTextIndex!=-1)
				this.remove(possibleValuesText);
			if (possibleValuesIndex == -1)
				this.add(possibleValues, constraint);

		}else if (editType == RuleEditableType.ITERATION){
			possibleValues.removeAllItems();
			Iteration[] all = knownIterations;

			possibleValues.addItem(Iteration.getBacklog());
			if (all!=null)
				for (int i = 0 ; i < all.length ; i ++)
					possibleValues.addItem(all[i]);

			if (possibleValuesTextIndex!=-1)
				this.remove(possibleValuesText);
			if (possibleValuesIndex == -1)
				this.add(possibleValues, constraint);
		} else if (editType == RuleEditableType.USER){
			possibleValues.removeAllItems();
			User[] all = knownUsers;

			possibleValues.addItem("No One  ");
			if (all!=null){
				String[] allString = new String[all.length];
				for (int i = 0 ; i < allString.length ; i ++)
					allString[i] = all[i].getUsername();
				for (int i = 0 ; i < all.length ; i ++)
					possibleValues.addItem(allString[i]);
			}

			if (possibleValuesTextIndex!=-1)
				this.remove(possibleValuesText);
			if (possibleValuesIndex == -1)
				this.add(possibleValues, constraint);
		}
		else if (editType == RuleEditableType.NUMBER){
			possibleValuesText.setText("0");

			removeAllListeners();
			possibleValuesText.addKeyListener(new KeyAdapter(){
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
						e.consume();  // ignore event
					}
				}
			});
			//}
			if (possibleValuesTextIndex ==-1)
				this.add(possibleValuesText, constraint);
			if (possibleValuesIndex != -1)
				this.remove(possibleValues);
		} else if (editType == RuleEditableType.STRING){
			removeAllListeners();
			possibleValuesText.setText("");

			if (possibleValuesTextIndex ==-1)
				this.add(possibleValuesText, constraint);
			if (possibleValuesIndex != -1)
				this.remove(possibleValues);
		}

		if (possibleValuesTextIndex != -1){
			possibleValuesText.setVisible(true);
			if (editType == RuleEditableType.ALL)
				possibleValuesText.setVisible(false);
		}
		if (possibleValuesIndex != -1){
			possibleValues.setVisible(true);
			if (editType == RuleEditableType.ALL)
				possibleValues.setVisible(false);
		}

		this.repaint();
		this.setAlignmentY(Component.LEFT_ALIGNMENT);
	}

	@SuppressWarnings("unchecked")
	public void setUserValues(User[] users){
		test = false;
		if (editType == RuleEditableType.USER){
			Object sel = possibleValues.getSelectedItem();
			possibleValues.removeAllItems();
			User[] all = users;
			String[] allString = new String[all.length];
			for (int i = 0 ; i < allString.length ; i ++)
				allString[i] = all[i].getUsername();
			possibleValues.addItem("No One  ");
			for (int i = 0 ; i < all.length ; i ++)
				possibleValues.addItem(allString[i]);
			if (sel!=null)
				possibleValues.setSelectedItem(sel);

			knownUsers = all;
		}
		test = true;
	}

	@SuppressWarnings("unchecked")
	public void setIterationValues(Iteration[] iterations){
		test = false;
		if (editType == RuleEditableType.ITERATION){

			Object sel = possibleValues.getSelectedItem();
			possibleValues.removeAllItems();
			Iteration[] all = iterations;
			possibleValues.addItem(Iteration.getBacklog());
			for (int i = 0 ; i < all.length ; i ++)
				possibleValues.addItem(all[i]);
			if (sel!=null)
				possibleValues.setSelectedItem(sel);
			knownIterations = all;
		}
		test = true;
	}

	/**
	 * Reset the listeners of the possible values text component
	 * 
	 */
	private void removeAllListeners(){
		this.remove(possibleValuesText);
		possibleValuesText = new JTextField(12);
		this.add(possibleValuesText, constraint);
		possibleValuesText.addKeyListener(new KeyAdapter(){
			/**
			 * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyTyped(KeyEvent e) {
				if (test){
					filterPanel.triggerTableUpdate();
				}
			}
		});
	}

	/**
	 * Color the panel correctly if it has not been assigned a value.
	 * 
	 */
	protected void checkForNullRule(){
		
		Color backColor = Color.white;
		System.out.println("checking for null rule");
		if (this.enabled)
			backColor = new Color(208,255,208);
		else backColor = new Color(255,208,208);
		
		this.enabledBox.setEnabled(true);
		if (this.field.getSelectedItem().equals(" ")){
			backColor = new Color(208, 208, 208);	
			System.out.println("grey");
			test = false;
			this.enabledBox.setEnabled(false);
			this.enabledBox.setSelected(false);
			test = true;
		}
		
		for (int i = 0 ; i < this.getComponentCount(); i ++){
			this.getComponent(i).setBackground(backColor);
		}
	}
	
	
	/**
	 * Change the values of the comparison mode box so that is reflects correctly how the user can compare things
	 * 
	 */
	public void updateCompareBox(){
		compareMode.setVisible(true);
		compareMode.removeAllItems();
		RuleComparisonMode[] modes = getValidComparisonModes();
		for (int i = 0 ; i < modes.length; i++)
			compareMode.addItem(modes[i]);

		compareMode.setPreferredSize(new Dimension(300,(int)compareMode.getPreferredSize().getHeight()));
		if (editType == RuleEditableType.ALL)
			compareMode.setVisible(false);

		this.setAlignmentY(Component.LEFT_ALIGNMENT);
	}

	/**
	 * Get the valid comparison modes depending on what field is being filtered
	 * 
	 * @return the rule comparison mode
	 */
	public RuleComparisonMode[] getValidComparisonModes(){
		RuleComparisonMode[] output = null;

		String fieldName = (String)field.getItemAt(field.getSelectedIndex());
		RuleEditableType fieldType = null;
		for (int i = 0 ; i < FilterTable.getRequirementTargets().length; i++){
			if (fieldName.equals(FilterTable.getRequirementTargets()[i])){
				fieldType = FilterTable.getRequirementTargetTypes()[i];
			}
		}

		if (fieldType == RuleEditableType.STRING){
			RuleComparisonMode[] all = {
					RuleComparisonMode.CONTAINS,
					RuleComparisonMode.EQUALS,
					RuleComparisonMode.NOTEQUALS};
			output = all;
		} else if (fieldType == RuleEditableType.NUMBER){
			RuleComparisonMode[] all = {
					RuleComparisonMode.GREATER,
					RuleComparisonMode.EQUALSGREATER,
					RuleComparisonMode.LESS,
					RuleComparisonMode.EQUALSLESS,
					RuleComparisonMode.EQUALS,
					RuleComparisonMode.NOTEQUALS};
			output = all;
		} else if (fieldType == RuleEditableType.ENUM){
			RuleComparisonMode[] all = {
					RuleComparisonMode.EQUALS,
					RuleComparisonMode.NOTEQUALS};
			output = all;
		} else if (fieldType == RuleEditableType.ALL){
			RuleComparisonMode[] all = {
					RuleComparisonMode.EQUALS};
			output = all;
		} else if (fieldType == RuleEditableType.ITERATION){
			RuleComparisonMode[] all = {
					RuleComparisonMode.EQUALS,
					RuleComparisonMode.NOTEQUALS};
			output = all;
		}else if (fieldType == RuleEditableType.USER){
			RuleComparisonMode[] all = {
					RuleComparisonMode.ASSIGNEDTO,
					RuleComparisonMode.NOTASSIGNEDTO};
			output = all;
		}		
		editType = fieldType;


		return output;
	}

	/**
	 * Get the valid fields to filter by
	 * 
	 * @return the field array to filter
	 */
	private String[] getPossibleFields(){

		String[] removeFields = null;

		if (filterPanel != null)
			removeFields = filterPanel.getRemoveFields();

		String[] allFields = FilterTable.getRequirementTargets();
		List<String> validFields = new ArrayList<String>();
		for (int i = 0 ; i < allFields.length; i ++)
			validFields.add(allFields[i]);
		if (removeFields!=null)
			for (int i = 0 ; i < removeFields.length; i ++)
				if (validFields.contains(removeFields[i]))
					validFields.remove(removeFields[i]);

		String[] validFieldArray = new String[validFields.size()];
		for (int i = 0 ; i < validFields.size(); i ++)
			validFieldArray[i] = validFields.get(i);

		return validFieldArray;
	}



	/**
	 * Create a new rule depending on what is shown in the panel
	 * 
	 */
	public Rule extractRule(){
		Rule r = null;
		if (editType == RuleEditableType.ALL)
			return null;
		else if (editType == RuleEditableType.ENUM){
			r = new Rule((Enum)possibleValues.getSelectedItem(),
					(RuleComparisonMode)compareMode.getSelectedItem(),
					(String)field.getSelectedItem());
		} else if (editType == RuleEditableType.ITERATION){
			r = new Rule(((Iteration)possibleValues.getSelectedItem()).getId(),
					(RuleComparisonMode)compareMode.getSelectedItem(),
					(String)field.getSelectedItem());
		} else if (editType == RuleEditableType.USER){

			String userName = "";
			if (possibleValues.getSelectedItem() instanceof User)
				userName = ((User)possibleValues.getSelectedItem()).getUsername();
			else userName = (String)possibleValues.getSelectedItem();

			if (userName.equals("No One  "))
				userName = "";

			r = new Rule(userName,
					(RuleComparisonMode)compareMode.getSelectedItem(),
					(String)field.getSelectedItem());
		}
		else if (editType == RuleEditableType.NUMBER) {
			int number = 0;
			boolean noErrors = true;
			try {
				number = Integer.parseInt(possibleValuesText.getText());
			} catch (NumberFormatException e){

				noErrors = false;
			}
			if (noErrors){
				r = new Rule((Comparable)number,
						(RuleComparisonMode)compareMode.getSelectedItem(),
						(String)field.getSelectedItem());
			} else{
				r = new Rule((Comparable)0,
						(RuleComparisonMode)compareMode.getSelectedItem(),
						(String)field.getSelectedItem());
			}
		} else{
			r = new Rule((Comparable)possibleValuesText.getText(),
					(RuleComparisonMode)compareMode.getSelectedItem(),
					(String)field.getSelectedItem());
		}
		boolean isAnd = ((RulesCombinor)andOrBox.getSelectedItem()) == RulesCombinor.MUST;
		r.setIsAnd( isAnd);

		r.setEnabled(enabled);

		return r;
	}


	/**
	 * Gets the enabledBox
	 * @return the enabledBox
	 */
	public JCheckBox getEnabledBox() {
		return enabledBox;
	}

	/**
	 * Gets the enabled
	 * @return the enabled
	 */
	public boolean getIsEnabled() {
		return enabled;
	}

	/**
	 * Gets the field
	 * @return the field
	 */
	public JComboBox<String> getField() {
		return field;
	}

	/**
	 * Gets the compareMode
	 * @return the compareMode
	 */
	public JComboBox<RuleComparisonMode> getCompareMode() {
		return compareMode;
	}

	/**
	 * Gets the possibleValues
	 * @return the possibleValues
	 */
	public JComboBox getPossibleValues() {
		return possibleValues;
	}

	/**
	 * Gets the possibleValuesText
	 * @return the possibleValuesText
	 */
	public JTextField getPossibleValuesText() {
		return possibleValuesText;
	}
}
