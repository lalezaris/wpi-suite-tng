package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

public class GamePanel extends JPanel{

	
	SnakePanel playPanel;
	Controller controller;
	Snake snake;
	
	JLabel score;
	

	
	public GamePanel() {
		Dimension size = new Dimension(500,500);
		this.setPreferredSize(new Dimension(500,500));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		playPanel = new SnakePanel();
		playPanel.setPreferredSize(new Dimension(size.width-20, size.height-80));
		playPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		c.insets = new Insets(10,10,0,0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weighty = 0;
		c.weightx = .5;
		this.add(playPanel,c);
		
		
		score = new JLabel("Score");
		Font scoreFont = new Font("Serif", Font.PLAIN, 25);
		score.setFont(scoreFont);
		c.weighty = 1;
		c.gridy = 1;
		this.add(score, c);
		
		
		snake = new Snake(new Spot(5,5));
		
		this.controller = new Controller(playPanel, snake, this);
		this.snake.setController(controller);
	}
	
	
	Tab containingTab;
	/**
	 * Sets the tab.
	 *
	 * @param tab tab to set
	 */
	public void setTab(Tab tab)
	{
		containingTab = tab;
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("Snake");
		containingTab.setToolTipText("play an awesome game of snake");
	}

}
