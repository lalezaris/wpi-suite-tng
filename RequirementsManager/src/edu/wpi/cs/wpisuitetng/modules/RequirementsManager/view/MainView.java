package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

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
		JSplitPane splitPane;
		
		// Simple outline, can be changed later
		this.setLayout(new BorderLayout());
		
		// Set up the split panels
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(200, this.getSize().height));
		
		// Add requirements tree to the left panel
		TreeView treeView = new TreeView();
		leftPanel.add(treeView);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(mainTabView);		
		
		//new split pane to adjust size
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

		//add split pane to main view
		this.add(splitPane, BorderLayout.CENTER);
	}
}
