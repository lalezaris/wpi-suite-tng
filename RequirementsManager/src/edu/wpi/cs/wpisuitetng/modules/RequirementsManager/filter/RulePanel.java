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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
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
 * ITS A DESC. (ription). What is a ription? 
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
	private boolean test = true;
	private boolean enabled = true;
	private GridBagConstraints constraint;
	
	
	private Iteration[] knownIterations;
	private User[] knownUsers;
	/**
	 * create a blank panel for a rule.
	 * 
	 */
	public RulePanel(FilterPanel parent) {
		this.filterPanel = parent;
		enabledBox = new JCheckBox();
		title = new JLabel("Rule: ");
		field = new JComboBox<String>();
		compareMode = new JComboBox<RuleComparisonMode>();
		possibleValues = new JComboBox();
		possibleValuesText = new JTextField(12);
		andOrBox = new JComboBox<RulesCombinor>();
		andOrBox.addItem(RulesCombinor.MUST);
		andOrBox.addItem(RulesCombinor.ANY);
		
		this.setLayout(new GridBagLayout());
		constraint = new GridBagConstraints();


		

		compareMode.setMaximumSize(new Dimension(100, field.getSize().height));

		field.setMaximumSize(new Dimension(100, field.getSize().height));

		possibleValues.setMaximumSize(new Dimension(100, field.getSize().height));

		possibleValuesText.setSize(200, possibleValues.getHeight());
		possibleValuesText.setMinimumSize(new Dimension((int)possibleValuesText.getPreferredSize().getWidth(), possibleValues.getHeight()));

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
	}

	/**
	 * enable or disable the rule. The color of the components will change to let the user know
	 * 
	 * @param enabled
	 */
	protected void setRuleEnabled(boolean enabled){
		this.enabled = enabled;
		Color backColor = new Color(208, 255, 208);
		if (enabled){
			
		} else{
			backColor = new Color(255, 208, 208);
		}
		
		for (int i = 0 ; i < this.getComponentCount(); i ++){
			this.getComponent(i).setBackground(backColor);
		}
		
	}
	
	/**
	 * Assigns the listeners to different components to make sure that auto-refresh is working
	 * 
	 */
	protected void addListeners(){
		
		final RulePanel rp = this;
		ItemListener l1 = new ItemListener(){

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

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (test){
					filterPanel.triggerTableUpdate();
				}
			}
			
		};
		
		KeyListener k = new KeyAdapter(){

			@Override
			public void keyTyped(KeyEvent e) {
				if (test){
					filterPanel.triggerTableUpdate();
				}
			}

			
		};
		ChangeListener c = new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				if (test){
					filterPanel.triggerTableUpdate();
				}
			}
			
		};
		

		andOrBox.addItemListener(l2);
		field.addItemListener(l1);
		compareMode.addItemListener(l2);
		possibleValues.addItemListener(l2);
		possibleValuesText.addKeyListener(k);
		enabledBox.addChangeListener(c);
	}
	
	/**
	 * set up the rule panel
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
	private void updatePossibleValues(){

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
			//Enum[] all = FilterTable.getInstance().getEnumFromString((String)field.getSelectedItem());
			Iteration[] all = knownIterations;//this.filterPanel.getView().getModel().getIterations();
			
			possibleValues.addItem(Iteration.getBacklog());
			if (all!=null)
				for (int i = 0 ; i < all.length ; i ++)
					possibleValues.addItem(all[i]);
			System.out.println("Updating Iteations");
			
			if (possibleValuesTextIndex!=-1)
				this.remove(possibleValuesText);
			if (possibleValuesIndex == -1)
				this.add(possibleValues, constraint);
		} else if (editType == RuleEditableType.USER){
			possibleValues.removeAllItems();
			//Enum[] all = FilterTable.getInstance().getEnumFromString((String)field.getSelectedItem());
			User[] all = knownUsers;//this.filterPanel.getView().getModel().getIterations();
			
			//possibleValues.addItem(Iteration.getBacklog());
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
			

				System.out.println("Adding numEnforcer!");
				
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
		
		this.repaint();
		this.setAlignmentY(Component.LEFT_ALIGNMENT);
	}
	
	public void setUserValues(User[] users){
		test = false;
		if (editType == RuleEditableType.USER){
			Object sel = possibleValues.getSelectedItem();
			possibleValues.removeAllItems();
			//Enum[] all = FilterTable.getInstance().getEnumFromString((String)field.getSelectedItem());
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
	
	public void setIterationValues(Iteration[] iterations){
		test = false;
		if (editType == RuleEditableType.ITERATION){
			
			Object sel = possibleValues.getSelectedItem();
			possibleValues.removeAllItems();
			//Enum[] all = FilterTable.getInstance().getEnumFromString((String)field.getSelectedItem());
			Iteration[] all = iterations;
			possibleValues.addItem(Iteration.getBacklog());
			for (int i = 0 ; i < all.length ; i ++)
				possibleValues.addItem(all[i]);
			if (sel!=null)
				possibleValues.setSelectedItem(sel);
			System.out.println("Updating Iteations in function");
			knownIterations = all;
//			if (possibleValuesTextIndex!=-1)
//				this.remove(possibleValuesText);
//			if (possibleValuesIndex == -1)
//				this.add(possibleValues, constraint);
		}
		test = true;
	}
	
	/**
	 * reset the listeners of the possible values text component
	 * 
	 */
	private void removeAllListeners(){
		this.remove(possibleValuesText);
		possibleValuesText = new JTextField(12);
		this.add(possibleValuesText, constraint);
		possibleValuesText.addKeyListener(new KeyAdapter(){
			@Override
			public void keyTyped(KeyEvent e) {
				if (test){
					filterPanel.triggerTableUpdate();
				}
			}
		});
	}
	
	/**
	 * Change the values of the comparison mode box so that is reflects correctly how the user can compare things
	 * 
	 */
	private void updateCompareBox(){

		
		compareMode.removeAllItems();
		RuleComparisonMode[] modes = getValidComparisonModes();
		for (int i = 0 ; i < modes.length; i++)
			compareMode.addItem(modes[i]);
		compareMode.setPreferredSize(new Dimension(300,(int)compareMode.getPreferredSize().getHeight()));
		this.setAlignmentY(Component.LEFT_ALIGNMENT);
	}

	/**
	 * Get the valid comparison modes depending on what field is being filtered
	 * 
	 * @return
	 */
	private RuleComparisonMode[] getValidComparisonModes(){
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
		
		this.editType = fieldType;


		return output;
	}

	/**
	 * Get the valid fields to filter by
	 * 
	 * @return
	 */
	private String[] getPossibleFields(){

		String[] removeFields = null;
		
		if (this.filterPanel != null)
			removeFields = this.filterPanel.getRemoveFields();
		
		String[] allFields = FilterTable.getRequirementTargets();
		ArrayList<String> validFields = new ArrayList<String>();
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
	 * create a new rule depending on what is shown in the panel
	 * 
	 * @return
	 */
	public Rule extractRule(){
		Rule r = null;
		if (editType == RuleEditableType.ENUM){
			r = new Rule((Enum)possibleValues.getSelectedItem(),
					(RuleComparisonMode)compareMode.getSelectedItem(),
					(String)field.getSelectedItem());
		} else if (editType == RuleEditableType.ITERATION){
			r = new Rule(((Iteration)possibleValues.getSelectedItem()).getId(),
					(RuleComparisonMode)compareMode.getSelectedItem(),
					(String)field.getSelectedItem());
		} else if (editType == RuleEditableType.USER){
			
			//uhm..... 
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
		System.out.println("isAnd = " + isAnd);
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





}
