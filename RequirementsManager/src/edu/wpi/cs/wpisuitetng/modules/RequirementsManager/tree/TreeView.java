package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

public class TreeView extends JPanel {

	public TreeView(){
		//this.add(new JPanel(new BoxLayout(this, BoxLayout.Y_AXIS)));
		JLabel titleLabel = new JLabel("Requirements", JLabel.CENTER);
		
		titleLabel.setFont(new Font(this.getFont().getFontName(), Font.BOLD,16));
		
		this.add(titleLabel);
		
	}
}
