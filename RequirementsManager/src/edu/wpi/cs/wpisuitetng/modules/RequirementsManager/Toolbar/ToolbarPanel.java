package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Toolbar Panel for Requirements Manager
 * 
 * @author Tyler Stone
 */
public class ToolbarPanel extends JPanel {
	
	public ToolbarPanel() {
		//Basic stuff. We can add more later
		this.add(new JLabel("RM toolbar placeholder")); //label with placeholder text
		this.setBorder(BorderFactory.createLineBorder(Color.blue, 2)); //add a border
	}
}
