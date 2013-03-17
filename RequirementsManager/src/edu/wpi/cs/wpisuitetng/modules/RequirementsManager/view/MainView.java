package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

/**
 * Main View Panel class for our Requirements Manager
 * 
 * @author Tyler Stone
 *
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {
	
	public MainView() {
		//Basic stuff. We can add more later
		this.add(new JLabel("RM placeholder"));
		this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
	}
}
