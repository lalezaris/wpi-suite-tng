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
	
	JPanel scorePanel;
	JLabel score, scoreBody;
	JLabel highScore, highScoreBody;

	
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
		
		scorePanel = new JPanel();
		scorePanel.setLayout(new GridBagLayout());
		scorePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		Font titleFont = new Font("Serif", Font.PLAIN, 15);
		Font bodyFont = new Font("Serif", Font.PLAIN, 25);
		score = new JLabel("SCORE:");
		score.setFont(titleFont);
		c.weighty = 1;
		c.gridy = 1;
		c.gridx = 0;
		scorePanel.add(score, c);
		
		highScore = new JLabel("HIGHSCORE:");
		highScore.setFont(titleFont);
		c.gridy = 1;
		c.gridx = 1;
		scorePanel.add(highScore, c);
		
		scoreBody = new JLabel("filler");
		scoreBody.setFont(bodyFont);
		c.gridy = 2;
		c.gridx = 0;
		scorePanel.add(scoreBody,c);
		
		highScoreBody = new JLabel("Unknown");
		highScoreBody.setFont(bodyFont);
		c.gridx = 1;
		scorePanel.add(highScoreBody,c);
		
		c.gridy = 1;
		c.gridx = 0;
		scorePanel.setPreferredSize(new Dimension(480, (int)scorePanel.getPreferredSize().getHeight()));
		this.add(scorePanel,c);
		
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
