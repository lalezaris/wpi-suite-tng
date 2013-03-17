package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;

/**
 * Main View Panel class for our Requirements Manager
 * 
 * @author Tyler Stone
 *
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {
	
	public MainView() {
		// Simple outline, can be changed later
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		// Set up the split panels
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(200, this.getSize().height));
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.red, 2));
		this.add(leftPanel, BorderLayout.LINE_START);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		this.add(rightPanel, BorderLayout.LINE_END);
		
		TreeView treeView = new TreeView();
		leftPanel.add(treeView);
	}
}
