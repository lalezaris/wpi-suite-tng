package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;

/**
 * Main View Panel class for our Requirements Manager
 * 
 * @author Tyler Stone
 *
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {
	public final MainTabController mainTabController;
	
	public MainView() {
		MainTabView mainTabView = new MainTabView();
		mainTabController = new MainTabController(mainTabView);
		
		// Simple outline, can be changed later
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		// Set up the split panels
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(200, this.getSize().height));
		this.add(leftPanel, BorderLayout.LINE_START);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		this.add(rightPanel, BorderLayout.CENTER);
		rightPanel.add(mainTabView);
		
		TreeView treeView = new TreeView();
		leftPanel.add(treeView);
	}
}
