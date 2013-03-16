package edu.wpi.cs.wpisuitetng.modules.RequirementsManager;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.KeyboardShortcut;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.ToolbarPanel;

public class RequirementsManager implements IJanewayModule {
	//initialize tabs
	private List<JanewayTabModel> tabs;
	
	//class constructor
	public RequirementsManager() {
		tabs = new ArrayList<JanewayTabModel>();
		
		//new main panel
		MainView mainView = new MainView();
		
		//new toolbar panel
		ToolbarPanel toolbarPanel = new ToolbarPanel();
	
		//create a tab model that contains toolbar and content panel
		JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarPanel, mainView);
		
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
