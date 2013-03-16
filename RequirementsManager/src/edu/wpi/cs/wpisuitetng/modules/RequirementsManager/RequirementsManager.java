package edu.wpi.cs.wpisuitetng.modules.RequirementsManager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import javax.swing.KeyStroke;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.KeyboardShortcut;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

public class RequirementsManager implements IJanewayModule {
	//initialize tabs
	private List<JanewayTabModel> tabs;
	
	//class constructor
	public RequirementsManager() {
		tabs = new ArrayList<JanewayTabModel>();
		
		/**
		 * create a JPanel to hold the toolbar for the tab
		 * 
		 * adapted from PostBoard tutorial by Tyler Stone
		 */
		JPanel toolbarPanel = new JPanel();
		toolbarPanel.add(new JLabel("RM toolbar placeholder")); //label with placeholder text
		toolbarPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2)); //add a border
		//JPanel to hold contents of the tab
		JPanel mainPanel = new JPanel();
		mainPanel.add(new JLabel("RM placeholder"));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
	
		//create a tab model that contains toolbar and content panel
		JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarPanel, mainPanel);
		
		//add to the list of tabs
		tabs.add(tab1);
	}
	
	//interface-specific function
	@Override
	public String getName() {
		return "Requirements Manager";
	}
	
	//interface-specific function
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}
