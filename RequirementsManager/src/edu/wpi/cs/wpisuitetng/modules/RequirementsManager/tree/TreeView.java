package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeView extends JPanel {

	public TreeView(){
		this.setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel("<html><bold>Requirements</bold></html>", JLabel.CENTER);
		this.add(titleLabel, BorderLayout.PAGE_START);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("ReqPlaceHolder1");
		top.add(new DefaultMutableTreeNode("ChildReqPlaceHolder1"));
		top.add(new DefaultMutableTreeNode("ChildReqPlaceHolder2"));

		JTree tree = new JTree(top);
		this.add(tree, BorderLayout.CENTER);
		
		
	}
}
